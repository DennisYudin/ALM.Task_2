package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.TicketDAO;
import dev.andrylat.task2.entities.Ticket;
import dev.andrylat.task2.exceptions.ServiceException;
import dev.andrylat.task2.services.TicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TicketServiceImpl implements TicketService {

    private static final Logger logger = Logger.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketDAO ticketDAO;

    @Override
    public Ticket getById(long id) {
        logger.trace("Call method getById() with id = " + id);

        Ticket ticket;
        try {
            ticket = ticketDAO.getById(id);
        } catch (Exception ex) {
            logger.error("Could not get ticket by id = " + id, ex);
            throw new ServiceException("Could not get ticket by id = " + id, ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Ticket is" + ticket);
        }
        return ticket;
    }

    @Override
    public List<Ticket> findAll() {
        logger.trace("Call method findAll()");

        List<Ticket> tickets;
        try {
            tickets = ticketDAO.findAll();
        } catch (Exception ex) {
            logger.error("Could not get tickets", ex);
            throw new ServiceException("Could not get tickets", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Tickets are " + tickets);
        }
        return tickets;
    }

    @Override
    public void save(Ticket ticket) {
        logger.trace("Call method save()");

        try {
            ticketDAO.save(ticket);
        } catch (Exception ex) {
            logger.error("Could not save ticket", ex);
            throw new ServiceException("Could not save ticket", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(ticket + "is added in DB");
        }
    }

    @Override
    public void update(Ticket ticket) {
        logger.trace("Call method update()");

        try {
            ticketDAO.update(ticket);
        } catch (Exception ex) {
            logger.error("Could not update ticket", ex);
            throw new ServiceException("Could not update ticket", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(ticket + "is updated in DB");
        }
    }

    @Override
    public void delete(long id) {
        logger.trace("Call method delete() with id = " + id);

        try {
            ticketDAO.delete(id);
        } catch (Exception ex) {
            logger.error("Could not delete ticket", ex);
            throw new ServiceException("Could not delete ticket", ex);
        }
        logger.debug("Ticket is deleted in DB");
    }
}

