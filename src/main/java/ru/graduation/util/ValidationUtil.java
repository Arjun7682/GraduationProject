package ru.graduation.util;

import ru.graduation.util.exception.BusinessException;
import ru.graduation.util.exception.NotFoundException;

import java.time.LocalTime;

public class ValidationUtil {
    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String arg) {
        if (!found) {
            throw new NotFoundException(arg);
        }
    }

    public static void checkDateTimeValid() {
        if (LocalTime.now().isAfter(LocalTime.of(11, 00))) {
            throw new BusinessException("You can't vote after 11AM");
        }
    }
}
