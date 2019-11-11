package ru.graduation.to;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

public class DishTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 300)
    @NotBlank
    @SafeHtml
    private String description;

    @Range(min = 10, max = 10000000)
    private Integer price;

    private int restaurantId;

    @NotNull
    private LocalDateTime dateTime = LocalDateTime.now();

    public DishTo() {
    }

    public DishTo(Integer id, LocalDateTime dateTime, int restaurantId, String description, Integer price) {
        super(id);
        this.dateTime = dateTime;
        this.restaurantId = restaurantId;
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurant) {
        this.restaurantId = restaurant;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "name='" + description + '\'' +
                ", price=" + price +
                ", restaurantId=" + restaurantId +
                ", dateTime=" + dateTime +
                ", id=" + id +
                '}';
    }
}
