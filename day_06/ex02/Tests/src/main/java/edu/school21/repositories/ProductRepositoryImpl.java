package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.*;
import java.sql.*;
import java.util.*;

public class ProductRepositoryImpl implements ProductRepository {
	
	private DataSource dataSource;

	private static final String FIND_ALL_QUERY = "select * from product";
	private static final String FIND_BY_ID_QUERY = "select * from product where id=?";
	private static final String UPDATE_QUERY = "update product set name=?, price=? where id=?";
	private static final String INSERT_QUERY = "insert into product (id, name, price) values (?, ?, ?)";
	private static final String DELETE_QUERY = "delete from product where id = ?";

	public ProductRepositoryImpl(DataSource dataSource ) {
		this.dataSource = dataSource;
	}

	public List<Product> findAll() {
		try (Connection connection = dataSource.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
			ResultSet result = preparedStatement.executeQuery();
			List<Product> products = new ArrayList<>();
			while (result.next()) {
				try {
					products.add(extractProduct(result));
				} catch (SQLException e) {
					throw e;
				}
			}
			return products;
		} catch (SQLException e) {
			System.err.println("Failed to execute query: " + FIND_ALL_QUERY + "Exception: " + e);
		}
		return null;
	}
	
	public Optional<Product> findById(Long id) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
			preparedStatement.setLong(1, id);
			ResultSet result = preparedStatement.executeQuery();
			if (result.next()) {
				return Optional.of(extractProduct(result));
			}
		} catch (SQLException e) {
			System.err.println("Failed to execute query: " + FIND_BY_ID_QUERY + "Exception: " + e);
		}
		return Optional.empty();
	}
	
	public void update(Product product) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
			preparedStatement.setString(1, product.getName());
			preparedStatement.setInt(2, product.getPrice());
			preparedStatement.setLong(3, product.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to execute query: " + UPDATE_QUERY + "Exception: " + e);
		}
	}
	
	public void save(Product product) {
		Optional<Product> p = findById(product.getId());
		if (p.isPresent()) {
			update(product);
		} else {
			try (Connection connection = dataSource.getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
				preparedStatement.setLong(1, product.getId());
				preparedStatement.setString(2, product.getName());
				preparedStatement.setInt(3, product.getPrice());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Failed to execute query: " + UPDATE_QUERY + "Exception: " + e);
			}
		}
	}

	public void delete (Long id) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to execute query: " + UPDATE_QUERY + "Exception: " + e);
		}
	}

	private Product extractProduct(ResultSet resultSet) throws SQLException {
		final int id = resultSet.getInt(1);
		final String name = resultSet.getString(2);
		final int price = resultSet.getInt(3);
		return new Product(id, name, price);
	}
}