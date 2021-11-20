package school21.spring.service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import javax.sql.DataSource;
import java.sql.SQLException;

public class UsersServiceImplTest {

    private UsersService usersServiceJdbc;

    private UsersService usersServiceJdbcTemplate;

    @BeforeEach
    public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);

        DataSource dataSource = context.getBean("HSQLDataSource", DataSource.class);

        usersServiceJdbc = new UsersServiceImpl(new UsersRepositoryJdbcImpl(dataSource));
        usersServiceJdbcTemplate = new UsersServiceImpl(new UsersRepositoryJdbcTemplateImpl(dataSource));
    }

    @ParameterizedTest
    @ValueSource(strings = {"qq@qq.com", "ww@ww.com", "tt@tt.com"})
    public void isReturnedPassword(String email) throws SQLException {
        Assertions.assertNotNull(usersServiceJdbc.signUp(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ee@ee.com", "rr@rr.com", "yy@yy.com"})
    public void isReturnedPasswordTemplate(String email) throws SQLException {
        Assertions.assertNotNull(usersServiceJdbcTemplate.signUp(email));
    }
}