package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Category;

import java.util.List;

public interface CategoryDAO {

    Category getCategoryById(long theId);
    List<Category> getCategories();
    void saveCategory(Category theCategory);
    void updateCategory(Category theCategory);
    void deleteCategoryById(long theId);
}
