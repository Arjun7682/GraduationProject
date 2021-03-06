package ru.graduation.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.View;
import ru.graduation.model.Dish;
import ru.graduation.model.Restaurant;
import ru.graduation.service.DishService;
import ru.graduation.service.RestaurantService;
import ru.graduation.to.DishTo;
import ru.graduation.util.DishUtil;

import java.net.URI;
import java.util.List;

import static ru.graduation.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = DishRestController.REST_DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    public static final String REST_DISH_URL = "/rest/dishes";
    private static final Logger log = LoggerFactory.getLogger(DishRestController.class);

    private final DishService dishService;
    private final RestaurantService restaurantService;

    @Autowired
    public DishRestController(DishService dishService, RestaurantService restaurantService) {
        this.dishService = dishService;
        this.restaurantService = restaurantService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Dish> createWithLocation(@Validated(View.Web.class) @RequestBody DishTo dishTo) {
        Restaurant restaurant = restaurantService.get(dishTo.getRestaurantId());
        Dish created = DishUtil.createNewFromTo(dishTo, restaurant);
        Dish dish = dishService.create(created);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_DISH_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(dish);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get dish with id {}", id);
        return dishService.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody DishTo dishTo, @PathVariable int id) {
        assureIdConsistent(dishTo, id);
        log.info("update dish {}", id);
        dishService.update(dishTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish with id {}", id);
        dishService.delete(id);
    }

    @GetMapping
    public List<Dish> getDailyMenu(@RequestParam int restaurantId) {
        log.info("get all dish by restaurant id {}", restaurantId);
        return dishService.getDailyMenu(restaurantId);
    }
}
