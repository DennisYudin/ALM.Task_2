package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.User;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAOImpl implements UserDAO {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(long theId) {

        String sqlQuery = "SELECT * FROM users WHERE user_id = :id";

        final Map<String, Object> namedParameters = new HashMap<>();

        namedParameters.put("id", theId);

        User theUser = jdbcTemplate.queryForObject(
                sqlQuery,
                namedParameters,
                new UserRowMapper());

        return theUser;
    }

    @Override
    public List<User> getUsers() {

        String sqlQuery = "SELECT * FROM users ORDER BY name";

        List<User> users = jdbcTemplate.query(
                sqlQuery,
                new UserRowMapper());

        return users;
    }

    @Override
    public void saveUser(User theUser) {

        String sqlQuery = "INSERT INTO users (user_id, name, surname, email, login, password,type) " +
                "VALUES (:id, :name, :surname, :email, :login, :password, :type)";

        long id = theUser.getId();
        String name = theUser.getName();
        String surname = theUser.getSurname();
        String email = theUser.getEmail();
        String login = theUser.getLogin();
        String password = theUser.getPassword();
        String type = theUser.getType();

        jdbcTemplate.update(
                sqlQuery,
                Map.of(
                        "id", id,
                        "name", name,
                        "surname", surname,
                        "email", email,
                        "login", login,
                        "password", password,
                        "type", type
                )
        );
    }

    @Override
    public void updateUser(User theUser) {

        String sqlQuery = "UPDATE users SET name = :name, surname = :surname, " +
                "email = :email, login = :login, password = :password, type = :type WHERE user_id = :id";

        long id = theUser.getId();
        String name = theUser.getName();
        String surname = theUser.getSurname();
        String email = theUser.getEmail();
        String login = theUser.getLogin();
        String password = theUser.getPassword();
        String type = theUser.getType();

        jdbcTemplate.update(
                sqlQuery,
                Map.of(
                        "id", id,
                        "name", name,
                        "surname", surname,
                        "email", email,
                        "login", login,
                        "password", password,
                        "type", type
                )
        );
    }

    @Override
    public void deleteUser(long theId) {

        String sqlQuery = "DELETE FROM users WHERE user_id = :id";

        jdbcTemplate.update(
                sqlQuery,
                Map.of(
                        "id", theId
                )
        );
    }
}
