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
import ru.graduation.model.Restaurant;
import ru.graduation.service.RestaurantService;
import ru.graduation.to.RestaurantTo;
import ru.graduation.util.RestaurantUtil;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static ru.graduation.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = RestaurantRestController.REST_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    public static final String REST_REST_URL = "/rest/restaurants";
    private static final Logger log = LoggerFactory.getLogger(RestaurantRestController.class);

    private final RestaurantService service;

    @Autowired
    public RestaurantRestController(RestaurantService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Restaurant> createWithLocation(@Validated(View.Web.class) @RequestBody Restaurant restaurant) {
        Restaurant created = service.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        log.info("get restaurants by id {}", id);
        return RestaurantUtil.asTo(service.get(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Restaurant restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.info("delete restaurant by id {}", id);
        service.delete(id);
    }

    @GetMapping("/all")
    public List<RestaurantTo> getAll() {
        return service.getAll().stream().map(RestaurantUtil::asTo).collect(Collectors.toList());
    }
}