package dev.andrylat.task2.services;

import dev.andrylat.task2.entities.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(long id);

    List<Category> findAllCategories(Pageable pageable);

    void saveCategory(Category category);

    void deleteCategoryById(long id);
}

