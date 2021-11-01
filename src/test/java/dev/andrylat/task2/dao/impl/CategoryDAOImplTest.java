package dev.andrylat.task2.dao.impl;

import dev.andrylat.task2.configs.AppConfigTest;
import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.entities.Category;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfigTest.class)
@Sql(scripts = {
        "file:src/test/resources/createTables.sql",
        "file:src/test/resources/populateTables.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "file:src/test/resources/cleanUpTables.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CategoryDAOImplTest {
    private static final String SQL_SELECT_CATEGORY_ID = "SELECT category_id FROM categories WHERE name = ?";
    private static final String SQL_SELECT_ALL_CATEGORIES_ID = "SELECT category_id FROM categories";

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getById_ShouldReturnCategoryById_WhenInputIsId() {

        Category expectedCategory = getCategory(1, "exhibition");
        Category actualCategory = categoryDAO.getById(1);

        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    public void getById_ShouldThrowDataNotFoundException_WhenInputIsIncorrectId() {

        Throwable exception = assertThrows(DataNotFoundException.class,
                () -> categoryDAO.getById(-1));

        String expected = "There is no such category with id = -1";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void findAll_ShouldReturnAllCategoriesSortedByName_WhenInputIsPageRequestWithoutSort() {

        Pageable sortedByName = PageRequest.of(0, 4);

        List<Category> actualCategories = categoryDAO.findAll(sortedByName);
        List<String> expectedCategoryNames = new ArrayList<>();

        expectedCategoryNames.add("Art concert");
        expectedCategoryNames.add("exhibition");
        expectedCategoryNames.add("movie");
        expectedCategoryNames.add("theatre");

        for (int i = 0; i < actualCategories.size(); i++) {

            String actualCategoryName = actualCategories.get(i).getTitle();
            String expectedCategoryName = expectedCategoryNames.get(i);

            assertEquals(expectedCategoryName, actualCategoryName);
        }
    }

    @Test
    public void findAll_ShouldReturnTwoCategoriesSortedByName_WhenInputIsPageRequestWithSizeTwoWithoutSort() {

        Pageable sortedByName = PageRequest.of(0, 2);

        List<Category> actualCategoryNames = categoryDAO.findAll(sortedByName);
        List<String> expectedCategoryNames = new ArrayList<>();

        expectedCategoryNames.add("Art concert");
        expectedCategoryNames.add("exhibition");

        for (int i = 0; i < actualCategoryNames.size(); i++) {

            String actualCategoryName = actualCategoryNames.get(i).getTitle();
            String expectedCategoryName = expectedCategoryNames.get(i);

            assertEquals(expectedCategoryName, actualCategoryName);
        }
    }

    @Test
    public void findAll_ShouldReturnAllCategoriesSortedById_WhenInputIsPageRequestWithSortValue() {

        Pageable sortedById = PageRequest.of(0, 1, Sort.by("category_id"));

        List<Category> actualCategoryIDs = categoryDAO.findAll(sortedById);
        List<Integer> expectedCategoryIDs = new ArrayList<>();

        expectedCategoryIDs.add(1);
        expectedCategoryIDs.add(2);
        expectedCategoryIDs.add(3);
        expectedCategoryIDs.add(4);

        for (int i = 0; i < actualCategoryIDs.size(); i++) {
            long actualCategoryID = actualCategoryIDs.get(i).getId();
            long expectedCategoryID = expectedCategoryIDs.get(i);

            assertEquals(expectedCategoryID, actualCategoryID);
        }
    }

    @Test
    public void findAll_ShouldReturnAllCategoriesSortedByName_WhenPageIsNull() {

        Pageable page = null;

        List<Category> actualCategoryNames = categoryDAO.findAll(page);
        List<String> expectedCategoryNames = new ArrayList<>();

        expectedCategoryNames.add("Art concert");
        expectedCategoryNames.add("exhibition");
        expectedCategoryNames.add("movie");
        expectedCategoryNames.add("theatre");

        for (int i = 0; i < actualCategoryNames.size(); i++) {

            String actualCategoryName = actualCategoryNames.get(i).getTitle();
            String expectedCategoryName = expectedCategoryNames.get(i);

            assertEquals(expectedCategoryName, actualCategoryName);
        }
    }

    @Test
    public void save_ShouldSaveCategory_WhenInputIsCategoryObjectWithIdAndName() {

        Category newCategory = getCategory(5, "opera");

        categoryDAO.save(newCategory);

        String checkName = "opera";

        long expectedId = 5;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_CATEGORY_ID,
                Long.class,
                checkName
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    public void update_ShouldUpdateExistedCategory_WhenInputIsCategoryObjectWithIdAndName() {

        Category updatedCategory = getCategory(1, "opera");

        categoryDAO.update(updatedCategory);

        String checkName = "opera";

        int expectedId = 1;
        Integer actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_CATEGORY_ID,
                Integer.class,
                checkName
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    public void delete_ShouldDeleteCategoryById_WhenInputIsId() {

        long categoryId = 1;

        categoryDAO.delete(categoryId);

        List<Long> actualId = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_CATEGORIES_ID,
                Long.class
        );
        int expectedSize = 3;
        int actualSize = actualId.size();

        long checkedId = 1;

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

