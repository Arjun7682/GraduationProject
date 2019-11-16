package ru.graduation;

import ru.graduation.model.Vote;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.graduation.RestaurantTestData.*;
import static ru.graduation.UserTestData.USER;
import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final int USER_VOTE_ID = START_SEQ + 22;

    public static final Vote VOTE1 = new Vote(USER_VOTE_ID, LocalDateTime.of(2019, 8, 20, 17, 0), MARKETPLACE, USER);
    public static final Vote VOTE2 = new Vote(100025, LocalDateTime.of(2019, 8, 21, 17, 0), MCDONALDS, USER);
    public static final Vote VOTE3 = new Vote(100026, LocalDateTime.of(2019, 8, 22, 17, 0), KFC, USER);

    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant", "user");
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        //assertMatch(actual, List.of(expected));
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "user").isEqualTo(expected);
    }
}
