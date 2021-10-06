package dev.andrylat.task2.services;

import dev.andrylat.task2.entities.Ticket;

import java.util.List;

public interface TicketService {

    Ticket getById(long id);

    List<Ticket> findAll();

    void save(Ticket ticket);

    void update(Ticket ticket);

    void delete(long id);
}
