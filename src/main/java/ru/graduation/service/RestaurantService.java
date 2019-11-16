package ru.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduation.model.Restaurant;
import ru.graduation.repository.RestaurantRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.graduation.repository.RestaurantRepository.SORT_NAME;
import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service("restaurantService")
public class RestaurantService {
    private final RestaurantRepository repository;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public List<Restaurant> getAll() {
        return repository.findAll(SORT_NAME);
    }

    public List<Restaurant> getRestaurantsByDate(LocalDateTime date) {
        return repository.getRestaurantsByDate(date);
    }

    public Restaurant getWithDish(int id) {
        return checkNotFoundWithId(repository.getWithDishes(id), id);
    }

    public void enable(int id, boolean enabled) {
        Restaurant restaurant = get(id);
        restaurant.setEnabled(enabled);
        repository.save(restaurant);
    }
}
