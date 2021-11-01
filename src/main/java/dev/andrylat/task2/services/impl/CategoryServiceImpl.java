package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.entities.Category;
import dev.andrylat.task2.exceptions.DAOException;
import dev.andrylat.task2.exceptions.DataNotFoundException;
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

        validateId(id);

        Category category;
        try {
            category = categoryDAO.getById(id);
        } catch (DataNotFoundException ex) {
            logger.error("There is no such category with id = " + id);
            throw new ServiceException("There is no such category with id = " + id, ex);
        } catch (DAOException ex) {
            logger.error("Something went wrong when trying to call the method getCategoryById()");
            throw new ServiceException("Something went wrong when trying to call the method getCategoryById()", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Category is " + category);
        }
        return category;
    }

    private void validateId(long id) {
        logger.debug("Call method validateId() with id = " + id);
        if (id <= 0) {
            logger.error("id can not be less or equals zero");
            throw new ServiceException("id can not be less or equals zero");
        }
    }

    @Override
    public List<Category> findAllCategories(Pageable pageable) {
        logger.debug("Call method findAllCategories()");

        List<Category> categories;
        try {
            categories = categoryDAO.findAll(pageable);
        } catch (DAOException ex) {
            logger.error("Could not get categories", ex);
            throw new ServiceException("Could not get categories", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Categories are " + categories);
        }
        return categories;
    }

    @Override
    public void saveCategory(Category category) {
        logger.debug("Call method saveCategory() for category with id = " + category.getId());

        validate(category);
    }

    private void validate(Category category) {
        logger.debug("Call method validate() for category with id = " + category.getId());

        validateId(category.getId());

        try {
            categoryDAO.getById(category.getId());

            updateCategory(category);
        } catch (DataNotFoundException ex) {
            saveNewCategory(category);
        } catch (DAOException ex) {
            throw new ServiceException("Something went wrong when trying to call the method saveCategory()", ex);
        }
    }

    private void saveNewCategory(Category category) {
        logger.debug("Call method saveNewCategory() for category with id = " + category.getId());

        try {
            categoryDAO.save(category);
        } catch (DAOException ex) {
            logger.error("Could not save category with id = " + category.getId(), ex);
            throw new ServiceException("Could not save category with id = " + category.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(category + " is added in DB");
        }
    }

    private void updateCategory(Category category) {
        logger.debug("Call method updateCategory() for category with id = " + category.getId());

        try {
            categoryDAO.update(category);
        } catch (DAOException ex) {
            logger.error("Could not update category with id = " + category.getId(), ex);
            throw new ServiceException("Could not update category with id = " + category.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(category + " is updated in DB");
        }
    }

    @Override
    public void deleteCategoryById(long id) {
        logger.debug("Call method deleteCategoryById() with id = " + id);

        validateId(id);

        try {
            categoryDAO.delete(id);
        } catch (DAOException ex) {
            logger.error("Could not delete category", ex);
            throw new ServiceException("Could not delete category", ex);
        }
        logger.debug("Category with id = " + id + " is deleted in DB");
    }
}
