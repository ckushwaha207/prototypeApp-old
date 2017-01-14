package com.app.proto.service;

import com.app.proto.service.dto.StoreBranchDTO;
import java.util.List;

/**
 * Service Interface for managing StoreBranch.
 */
public interface StoreBranchService {

    /**
     * Save a storeBranch.
     *
     * @param storeBranchDTO the entity to save
     * @return the persisted entity
     */
    StoreBranchDTO save(StoreBranchDTO storeBranchDTO);

    /**
     *  Get all the storeBranches.
     *  
     *  @return the list of entities
     */
    List<StoreBranchDTO> findAll();

    /**
     *  Get the "id" storeBranch.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StoreBranchDTO findOne(Long id);

    /**
     *  Delete the "id" storeBranch.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
