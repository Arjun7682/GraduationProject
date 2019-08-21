package ru.graduation.model;

import java.time.LocalDateTime;

public class Vote {
    private LocalDateTime dateTime = LocalDateTime.now();
    private Restaurant restaurant;
    private User user;
}
