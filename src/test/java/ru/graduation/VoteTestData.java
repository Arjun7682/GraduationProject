package ru.graduation;

import ru.graduation.model.Vote;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.graduation.RestaurantTestData.MARKETPLACE;
import static ru.graduation.UserTestData.USER;
import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final int USER_VOTE_ID = START_SEQ + 22;
    public static final int ADMIN_VOTE_ID = START_SEQ + 23;


    public static final Vote VOTE = new Vote(USER_VOTE_ID, LocalDateTime.of(2019, 8, 20, 17, 0), MARKETPLACE, USER);

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
