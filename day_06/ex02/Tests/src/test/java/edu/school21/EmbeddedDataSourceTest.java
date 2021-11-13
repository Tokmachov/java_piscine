import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.datasource.embedded.*;
import java.sql.*;
import org.junit.jupiter.api.*;

public class EmbeddedDataSourceTest {
	
	private EmbeddedDatabase db;
	
	@BeforeEach
	void intit() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		db = builder.setType(EmbeddedDatabaseType.HSQL).addScript("schema.sql").addScript("data.sql").build();
	}
	
	@Test
	public void connectionTest() throws SQLException {
		try (Connection connection = db.getConnection()) {
			Assertions.assertNotNull(connection);
		}
	}

	@Test
	public void doSomethingTest() {
		ProductRepositoryImpl productRepository = new ProductRepository(db);
		
	}
}