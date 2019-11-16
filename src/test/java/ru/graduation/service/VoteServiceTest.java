package ru.graduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.graduation.VoteTestData;
import ru.graduation.model.Vote;
import ru.graduation.util.exception.BusinessException;
import ru.graduation.util.exception.NotFoundException;

import java.time.LocalTime;
import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.graduation.RestaurantTestData.MARKETPLACE;
import static ru.graduation.RestaurantTestData.MCDONALDS;
import static ru.graduation.UserTestData.*;
import static ru.graduation.VoteTestData.USER_VOTE_ID;
import static ru.graduation.VoteTestData.VOTE;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class VoteServiceTest {

    @Autowired
    VoteService voteService;

    @Test
    void create() throws Exception {
        Vote vote = new Vote(of(2019, Month.AUGUST, 21, 17, 0), MCDONALDS, USER);
        voteService.create(vote, USER_ID);
        VoteTestData.assertMatch(voteService.getAll(USER_ID), VOTE, vote);

    }

    @Test
    void get() throws Exception {
        final Vote actual = voteService.get(USER_VOTE_ID, USER_ID);
        VoteTestData.assertMatch(actual, VOTE);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> voteService.get(1, USER_ID));
    }

    @Test
    void getNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () -> voteService.get(USER_VOTE_ID, ADMIN_ID));
    }

    @Test
    void update() throws Exception {
        //assumeFalse(LocalTime.now().isAfter(LocalTime.of(11, 0)), "You can not vote after 11:00");
        Vote vote = new Vote(VOTE);
        vote.setRestaurant(MARKETPLACE);
        int userId = VOTE.getUser().getId();
        voteService.create(vote, userId);
        VoteTestData.assertMatch(voteService.get(USER_VOTE_ID, userId), vote);
    }

    @Test
    void delete() throws Exception {
        if (LocalTime.now().isAfter(LocalTime.of(11, 0))) {
            assertThrows(BusinessException.class, () -> voteService.delete(USER_VOTE_ID, USER_ID));
        } else {
            voteService.delete(USER_VOTE_ID, USER_ID);
            assertThrows(NotFoundException.class, () -> voteService.get(USER_VOTE_ID, USER_ID));
        }
    }

    @Test
    void deleteNotFound() throws Exception {
        if (LocalTime.now().isAfter(LocalTime.of(11, 0))) {
            assertThrows(BusinessException.class, () -> voteService.delete(1, USER_ID));
        } else {
            voteService.delete(1, USER_ID);
            assertThrows(NotFoundException.class, () -> voteService.get(USER_VOTE_ID, USER_ID));
        }
    }

    @Test
    void deleteNotOwn() throws Exception {
        if (LocalTime.now().isAfter(LocalTime.of(11, 0))) {
            assertThrows(BusinessException.class, () -> voteService.delete(USER_VOTE_ID, ADMIN_ID));
        } else {
            voteService.delete(1, USER_ID);
            assertThrows(NotFoundException.class, () -> voteService.delete(USER_VOTE_ID, ADMIN_ID));
        }
    }

    /*@Test
    void getAll() throws Exception {
        VoteTestData.assertMatch(voteService.getAll(USER_ID), VOTE);
    }*/
}
