package ru.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduation.model.Dish;
import ru.graduation.model.Restaurant;
import ru.graduation.repository.DishRepository;
import ru.graduation.repository.RestaurantRepository;
import ru.graduation.to.DishTo;
import ru.graduation.util.DishUtil;

import java.time.LocalDateTime;
import java.util.List;

import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service("dishService")
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Dish create(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return dishRepository.save(dish);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(dishRepository.findById(id).orElse(null), id);
    }

    public void update(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(dishRepository.save(dish), dish.getId());
    }

    public void update(DishTo dishTo) {
        Assert.notNull(dishTo, "dish must not be null");
        Dish dish = get(dishTo.getId());
        checkNotFoundWithId(dishRepository.save(DishUtil.updateFromTo(dish, dishTo)), dishTo.getId());
    }

    public void delete(int id) {
        checkNotFoundWithId(dishRepository.delete(id) != 0, id);
    }

    public List<Dish> getDailyMenu(int restaurantId) {
        Assert.notNull(restaurantId, "restaurant id must not be null");
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        LocalDateTime now = LocalDateTime.of(2019, 8, 20, 0, 0);//LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59, 59);
        return dishRepository.getDailyMenu(startDate, endDate, restaurant);
    }
}
