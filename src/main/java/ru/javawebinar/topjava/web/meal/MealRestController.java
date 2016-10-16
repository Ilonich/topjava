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
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    @RequestMapping(value = "/meals", method = RequestMethod.GET, params = "!action")
    public String getMeals(Model model){
        LOG.info("getAll for User {}", AuthorizedUser.id());
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET, params = "action=delete")
    public String deleteMeal(@RequestParam("id") int id){
        LOG.info("delete meal {} for User {}", id, AuthorizedUser.id());
        service.delete(id, AuthorizedUser.id());
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET, params = "action=create")
    public String createMeal(Model model){
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000));
        return "forward:meal";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET, params = "action=update")
    public String updateMeal(Model model, @RequestParam("id") int id){
        model.addAttribute("meal", service.get(id, AuthorizedUser.id()));
        return "forward:meal";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST, params = "action=filter")
    public String getFilteredMeal(HttpServletRequest request){
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        LOG.info("getBetween dates {} - {} for time {} - {} for User {}", startDate, endDate, startTime, endTime, AuthorizedUser.id());
        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(startDate != null ? startDate : TimeUtil.MIN_DATE,
                        endDate != null ? endDate : TimeUtil.MAX_DATE,
                        AuthorizedUser.id()),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        ));
        return "forward:meals";
    }

    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }


    @RequestMapping(value = "/meals", method = RequestMethod.POST, params = "!action")
    public String saveUpdatedMeal(@RequestParam("id") int id,
                                  @RequestParam("description") String desc,
                                  @RequestParam("calories") int calories,
                                  @RequestParam("dateTime") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime source){
        Meal meal = new Meal(id, source, desc, calories);
        LOG.info("update {} for User {}", meal, AuthorizedUser.id());
        service.update(meal, AuthorizedUser.id());
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST, params = {"!action", "!id"})
    public String saveMeal(@RequestParam("description") String desc,
                           @RequestParam("calories") int calories,
                           @RequestParam("dateTime") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime source){
        Meal meal = new Meal(null, source, desc, calories);
        LOG.info("create {} for User {}", meal, AuthorizedUser.id());
        service.save(meal, AuthorizedUser.id());
        return "redirect:/meals";
    }




    public Meal get(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get meal {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    public void update(Meal meal, int id) {
        meal.setId(id);
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", meal, userId);
        service.update(meal, userId);
    }

    public Meal create(Meal meal) {
        meal.setId(null);
        int userId = AuthorizedUser.id();
        LOG.info("create {} for User {}", meal, userId);
        return service.save(meal, userId);
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates {} - {} for time {} - {} for User {}", startDate, endDate, startTime, endTime, userId);

        return MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );
    }
}
