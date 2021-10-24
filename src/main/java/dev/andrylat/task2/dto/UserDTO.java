package dev.andrylat.task2.dto;

import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.Ticket;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private long id;
    private String name;
    private String surname;
    private String email;

    private List<Event> events;

    private UserDTO(Builder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
        this.surname = builder.getSurname();
        this.email = builder.getEmail();
        this.events = builder.getEvents();
    }

    public static class Builder {
        private long id;
        private String name;
        private String surname;
        private String email;

        private List<Event> events;

        private Ticket ticket;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder events(List<Event> events) {
            this.events = events;
            return this;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getEmail() {
            return email;
        }

        public List<Event> getEvents() {
            return events;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }
    }
}

