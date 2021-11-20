package edu.school21.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.datasource.embedded.*;
import java.sql.*;
import org.junit.jupiter.api.*;

public class EmbeddedDataSourceTest {
	
	private EmbeddedDatabase db;
	
	@BeforeEach
	public void intit() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		db = builder.setType(EmbeddedDatabaseType.HSQL).addScript("schema.sql").addScript("data.sql").build();
	}

	@AfterEach
	public void deinit() {
		db.shutdown();
	}

	@Test
	public void connectionTest() throws SQLException {
		try (Connection connection = db.getConnection()) {
			Assertions.assertNotNull(connection);
		}
	}
}