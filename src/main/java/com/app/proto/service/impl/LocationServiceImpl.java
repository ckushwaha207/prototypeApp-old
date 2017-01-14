package com.app.proto.service.impl;

import com.app.proto.service.LocationService;
import com.app.proto.domain.Location;
import com.app.proto.repository.LocationRepository;
import com.app.proto.service.dto.LocationDTO;
import com.app.proto.service.mapper.LocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Location.
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService{

    private final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);
    
    @Inject
    private LocationRepository locationRepository;

    @Inject
    private LocationMapper locationMapper;

    /**
     * Save a location.
     *
     * @param locationDTO the entity to save
     * @return the persisted entity
     */
    public LocationDTO save(LocationDTO locationDTO) {
        log.debug("Request to save Location : {}", locationDTO);
        Location location = locationMapper.locationDTOToLocation(locationDTO);
        location = locationRepository.save(location);
        LocationDTO result = locationMapper.locationToLocationDTO(location);
        return result;
    }

    /**
     *  Get all the locations.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<LocationDTO> findAll() {
        log.debug("Request to get all Locations");
        List<LocationDTO> result = locationRepository.findAll().stream()
            .map(locationMapper::locationToLocationDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }


    /**
     *  get all the locations where Branch is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<LocationDTO> findAllWhereBranchIsNull() {
        log.debug("Request to get all locations where Branch is null");
        return StreamSupport
            .stream(locationRepository.findAll().spliterator(), false)
            .filter(location -> location.getBranch() == null)
            .map(locationMapper::locationToLocationDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one location by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LocationDTO findOne(Long id) {
        log.debug("Request to get Location : {}", id);
        Location location = locationRepository.findOne(id);
        LocationDTO locationDTO = locationMapper.locationToLocationDTO(location);
        return locationDTO;
    }

    /**
     *  Delete the  location by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.delete(id);
    }
}
