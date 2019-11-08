package ru.graduation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.graduation.model.Dish;
import ru.graduation.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.graduation.DishTestData.*;
import static ru.graduation.RestaurantTestData.MCDONALDS;

@SpringJUnitConfig(locations = "classpath:spring/spring-db.xml")
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DishServiceTest {

    @Autowired
    private DishService service;

    @Test
    public void create() throws Exception {
        Dish newDish = getCreated();
        Dish created = service.create(newDish);
        newDish.setId(created.getId());
        assertMatch(newDish, created);
        assertMatch(service.getDailyMenu(MCDONALDS), DISH1, DISH2, DISH3, newDish);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MAC_DISH_ID);
        assertMatch(service.getDailyMenu(MCDONALDS), DISH2, DISH3);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    public void get() throws Exception {
        Dish actual = service.get(MAC_DISH_ID);
        assertMatch(actual, DISH1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    public void update() throws Exception {
        Dish updated = getUpdated();
        service.update(updated);
        assertMatch(service.get(MAC_DISH_ID), updated);
    }

    /*@Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + MEAL1_ID);
        service.update(MEAL1, ADMIN_ID);
    }*/

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getDailyMenu(MCDONALDS), MAC_DISHES);
    }

    /*@Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }*/
}
