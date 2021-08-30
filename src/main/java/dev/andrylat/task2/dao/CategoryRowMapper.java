package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {

        Category category = new Category();

        long id = rs.getLong("category_id");
        String name = rs.getString("name");

        category.setId(id);
        category.setTitle(name);

        return category;
    }
}

