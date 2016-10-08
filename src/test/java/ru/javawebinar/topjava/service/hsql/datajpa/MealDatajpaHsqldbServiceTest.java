package ru.javawebinar.topjava.service.hsql.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;

/**
 * Created by Никола on 08.10.2016.
 */
@ActiveProfiles({Profiles.HSQLDB, Profiles.DATAJPA})
public class MealDatajpaHsqldbServiceTest extends MealServiceTest {

    @Test
    public void testGetWithUser() throws Exception {
        Meal meal = service.getWithUser(MEAL1_ID);
        MATCHER.assertEquals(MealTestData.MEAL1, meal);
        UserTestData.MATCHER.assertEquals(UserTestData.USER, meal.getUser());
    }
}
