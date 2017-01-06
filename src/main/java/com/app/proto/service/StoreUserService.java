package com.app.proto.service;

import com.app.proto.service.dto.StoreUserDTO;
import java.util.List;

/**
 * Service Interface for managing StoreUser.
 */
public interface StoreUserService {

    /**
     * Save a storeUser.
     *
     * @param storeUserDTO the entity to save
     * @return the persisted entity
     */
    StoreUserDTO save(StoreUserDTO storeUserDTO);

    /**
     *  Get all the storeUsers.
     *  
     *  @return the list of entities
     */
    List<StoreUserDTO> findAll();

    /**
     *  Get the "id" storeUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StoreUserDTO findOne(Long id);

    /**
     *  Delete the "id" storeUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
