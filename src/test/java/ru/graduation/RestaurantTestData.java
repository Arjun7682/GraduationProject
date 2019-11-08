package ru.graduation;

import org.assertj.core.util.Lists;
import ru.graduation.model.Restaurant;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {

    public static final int MCDONALDS_ID = START_SEQ + 2;
    public static final int KFC_ID = START_SEQ + 3;
    public static final int TEREMOK_ID = START_SEQ + 4;
    public static final int MARKETPLACE_ID = START_SEQ + 5;
    public static final int BIGKAHUNABURGER_ID = START_SEQ + 6;

    public static final Restaurant MCDONALDS = new Restaurant(MCDONALDS_ID, "McDonalds");
    public static final Restaurant KFC = new Restaurant(KFC_ID, "KFC");
    public static final Restaurant TEREMOK = new Restaurant(TEREMOK_ID, "Teremok");
    public static final Restaurant MARKETPLACE = new Restaurant(MARKETPLACE_ID, "Marketplace");
    public static final Restaurant BIGKAHUNABURGER = new Restaurant(BIGKAHUNABURGER_ID, "Big Kahuna Burger");

    public static final List<Restaurant> RESTAURANTS = Lists.newArrayList(BIGKAHUNABURGER, KFC, MARKETPLACE, MCDONALDS, TEREMOK);

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes", "enabled");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        //assertMatch(actual, List.of(expected));
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dishes", "enabled").isEqualTo(expected);
    }
}
