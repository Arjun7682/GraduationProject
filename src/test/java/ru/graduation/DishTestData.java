package ru.graduation;

import org.assertj.core.util.Lists;
import ru.graduation.model.Dish;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.graduation.RestaurantTestData.MCDONALDS;
import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {

    public static final int MAC_DISH_ID = START_SEQ + 7;

    public static final Dish DISH1 = new Dish(MAC_DISH_ID, of(2019, 8, 20, 17, 0), MCDONALDS, "Burger", 16000);
    public static final Dish DISH2 = new Dish(MAC_DISH_ID + 1, of(2019, 8, 20, 17, 0), MCDONALDS, "Fries", 7000);
    public static final Dish DISH3 = new Dish(MAC_DISH_ID + 2, of(2019, 8, 20, 17, 0), MCDONALDS, "Americano", 5000);

    public static final List<Dish> MAC_DISHES = Lists.newArrayList(DISH1, DISH2, DISH3);

    public static Dish getCreated() {
        return new Dish(null, of(2019, Month.AUGUST, 20, 17, 0), MCDONALDS, "Created dish", 10000);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1.getId(), DISH1.getDateTime(), DISH1.getRestaurant(), "Updated dish", 11000);
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        //assertMatch(actual, List.of(expected));
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}
