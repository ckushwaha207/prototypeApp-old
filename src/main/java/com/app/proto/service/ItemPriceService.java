package com.app.proto.service;

import com.app.proto.service.dto.ItemPriceDTO;
import java.util.List;

/**
 * Service Interface for managing ItemPrice.
 */
public interface ItemPriceService {

    /**
     * Save a itemPrice.
     *
     * @param itemPriceDTO the entity to save
     * @return the persisted entity
     */
    ItemPriceDTO save(ItemPriceDTO itemPriceDTO);

    /**
     *  Get all the itemPrices.
     *  
     *  @return the list of entities
     */
    List<ItemPriceDTO> findAll();
    /**
     *  Get all the ItemPriceDTO where Product is null.
     *
     *  @return the list of entities
     */
    List<ItemPriceDTO> findAllWhereProductIsNull();

    /**
     *  Get the "id" itemPrice.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ItemPriceDTO findOne(Long id);

    /**
     *  Delete the "id" itemPrice.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
