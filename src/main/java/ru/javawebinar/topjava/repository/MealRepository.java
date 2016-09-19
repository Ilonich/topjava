package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(Meal Meal);

    boolean delete(int id, int ownerId);

    Meal get(int id, int ownerId);

    Collection<Meal> getAll(int ownerId);

    Collection<Meal> getFiltered(int ownerID, LocalDate startDate, LocalDate endDate);
}
