package com.app.proto.service.impl;

import com.app.proto.service.StoreUserService;
import com.app.proto.domain.StoreUser;
import com.app.proto.repository.StoreUserRepository;
import com.app.proto.service.dto.StoreUserDTO;
import com.app.proto.service.mapper.StoreUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing StoreUser.
 */
@Service
@Transactional
public class StoreUserServiceImpl implements StoreUserService{

    private final Logger log = LoggerFactory.getLogger(StoreUserServiceImpl.class);
    
    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private StoreUserMapper storeUserMapper;

    /**
     * Save a storeUser.
     *
     * @param storeUserDTO the entity to save
     * @return the persisted entity
     */
    public StoreUserDTO save(StoreUserDTO storeUserDTO) {
        log.debug("Request to save StoreUser : {}", storeUserDTO);
        StoreUser storeUser = storeUserMapper.storeUserDTOToStoreUser(storeUserDTO);
        storeUser = storeUserRepository.save(storeUser);
        StoreUserDTO result = storeUserMapper.storeUserToStoreUserDTO(storeUser);
        return result;
    }

    /**
     *  Get all the storeUsers.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<StoreUserDTO> findAll() {
        log.debug("Request to get all StoreUsers");
        List<StoreUserDTO> result = storeUserRepository.findAll().stream()
            .map(storeUserMapper::storeUserToStoreUserDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one storeUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public StoreUserDTO findOne(Long id) {
        log.debug("Request to get StoreUser : {}", id);
        StoreUser storeUser = storeUserRepository.findOne(id);
        StoreUserDTO storeUserDTO = storeUserMapper.storeUserToStoreUserDTO(storeUser);
        return storeUserDTO;
    }

    /**
     *  Delete the  storeUser by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StoreUser : {}", id);
        storeUserRepository.delete(id);
    }
}
