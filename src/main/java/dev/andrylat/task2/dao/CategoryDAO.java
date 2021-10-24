package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryDAO extends GenericDAO<Category> {

    Category getById(long id);

    List<Category> findAll(Pageable pageable);

    void save(Category category);

    void update(Category category);

    void delete(long id);
}

