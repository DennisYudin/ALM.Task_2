package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Ticket;

import java.util.List;

public interface TicketDAO {

    Ticket getTicket(long id);
    List<Ticket> findAll();
    void save(Ticket ticket);
    void update(Ticket ticket);
    void delete(long id);
}
