package com.app.proto.service.impl;

import com.app.proto.service.ItemPriceService;
import com.app.proto.domain.ItemPrice;
import com.app.proto.repository.ItemPriceRepository;
import com.app.proto.service.dto.ItemPriceDTO;
import com.app.proto.service.mapper.ItemPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing ItemPrice.
 */
@Service
@Transactional
public class ItemPriceServiceImpl implements ItemPriceService{

    private final Logger log = LoggerFactory.getLogger(ItemPriceServiceImpl.class);
    
    @Inject
    private ItemPriceRepository itemPriceRepository;

    @Inject
    private ItemPriceMapper itemPriceMapper;

    /**
     * Save a itemPrice.
     *
     * @param itemPriceDTO the entity to save
     * @return the persisted entity
     */
    public ItemPriceDTO save(ItemPriceDTO itemPriceDTO) {
        log.debug("Request to save ItemPrice : {}", itemPriceDTO);
        ItemPrice itemPrice = itemPriceMapper.itemPriceDTOToItemPrice(itemPriceDTO);
        itemPrice = itemPriceRepository.save(itemPrice);
        ItemPriceDTO result = itemPriceMapper.itemPriceToItemPriceDTO(itemPrice);
        return result;
    }

    /**
     *  Get all the itemPrices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ItemPriceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPrices");
        Page<ItemPrice> result = itemPriceRepository.findAll(pageable);
        return result.map(itemPrice -> itemPriceMapper.itemPriceToItemPriceDTO(itemPrice));
    }


    /**
     *  get all the itemPrices where Product is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ItemPriceDTO> findAllWhereProductIsNull() {
        log.debug("Request to get all itemPrices where Product is null");
        return StreamSupport
            .stream(itemPriceRepository.findAll().spliterator(), false)
            .filter(itemPrice -> itemPrice.getProduct() == null)
            .map(itemPriceMapper::itemPriceToItemPriceDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one itemPrice by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ItemPriceDTO findOne(Long id) {
        log.debug("Request to get ItemPrice : {}", id);
        ItemPrice itemPrice = itemPriceRepository.findOne(id);
        ItemPriceDTO itemPriceDTO = itemPriceMapper.itemPriceToItemPriceDTO(itemPrice);
        return itemPriceDTO;
    }

    /**
     *  Delete the  itemPrice by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemPrice : {}", id);
        itemPriceRepository.delete(id);
    }
}
