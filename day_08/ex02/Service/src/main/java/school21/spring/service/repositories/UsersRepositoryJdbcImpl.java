package school21.spring.service.repositories;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component ("usersRepositoryJdbc")
public class UsersRepositoryJdbcImpl implements UsersRepository {

    DataSource          dataSource;
    PreparedStatement   preparedStatement;
    ResultSet           resultSet;

    public UsersRepositoryJdbcImpl(DataSource ds) {
        this.dataSource = ds;
    }

    @Autowired
    public UsersRepositoryJdbcImpl(HikariDataSource ds) {
        this.dataSource = ds;
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM public.user WHERE identifier = ";

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sql + id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                String email = resultSet.getString("email");

                return new User(id, email);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement("SELECT * FROM public.user;");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Long id = resultSet.getLong("identifier");

                String email = resultSet.getString("email");

                list.add(new User(id, email));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(User entity) {
        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO public.user (email, password) VALUES (?, ?);");
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE public.user SET email = ";

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sql + '\'' + entity.getEmail() + '\'' + " WHERE identifier = " + entity.getIdentifier());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM public.user WHERE identifier = ";

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sql + id);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM public.user WHERE email = ";

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sql + '\'' + email + '\'');
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(resultSet.getLong(1), resultSet.getString(2));

                return Optional .of(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }
}
