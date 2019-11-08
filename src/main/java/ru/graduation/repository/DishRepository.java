package ru.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Dish;
import ru.graduation.model.Restaurant;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Modifying
    @Query("SELECT d from Dish d WHERE  d.restaurant = :restaurant AND d.dateTime BETWEEN :startDate AND :endDate ORDER BY d.dateTime DESC")
    List<Dish> getDailyMenu(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, /*@Param("userId") int userId,*/ @Param("restaurant")Restaurant restaurant);
}
