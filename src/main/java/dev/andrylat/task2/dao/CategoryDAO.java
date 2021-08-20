package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Category;

import java.util.List;

public interface CategoryDAO {

    List<Category> findAll();
    void insert(Category category);
    void update(Category category);
    void delete(long categoryId);
}
