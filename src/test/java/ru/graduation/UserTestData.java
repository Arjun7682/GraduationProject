package ru.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.graduation.model.Role;
import ru.graduation.model.User;
import ru.graduation.to.UserTo;
import ru.graduation.web.json.JsonUtil;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.graduation.TestUtil.readFromJsonMvcResult;
import static ru.graduation.TestUtil.readListFromJsonMvcResult;
import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        //assertMatch(actual, List.of(expected));
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles", "password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), Arrays.asList(expected));
    }

    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }

    public static String jsonWithPassword(UserTo user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}
