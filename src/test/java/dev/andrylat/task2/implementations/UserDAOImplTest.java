package dev.andrylat.task2.implementations;

import dev.andrylat.task2.configs.AppConfig;
import dev.andrylat.task2.dao.UserDAO;
import dev.andrylat.task2.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void shouldReturnUserById() {

        User user = new User();
        user.setId(1000);
        user.setName("Dennis");
        user.setSurname("Yudin");
        user.setEmail("dennisYudin@mail.ru");
        user.setLogin("BigBoss");
        user.setPassword("1234");
        user.setType("customer");

        userDAO.deleteUser(user.getId());
        assertNotNull(userDAO);

        userDAO.saveUser(user);

        long id = user.getId();
        User userName = userDAO.getUser(id);

        assertEquals("Dennis", userName.getName());

        userDAO.deleteUser(user.getId());
    }

    @Test
    public void shouldReturnAllUsers() {

        User user = new User();
        user.setId(1000);
        user.setName("Dennis");
        user.setSurname("Yudin");
        user.setEmail("dennisYudin@mail.ru");
        user.setLogin("BigBoss");
        user.setPassword("1234");
        user.setType("customer");

        userDAO.deleteUser(user.getId());
        assertNotNull(userDAO);

        userDAO.saveUser(user);

        List<User> users = userDAO.getUsers();

        assertTrue(users.size() == 1);

        userDAO.deleteUser(user.getId());
    }

    @Test
    public void shouldSaveUserIntoTable() {

        User user = new User();
        user.setId(1000);
        user.setName("Dennis");
        user.setSurname("Yudin");
        user.setEmail("dennisYudin@mail.ru");
        user.setLogin("BigBoss");
        user.setPassword("1234");
        user.setType("customer");

        userDAO.deleteUser(user.getId());
        assertNotNull(userDAO);

        userDAO.saveUser(user);

        long id = user.getId();
        User userName = userDAO.getUser(id);

        assertEquals("Dennis", userName.getName());

        userDAO.deleteUser(user.getId());
    }

    @Test
    public void shouldUpdateUser() {

        User user = new User();
        user.setId(1000);
        user.setName("Dennis");
        user.setSurname("Yudin");
        user.setEmail("dennisYudin@mail.ru");
        user.setLogin("BigBoss");
        user.setPassword("1234");
        user.setType("customer");

        userDAO.deleteUser(user.getId());

        assertNotNull(userDAO);
        userDAO.saveUser(user);

        user.setName("Mark");

        userDAO.updateUser(user);

        long id = user.getId();
        User userName = userDAO.getUser(id);

        assertEquals("Mark", userName.getName());

        userDAO.deleteUser(user.getId());
    }

    @Test
    public void shouldDeleteUser() {

        User user = new User();
        user.setId(1000);
        user.setName("Dennis");
        user.setSurname("Yudin");
        user.setEmail("dennisYudin@mail.ru");
        user.setLogin("BigBoss");
        user.setPassword("1234");
        user.setType("customer");

        userDAO.deleteUser(user.getId());

        assertNotNull(userDAO);
        userDAO.saveUser(user);

        userDAO.deleteUser(user.getId());
    }
}
