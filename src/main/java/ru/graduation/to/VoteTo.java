package ru.graduation.to;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class VoteTo extends BaseTo implements Serializable {
    @JsonIgnore
    private int userId;

    private int restaurantId;

    @NotNull
    private LocalDateTime dateTime = LocalDateTime.now();

    public VoteTo() {
    }

    public VoteTo(VoteTo voteTo) {
        this(voteTo.getId(), voteTo.getDateTime(), voteTo.getRestaurantId());
    }

    public VoteTo(int restaurantId) {
        this(null, restaurantId);
    }

    public VoteTo(Integer id, int restaurantId) {
        super(id);
        this.restaurantId = restaurantId;
    }

    public VoteTo(Integer id, LocalDateTime dateTime, int restaurantId) {
        super(id);
        this.dateTime = dateTime;
        this.restaurantId = restaurantId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", dateTime=" + dateTime +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteTo voteTo = (VoteTo) o;
        return userId == voteTo.userId &&
                restaurantId == voteTo.restaurantId &&
                dateTime.equals(voteTo.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, restaurantId, dateTime);
    }
}
