package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Category;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDAOImpl implements CategoryDAO {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CategoryDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Category getCategoryById(long theId) {

        String sqlQuery = "SELECT * FROM categories WHERE category_id = :id";

        final Map<String, Object> namedParameters = new HashMap<>();

        namedParameters.put("id", theId);

        Category theCategory = jdbcTemplate.queryForObject(
                sqlQuery,
                namedParameters,
                new CategoryRowMapper());

        return theCategory;
    }

    @Override
    public List<Category> getCategories() {

        String sqlQuery = "SELECT * FROM categories ORDER BY name";

        List<Category> categories = jdbcTemplate.query(
                sqlQuery,
                new CategoryRowMapper());

        return categories;
    }

    @Override
    public void saveCategory(Category theCategory) {

        String sqlQuery = "INSERT INTO categories (category_id, name) VALUES (:id, :name)";

        long id = theCategory.getId();
        String title = theCategory.getTitle();

        jdbcTemplate.update(
                sqlQuery,
                Map.of("id", id, "name", title));
    }

    @Override
    public void updateCategory(Category theCategory) {

        String sqlQuery = "UPDATE categories SET name = :name WHERE category_id = :id";

        long id = theCategory.getId();
        String title = theCategory.getTitle();

        jdbcTemplate.update(
                sqlQuery,
                Map.of(
                        "id", id,
                        "name", title
                )
        );
    }

    @Override
    public void deleteCategoryById(long theId) {

        String sqlQuery = "DELETE FROM categories WHERE category_id = :id";

        jdbcTemplate.update(
                sqlQuery,
                Map.of(
                        "id", theId
                )
        );
    }
}
