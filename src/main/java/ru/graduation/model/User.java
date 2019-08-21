package ru.graduation.model;

import java.time.LocalDateTime;
import java.util.Set;

public class User {
    private String name;
    private String email;
    private String password;
    private LocalDateTime registered = LocalDateTime.now();
    private boolean enabled = true;
    private Set<Role> roles;
}
