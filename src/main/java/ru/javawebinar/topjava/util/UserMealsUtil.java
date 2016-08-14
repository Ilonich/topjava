package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMeal> filtered = new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDayTest = new HashMap<>();
        List<UserMealWithExceed> filteredExceed = new ArrayList<>();
        for (UserMeal userMealTest : mealList){
            if (TimeUtil.isBetween(userMealTest.getDateTime().toLocalTime(), startTime, endTime)){
                filtered.add(userMealTest);
            }
            LocalDate caloriesPerDayTestKey = userMealTest.getDateTime().toLocalDate();
            if (!caloriesPerDayTest.containsKey(caloriesPerDayTestKey)) {
                caloriesPerDayTest.put(caloriesPerDayTestKey, userMealTest.getCalories());
            } else {
                caloriesPerDayTest.put(caloriesPerDayTestKey, caloriesPerDayTest.get(caloriesPerDayTestKey) + userMealTest.getCalories());
            }
        }
        for (UserMeal testing : filtered){
            filteredExceed.add(new UserMealWithExceed(testing.getDateTime(), testing.getDescription(), testing.getCalories(),
                    caloriesPerDayTest.get(testing.getDateTime().toLocalDate()) > caloriesPerDay));
        }
        return filteredExceed;
    }
}
