package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Ticket;

import java.util.List;

public interface TicketDAO {

    List<Ticket> findAll();
    List<Ticket> findByTitle(String title);
    void insert(Ticket ticket);
    void update(Ticket ticket);
    void delete(long ticketId);
}
