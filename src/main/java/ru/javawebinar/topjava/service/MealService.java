package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {

    Meal save(Meal meal);

    void delete(int id, int ownerId) throws NotFoundException;

    Meal get(int id, int ownerId) throws NotFoundException;

    Collection<Meal> getAll(int ownerId);

    Collection<Meal> getFiltered(int ownerID, LocalDate startDate, LocalDate endDate);

    void update(Meal meal);
}
