package com.app.proto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.app.proto.service.CommerceItemService;
import com.app.proto.web.rest.util.HeaderUtil;
import com.app.proto.service.dto.CommerceItemDTO;

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

/**
 * REST controller for managing CommerceItem.
 */
@RestController
@RequestMapping("/api")
public class CommerceItemResource {

    private final Logger log = LoggerFactory.getLogger(CommerceItemResource.class);
        
    @Inject
    private CommerceItemService commerceItemService;

    /**
     * POST  /commerce-items : Create a new commerceItem.
     *
     * @param commerceItemDTO the commerceItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerceItemDTO, or with status 400 (Bad Request) if the commerceItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commerce-items")
    @Timed
    public ResponseEntity<CommerceItemDTO> createCommerceItem(@Valid @RequestBody CommerceItemDTO commerceItemDTO) throws URISyntaxException {
        log.debug("REST request to save CommerceItem : {}", commerceItemDTO);
        if (commerceItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("commerceItem", "idexists", "A new commerceItem cannot already have an ID")).body(null);
        }
        CommerceItemDTO result = commerceItemService.save(commerceItemDTO);
        return ResponseEntity.created(new URI("/api/commerce-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("commerceItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commerce-items : Updates an existing commerceItem.
     *
     * @param commerceItemDTO the commerceItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerceItemDTO,
     * or with status 400 (Bad Request) if the commerceItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the commerceItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commerce-items")
    @Timed
    public ResponseEntity<CommerceItemDTO> updateCommerceItem(@Valid @RequestBody CommerceItemDTO commerceItemDTO) throws URISyntaxException {
        log.debug("REST request to update CommerceItem : {}", commerceItemDTO);
        if (commerceItemDTO.getId() == null) {
            return createCommerceItem(commerceItemDTO);
        }
        CommerceItemDTO result = commerceItemService.save(commerceItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("commerceItem", commerceItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commerce-items : get all the commerceItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerceItems in body
     */
    @GetMapping("/commerce-items")
    @Timed
    public List<CommerceItemDTO> getAllCommerceItems() {
        log.debug("REST request to get all CommerceItems");
        return commerceItemService.findAll();
    }

    /**
     * GET  /commerce-items/:id : get the "id" commerceItem.
     *
     * @param id the id of the commerceItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerceItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commerce-items/{id}")
    @Timed
    public ResponseEntity<CommerceItemDTO> getCommerceItem(@PathVariable Long id) {
        log.debug("REST request to get CommerceItem : {}", id);
        CommerceItemDTO commerceItemDTO = commerceItemService.findOne(id);
        return Optional.ofNullable(commerceItemDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /commerce-items/:id : delete the "id" commerceItem.
     *
     * @param id the id of the commerceItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerceItem(@PathVariable Long id) {
        log.debug("REST request to delete CommerceItem : {}", id);
        commerceItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("commerceItem", id.toString())).build();
    }

}
