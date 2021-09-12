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
    private static final String SQL_SELECT_CATEGORY = "SELECT name FROM categories WHERE category_id = ?";
    private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT name FROM categories";

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getCategory_ShouldGetCategoryById_WhenInputIsId() {

        long id = 1;
        String actualCategory = categoryDAO.getCategory(id).getTitle();
        String expectedCategory = "exhibition";

        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    public void getCategories_ShouldGetAllCategories_WhenCallMethod() {

        List<Category> expectedCategories = categoryDAO.getCategories();
        List<String> actualCategories = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_CATEGORIES,
                String.class
        );
        int expectedSize = expectedCategories.size();
        int actualSize = actualCategories.size();

        assertEquals(expectedSize, actualSize);

        for (Category category : expectedCategories) {
            String expectedCategory = category.getTitle();

            assertTrue(actualCategories.contains(expectedCategory));
        }
    }

    @Test
    public void saveCategory_ShouldSaveCategory_WhenInputIsCategoryObjectWithIdAndName() {

        Category newCategory = new Category();
        newCategory.setId(4);
        newCategory.setTitle("opera");

        categoryDAO.saveCategory(newCategory);

        long checkId = 4;
        String expectedCategoryName = "opera";
        String actualCategoryName = jdbcTemplate.queryForObject(
                SQL_SELECT_CATEGORY,
                new Object[]{checkId},
                String.class
        );
        assertEquals(expectedCategoryName, actualCategoryName);
    }

    @Test
    public void updateCategory_ShouldUpdateExistedCategory_WhenInputIsCategoryObjectWithIdAndName() {

        Category newCategory = new Category();
        newCategory.setId(1);
        newCategory.setTitle("opera");

        categoryDAO.updateCategory(newCategory);

        long checkId = 1;
        String expectedCategoryName = "opera";
        String actualCategoryName = jdbcTemplate.queryForObject(
                SQL_SELECT_CATEGORY,
                new Object[]{checkId},
                String.class
        );
        assertEquals(expectedCategoryName, actualCategoryName);
    }

    @Test
    public void deleteCategory_ShouldDeleteCategoryById_WhenInputIsId() {

        long categoryId = 1;

        categoryDAO.deleteCategory(categoryId);

        int expectedSize = 2;
        List<String> сategories = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_CATEGORIES,
                String.class
        );
        int actualSize = сategories.size();
        String checkedName = "exhibition";

        assertEquals(expectedSize, actualSize);
        assertFalse(сategories.contains(checkedName));
    }
}
