package dev.andrylat.task2.configs;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
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

        theDataSource.setDriverClass(propertyDataHolder.getProperty("jdbc.driver"));
        theDataSource.setJdbcUrl(propertyDataHolder.getProperty("jdbc.url"));
        theDataSource.setUser(propertyDataHolder.getProperty("jdbc.user"));
        theDataSource.setPassword(propertyDataHolder.getProperty("jdbc.password"));

        return theDataSource;
    }
}

