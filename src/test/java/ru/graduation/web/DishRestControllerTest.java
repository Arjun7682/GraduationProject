package ru.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.DishTestData;
import ru.graduation.model.Dish;
import ru.graduation.to.DishTo;
import ru.graduation.util.DishUtil;
import ru.graduation.web.controller.DishRestController;
import ru.graduation.web.json.JsonUtil;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.DishTestData.*;
import static ru.graduation.RestaurantTestData.MCDONALDS;
import static ru.graduation.RestaurantTestData.MCDONALDS_ID;
import static ru.graduation.TestUtil.*;
import static ru.graduation.UserTestData.ADMIN;

public class DishRestControllerTest extends AbstractControllerTest {
    private static final String REST_DISH_URL = DishRestController.REST_DISH_URL + "/";

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_DISH_URL + MAC_DISH_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> DishTestData.assertMatch(readFromJsonMvcResult(result, Dish.class), DISH1));
    }

    @Test
    void createWithLocation() throws Exception {
        DishTo expected = new DishTo(null, LocalDateTime.of(2015, 06, 01, 0, 0), MCDONALDS_ID, "New", 20000);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_DISH_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish returned = readFromJson(action, Dish.class);
        Dish created = DishUtil.createNewFromTo(expected, MCDONALDS);
        created.setId(returned.getId());

        DishTestData.assertMatch(returned, created);
    }

    @Test
    void update() throws Exception {
        DishTo updated = DishUtil.asTo(DISH1);
        updated.setDescription("Update");
        updated.setPrice(666);

        mockMvc.perform(MockMvcRequestBuilders.put(REST_DISH_URL + MAC_DISH_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Dish expected = new Dish(DISH1);
        expected = DishUtil.updateFromTo(expected, updated);

        DishTestData.assertMatch(dishService.get(MAC_DISH_ID), expected);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_DISH_URL + MAC_DISH_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DishTestData.assertMatch(dishService.getDailyMenu(MCDONALDS_ID), DISH2, DISH3);

    }

    /*@Test
    void getAllByRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_DISH_URL)
                .param("restaurantId", "100002"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH1, DISH2, DISH3));
    }

    @Test
    void getAllByRestaurantAndDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_DISH_URL)
                .param("restaurantId", "100020")
                .param("localDate", "2019-08-10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH1, DISH2, DISH3));
    }*/
}