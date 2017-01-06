package com.app.proto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.app.proto.service.StoreBranchService;
import com.app.proto.web.rest.util.HeaderUtil;
import com.app.proto.service.dto.StoreBranchDTO;

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
 * REST controller for managing StoreBranch.
 */
@RestController
@RequestMapping("/api")
public class StoreBranchResource {

    private final Logger log = LoggerFactory.getLogger(StoreBranchResource.class);
        
    @Inject
    private StoreBranchService storeBranchService;

    /**
     * POST  /store-branches : Create a new storeBranch.
     *
     * @param storeBranchDTO the storeBranchDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeBranchDTO, or with status 400 (Bad Request) if the storeBranch has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/store-branches")
    @Timed
    public ResponseEntity<StoreBranchDTO> createStoreBranch(@Valid @RequestBody StoreBranchDTO storeBranchDTO) throws URISyntaxException {
        log.debug("REST request to save StoreBranch : {}", storeBranchDTO);
        if (storeBranchDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("storeBranch", "idexists", "A new storeBranch cannot already have an ID")).body(null);
        }
        StoreBranchDTO result = storeBranchService.save(storeBranchDTO);
        return ResponseEntity.created(new URI("/api/store-branches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("storeBranch", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /store-branches : Updates an existing storeBranch.
     *
     * @param storeBranchDTO the storeBranchDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeBranchDTO,
     * or with status 400 (Bad Request) if the storeBranchDTO is not valid,
     * or with status 500 (Internal Server Error) if the storeBranchDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/store-branches")
    @Timed
    public ResponseEntity<StoreBranchDTO> updateStoreBranch(@Valid @RequestBody StoreBranchDTO storeBranchDTO) throws URISyntaxException {
        log.debug("REST request to update StoreBranch : {}", storeBranchDTO);
        if (storeBranchDTO.getId() == null) {
            return createStoreBranch(storeBranchDTO);
        }
        StoreBranchDTO result = storeBranchService.save(storeBranchDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("storeBranch", storeBranchDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /store-branches : get all the storeBranches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of storeBranches in body
     */
    @GetMapping("/store-branches")
    @Timed
    public List<StoreBranchDTO> getAllStoreBranches() {
        log.debug("REST request to get all StoreBranches");
        return storeBranchService.findAll();
    }

    /**
     * GET  /store-branches/:id : get the "id" storeBranch.
     *
     * @param id the id of the storeBranchDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storeBranchDTO, or with status 404 (Not Found)
     */
    @GetMapping("/store-branches/{id}")
    @Timed
    public ResponseEntity<StoreBranchDTO> getStoreBranch(@PathVariable Long id) {
        log.debug("REST request to get StoreBranch : {}", id);
        StoreBranchDTO storeBranchDTO = storeBranchService.findOne(id);
        return Optional.ofNullable(storeBranchDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /store-branches/:id : delete the "id" storeBranch.
     *
     * @param id the id of the storeBranchDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/store-branches/{id}")
    @Timed
    public ResponseEntity<Void> deleteStoreBranch(@PathVariable Long id) {
        log.debug("REST request to delete StoreBranch : {}", id);
        storeBranchService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("storeBranch", id.toString())).build();
    }

}
