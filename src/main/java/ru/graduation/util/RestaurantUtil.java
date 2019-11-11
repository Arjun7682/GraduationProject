package ru.graduation.util;

import ru.graduation.model.Restaurant;
import ru.graduation.to.RestaurantTo;

public class RestaurantUtil {

    public static RestaurantTo asTo (Restaurant restaurant){
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }
}
