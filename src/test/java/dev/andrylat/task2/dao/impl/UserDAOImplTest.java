package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.configs.AppConfigTest;
import dev.andrylat.task2.dao.UserDAO;
import dev.andrylat.task2.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfigTest.class)
@Sql(scripts = {"file:src/test/resources/createTables.sql",
        "file:src/test/resources/populateTablesWithoutTicketTable.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "file:src/test/resources/cleanUpTables.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserDAOImplTest {
    private static final String SQL_SELECT_USER_ID = "SELECT user_id " +
            "FROM users " +
            "WHERE name = ? AND surname = ? AND email = ? AND login = ? AND password = ? AND type = ?";
    private static final String SQL_SELECT_ALL_USERS_ID = "SELECT user_id FROM users";

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getById_ShouldGetUserById_WhenInputIsId() {

        long id = 2000;

        String expectedName = "Dennis";
        String expectedSurname = "Yudin";
        String expectedEmail = "dennisYudin@mail.ru";
        String expectedLogin = "Big boss";
        String expectedPassword = "0000";
        String expectedType = "customer";

        String actualName = userDAO.getById(id).getName();
        String actualSurname = userDAO.getById(id).getSurname();
        String actualEmail = userDAO.getById(id).getEmail();
        String actualLogin = userDAO.getById(id).getLogin();
        String actualPassword = userDAO.getById(id).getPassword();
        String actualType = userDAO.getById(id).getType();

        assertEquals(expectedName, actualName);
        assertEquals(expectedSurname, actualSurname);
        assertEquals(expectedEmail, actualEmail);
        assertEquals(expectedLogin, actualLogin);
        assertEquals(expectedPassword, actualPassword);
        assertEquals(expectedType, actualType);
    }

    @Test
    public void findAll_ShouldGetAllUsers_WhenCallMethod() {

        List<User> actualUsers = userDAO.findAll();
        List<Long> expectedId = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_USERS_ID,
                Long.class
        );
        int expectedSize = expectedId.size();
        int actualSize = actualUsers.size();

        assertEquals(expectedSize, actualSize);

        for (User user : actualUsers) {
            long actualUserId = user.getId();

            assertTrue(expectedId.contains(actualUserId));
        }
    }

    @Test
    public void save_ShouldSaveNewUser_WhenInputIsUserObjectWithDetails() {

        User newUser = getUser(
                2002, "Vandam",
                "Ivanov", "machoMan2013@yandex.ru",
                "wondefulFlower", "1234",
                "customer"
        );

        userDAO.save(newUser);

        String checkName = "Vandam";
        String checkSurname = "Ivanov";
        String checkEmail = "machoMan2013@yandex.ru";
        String checkLogin = "wondefulFlower";
        String checkPassword = "1234";
        String checkType = "customer";

        long expectedId = 2002;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_USER_ID,
                new Object[]{checkName, checkSurname, checkEmail,
                        checkLogin, checkPassword, checkType},
                Long.class
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    public void update_ShouldUpdateExistedUser_WhenInputIsUserObjectWithDetails() {

        User updatedUser = getUser(
                2000, "Oleg",
                "Petrov", "machoMan2013@yandex.ru",
                "wondefulFlower", "0000",
                "customer"
        );

        userDAO.update(updatedUser);

        String checkName = "Oleg";
        String checkSurname = "Petrov";
        String checkEmail = "machoMan2013@yandex.ru";
        String checkLogin = "wondefulFlower";
        String checkPassword = "0000";
        String checkType = "customer";

        long expectedId = 2000;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_USER_ID,
                new Object[]{checkName, checkSurname, checkEmail,
                        checkLogin, checkPassword, checkType},
                Long.class
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    public void delete_ShouldDeleteUserById_WhenInputIsId() {

        long userId = 2000;

        userDAO.delete(userId);

        List<Long> actualId = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_USERS_ID,
                Long.class
        );
        int expectedSize = 1;
        int actualSize = actualId.size();

        int checkedId = 2000;

        assertEquals(expectedSize, actualSize);
        assertFalse(actualId.contains(checkedId));
    }

    private User getUser(long id, String name,
                         String surname, String email,
                         String login, String password,
                         String type) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setType(type);
        return user;
    }
}
