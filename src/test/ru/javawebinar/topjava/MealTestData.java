package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static List<Meal> ALL_MEALS = Arrays.asList(new Meal(100007, LocalDateTime.of(2016, Month.SEPTEMBER, 2, 20, 0), "Ужин", 1000),
            new Meal(100006, LocalDateTime.of(2016, Month.SEPTEMBER, 2, 16, 0), "Обед", 500),
            new Meal(100005, LocalDateTime.of(2016, Month.SEPTEMBER, 2, 11, 0), "Завтрак", 510),
            new Meal(100004, LocalDateTime.of(2016, Month.SEPTEMBER, 1, 20, 0), "Ужин", 500),
            new Meal(100003, LocalDateTime.of(2016, Month.SEPTEMBER, 1, 16, 0), "Обед", 500),
            new Meal(100002, LocalDateTime.of(2016, Month.SEPTEMBER, 1, 11, 0), "Завтрак", 1000));


    public static Meal SOME_MEAL = new Meal(null, LocalDateTime.of(2016, Month.SEPTEMBER, 3, 23, 0), "Завтрак", 2000);

    public static int SOME_MEAL_ID = 100008;

    public static int FIRST_MEAL_ID = ALL_MEALS.get(0).getId();

    public static Meal FIRST_MEAL = ALL_MEALS.get(0);

    public static LocalDateTime END_DATE = LocalDateTime.of(2016, Month.SEPTEMBER, 2, 11, 0);

    public static LocalDateTime START_DATE = LocalDateTime.of(2016, Month.SEPTEMBER, 1, 20, 0);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();


}
