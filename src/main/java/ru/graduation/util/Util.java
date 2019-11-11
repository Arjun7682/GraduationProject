package ru.graduation.util;

import ru.graduation.util.exception.BusinessException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Util {
    private Util() {
    }

    public static void checkVoteTime() {
        LocalTime time = LocalTime.now();
        if (time.isAfter(LocalTime.of(11, 00))) {
            throw new BusinessException("You can not vote after 11:00");
        }
    }

    public static void checkVoteDate(LocalDate actual, LocalDate expected) {
        if (!actual.equals(expected)) {
            throw new BusinessException("Incorrect date");
        }
    }
}