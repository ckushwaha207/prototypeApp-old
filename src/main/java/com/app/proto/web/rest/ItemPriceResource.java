package com.app.proto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.app.proto.service.ItemPriceService;
import com.app.proto.web.rest.util.HeaderUtil;
import com.app.proto.service.dto.ItemPriceDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing ItemPrice.
 */
@RestController
@RequestMapping("/api")
public class ItemPriceResource {

    private final Logger log = LoggerFactory.getLogger(ItemPriceResource.class);
        
    @Inject
    private ItemPriceService itemPriceService;

    /**
     * POST  /item-prices : Create a new itemPrice.
     *
     * @param itemPriceDTO the itemPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemPriceDTO, or with status 400 (Bad Request) if the itemPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-prices")
    @Timed
    public ResponseEntity<ItemPriceDTO> createItemPrice(@Valid @RequestBody ItemPriceDTO itemPriceDTO) throws URISyntaxException {
        log.debug("REST request to save ItemPrice : {}", itemPriceDTO);
        if (itemPriceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("itemPrice", "idexists", "A new itemPrice cannot already have an ID")).body(null);
        }
        ItemPriceDTO result = itemPriceService.save(itemPriceDTO);
        return ResponseEntity.created(new URI("/api/item-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("itemPrice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-prices : Updates an existing itemPrice.
     *
     * @param itemPriceDTO the itemPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemPriceDTO,
     * or with status 400 (Bad Request) if the itemPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the itemPriceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-prices")
    @Timed
    public ResponseEntity<ItemPriceDTO> updateItemPrice(@Valid @RequestBody ItemPriceDTO itemPriceDTO) throws URISyntaxException {
        log.debug("REST request to update ItemPrice : {}", itemPriceDTO);
        if (itemPriceDTO.getId() == null) {
            return createItemPrice(itemPriceDTO);
        }
        ItemPriceDTO result = itemPriceService.save(itemPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("itemPrice", itemPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-prices : get all the itemPrices.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of itemPrices in body
     */
    @GetMapping("/item-prices")
    @Timed
    public List<ItemPriceDTO> getAllItemPrices(@RequestParam(required = false) String filter) {
        if ("product-is-null".equals(filter)) {
            log.debug("REST request to get all ItemPrices where product is null");
            return itemPriceService.findAllWhereProductIsNull();
        }
        log.debug("REST request to get all ItemPrices");
        return itemPriceService.findAll();
    }

    /**
     * GET  /item-prices/:id : get the "id" itemPrice.
     *
     * @param id the id of the itemPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemPriceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/item-prices/{id}")
    @Timed
    public ResponseEntity<ItemPriceDTO> getItemPrice(@PathVariable Long id) {
        log.debug("REST request to get ItemPrice : {}", id);
        ItemPriceDTO itemPriceDTO = itemPriceService.findOne(id);
        return Optional.ofNullable(itemPriceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /item-prices/:id : delete the "id" itemPrice.
     *
     * @param id the id of the itemPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteItemPrice(@PathVariable Long id) {
        log.debug("REST request to delete ItemPrice : {}", id);
        itemPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("itemPrice", id.toString())).build();
    }

}
