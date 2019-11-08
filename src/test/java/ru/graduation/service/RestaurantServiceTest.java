package ru.graduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.graduation.model.Restaurant;
import ru.graduation.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.graduation.RestaurantTestData.*;

@SpringJUnitConfig(locations = "classpath:spring/spring-db.xml")
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "New");
        Restaurant created = service.create(new Restaurant(newRestaurant));
        newRestaurant.setId(created.getId());
        assertMatch(created, newRestaurant);
        assertMatch(service.getAll(), BIGKAHUNABURGER, KFC, MARKETPLACE, MCDONALDS, newRestaurant, TEREMOK);
    }

    @Test
    void duplicateRestaurantCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new Restaurant(null, "McDonalds")));
    }

    @Test
    void delete() throws Exception {
        service.delete(KFC_ID);
        assertMatch(service.getAll(), BIGKAHUNABURGER, MARKETPLACE, MCDONALDS, TEREMOK);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void get() throws Exception {
        Restaurant restaurant = service.get(MARKETPLACE_ID);
        assertMatch(restaurant, MARKETPLACE);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = new Restaurant(MCDONALDS_ID, "UpdatedName");
        service.update(updated);
        assertMatch(service.get(MCDONALDS_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<Restaurant> all = service.getAll();
        assertMatch(all, RESTAURANTS);
    }

    /*@Test
    void enable() {
        service.enable(USER_ID, false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        assertTrue(service.get(USER_ID).isEnabled());
    }*/
}
