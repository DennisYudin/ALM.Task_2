package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.entities.Category;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import dev.andrylat.task2.exceptions.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryDAO categoryDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getCategoryById_ShouldReturnCategoryById_WhenInputIsId() {

        Category expectedCategory = getCategory(1, "exhibition");

        Mockito.when(categoryDAO.getById(1)).thenReturn(expectedCategory);

        Category actualCategory = categoryService.getCategoryById(1);

        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void getCategoryById_ShouldThrowServiceException_WhenInputIsIncorrectId() {

        Mockito.when(categoryDAO.getById(-1)).thenThrow(ServiceException.class);

        Throwable exception = assertThrows(ServiceException.class,
                () -> categoryService.getCategoryById(-1));

        String expected = "id can not be less or equals zero";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void findAllCategories_ShouldReturnAllCategoriesSortedByName_WhenInputIsPageRequestWithoutSortValue() {

        Pageable sortedByName = PageRequest.of(0, 4);

        List<Category> expectedCategoryNames = new ArrayList<>();

        expectedCategoryNames.add(getCategory(4, "Art concert"));
        expectedCategoryNames.add(getCategory(1, "exhibition"));
        expectedCategoryNames.add(getCategory(2, "movie"));
        expectedCategoryNames.add(getCategory(3, "theatre"));

        Mockito.when(categoryDAO.findAll(sortedByName)).thenReturn(expectedCategoryNames);

        List<Category> actualCategories = categoryService.findAllCategories(sortedByName);

        assertTrue(expectedCategoryNames.containsAll(actualCategories));
    }

    @Test
    void findAllCategories_ShouldReturnTwoCategoriesSortedByName_WhenInputIsPageRequestWithSizeTwoWithoutSortValue() {

        Pageable sortedByName = PageRequest.of(0, 2);

        List<Category> expectedCategoryNames = new ArrayList<>();

        expectedCategoryNames.add(getCategory(4, "Art concert"));
        expectedCategoryNames.add(getCategory(1, "exhibition"));

        Mockito.when(categoryDAO.findAll(sortedByName)).thenReturn(expectedCategoryNames);

        List<Category> actualCategories = categoryService.findAllCategories(sortedByName);

        assertTrue(expectedCategoryNames.containsAll(actualCategories));

    }

    @Test
    void findAllCategories_ShouldReturnAllCategoriesSortedById_WhenInputIsPageRequestWithSortValue() {

        Pageable sortedById = PageRequest.of(0, 4, Sort.by("category_id"));

        List<Category> expectedCategoryNames = new ArrayList<>();

        expectedCategoryNames.add(getCategory(1, "exhibition"));
        expectedCategoryNames.add(getCategory(2, "movie"));
        expectedCategoryNames.add(getCategory(3, "theatre"));
        expectedCategoryNames.add(getCategory(4, "Art concert"));

        Mockito.when(categoryDAO.findAll(sortedById)).thenReturn(expectedCategoryNames);

        List<Category> actualCategories = categoryService.findAllCategories(sortedById);

        assertTrue(expectedCategoryNames.containsAll(actualCategories));
    }

    @Test
    void findAllCategories_ShouldReturnAllCategoriesSortedByName_WhenPageIsNull() {

        Pageable page = null;

        List<Category> expectedCategoryNames = new ArrayList<>();

        expectedCategoryNames.add(getCategory(1, "exhibition"));
        expectedCategoryNames.add(getCategory(2, "movie"));
        expectedCategoryNames.add(getCategory(3, "theatre"));
        expectedCategoryNames.add(getCategory(4, "Art concert"));

        Mockito.when(categoryDAO.findAll(page)).thenReturn(expectedCategoryNames);

        List<Category> actualCategories = categoryService.findAllCategories(page);

        assertTrue(expectedCategoryNames.containsAll(actualCategories));
    }

    @Test
    void saveCategory_ShouldSaveNewCategory_WhenInputIsNewCategoryWithIdAndName() {

        Category newCategory = getCategory(5, "opera");

        Mockito.when(categoryDAO.getById(5)).thenThrow(DataNotFoundException.class);

        categoryService.saveCategory(newCategory);

        Mockito.verify(categoryDAO, Mockito.times(1)).save(newCategory);
    }

    @Test
    void saveCategory_ShouldThrowServiceException_WhenInputIsHasNegativeId() {

        Category newCategory = getCategory(-1, "opera");

        Throwable exception = assertThrows(ServiceException.class,
                () -> categoryService.saveCategory(newCategory));

        String expected = "id can not be less or equals zero";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void saveCategory_ShouldUpdateExistedCategory_WhenInputIsCategoryWithDetails() {

        Category oldCategory = getCategory(1, "exhibition");
        Category updatedCategory = getCategory(1, "opera");

        Mockito.when(categoryDAO.getById(1)).thenReturn(oldCategory);

        categoryService.saveCategory(updatedCategory);

        Mockito.verify(categoryDAO, Mockito.times(1)).update(updatedCategory);
    }

    @Test
    void deleteCategoryById_ShouldDeleteCategoryById_WhenInputIsId() {

        categoryService.deleteCategoryById(1);

        Mockito.verify(categoryDAO, Mockito.times(1)).delete(1);
    }

    @Test
    void deleteCategoryById_ShouldThrowServiceException_WhenInputHasNegativeId() {

        Throwable exception = assertThrows(ServiceException.class,
                () -> categoryService.deleteCategoryById(-1));

        String expected = "id can not be less or equals zero";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    private Category getCategory(long id, String title) {
        Category category = new Category();
        category.setId(id);
        category.setTitle(title);

        return category;
    }
}

