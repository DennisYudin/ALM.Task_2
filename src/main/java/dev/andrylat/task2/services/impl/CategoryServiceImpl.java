package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.entities.Category;
import dev.andrylat.task2.exceptions.ServiceException;
import dev.andrylat.task2.services.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public Category getCategoryById(long id) {
        logger.debug("Call method getCategoryById() with id = " + id);

        Category category;
        try {
            category = categoryDAO.getById(id);
        } catch (Exception ex) {
            logger.error("Could not get category by id = " + id, ex);
            throw new ServiceException("Could not get category by id = " + id, ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Category is " + category.toString());
        }
        return category;
    }

    @Override
    public List<Category> findAllCategories(Pageable pageable) {
        logger.debug("Call method findAllCategories()");

        List<Category> categories;
        try {
            categories = categoryDAO.findAll(pageable);
        } catch (Exception ex) {
            logger.error("Could not get categories", ex);
            throw new ServiceException("Could not get categories", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Categories are " + categories.toString());
        }
        return categories;
    }

    @Override
    public void saveCategory(Category category) {

        Category resultQuery = categoryDAO.getById(category.getId());

        if (resultQuery == null) {
            saveNewCategory(category);
        } else {
            updateCategory(category);
        }
    }

    private void saveNewCategory(Category category) {
        logger.debug("Call method saveCategory() for category with id = " + category.getId());

        try {
            categoryDAO.save(category);
        } catch (Exception ex) {
            logger.error("Could not save category with id = " + category.getId(), ex);
            throw new ServiceException("Could not save category with id = " + category.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(category.toString() + "is added in DB");
        }
    }

    private void updateCategory(Category category) {
        logger.debug("Call method updateCategory() for category with id = " + category.getId());

        try {
            categoryDAO.update(category);
        } catch (Exception ex) {
            logger.error("Could not update category with id = " + category.getId(), ex);
            throw new ServiceException("Could not update category with id = " + category.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(category.toString() + "is updated in DB");
        }
    }

    @Override
    public void deleteCategoryById(long id) {
        logger.debug("Call method deleteCategoryById() with id = " + id);

        try {
            categoryDAO.delete(id);
        } catch (Exception ex) {
            logger.error("Could not delete category", ex);
            throw new ServiceException("Could not delete category", ex);
        }
        logger.debug("Category with id = " + id + " is deleted in DB");
    }
}
