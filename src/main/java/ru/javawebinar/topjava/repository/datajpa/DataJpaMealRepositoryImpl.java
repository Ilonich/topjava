package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudMealRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()){
            meal.setUser(crudUserRepository.getOne(userId));
            return crudMealRepository.save(meal);
        } else {
            return crudMealRepository.update(meal.getDescription(),
                    meal.getCalories(),
                    meal.getDateTime(),
                    meal.getId(), userId) !=0 ? meal : null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.deleteByIdAndUser_Id(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudMealRepository.findByIdAndUser_Id(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.findByUser_IdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudMealRepository.findByUser_IdAndDateTimeIsBetweenOrderByDateTimeDesc(userId, startDate, endDate);
    }
}
