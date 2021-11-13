import javax.sql.*;
import java.sql.*;
import java.util.*;

public class ProductRepositoryImpl implements ProductRepository {
	
	private DataSource dataSource;

	private static final String FIND_ALL_QUERRY = "select * from product";

	public ProductRepositoryImpl(DataSource dataSource ) {
		this.dataSource = dataSource;
	}

	public List<Product> findAll() {
		try (Connection connection = dataSource.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERRY)) {
			ResultSet result = preparedStatement.executeQuery();
		} catch (SQLException e) {
			System.err.println("Failed to execute querry: " + FIND_ALL_QUERRY + "Exception: " + e);
		}
		return null;
	}
	
	public Optional<Product> findById(Long id) {
		return null;
	}
	
	public void update(Product product) {
		
	}
	
	public void save(Product product) {

	}
	
	public void delete (Long id) {

	}
}