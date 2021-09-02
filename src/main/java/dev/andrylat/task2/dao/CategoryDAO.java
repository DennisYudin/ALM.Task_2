package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CategoryDAO {

    Category getCategory(long id);
    List<Category> getCategories();
    void saveCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(long id);

//    Page<Category> sortByName(Pageable page); для будущих времен... сюда не смотри
}
