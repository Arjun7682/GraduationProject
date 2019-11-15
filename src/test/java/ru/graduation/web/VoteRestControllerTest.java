package ru.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.VoteTestData;
import ru.graduation.model.Vote;
import ru.graduation.service.VoteService;
import ru.graduation.to.VoteTo;
import ru.graduation.util.VoteUtil;
import ru.graduation.web.controller.VoteRestController;
import ru.graduation.web.json.JsonUtil;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.RestaurantTestData.MCDONALDS_ID;
import static ru.graduation.TestUtil.*;
import static ru.graduation.UserTestData.USER;
import static ru.graduation.UserTestData.USER_ID;
import static ru.graduation.VoteTestData.USER_VOTE_ID;
import static ru.graduation.VoteTestData.VOTE;

public class VoteRestControllerTest extends AbstractControllerTest {
    private final String REST_VOTE = VoteRestController.REST_VOTE_URL + "/";

    @Autowired
    VoteService voteService;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_VOTE + USER_VOTE_ID)
                .with(userAuth(USER))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> VoteTestData.assertMatch(readFromJsonMvcResult(result, VoteTo.class), VoteUtil.asTo(VOTE)));
    }

    @Test
    void createWithLocation() throws Exception {
        VoteTo expected = new VoteTo(null, MCDONALDS_ID);

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_VOTE)
                .with(userAuth(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote returned = readFromJson(action, Vote.class);
        expected.setId(returned.getId());
        VoteTo actual = new VoteTo(returned.getId(), returned.getDateTime(), returned.getRestaurant().getId());

        VoteTestData.assertMatch(actual, expected);
    }

    @Test
    void delete() throws Exception {

        assumeFalse(LocalTime.now().isAfter(LocalTime.of(11, 0)), "You can not vote after 11:00");
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_VOTE + USER_VOTE_ID)
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {
        assumeFalse(LocalTime.now().isAfter(LocalTime.of(11, 0)), "You can not vote after 11:00");
        VoteTo updated = VoteUtil.asTo(VOTE);
        updated.setRestaurantId(MCDONALDS_ID);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_VOTE + USER_VOTE_ID)
                .with(userAuth(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))).andDo(print())
                .andExpect(status().isNoContent());

        VoteTo actual = VoteUtil.asTo(voteService.get(USER_VOTE_ID, USER_ID));
        VoteTestData.assertMatch(actual, updated);
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_VOTE)
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}