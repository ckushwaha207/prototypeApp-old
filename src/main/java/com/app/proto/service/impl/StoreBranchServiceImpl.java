package com.app.proto.service.impl;

import com.app.proto.service.StoreBranchService;
import com.app.proto.domain.StoreBranch;
import com.app.proto.repository.StoreBranchRepository;
import com.app.proto.service.dto.StoreBranchDTO;
import com.app.proto.service.mapper.StoreBranchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing StoreBranch.
 */
@Service
@Transactional
public class StoreBranchServiceImpl implements StoreBranchService{

    private final Logger log = LoggerFactory.getLogger(StoreBranchServiceImpl.class);
    
    @Inject
    private StoreBranchRepository storeBranchRepository;

    @Inject
    private StoreBranchMapper storeBranchMapper;

    /**
     * Save a storeBranch.
     *
     * @param storeBranchDTO the entity to save
     * @return the persisted entity
     */
    public StoreBranchDTO save(StoreBranchDTO storeBranchDTO) {
        log.debug("Request to save StoreBranch : {}", storeBranchDTO);
        StoreBranch storeBranch = storeBranchMapper.storeBranchDTOToStoreBranch(storeBranchDTO);
        storeBranch = storeBranchRepository.save(storeBranch);
        StoreBranchDTO result = storeBranchMapper.storeBranchToStoreBranchDTO(storeBranch);
        return result;
    }

    /**
     *  Get all the storeBranches.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<StoreBranchDTO> findAll() {
        log.debug("Request to get all StoreBranches");
        List<StoreBranchDTO> result = storeBranchRepository.findAll().stream()
            .map(storeBranchMapper::storeBranchToStoreBranchDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one storeBranch by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public StoreBranchDTO findOne(Long id) {
        log.debug("Request to get StoreBranch : {}", id);
        StoreBranch storeBranch = storeBranchRepository.findOne(id);
        StoreBranchDTO storeBranchDTO = storeBranchMapper.storeBranchToStoreBranchDTO(storeBranch);
        return storeBranchDTO;
    }

    /**
     *  Delete the  storeBranch by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StoreBranch : {}", id);
        storeBranchRepository.delete(id);
    }
}
