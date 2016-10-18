package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * User: gkislin
 * Date: 26.08.2014
 */
@Transactional(readOnly = true)
@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final ResultSetExtractor<List<User>> ROW_MAPPER = new ResultSetExtractor<List<User>>() {
        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, User> map = new LinkedHashMap<>(16, 2.0f);
            User user = null;
            while (rs.next()) {
                Integer id = rs.getInt("id");
                user = map.get(id);
                if (user == null) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    Date registered = rs.getDate("registered");
                    int caloriesPerDay = rs.getInt("calories_per_day");
                    boolean enabled = rs.getBoolean("enabled");
                    Set<Role> roles = EnumSet.of(Role.valueOf(rs.getString("role")));
                    user = new User(id, name, email, password, caloriesPerDay, enabled, registered, roles);
                    map.put(id, user);
                } else {
                    user.addRole(Role.valueOf(rs.getString("role")));
                }
            }
            return new ArrayList<>(map.values());
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("id");
    }
    @Transactional
    @Override
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
            deleteRoles(user);
            insertRoles(user);
        }
        return user;
    }
    @Transactional
    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT id, name, calories_per_day, email, enabled, password, registered, role " +
                "FROM users LEFT JOIN user_roles\n" +
                "  ON users.id = user_roles.user_id WHERE users.id = ?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT id, name, calories_per_day, email, enabled, password, registered, role " +
                "FROM users LEFT JOIN user_roles" +
                " ON users.id = user_roles.user_id WHERE users.email = ?", ROW_MAPPER, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT id, name, calories_per_day, email, enabled, password, registered, role " +
                "FROM users LEFT JOIN user_roles " +
                "ON users.id = user_roles.user_id ORDER BY name, email", ROW_MAPPER);
    }

    private void deleteRoles(User u){
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id = ?", u.getId());
    }

    private void insertRoles(User u){
        Set<Role> roleSet = u.getRoles();
        Iterator<Role> roles = roleSet.iterator();
        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, u.getId());
                ps.setString(2, roles.next().name());
            }
            @Override
            public int getBatchSize() {
                return roleSet.size();
            }
        });
    }
}
