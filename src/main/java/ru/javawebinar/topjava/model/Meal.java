package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class Meal extends BaseEntity {

    private final Integer ownerId;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(Integer ownerId, LocalDateTime dateTime, String description, int calories) {
        this(null, ownerId, dateTime, description, calories);
    }

    public Meal(Integer id, Integer ownerId, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.ownerId = ownerId;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }

    public Integer getOwnerId() {
        return ownerId;
    }
}
