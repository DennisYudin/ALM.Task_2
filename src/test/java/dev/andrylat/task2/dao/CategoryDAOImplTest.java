package dev.andrylat.task2.dao;

import dev.andrylat.task2.configs.AppConfig;
import dev.andrylat.task2.entities.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CategoryDAOImplTest {

    @Autowired
    private CategoryDAO categoryDAO;

    @Test
    public void getCategoryById_ShouldGetCategoryById_WhenInputIsCategoryId() {

        Category category = new Category();
        category.setId(1000);
        category.setTitle("exhibition");

        categoryDAO.deleteCategory(1000);
        categoryDAO.saveCategory(category);

        long id = category.getId();
        Category categoryTitle = categoryDAO.getCategory(id);

        Assert.assertEquals("exhibition", categoryTitle.getTitle());
    }
}
