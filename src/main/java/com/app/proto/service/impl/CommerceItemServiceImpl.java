package com.app.proto.service.impl;

import com.app.proto.service.CommerceItemService;
import com.app.proto.domain.CommerceItem;
import com.app.proto.repository.CommerceItemRepository;
import com.app.proto.service.dto.CommerceItemDTO;
import com.app.proto.service.mapper.CommerceItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CommerceItem.
 */
@Service
@Transactional
public class CommerceItemServiceImpl implements CommerceItemService{

    private final Logger log = LoggerFactory.getLogger(CommerceItemServiceImpl.class);
    
    @Inject
    private CommerceItemRepository commerceItemRepository;

    @Inject
    private CommerceItemMapper commerceItemMapper;

    /**
     * Save a commerceItem.
     *
     * @param commerceItemDTO the entity to save
     * @return the persisted entity
     */
    public CommerceItemDTO save(CommerceItemDTO commerceItemDTO) {
        log.debug("Request to save CommerceItem : {}", commerceItemDTO);
        CommerceItem commerceItem = commerceItemMapper.commerceItemDTOToCommerceItem(commerceItemDTO);
        commerceItem = commerceItemRepository.save(commerceItem);
        CommerceItemDTO result = commerceItemMapper.commerceItemToCommerceItemDTO(commerceItem);
        return result;
    }

    /**
     *  Get all the commerceItems.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CommerceItemDTO> findAll() {
        log.debug("Request to get all CommerceItems");
        List<CommerceItemDTO> result = commerceItemRepository.findAll().stream()
            .map(commerceItemMapper::commerceItemToCommerceItemDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one commerceItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CommerceItemDTO findOne(Long id) {
        log.debug("Request to get CommerceItem : {}", id);
        CommerceItem commerceItem = commerceItemRepository.findOne(id);
        CommerceItemDTO commerceItemDTO = commerceItemMapper.commerceItemToCommerceItemDTO(commerceItem);
        return commerceItemDTO;
    }

    /**
     *  Delete the  commerceItem by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommerceItem : {}", id);
        commerceItemRepository.delete(id);
    }
}
