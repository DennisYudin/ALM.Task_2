package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.configs.AppConfigTest;
import dev.andrylat.task2.dao.UserDAO;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.User;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfigTest.class)
@Sql(scripts = {
        "file:src/test/resources/createTables.sql",
        "file:src/test/resources/populateTablesWithoutTicketTable.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "file:src/test/resources/cleanUpTables.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserDAOImplTest {
    private static final String SQL_SELECT_USER_ID = "" +
            "SELECT user_id " +
            "FROM users " +
            "WHERE name = ? AND surname = ? AND email = ? AND login = ? AND password = ? AND type = ?";
    private static final String SQL_SELECT_ALL_USERS_ID = "SELECT user_id FROM users";
    private static final String SQL_SELECT_ALL_EVENTS_BY_USER_ID = "" +
            "SELECT event_id " +
            "FROM event_subscriptions " +
            "WHERE user_id = ?";
    private static final String SQL_SELECT_EVENT_ID_BY_USER_ID_AND_EVENT_ID = "" +
            "SELECT event_id " +
            "FROM event_subscriptions " +
            "WHERE user_id = ? AND event_id = ?";

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getById_ShouldReturnUserById_WhenInputIsId() {

        User expectedUser = getUser(
                2000, "Dennis", "Yudin",
                "dennisYudin@mail.ru", "Big boss",
                "0000", "customer");

        User actualUser = userDAO.getById(2000);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getById_ShouldThrowDataNotFoundException_WhenInputIsIncorrectId() {

        Throwable exception = assertThrows(DataNotFoundException.class,
                () -> userDAO.getById(-1));

        String expected = "There is no such user with id = -1";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void findAll_ShouldReturnAllUsersSortedByName_WhenInputIsPageRequestWithoutSort() {

        Pageable sortedByName = PageRequest.of(0, 2);

        List<User> actualUsers = userDAO.findAll(sortedByName);
        List<User> expectedUsers = new ArrayList<>();

        User firstUser = getUser(
                2000, "Dennis",
                "Yudin", "dennisYudin@mail.ru",
                "Big boss", "0000", "customer");

        User secondUser = getUser(
                2001, "Mark",
                "Batmanov", "redDragon@mail.ru",
                "HelloWorld", "1234", "customer");

        expectedUsers.add(firstUser);
        expectedUsers.add(secondUser);

        for (int i = 0; i < actualUsers.size(); i++) {

            User actualUser = actualUsers.get(i);
            User expectedUser = expectedUsers.get(i);

            assertEquals(expectedUser, actualUser);
        }
    }

    @Test
    void findAll_ShouldReturnOneUsersSortedByName_WhenInputIsPageWithSizeOne() {

        Pageable sortedByName = PageRequest.of(0, 1);

        List<User> actualUsers = userDAO.findAll(sortedByName);
        List<User> expectedUsers = new ArrayList<>();

        User firstUser = getUser(
                2000, "Dennis",
                "Yudin", "dennisYudin@mail.ru",
                "Big boss", "0000", "customer");

        expectedUsers.add(firstUser);

        for (int i = 0; i < actualUsers.size(); i++) {

            User actualUser = actualUsers.get(i);
            User expectedUser = expectedUsers.get(i);

            assertEquals(expectedUser, actualUser);
        }
    }

    @Test
    void findAll_ShouldReturnAllUsersSortedBySurname_WhenInputIsPageRequestWithSortValue() {

        Pageable sortedBySurname = PageRequest.of(0, 2, Sort.by("surname"));

        List<User> actualUsers = userDAO.findAll(sortedBySurname);
        List<User> expectedUsers = new ArrayList<>();

        User firstUser = getUser(
                2000, "Dennis",
                "Yudin", "dennisYudin@mail.ru",
                "Big boss", "0000", "customer");

        User secondUser = getUser(
                2001, "Mark",
                "Batmanov", "redDragon@mail.ru",
                "HelloWorld", "1234", "customer");

        expectedUsers.add(secondUser);
        expectedUsers.add(firstUser);

        for (int i = 0; i < actualUsers.size(); i++) {

            User actualUser = actualUsers.get(i);
            User expectedUser = expectedUsers.get(i);

            assertEquals(expectedUser, actualUser);
        }
    }

    @Test
    void findAll_ShouldReturnAllUsersSortedByName_WhenPageIsNull() {

        Pageable page = null;

        List<User> actualUsers = userDAO.findAll(page);
        List<User> expectedUsers = new ArrayList<>();

        User firstUser = getUser(
                2000, "Dennis",
                "Yudin", "dennisYudin@mail.ru",
                "Big boss", "0000", "customer");

        User secondUser = getUser(
                2001, "Mark",
                "Batmanov", "redDragon@mail.ru",
                "HelloWorld", "1234", "customer");

        expectedUsers.add(firstUser);
        expectedUsers.add(secondUser);

        for (int i = 0; i < actualUsers.size(); i++) {

            User actualUser = actualUsers.get(i);
            User expectedUser = expectedUsers.get(i);

            assertEquals(expectedUser, actualUser);
        }
    }

    @Test
    void save_ShouldSaveNewUser_WhenInputIsUserObjectWithDetails() {

        User newUser = getUser(
                2002, "Vandam",
                "Ivanov", "machoMan2013@yandex.ru",
                "wonderfulFlower", "1234",
                "customer"
        );

        userDAO.save(newUser);

        String checkName = "Vandam";
        String checkSurname = "Ivanov";
        String checkEmail = "machoMan2013@yandex.ru";
        String checkLogin = "wonderfulFlower";
        String checkPassword = "1234";
        String checkType = "customer";

        long expectedId = 2002;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_USER_ID,
                Long.class,
                checkName, checkSurname, checkEmail,
                checkLogin, checkPassword, checkType
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    void update_ShouldUpdateExistedUser_WhenInputIsUserObjectWithDetails() {

        User updatedUser = getUser(
                2000, "Oleg",
                "Petrov", "machoMan2013@yandex.ru",
                "wonderfulFlower", "0000",
                "customer"
        );

        userDAO.update(updatedUser);

        String checkName = "Oleg";
        String checkSurname = "Petrov";
        String checkEmail = "machoMan2013@yandex.ru";
        String checkLogin = "wonderfulFlower";
        String checkPassword = "0000";
        String checkType = "customer";

        long expectedId = 2000;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_USER_ID,
                Long.class,
                checkName, checkSurname, checkEmail,
                checkLogin, checkPassword, checkType
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    void delete_ShouldDeleteUserById_WhenInputIsId() {

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

    @Test
    void getAllEventsByUserId_ShouldReturnAllEvents_WhenInputIsUserId() {

        long id = 2001;

        List<String> actualEvents = userDAO.getAllEventsByUserId(id);
        List<String> expectedEvents = new ArrayList<>(Arrays.asList("Oxxxymiron concert"));

        int expectedSize = expectedEvents.size();
        int actualSize = actualEvents.size();

        assertEquals(expectedSize, actualSize);
        assertTrue(expectedEvents.containsAll(actualEvents));
    }

    @Test
    void addNewEvent_ShouldAddNewEvent_WhenInputIsUserIdAndEventId() {

        long userId = 2001;
        long eventId = 1001;

        userDAO.addNewEvent(userId, eventId);

        long checkUserId = 2001;
        long checkEventId = 1001;

        long expectedId = 1001;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_EVENT_ID_BY_USER_ID_AND_EVENT_ID,
                Long.class,
                checkUserId, checkEventId
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    void removeEvent_ShouldDeleteEvent_WhenInputIsUserIdIdAndEventId() {

        long userId = 2000;
        long eventId = 1000;

        userDAO.removeEvent(userId, eventId);

        List<Long> actualEventIDs = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_EVENTS_BY_USER_ID,
                Long.class,
                userId
        );
        int expectedSize = 1;
        int actualSize = actualEventIDs.size();

        assertEquals(expectedSize, actualSize);
        assertFalse(actualEventIDs.contains(eventId));
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

