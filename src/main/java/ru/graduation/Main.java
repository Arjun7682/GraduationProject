package ru.graduation;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.graduation.service.UserService;

public class Main {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-db.xml")) {
            //System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            UserService service = appCtx.getBean(UserService.class);
            System.out.println(service.get(100000));
        }
    }
}
