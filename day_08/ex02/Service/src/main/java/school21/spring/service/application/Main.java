package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.services.UsersService;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        UsersService usersService = context.getBean("usersService", UsersService.class);

        try {
            usersService.signUp("dlindgren@carter.com");
            usersService.signUp("london92@hotmail.com");
            usersService.signUp("andrew.romaguera@prosacco.com");
            usersService.signUp(null);
            usersService.signUp("");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
