package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.TicketDAO;
import dev.andrylat.task2.entities.Ticket;
import dev.andrylat.task2.exceptions.ServiceException;
import dev.andrylat.task2.services.TicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = Logger.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketDAO ticketDAO;

    @Override
    public Ticket getTicketById(long id) {
        logger.debug("Call method getById() with id = " + id);

        Ticket ticket;
        try {
            ticket = ticketDAO.getById(id);
        } catch (Exception ex) {
            logger.error("Could not get ticket by id = " + id, ex);
            throw new ServiceException("Could not get ticket by id = " + id, ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Ticket is" + ticket.toString());
        }
        return ticket;
    }

    @Override
    public List<Ticket> findAllTickets(Pageable pageable) {
        logger.debug("Call method findAll()");

        List<Ticket> tickets;
        try {
            tickets = ticketDAO.findAll(pageable);
        } catch (Exception ex) {
            logger.error("Could not get tickets", ex);
            throw new ServiceException("Could not get tickets", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Tickets are " + tickets.toString());
        }
        return tickets;
    }

    @Override
    public void saveTicket(Ticket ticket) {

        Ticket resultQuery = getTicketById(ticket.getId());

        if (resultQuery == null) {
            saveNewTicket(ticket);
        } else {
            update(ticket);
        }
    }

    private void saveNewTicket(Ticket ticket) {
        logger.debug("Call method saveNewTicket() for ticket with id = " + ticket.getId());

        try {
            ticketDAO.save(ticket);
        } catch (Exception ex) {
            logger.error("Could not save ticket with id = " + ticket.getId(), ex);
            throw new ServiceException("Could not save ticket with id = " + ticket.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(ticket.toString() + "is added in DB");
        }
    }

    private void update(Ticket ticket) {
        logger.debug("Call method update() for ticket with id = " + ticket.getId());

        try {
            ticketDAO.update(ticket);
        } catch (Exception ex) {
            logger.error("Could not update ticket with id = " + ticket.getId(), ex);
            throw new ServiceException("Could not update ticket with id = " + +ticket.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(ticket.toString() + "is updated in DB");
        }
    }

    @Override
    public void deleteTicketById(long id) {
        logger.debug("Call method deleteTicketById() with id = " + id);

        try {
            ticketDAO.delete(id);
        } catch (Exception ex) {
            logger.error("Could not delete ticket with id = " + id, ex);
            throw new ServiceException("Could not delete ticket with id = " + id, ex);
        }
        logger.debug("Ticket with id = " + id + " is deleted in DB");
    }
}

