package dev.andrylat.task2.services.impl;

import dev.andrylat.task2.dao.LocationDAO;
import dev.andrylat.task2.entities.Location;
import dev.andrylat.task2.exceptions.DAOException;
import dev.andrylat.task2.exceptions.DataNotFoundException;
import dev.andrylat.task2.exceptions.ServiceException;
import dev.andrylat.task2.services.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger logger = Logger.getLogger(LocationServiceImpl.class);

    @Autowired
    private LocationDAO locationDAO;

    @Override
    public Location getLocationById(long id) {
        logger.debug("Call method getLocationById() with id = " + id);

        validateId(id);

        Location location;
        try {
            location = locationDAO.getById(id);
        } catch (DataNotFoundException ex) {
            logger.error("There is no such location with id = " + id);
            throw new ServiceException("There is no such location with id = " + id, ex);
        } catch (DAOException ex) {
            logger.error("Something went wrong when trying to call the method getLocationById()");
            throw new ServiceException("Something went wrong when trying to call the method getLocationById()", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Location is " + location);
        }
        return location;
    }

    private void validateId(long id) {
        logger.debug("Call method validateId() with id = " + id);
        if (id <= 0) {
            logger.error("id can not be less or equals zero");
            throw new ServiceException("id can not be less or equals zero");
        }
    }

    @Override
    public List<Location> findAllLocations(Pageable pageable) {
        logger.debug("Call method findAllLocations()");

        List<Location> locations;
        try {
            locations = locationDAO.findAll(pageable);
        } catch (DAOException ex) {
            logger.error("Could not get locations", ex);
            throw new ServiceException("Could not get locations", ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Locations are " + locations);
        }
        return locations;
    }

    @Override
    public void saveLocation(Location location) {
        logger.debug("Call method saveLocation() for location with id = " + location.getId());

        validate(location);
    }

    private void validate(Location location) {
        logger.debug("Call method validate() for location with id = " + location.getId());

        validateId(location.getId());

        try {
            locationDAO.getById(location.getId());

            updateLocation(location);
        } catch(DataNotFoundException ex) {
            saveNewLocation(location);
        } catch (DAOException ex) {
            throw new ServiceException("Something went wrong when trying to call the method saveLocation()", ex);
        }
    }

    private void saveNewLocation(Location location) {
        logger.debug("Call method saveNewLocation() for location with id = " + location.getId());

        try {
            locationDAO.save(location);
        } catch (DAOException ex) {
            logger.error("Could not save location with id = " + location.getId(), ex);
            throw new ServiceException("Could not save location with id = " + location.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(location + "is added in DB");
        }
    }

    private void updateLocation(Location location) {
        logger.debug("Call method updateLocation() for location with id = " + location.getId());

        try {
            locationDAO.update(location);
        } catch (DAOException ex) {
            logger.error("Could not update location with id = " + location.getId(), ex);
            throw new ServiceException("Could not update location with id = " + location.getId(), ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(location + "is updated in DB");
        }
    }

    @Override
    public void deleteLocationById(long id) {
        logger.debug("Call method deleteLocationById() with id = " + id);

        validateId(id);

        try {
            locationDAO.delete(id);
        } catch (Exception ex) {
            logger.error("Could not delete location with id = " + id, ex);
            throw new ServiceException("Could not delete location with id = " + id, ex);
        }
        logger.debug("Location with id = " + id + " is deleted in DB");
    }
}
