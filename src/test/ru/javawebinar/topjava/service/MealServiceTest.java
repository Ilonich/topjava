package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by Никола on 26.09.2016.
 */
@ContextConfiguration({
        "classpath:springtest/spring-app-test-repo-db.xml",
        "classpath:springtest/spring-test-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    protected MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        MATCHER.assertEquals(service.get(FIRST_MEAL_ID, UserTestData.USER_ID), FIRST_MEAL);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(FIRST_MEAL_ID, UserTestData.USER_ID);
        MATCHER.assertCollectionEquals(service.getAll(UserTestData.USER_ID), ALL_MEALS.subList(1, ALL_MEALS.size()));
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        MATCHER.assertCollectionEquals(service.getBetweenDateTimes(START_DATE, END_DATE, UserTestData.USER_ID),
                ALL_MEALS.subList(2, 4));
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(service.getAll(UserTestData.USER_ID), ALL_MEALS);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updatedMeal = ALL_MEALS.get(0);
        updatedMeal.setDescription("WOW! UPDATED!");
        service.update(updatedMeal, UserTestData.USER_ID);
        MATCHER.assertCollectionEquals(service.getAll(UserTestData.USER_ID), ALL_MEALS);
    }

    @Test
    public void testSave() throws Exception {
        service.save(SOME_MEAL, UserTestData.USER_ID);
        Meal savedWithID = new Meal(SOME_MEAL);
        service.save(savedWithID, UserTestData.USER_ID);
        savedWithID.setId(SOME_MEAL_ID);
        MATCHER.assertEquals(service.get(savedWithID.getId(), UserTestData.USER_ID), savedWithID);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateFail() throws Exception {
        service.update(ALL_MEALS.get(0), UserTestData.ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetFail() throws Exception {
        service.get(ALL_MEALS.get(0).getId(), UserTestData.ADMIN_ID);
    }

    @Test
    public void testGetAllFail() throws Exception {
        Assert.assertEquals(true, service.getAll(UserTestData.ADMIN_ID).isEmpty());
    }

    @Test
    public void testGetBetweenFail() throws Exception {
        Assert.assertEquals(true, service.getBetweenDateTimes(START_DATE, END_DATE, UserTestData.ADMIN_ID).isEmpty());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteFail() throws Exception {
        service.delete(ALL_MEALS.get(0).getId(), UserTestData.ADMIN_ID);
    }
}