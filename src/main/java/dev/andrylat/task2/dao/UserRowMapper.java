package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();

        long id = rs.getLong("user_id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String password = rs.getString("password");
        String type = rs.getString("type");

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
