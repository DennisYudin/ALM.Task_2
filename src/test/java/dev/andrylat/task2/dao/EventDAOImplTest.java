package dev.andrylat.task2.dao;

import dev.andrylat.task2.configs.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

public class EventDAOImplTest {

    private AnnotationConfigApplicationContext context;
    private EventDAO eventDAO;

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext(
                AppConfig.class);
        eventDAO = context.getBean(EventDAO.class);
        assertNotNull(eventDAO);
    }

    @Test
    public void test() {

    }
}
