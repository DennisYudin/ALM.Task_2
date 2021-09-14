package dev.andrylat.task2.implementations;

import configs.AppConfigTest;
import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.entities.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfigTest.class)
@Sql(scripts = {"file:src/test/resources/createTables.sql",
        "file:src/test/resources/populateTables.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "file:src/test/resources/cleanUpDatabase.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CategoryDAOImplTest {
    private static final String SQL_SELECT_CATEGORY_ID = "SELECT category_id FROM categories WHERE name = ?";
    private static final String SQL_SELECT_ALL_CATEGORIES_ID = "SELECT category_id FROM categories";

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getCategory_ShouldGetCategoryById_WhenInputIsId() {

        long id = 1;

        String expectedName = "exhibition";
        String actualName = categoryDAO.getCategory(id).getTitle();

        assertEquals(expectedName, actualName);
    }

    @Test
    public void getCategories_ShouldGetAllCategories_WhenCallMethod() {

        List<Category> actualCategories = categoryDAO.getCategories();
        List<Long> expectedId = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_CATEGORIES_ID,
                Long.class
        );
        int actualSize = actualCategories.size();
        int expectedSize = expectedId.size();

        assertEquals(expectedSize, actualSize);

        for (Category category : actualCategories) {
            long actualCategoryId = category.getId();

            assertTrue(expectedId.contains(actualCategoryId));
        }
    }

    @Test
    public void saveCategory_ShouldSaveCategory_WhenInputIsCategoryObjectWithIdAndName() {

        Category newCategory = getCategory(4, "opera");

        categoryDAO.saveCategory(newCategory);

        String checkName = "opera";

        long expectedId = 4;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_CATEGORY_ID,
                new Object[]{checkName},
                Long.class
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    public void updateCategory_ShouldUpdateExistedCategory_WhenInputIsCategoryObjectWithIdAndName() {

        Category updatedCategory = getCategory(1, "opera");

        categoryDAO.updateCategory(updatedCategory);

        String checkName = "opera";

        int expectedId = 1;
        Integer actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_CATEGORY_ID,
                new Object[]{checkName},
                Integer.class
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    public void deleteCategory_ShouldDeleteCategoryById_WhenInputIsId() {

        long categoryId = 1;

        categoryDAO.deleteCategory(categoryId);

        List<Long> actualId = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_CATEGORIES_ID,
                Long.class
        );
        int expectedSize = 2;
        int actualSize = actualId.size();

        int checkedId = 1;

        assertEquals(expectedSize, actualSize);
        assertFalse(actualId.contains(checkedId));
    }

    private Category getCategory(long id, String title) {
        Category category = new Category();
        category.setId(id);
        category.setTitle(title);
        return category;
    }
}

