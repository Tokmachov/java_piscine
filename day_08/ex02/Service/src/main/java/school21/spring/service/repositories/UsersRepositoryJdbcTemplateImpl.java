package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component ("usersRepositoryJdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(DriverManagerDataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public User findById(Long id) {
        return (User)jdbcTemplate.query("SELECT * FROM public.user WHERE identifier = ?",
                                            new Object[]{id},
                                            new BeanPropertyRowMapper<>(User.class))
                                            .stream().findAny().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM public.user", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update("INSERT INTO public.user (email, password) VALUES (?, ?);", user.getEmail(), user.getPassword());
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("UPDATE public.user SET email = ? WHERE identifier = ?",
                                            user.getEmail(),
                                            user.getIdentifier());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM public.user WHERE identifier = ? ", id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = (User)jdbcTemplate.query("SELECT * FROM public.user WHERE email = ?",
                                            new Object[]{email},
                                            new BeanPropertyRowMapper<>(User.class))
                                            .stream().findAny().orElse(null);
        if (user == null)
            return Optional.empty();
        return Optional.of(user);
    }
}