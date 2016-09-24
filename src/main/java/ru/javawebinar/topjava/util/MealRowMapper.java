package ru.javawebinar.topjava.util;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Meal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Никола on 24.09.2016.
 */
public class MealRowMapper implements RowMapper<Meal> {
    @Override
    public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {
        Meal meal = new Meal();
        meal.setId(rs.getInt(1));
        meal.setDateTime(TimeUtil.fromSQLTimestamp(rs.getTimestamp(3)));
        meal.setDescription(rs.getString(4));
        meal.setCalories(rs.getInt(5));
        return meal;
    }
}
