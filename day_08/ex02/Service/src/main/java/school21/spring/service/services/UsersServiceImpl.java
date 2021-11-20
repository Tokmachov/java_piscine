package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.sql.SQLException;
import java.util.UUID;

@Component ("usersService")
public class UsersServiceImpl implements UsersService {

    UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplate") UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) throws SQLException {

        String password = UUID.randomUUID().toString();

        if (email == null || email.equals("")) {
            System.err.println("ERROR. Email is required");
            return password;
        }
        usersRepository.save(new User(email, password));
        return password;
    }
}
