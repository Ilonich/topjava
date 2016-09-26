package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealRowMapper;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final MealRowMapper MEAL_ROW_MAPPER = new MealRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource) {
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Objects.requireNonNull(meal);
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("userId", userId)
                .addValue("dateTime", TimeUtil.fromLocalDateTime(meal.getDateTime()))
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());
        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else {
            return namedParameterJdbcTemplate.update(
                    "UPDATE meals SET date_time=:dateTime, description=:description, " +
                            "calories=:calories WHERE id=:id AND user_id=:userId", map) == 0 ? null : meal;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", MEAL_ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC", MEAL_ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("startDate", TimeUtil.fromLocalDateTime(startDate))
                .addValue("endDate", TimeUtil.fromLocalDateTime(endDate));
        return namedParameterJdbcTemplate.query("SELECT * FROM meals WHERE user_id=:userId AND date_time BETWEEN :startDate AND :endDate ORDER BY date_time DESC", map, MEAL_ROW_MAPPER);
    }
}
