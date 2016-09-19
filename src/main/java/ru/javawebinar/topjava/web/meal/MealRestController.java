package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController extends AbstractMealController {

    public void delete(int id) throws NotFoundException {
        super.delete(id, AuthorizedUser.getId());
    }

    public Meal get(int id) throws NotFoundException{
        return super.get(id, AuthorizedUser.getId());
    }

    public Collection<MealWithExceed> getAll(){
        return super.getAll(AuthorizedUser.getId());
    }

    public Collection<MealWithExceed> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        return super.getFiltered(AuthorizedUser.getId(), startDate, endDate, startTime, endTime);
    }
}
