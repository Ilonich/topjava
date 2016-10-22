package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


/**
 * Created by Никола on 21.10.2016.
 */
public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL;

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEEDED.contentListMatcher(MealsUtil.getWithExceeded(MEALS, 2000)));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/delete/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), mealService.getAll(USER_ID));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "Тест", 200);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated())
                .andDo(print());

        Meal returned = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), Meal.class);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(returned, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(USER_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL3);
        updated.setDescription("Pizza");
        updated.setCalories(650);
        mockMvc.perform(put(REST_URL + "/" + MEAL3.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, mealService.get(MEAL3.getId(), USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(post(REST_URL + "/filter")
                .param("startDate", "2015-05-30")
                .param("endDate", "")
                .param("startTime", "12:30")
                .param("endTime", "21:30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEEDED.contentListMatcher(MealsUtil.getWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL3, MEAL2), 1500)));
    }

}