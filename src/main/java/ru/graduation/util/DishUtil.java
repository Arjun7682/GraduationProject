package ru.graduation.util;

import ru.graduation.model.Dish;
import ru.graduation.model.Restaurant;
import ru.graduation.to.DishTo;

public class DishUtil {
    public static Dish createNewFromTo(DishTo dishTo, Restaurant restaurant) {
        return new Dish(dishTo.getDateTime(), restaurant, dishTo.getDescription(), dishTo.getPrice());
    }

    public static DishTo asTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getDateTime(), dish.getRestaurant().getId(), dish.getDescription(), dish.getPrice());
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setDescription(dishTo.getDescription());
        dish.setPrice(dishTo.getPrice());
        dish.setDateTime(dishTo.getDateTime());
        return dish;
    }
}
