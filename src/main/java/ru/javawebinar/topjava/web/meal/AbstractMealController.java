package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Никола on 18.09.2016.
 */
public class AbstractMealController {

    private static final Logger LOG = getLogger(AbstractMealController.class);

    @Autowired
    private MealService service;

    public Meal save(Meal meal){
        return service.save(meal);
    }

    public void delete(int id, int ownerId) throws NotFoundException{
        service.delete(id, ownerId);
    }

    public Meal get(int id, int ownerId) throws NotFoundException{
        return service.get(id, ownerId);
    }

    public Collection<MealWithExceed> getAll(int ownerId){
        return MealsUtil.getWithExceeded(service.getAll(ownerId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public void update(Meal meal){
        service.update(meal);
    }

    Collection<MealWithExceed> getFiltered(int ownerId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        return MealsUtil.getFilteredWithExceededTime(service.getFiltered(ownerId, startDate, endDate),
                startTime,
                endTime,
                MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}
