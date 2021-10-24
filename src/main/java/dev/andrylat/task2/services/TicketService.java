package dev.andrylat.task2.services;

import dev.andrylat.task2.entities.Ticket;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TicketService {

    Ticket getTicketById(long id);

    List<Ticket> findAllTickets(Pageable pageable);

    void saveTicket(Ticket ticket);

    void deleteTicketById(long id);
}

