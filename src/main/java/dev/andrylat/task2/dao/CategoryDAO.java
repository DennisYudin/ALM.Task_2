package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Category;

import java.util.List;

public interface CategoryDAO {

    Category getCategory(long id);
    List<Category> findAll();
    void save(Category category);
    void update(Category category);
    void delete(long id);

//    Page<Category> sortByName(Pageable page); для будущих времен... сюда не смотри
}
