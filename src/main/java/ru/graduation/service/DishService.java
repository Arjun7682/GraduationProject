package ru.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduation.model.Dish;
import ru.graduation.model.Restaurant;
import ru.graduation.repository.DishRepository;
import ru.graduation.to.DishTo;
import ru.graduation.util.DishUtil;

import java.time.LocalDateTime;
import java.util.List;

import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service("DishService")
public class DishService {

    private static final Sort SORT_BY_DATE = Sort.by(Sort.Order.desc("date"));

    private final DishRepository repository;

    @Autowired
    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public Dish create(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public void update(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish), dish.getId());
    }

    public void update(DishTo dishTo){
        Assert.notNull(dishTo, "dish must not be null");
        Dish dish = get(dishTo.getId());
        checkNotFoundWithId(repository.save(DishUtil.updateFromTo(dish, dishTo)), dishTo.getId());
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public List<Dish> getDailyMenu(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        LocalDateTime now = LocalDateTime.of(2019, 8, 20, 0, 0);//LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59, 59);
        return repository.getDailyMenu(startDate, endDate, restaurant);
    }

    /*public List<Dish> getAllByRestaurantId(int restaurantId){
        return repository.getDishesByRestaurantId(restaurantId, SORT_BY_DATE);
    }*/
}
