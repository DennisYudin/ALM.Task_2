package dev.andrylat.task2.implementations;

import dev.andrylat.task2.configs.AppConfig;
import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.entities.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class CategoryDAOImplTest {

    @Autowired
    private CategoryDAO categoryDAO;

    @Test
    public void shouldReturnCategoryById() {

        Category category = new Category();
        category.setId(1000);
        category.setTitle("exhibition");

        categoryDAO.deleteCategory(category.getId());

        assertNotNull(categoryDAO);
        categoryDAO.saveCategory(category);

        long id = category.getId();
        Category categoryTitle = categoryDAO.getCategory(id);

        assertEquals("exhibition", categoryTitle.getTitle());

        categoryDAO.deleteCategory(category.getId());
    }

    @Test
    public void shouldReturnAllCategories() {

        Category category = new Category();
        category.setId(1000);
        category.setTitle("exhibition");

        categoryDAO.deleteCategory(category.getId());
        assertNotNull(categoryDAO);

        categoryDAO.saveCategory(category);

        List<Category> categories = categoryDAO.getCategories();

        assertTrue(categories.size() == 1);

        categoryDAO.deleteCategory(category.getId());
    }

    @Test
    public void shouldSaveCategoryIntoTable() {

        Category category = new Category();
        category.setId(1000);
        category.setTitle("exhibition");

        categoryDAO.deleteCategory(category.getId());
        assertNotNull(categoryDAO);

        categoryDAO.saveCategory(category);

        long id = category.getId();
        Category categoryTitle = categoryDAO.getCategory(id);

        assertEquals("exhibition", categoryTitle.getTitle());

        categoryDAO.deleteCategory(category.getId());
    }

    @Test
    public void shouldUpdateCategory() {

        Category category = new Category();
        category.setId(1000);
        category.setTitle("exhibition");

        categoryDAO.deleteCategory(category.getId());
        assertNotNull(categoryDAO);

        categoryDAO.saveCategory(category);

        category.setId(1000);
        category.setTitle("concert");

        categoryDAO.updateCategory(category);

        long id = category.getId();
        Category categoryTitle = categoryDAO.getCategory(id);

        assertEquals("concert", categoryTitle.getTitle());

        categoryDAO.deleteCategory(category.getId());
    }

    @Test
    public void shouldDeleteCategory() {

        Category category = new Category();
        category.setId(1000);
        category.setTitle("exhibition");

        categoryDAO.deleteCategory(category.getId());

        assertNotNull(categoryDAO);
        categoryDAO.saveCategory(category);

        categoryDAO.deleteCategory(category.getId());
    }
}
