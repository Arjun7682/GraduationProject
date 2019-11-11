package ru.graduation.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class RestaurantTo extends BaseTo implements Serializable {

    @Size(min = 2, max = 100)
    @NotBlank
    private String name;

    public RestaurantTo(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
