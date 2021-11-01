package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.UserDAO;
import dev.andrylat.task2.entities.User;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import dev.andrylat.task2.exceptions.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getById_ShouldReturnUserById_WhenInputIsId() {

        User expectedUser = getUser(
                2000, "Dennis", "Yudin",
                "dennisYudin@mail.ru", "Big boss",
                "0000", "customer"
        );
        Mockito.when(userDAO.getById(2000)).thenReturn(expectedUser);

        User actualUser = userService.getUserById(2000);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getById_ShouldThrowServiceException_WhenInputIsIncorrectId() {

        Mockito.when(userDAO.getById(-1)).thenThrow(ServiceException.class);

        Throwable exception = assertThrows(ServiceException.class,
                () -> userService.getUserById(-1));

        String expected = "id can not be less or equals zero";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void findAllUsers_ShouldReturnAllUsersSortedByName_WhenInputIsPageRequestWithoutSort() {

        Pageable sortedByName = PageRequest.of(0, 2);

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

        Mockito.when(userDAO.findAll(sortedByName)).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findAllUsers(sortedByName);

        assertTrue(expectedUsers.containsAll(actualUsers));
    }

    @Test
    void findAllUsers_ShouldReturnOneUserSortedByName_WhenInputIsPageWithSizeOne() {

        Pageable sortedByName = PageRequest.of(0, 1);

        List<User> expectedUsers = new ArrayList<>();

        User firstUser = getUser(
                2000, "Dennis",
                "Yudin", "dennisYudin@mail.ru",
                "Big boss", "0000", "customer");

        expectedUsers.add(firstUser);

        Mockito.when(userDAO.findAll(sortedByName)).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findAllUsers(sortedByName);

        assertTrue(expectedUsers.containsAll(actualUsers));
    }

    @Test
    void findAllUsers_ShouldReturnAllUsersSortedBySurname_WhenInputIsPageRequestWithSortValue() {

        Pageable sortedBySurname = PageRequest.of(0, 2, Sort.by("surname"));

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

        Mockito.when(userDAO.findAll(sortedBySurname)).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findAllUsers(sortedBySurname);

        assertTrue(expectedUsers.containsAll(actualUsers));
    }

    @Test
    void findAllUsers_ShouldReturnAllUsersSortedByName_WhenPageIsNull() {

        Pageable page = null;

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

        Mockito.when(userDAO.findAll(page)).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findAllUsers(page);

        assertTrue(expectedUsers.containsAll(actualUsers));
    }

    @Test
    void saveUser_ShouldSaveNewUser_WhenInputIsUserObjectWithDetails() {

        User newUser = getUser(
                2002, "Vandam",
                "Ivanov", "machoMan2013@yandex.ru",
                "wonderfulFlower", "1234",
                "customer"
        );
        Mockito.when(userDAO.getById(2002)).thenThrow(DataNotFoundException.class);

        userService.saveUser(newUser);

        Mockito.verify(userDAO, Mockito.times(1)).save(newUser);
    }

    @Test
    void saveUser_ShouldUpdateExistedUser_WhenInputIsUserObjectWithDetails() {

        User oldUser = getUser(
                2000, "Dennis",
                "Yudin", "dennisYudin@mail.ru",
                "Big boss", "0000", "customer");
        User updatedUser = getUser(
                2000, "Oleg",
                "Petrov", "machoMan2013@yandex.ru",
                "wonderfulFlower", "0000",
                "customer"
        );
        Mockito.when(userDAO.getById(200)).thenReturn(oldUser);

        userService.saveUser(updatedUser);

        Mockito.verify(userDAO, Mockito.times(1)).update(updatedUser);
    }

    @Test
    void deleteUserById_ShouldDeleteUserById_WhenInputIsId() {

        userService.deleteUserById(2000);

        Mockito.verify(userDAO, Mockito.times(1)).delete(2000);
    }

    @Test
    void deleteUserById_ShouldThrowServiceException_WhenInputHasNegativeId() {

        Throwable exception = assertThrows(ServiceException.class,
                () -> userService.deleteUserById(-1));

        String expected = "id can not be less or equals zero";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void getAllEventsByUserId_ShouldReturnAllEvents_WhenInputIsUserId() {

        List<String> expectedEvents = new ArrayList<>(Arrays.asList("Oxxxymiron concert"));

        Mockito.when(userDAO.getAllEventsByUserId(2000)).thenReturn(expectedEvents);

        List<String> actualEvents = userService.getAllEventsByUserId(2000);

        assertTrue(expectedEvents.containsAll(actualEvents));
    }

    @Test
    void getAllEventsByUserId_ShouldReturnEmptyList_WhenInputIsDoesNotExistUserId() {

        List<String> expectedEvents = new ArrayList<>();

        Mockito.when(userDAO.getAllEventsByUserId(165)).thenReturn(expectedEvents);

        List<String> actualEvents = userService.getAllEventsByUserId(165);

        assertTrue(actualEvents.isEmpty());
    }

    @Test
    void getAllEventsByUserId_ShouldThrowServiceException_WhenInputHasNegativeId() {

        Throwable exception = assertThrows(ServiceException.class,
                () -> userService.getAllEventsByUserId(-1));

        String expected = "id can not be less or equals zero";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void addNewEvent_ShouldAddNewEvent_WhenInputIsUserIdAndEventId() {

        long userId = 2001;
        long eventId = 1001;

        userService.addNewEvent(2001, 1001);

        Mockito.verify(userDAO, Mockito.times(1)).addNewEvent(userId, eventId);
    }

    @Test
    void removeEvent_ShouldDeleteEvent_WhenInputIsUserIdIdAndEventId() {

        long userId = 2000;
        long eventId = 1000;

        userService.removeEvent(userId, eventId);

        Mockito.verify(userDAO, Mockito.times(1)).removeEvent(userId, eventId);
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

