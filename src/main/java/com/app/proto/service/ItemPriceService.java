package com.app.proto.service;

import com.app.proto.service.dto.ItemPriceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ItemPriceDTO> findAll(Pageable pageable);
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
