package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
    private static final String SQL_SELECT_USER = "SELECT * FROM users WHERE user_id = ?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users ORDER BY name";
    private static final String SQL_SAVE_USER = "INSERT INTO users " +
            "(user_id, name, surname, email, login, password, type) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE users " +
            "SET name = ?, surname = ?, email = ?, login = ?, password = ?, type = ? " +
            "WHERE user_id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE user_id = ?";

    private final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUser(long id) {

        User theUser = jdbcTemplate.queryForObject(
                SQL_SELECT_USER,
                new Object[]{id},
                new UserRowMapper()
        );
        return theUser;
    }

    @Override
    public List<User> getUsers() {

        List<User> users = jdbcTemplate.query(
                SQL_SELECT_ALL_USERS,
                new UserRowMapper()
        );
        return users;
    }

    @Override
    public void saveUser(User user) {

        long id = user.getId();
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        String login = user.getLogin();
        String password = user.getPassword();
        String type = user.getType();

        jdbcTemplate.update(
                SQL_SAVE_USER,
                id, name, surname, email, login, password, type
        );
    }

    @Override
    public void updateUser(User user) {

        long id = user.getId();
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        String login = user.getLogin();
        String password = user.getPassword();
        String type = user.getType();

        jdbcTemplate.update(
                SQL_UPDATE_USER,
                name, surname, email, login, password, type, id
        );
    }

    @Override
    public void deleteUser(long id) {

        jdbcTemplate.update(
                SQL_DELETE_USER,
                id
        );
    }
}
