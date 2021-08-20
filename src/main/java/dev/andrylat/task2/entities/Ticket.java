package dev.andrylat.task2.entities;

import java.util.Date;
import java.util.Objects;

public class Ticket {

    private long id;
    private String uniqueCode;
    private Date creationDate;
    private String status;
    private User customer;
    private Event event;

    public Ticket(long id, String uniqueCode, Date creationDate,
                  String status, User customer, Event event) {
        this.id = id;
        this.uniqueCode = uniqueCode;
        this.creationDate = creationDate;
        this.status = status;
        this.customer = customer;
        this.event = event;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return uniqueCode.equals(ticket.uniqueCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueCode);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", creationDate=" + creationDate +
                ", status='" + status + '\'' +
                ", customer=" + customer +
                ", event=" + event +
                '}';
    }
}
