package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int ownerId) throws NotFoundException {
        ExceptionUtil.checkNotFound(repository.delete(id, ownerId), "Meal id = " + id + ", for user id = " +ownerId + " not exist");
    }

    @Override
    public Meal get(int id, int ownerId) throws NotFoundException {
        return ExceptionUtil.checkNotFound(repository.get(id, ownerId), "Meal id = " + id + ", for user id = " +ownerId + " not exist");
    }

    @Override
    public Collection<Meal> getAll(int ownerId) {
        return repository.getAll(ownerId);
    }

    @Override
    public Collection<Meal> getFiltered(int ownerID, LocalDate startDate, LocalDate endDate) {
        return repository.getFiltered(ownerID, startDate, endDate);
    }

    @Override
    public void update(Meal meal) {
        repository.save(meal);
    }
}
