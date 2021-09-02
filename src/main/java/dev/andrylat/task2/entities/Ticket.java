package dev.andrylat.task2.entities;

import java.util.Date;
import java.util.Objects;

public class Ticket {

    private long id;
    private String eventName;
    private String uniqueCode;
    private Date creationDate;
    private String status;
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    private long eventId;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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
                ", eventName='" + eventName + '\'' +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", creationDate=" + creationDate +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", eventId=" + eventId +
                '}';
    }
}
