package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Никола on 11.09.2016.
 */
public class MealSimpleRepo {
    private MealSimpleRepo() {
    }
    private static AtomicInteger idcount = new AtomicInteger(0);
    private static ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();
    static {
        add(new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new Meal(0, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(0, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new Meal(0, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static List<Meal> showAll(){
        return Collections.unmodifiableList(meals.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()));
    }

    public static Meal mealById(int id){
        return meals.getOrDefault(id, new Meal(id, LocalDateTime.now(), "Null", 0));
    }

    public static int add(Meal meal){
        int id = (idcount.incrementAndGet());
        meal.setId(id);
        meals.put(id, meal);
        return id;
    }

    public static boolean update(Meal meal){
        if (meals.containsKey(meal.getId())){
            meals.put(meal.getId(), meal);
            return true;
        } else {
            return false;
        }
    }

    public static boolean delete(int id){
        if (meals.containsKey(id)){
            meals.remove(id);
            return true;
        } else {
            return false;
        }
    }

}
