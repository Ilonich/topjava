package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by Никола on 17.10.2016.
 */
@Controller
public class MealController {
    private static final Logger LOG = LoggerFactory.getLogger(MealController.class);

    @Autowired
    private MealService service;

    @RequestMapping(value = "/meals", method = RequestMethod.GET, params = "!action")
    public String getMeals(Model model) {
        LOG.info("getAll for User {}", AuthorizedUser.id());
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET, params = "action=delete")
    public String deleteMeal(@RequestParam("id") int id) {
        LOG.info("delete meal {} for User {}", id, AuthorizedUser.id());
        service.delete(id, AuthorizedUser.id());
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET, params = "action=create")
    public String createMeal(Model model) {
        model.addAttribute("meal", new Meal(0, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000));
        return "forward:meal";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET, params = "action=update")
    public String updateMeal(Model model, @RequestParam("id") int id) {
        model.addAttribute("meal", service.get(id, AuthorizedUser.id()));
        return "forward:meal";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST, params = "action=filter")
    public String getFilteredMeals(@RequestParam("startDate") String startDateR,
                                   @RequestParam("endDate") String endDateR,
                                   @RequestParam("startTime") String startTimeR,
                                   @RequestParam("endTime") String endTimeR, Model model) {
        LocalDate startDate = TimeUtil.parseLocalDate(startDateR);
        LocalDate endDate = TimeUtil.parseLocalDate(endDateR);
        LocalTime startTime = TimeUtil.parseLocalTime(startTimeR);
        LocalTime endTime = TimeUtil.parseLocalTime(endTimeR);
        LOG.info("getBetween dates {} - {} for time {} - {} for User {}", startDate, endDate, startTime, endTime, AuthorizedUser.id());
        model.addAttribute("meals", MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(startDate != null ? startDate : TimeUtil.MIN_DATE,
                        endDate != null ? endDate : TimeUtil.MAX_DATE,
                        AuthorizedUser.id()),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        ));
        return "meals";
    }


    @RequestMapping(value = "/meals", method = RequestMethod.POST, params = "!action")
    public String saveUpdatedMeal(@RequestParam("id") int id,
                                  @RequestParam("description") String desc,
                                  @RequestParam("calories") int calories,
                                  @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime source) {
        Meal meal = new Meal(id, source, desc, calories);
        LOG.info("update {} for User {}", meal, AuthorizedUser.id());
        service.update(meal, AuthorizedUser.id());
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST, params = {"!action", "id=0"})
    public String saveMeal(@RequestParam("description") String desc,
                           @RequestParam("calories") int calories,
                           @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime source) {
        Meal meal = new Meal(null, source, desc, calories);
        LOG.info("create {} for User {}", meal, AuthorizedUser.id());
        service.save(meal, AuthorizedUser.id());
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meal", method = RequestMethod.GET)
    public String processCreatingOrEditing() {
        return "meal";
    }
}
