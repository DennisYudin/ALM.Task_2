package dev.andrylat.task2.configs;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
public class InitDB {
	private DataSource adminDataSource;
	private DataSource dataSource;

	@Autowired
	public InitDB(@Qualifier("adminDataSource") DataSource adminDataSource,
				  @Qualifier("dataSource") DataSource dataSource) {
		this.adminDataSource = adminDataSource;
		this.dataSource = dataSource;
	}

	@PostConstruct
	public void init() throws SQLException {

//		PreparedStatement statement = null;
//		try (Connection conn = adminDataSource.getConnection()) {
//			statement = conn.prepareStatement("" +
//					"DROP DATABASE eventholderdb;" +
//					"CREATE DATABASE eventholderdb;");
//			statement.executeUpdate();
//		} finally {
//			Objects.requireNonNull(statement).close();
//		}

		runScript(adminDataSource, "createDBlink.sql");
		runScript(adminDataSource, "createDB.sql");
//		runScript(dataSource, "createTables.sql");
//		runScript(dataSource,"populateTables.sql");
	}

	public void runScript(DataSource dataSource, String fileName) {
		try (Connection connection = dataSource.getConnection()) {
			ScriptRunner runner = new ScriptRunner(connection);
			connection.setAutoCommit(true);
			InputStreamReader reader = new InputStreamReader(getFileFromResourceAsStream(fileName));
			runner.runScript(reader);
		} catch (SQLException ex) {
			throw new RuntimeException("Could not get connection", ex);
		}
	}

	private InputStream getFileFromResourceAsStream(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);
		if (inputStream == null) {
			throw new RuntimeException("file not found! " + fileName);
		} else {
			return inputStream;
		}
	}
}
