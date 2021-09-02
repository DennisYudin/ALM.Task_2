package dev.andrylat.task2.dao;

import dev.andrylat.task2.configs.AppConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DataSourceTest {
    private static Logger logger = LoggerFactory.getLogger(DataSourceTest.class);

    @Test
    public void testTwo() throws SQLException {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(
                        AppConfig.class);
        DataSource dataSource = ctx.getBean("dataSource", DataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);
        ctx.close();
    }

    private void testDataSource(DataSource dataSource) throws SQLException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT 1");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int mockVal = resultSet.getInt(1);
                assertTrue(mockVal == 1);
                statement.close();
            }
        } catch (Exception e) {
            logger.debug("Something unexpected happened.", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
