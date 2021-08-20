package dev.andrylat.task2.entities;

import java.util.Objects;

public class Location {

    private long id;
    private String title;
    private String workingHours;
    private String type;
    private String address;
    private String description;
    private int capacityPeople;

    public Location(long id, String title, String workingHours, String type,
                    String address, String description, int capacityPeople) {
        this.id = id;
        this.title = title;
        this.workingHours = workingHours;
        this.type = type;
        this.address = address;
        this.description = description;
        this.capacityPeople = capacityPeople;
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

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacityPeople() {
        return capacityPeople;
    }

    public void setCapacityPeople(int capacityPeople) {
        this.capacityPeople = capacityPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event location = (Event) o;
        return title.equals(location.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", workingHours='" + workingHours + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", capacityPeople=" + capacityPeople +
                '}';
    }
}
