package dev.andrylat.task2.entities;

import java.util.Objects;

public class Category {

    private long id;
    private String title;

    public Category(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return title.equals(category.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
