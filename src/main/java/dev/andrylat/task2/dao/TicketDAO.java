package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Ticket;

import java.util.List;

public interface TicketDAO {

    Ticket getTicket(long id);
    List<Ticket> getTickets();
    void saveTicket(Ticket ticket);
    void updateTicket(Ticket ticket);
    void deleteTicket(long id);
}
