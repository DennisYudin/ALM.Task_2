package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.entities.Location;
import dev.andrylat.task2.exceptions.DAOException;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import dev.andrylat.task2.exceptions.ServiceException;
import dev.andrylat.task2.services.LocationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@Service
public class LocationServiceImpl implements LocationService {
    private static final String ERROR_MESSAGE_FOR_GETBYID_METHOD = "Error during call the method getById()";
    private static final String ERROR_MESSAGE_FOR_VALIDATE_METHOD = "id can not be less or equals zero";
    private static final String EMPTY_RESULT_MESSAGE = "There is no such location with id = ";
    private static final String ERROR_MESSAGE_FOR_FINDALL_METHOD = "Error during call the method findAll()";
    private static final String ERROR_MESSAGE_FOR_SAVE_METHOD = "Error during call the method save()";
    private static final String ERROR_MESSAGE_FOR_DELETE_METHOD = "Error during call the method delete()";

    @Autowired
    private LocationDAO locationDAO;

    @Override
    public Location getById(long id) {
        log.debug("Call method getById() with id = " + id);

        validateId(id);

        Location location;
        try {
            location = locationDAO.getById(id);
            if (log.isDebugEnabled()) {
                log.debug("Location is " + location);
            }
            return location;
        } catch (DataNotFoundException ex) {
            log.error(EMPTY_RESULT_MESSAGE + id, ex);
            throw new ServiceException(EMPTY_RESULT_MESSAGE + id, ex);
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_GETBYID_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_GETBYID_METHOD, ex);
        }
    }

    private void validateId(long id) {
        log.debug("Call method validateId() with id = " + id);
        if (id <= 0) {
            log.error(ERROR_MESSAGE_FOR_VALIDATE_METHOD);
            throw new ServiceException(ERROR_MESSAGE_FOR_VALIDATE_METHOD);
        }
    }

    @Override
    public List<Location> findAll(Pageable pageable) {
        log.debug("Call method findAll()");

        List<Location> locations;
        try {
            locations = locationDAO.findAll(pageable);
            if (log.isDebugEnabled()) {
                log.debug("Locations are " + locations);
            }
            return locations;
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_FINDALL_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_FINDALL_METHOD, ex);
        }
    }

    @Override
    public void save(Location location) {
        log.debug("Call method save() for location with id = " + location.getId());

        validateId(location.getId());

        try {
            locationDAO.save(location);
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_SAVE_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_SAVE_METHOD, ex);
        }
    }

    @Override
    public void delete(long id) {
        log.debug("Call method delete() with id = " + id);

        validateId(id);

        try {
            locationDAO.delete(id);
            log.debug("Location with id = " + id + " is deleted in DB");
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_DELETE_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_DELETE_METHOD, ex);
        }
    }
}

