package dev.andrylat.task2.configs;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import dev.andrylat.task2.dao.*;
import dev.andrylat.task2.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

@Configuration
//@EnableWebMvc
@ComponentScan(basePackages = "dev.andrylat.task2")
@PropertySource("classpath:persistence-postgresql.properties")
public class AppConfig {

    @Autowired
    private Environment propertyDataHolder;

    @Bean
    public ViewResolver viewResolver() {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource theDataSource = new DriverManagerDataSource();

        String jdbcDriver = propertyDataHolder.getProperty("jdbc.driver");
        String jdbcUrl = propertyDataHolder.getProperty("jdbc.url");
        String jdbcUser = propertyDataHolder.getProperty("jdbc.user");
        String jdbcPassword = propertyDataHolder.getProperty("jdbc.password");

        theDataSource.setDriverClass(jdbcDriver);
        theDataSource.setJdbcUrl(jdbcUrl);
        theDataSource.setUser(jdbcUser);
        theDataSource.setPassword(jdbcPassword);

        return theDataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParamJdbcTemplate() {

        NamedParameterJdbcTemplate namedParamJdbcTemplate =
                new NamedParameterJdbcTemplate(dataSource());

        return namedParamJdbcTemplate;
    }

    @Bean
    public CategoryDAO categoryDAO() {
        return new CategoryDAOImpl(namedParamJdbcTemplate());
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImpl(namedParamJdbcTemplate());
    }

    @Bean
    public LocationDAO locationDAO() {
        return new LocationDAOImpl(namedParamJdbcTemplate());
    }

    @Bean
    public EventDAO eventDAO() {
        return new EventDAOImpl(namedParamJdbcTemplate());
    }
}

