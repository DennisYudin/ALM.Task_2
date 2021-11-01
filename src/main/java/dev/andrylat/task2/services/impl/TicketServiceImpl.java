package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.TicketDAO;
import dev.andrylat.task2.entities.Ticket;
import dev.andrylat.task2.exceptions.DAOException;
import dev.andrylat.task2.exceptions.DataNotFoundException;
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

        validateId(id);

        Ticket ticket;
        try {
            ticket = ticketDAO.getById(id);
        } catch (DataNotFoundException ex) {
            logger.error("There is no such ticket with id = " + id);
            throw new ServiceException("There is no such ticket with id = " + id, ex);
        } catch (DAOException ex) {
            logger.error("Something went wrong when trying to call the method getTicketById()");
            throw new ServiceException("Something went wrong when trying to call the method getTicketById()", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Ticket is" + ticket);
        }
        return ticket;
    }

    private void validateId(long id) {
        logger.debug("Call method validateId() with id = " + id);
        if (id <= 0) {
            logger.error("id can not be less or equals zero");
            throw new ServiceException("id can not be less or equals zero");
        }
    }

    @Override
    public List<Ticket> findAllTickets(Pageable pageable) {
        logger.debug("Call method findAll()");

        List<Ticket> tickets;
        try {
            tickets = ticketDAO.findAll(pageable);
        } catch (DAOException ex) {
            logger.error("Could not get tickets", ex);
            throw new ServiceException("Could not get tickets", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Tickets are " + tickets);
        }
        return tickets;
    }

    @Override
    public void saveTicket(Ticket ticket) {
        logger.debug("Call method saveTicket() for ticket with id = " + ticket.getId());

        validate(ticket);
    }

    private void validate(Ticket ticket) {
        logger.debug("Call method validate() for ticket with id = " + ticket.getId());

        validateId(ticket.getId());

        try {
            ticketDAO.getById(ticket.getId());

            update(ticket);
        } catch (DataNotFoundException ex) {
            saveNewTicket(ticket);
        } catch (DAOException ex) {
            throw new ServiceException("Something went wrong when trying to call the method saveTicket()", ex);
        }
    }

    private void saveNewTicket(Ticket ticket) {
        logger.debug("Call method saveNewTicket() for ticket with id = " + ticket.getId());

        try {
            ticketDAO.save(ticket);
        } catch (DAOException ex) {
            logger.error("Could not save ticket with id = " + ticket.getId(), ex);
            throw new ServiceException("Could not save ticket with id = " + ticket.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(ticket + " is added in DB");
        }
    }

    private void update(Ticket ticket) {
        logger.debug("Call method update() for ticket with id = " + ticket.getId());

        try {
            ticketDAO.update(ticket);
        } catch (DAOException ex) {
            logger.error("Could not update ticket with id = " + ticket.getId(), ex);
            throw new ServiceException("Could not update ticket with id = " + +ticket.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(ticket + " is updated in DB");
        }
    }

    @Override
    public void deleteTicketById(long id) {
        logger.debug("Call method deleteTicketById() with id = " + id);

        validateId(id);

        try {
            ticketDAO.delete(id);
        } catch (DAOException ex) {
            logger.error("Could not delete ticket with id = " + id, ex);
            throw new ServiceException("Could not delete ticket with id = " + id, ex);
        }
        logger.debug("Ticket with id = " + id + " is deleted in DB");
    }
}

