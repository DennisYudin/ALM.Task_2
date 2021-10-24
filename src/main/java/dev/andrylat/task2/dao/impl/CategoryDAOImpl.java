package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.entities.Category;
import dev.andrylat.task2.mappers.CategoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {
    private static final String SQL_SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE category_id = ?";
    private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT * FROM categories";
    private static final String SQL_SELECT_ALL_CATEGORIES_ORDER_BY_NAME = "SELECT * FROM categories ORDER BY name";
    private static final String SQL_SAVE_CATEGORY = "INSERT INTO categories (category_id, name) VALUES (?, ?)";
    private static final String SQL_UPDATE_CATEGORY = "UPDATE categories SET name = ? WHERE category_id = ?";
    private static final String SQL_DELETE_CATEGORY = "DELETE FROM categories WHERE category_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private CategoryRowMapper categoryRowMapper;

    @Override
    public Category getById(long id) {

        Category category = jdbcTemplate.queryForObject(
                SQL_SELECT_CATEGORY_BY_ID,
                categoryRowMapper,
                new Object[]{id}
        );
        return category;
    }

    @Override
    public List<Category> findAll(Pageable page) {

        String sqlQuery;
        if (page != null) {
            sqlQuery = getSqlQuery(page);
        } else {
            sqlQuery = SQL_SELECT_ALL_CATEGORIES_ORDER_BY_NAME;
        }

        List<Category> categories = jdbcTemplate.query(
                sqlQuery,
                categoryRowMapper
        );
        return categories;
    }

    private String getSqlQuery(Pageable pageable) {
        String query;
        if (pageable.getSort().isEmpty()) {
            Sort.Order order = Sort.Order.by("name");

            query = collectSqlQuery(pageable, order);
        } else {
            Sort.Order order = pageable.getSort().toList().get(0);

            query = collectSqlQuery(pageable, order);
        }
        return query;
    }

    private String collectSqlQuery(Pageable pageable, Sort.Order sort) {

        String query = SQL_SELECT_ALL_CATEGORIES
                + " ORDER BY " + sort.getProperty() + " " + sort.getDirection().name()
                + " LIMIT " + pageable.getPageSize()
                + " OFFSET " + pageable.getOffset();

        return query;
    }

    @Override
    public void save(Category category) {

        long id = category.getId();
        String title = category.getTitle();

        jdbcTemplate.update(
                SQL_SAVE_CATEGORY,
                id, title
        );
    }

    @Override
    public void update(Category category) {

        long id = category.getId();
        String title = category.getTitle();

        jdbcTemplate.update(
                SQL_UPDATE_CATEGORY,
                title, id
        );
    }

    @Override
    public void delete(long id) {

        jdbcTemplate.update(
                SQL_DELETE_CATEGORY,
                id
        );
    }
}


