package dev.andrylat.task2.implementations;

import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.entities.Category;
import dev.andrylat.task2.mappers.CategoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {
    private static final String SQL_SELECT_CATEGORY = "SELECT * FROM categories WHERE category_id = ?";
    private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT * FROM categories ORDER BY name";
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
    public Category getCategory(long id) {

        Category category = jdbcTemplate.queryForObject(
                SQL_SELECT_CATEGORY,
                new Object[]{id},
                categoryRowMapper
        );
        return category;
    }

    @Override
    public List<Category> getCategories() {

        List<Category> categories = jdbcTemplate.query(
                SQL_SELECT_ALL_CATEGORIES,
                new CategoryRowMapper()
        );
        return categories;
    }

    @Override
    public void saveCategory(Category category) {

        long id = category.getId();
        String title = category.getTitle();

        jdbcTemplate.update(
                SQL_SAVE_CATEGORY,
                id, title
        );
    }

    @Override
    public void updateCategory(Category category) {

        long id = category.getId();
        String title = category.getTitle();

        jdbcTemplate.update(
                SQL_UPDATE_CATEGORY,
                title, id
        );
    }

    @Override
    public void deleteCategory(long id) {

        jdbcTemplate.update(
                SQL_DELETE_CATEGORY,
                id
        );
    }

//    @Override Для ближайшего будущего
//    public Page<Category> sortByName(Pageable page) {
//        String sql = "SELECT * FROM categories ORDER BY ?";
//
//        Sort.Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Sort.Order.by("name");
//
//        List<Category> categories = jdbcTemplate.query(
//                sql,
//                new Object[]{order.getDirection().name()},
//                new CategoryRowMapper()
//        );
//        return new PageImpl<Category>(categories, page, 10);
//    }
}
