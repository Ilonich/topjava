package ru.javawebinar.topjava.service.hsql.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;

/**
 * Created by Никола on 08.10.2016.
 */
@ActiveProfiles({Profiles.HSQLDB, Profiles.DATAJPA})
public class UserDatajpaHsqldbServiceTest extends UserServiceTest {

    @Test
    public void testGetWithMeals() throws Exception {
        User user = service.getWithMeals(UserTestData.USER_ID);
        MATCHER.assertCollectionEquals(MealTestData.MEALS, user.getMeals());
        UserTestData.MATCHER.assertEquals(UserTestData.USER, user);
    }
}
