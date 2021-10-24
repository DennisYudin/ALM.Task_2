package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.configs.AppConfigTest;
import dev.andrylat.task2.entities.Category;
import dev.andrylat.task2.exceptions.ServiceException;
import dev.andrylat.task2.services.CategoryService;
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
class CategoryServiceImplTest {
    private static final String SQL_SELECT_CATEGORY_ID = "SELECT category_id FROM categories WHERE name = ?";
    private static final String SQL_SELECT_ALL_CATEGORIES_ID = "SELECT category_id FROM categories";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getCategoryById_ShouldGetCategoryById_WhenInputIsId() {

        Category expectedCategory = getCategory(1, "exhibition");
        Category actualCategory = categoryService.getCategoryById(1);

        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void getCategoryById_ShouldThrowServiceException_WhenInputIsZero() {

        Throwable exception = assertThrows(ServiceException.class,
                () -> categoryService.getCategoryById(0));

        String expected = "Could not get category by id = 0";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void findAllCategories_ShouldGetAllCategoriesSortedByName_WhenInputIsPageRequestWithoutSort() {

        Pageable sortedByName = PageRequest.of(0, 4);

        List<Category> actualCategories = categoryService.findAllCategories(sortedByName);

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
    public void findAllCategories_ShouldGetTwoCategoriesSortedByName_WhenInputIsPageRequestWithSizeTwoWithoutSort() {

        Pageable sortedByName = PageRequest.of(0, 2);

        List<Category> actualCategoryNames = categoryService.findAllCategories(sortedByName);
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
    public void findAllCategories_ShouldGetAllCategoriesSortedById_WhenInputIsPageRequestWithSortValue() {

        Pageable sortedById = PageRequest.of(0, 4, Sort.by("category_id"));

        List<Category> actualCategoryIDs = categoryService.findAllCategories(sortedById);
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
    public void findAllCategories_ShouldGetAllCategoriesSortedByName_WhenPageIsNull() {

        Pageable page = null;

        List<Category> actualCategoryNames = categoryService.findAllCategories(page);
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
    void saveCategory_ShouldSaveCategory_WhenInputIsNewCategoryWithIdAndName() {

        Category newCategory = getCategory(5, "opera");

        categoryService.saveCategory(newCategory);

        String checkName = "opera";

        long expectedId = 5;
        Long actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_CATEGORY_ID,
                new Object[]{checkName},
                Long.class
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    void deleteCategoryById() {
    }

    private Category getCategory(long id, String title) {
        Category category = new Category();
        category.setId(id);
        category.setTitle(title);

        return category;
    }
}

