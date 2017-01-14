package com.app.proto.service;

import com.app.proto.service.dto.CommerceItemDTO;
import java.util.List;

/**
 * Service Interface for managing CommerceItem.
 */
public interface CommerceItemService {

    /**
     * Save a commerceItem.
     *
     * @param commerceItemDTO the entity to save
     * @return the persisted entity
     */
    CommerceItemDTO save(CommerceItemDTO commerceItemDTO);

    /**
     *  Get all the commerceItems.
     *  
     *  @return the list of entities
     */
    List<CommerceItemDTO> findAll();

    /**
     *  Get the "id" commerceItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CommerceItemDTO findOne(Long id);

    /**
     *  Delete the "id" commerceItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
