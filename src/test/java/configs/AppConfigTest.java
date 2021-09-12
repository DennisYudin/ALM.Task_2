package configs;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "dev.andrylat.task2")
@PropertySource("classpath:persistence-postgresql.properties")
public class AppConfigTest {

    @Autowired
    private Environment propertyDataHolder;

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        String jdbcDriver = propertyDataHolder.getProperty("jdbc.driver.test");
        String jdbcUrl = propertyDataHolder.getProperty("jdbc.url.test");
        String jdbcUser = propertyDataHolder.getProperty("jdbc.user.test");
        String jdbcPassword = propertyDataHolder.getProperty("jdbc.password.test");

        dataSource.setDriverClass(jdbcDriver);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(jdbcUser);
        dataSource.setPassword(jdbcPassword);

        return dataSource;
    }

    @Bean
    public JdbcTemplate JdbcTemplate() {

        JdbcTemplate JdbcTemplate = new JdbcTemplate(dataSource());

        return JdbcTemplate;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());

        return transactionManager;
    }
}

