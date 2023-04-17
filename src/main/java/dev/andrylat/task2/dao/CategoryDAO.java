package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Category;

import java.util.List;

public interface CategoryDAO extends GenericDAO<Category> {

    List<Category> getByName(String name);

}

