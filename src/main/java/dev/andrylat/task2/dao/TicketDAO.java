package dev.andrylat.task2.dao;

import dev.andrylat.task2.entities.Ticket;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketDAO extends GenericDAO<Ticket>{

    Ticket getById(long id);

    List<Ticket> findAll(Pageable pageable);

    void save(Ticket ticket);

    void update(Ticket ticket);

    void delete(long id);
}
