package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.entities.Category;
import dev.andrylat.task2.exceptions.DAOException;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import dev.andrylat.task2.mappers.CategoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {
    private static final String SQL_SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE category_id = ?";
    private static final String SQL_SELECT_ALL_CATEGORIES_ORDER_BY = "SELECT * FROM categories ORDER BY";
    private static final String SQL_SELECT_ALL_CATEGORIES_ORDER_BY_NAME = "SELECT * FROM categories ORDER BY name";
    private static final String SQL_SAVE_CATEGORY = "INSERT INTO categories (category_id, name) VALUES (?, ?)";
    private static final String SQL_UPDATE_CATEGORY = "UPDATE categories SET name = ? WHERE category_id = ?";
    private static final String SQL_DELETE_CATEGORY = "DELETE FROM categories WHERE category_id = ?";
    private static final String SORT_BY_COLUMN = "name";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private CategoryRowMapper categoryRowMapper;

    @Override
    public Category getById(long id) {

        Category category;
        try {
            category = getCategory(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new DataNotFoundException(
                    "There is no such category with id = " + id, ex);
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method getById()", ex);
        }
        return category;
    }

    private Category getCategory(long id) {
        Category category = jdbcTemplate.queryForObject(
                SQL_SELECT_CATEGORY_BY_ID,
                categoryRowMapper,
                id
        );
        return category;
    }

    @Override
    public List<Category> findAll(Pageable page) {

        String sqlQuery = getSqlQuery(page);

        List<Category> categories;
        try {
            categories = getCategories(sqlQuery);
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method findAll()", ex);
        }
        return categories;
    }

    private List<Category> getCategories(String query) {
        List<Category> result = jdbcTemplate.query(
                query,
                categoryRowMapper
        );
        return result;
    }

    private String getSqlQuery(Pageable pageable) {
        String query = SQL_SELECT_ALL_CATEGORIES_ORDER_BY_NAME;
        if (pageable != null) {
            query = buildSqlQuery(pageable);
        }
        return query;
    }

    private String buildSqlQuery(Pageable pageable) {
        String query;
        if (pageable.getSort().isEmpty()) {
            Sort.Order order = Sort.Order.by(SORT_BY_COLUMN);

            query = collectSqlQuery(pageable, order);
        } else {
            Sort.Order order = pageable.getSort().iterator().next();

            query = collectSqlQuery(pageable, order);
        }
        return query;
    }

    private String collectSqlQuery(Pageable pageable, Sort.Order sort) {

        String sortProperty = sort.getProperty();
        String sortDirectionName = sort.getDirection().name();
        String limit = "LIMIT";
        int pageSize = pageable.getPageSize();
        String offset = "OFFSET";
        long pageOffset = pageable.getOffset();

        String result = String.format(
                SQL_SELECT_ALL_CATEGORIES_ORDER_BY + " %1$s %2$s %3$s %4$d %5$s %6$d",
                sortProperty, sortDirectionName, limit, pageSize, offset, pageOffset);

        return result;
    }

    @Override
    public void save(Category category) {

        long id = category.getId();
        String title = category.getTitle();

        try {
            jdbcTemplate.update(
                    SQL_SAVE_CATEGORY,
                    id, title
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method save()", ex);
        }
    }

    @Override
    public void update(Category category) {

        long id = category.getId();
        String title = category.getTitle();
        try {
            jdbcTemplate.update(
                    SQL_UPDATE_CATEGORY,
                    title, id
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method update()", ex);
        }
    }

    @Override
    public void delete(long id) {
        try {
            jdbcTemplate.update(
                    SQL_DELETE_CATEGORY,
                    id
            );
        } catch (DataAccessException ex) {
            throw new DAOException(
                    "Something went wrong when trying to call the method delete()", ex);
        }
    }
}


