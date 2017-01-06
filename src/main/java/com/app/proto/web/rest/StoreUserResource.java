package com.app.proto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.app.proto.service.StoreUserService;
import com.app.proto.web.rest.util.HeaderUtil;
import com.app.proto.service.dto.StoreUserDTO;

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
 * REST controller for managing StoreUser.
 */
@RestController
@RequestMapping("/api")
public class StoreUserResource {

    private final Logger log = LoggerFactory.getLogger(StoreUserResource.class);
        
    @Inject
    private StoreUserService storeUserService;

    /**
     * POST  /store-users : Create a new storeUser.
     *
     * @param storeUserDTO the storeUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeUserDTO, or with status 400 (Bad Request) if the storeUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/store-users")
    @Timed
    public ResponseEntity<StoreUserDTO> createStoreUser(@Valid @RequestBody StoreUserDTO storeUserDTO) throws URISyntaxException {
        log.debug("REST request to save StoreUser : {}", storeUserDTO);
        if (storeUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("storeUser", "idexists", "A new storeUser cannot already have an ID")).body(null);
        }
        StoreUserDTO result = storeUserService.save(storeUserDTO);
        return ResponseEntity.created(new URI("/api/store-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("storeUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /store-users : Updates an existing storeUser.
     *
     * @param storeUserDTO the storeUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeUserDTO,
     * or with status 400 (Bad Request) if the storeUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the storeUserDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/store-users")
    @Timed
    public ResponseEntity<StoreUserDTO> updateStoreUser(@Valid @RequestBody StoreUserDTO storeUserDTO) throws URISyntaxException {
        log.debug("REST request to update StoreUser : {}", storeUserDTO);
        if (storeUserDTO.getId() == null) {
            return createStoreUser(storeUserDTO);
        }
        StoreUserDTO result = storeUserService.save(storeUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("storeUser", storeUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /store-users : get all the storeUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of storeUsers in body
     */
    @GetMapping("/store-users")
    @Timed
    public List<StoreUserDTO> getAllStoreUsers() {
        log.debug("REST request to get all StoreUsers");
        return storeUserService.findAll();
    }

    /**
     * GET  /store-users/:id : get the "id" storeUser.
     *
     * @param id the id of the storeUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storeUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/store-users/{id}")
    @Timed
    public ResponseEntity<StoreUserDTO> getStoreUser(@PathVariable Long id) {
        log.debug("REST request to get StoreUser : {}", id);
        StoreUserDTO storeUserDTO = storeUserService.findOne(id);
        return Optional.ofNullable(storeUserDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /store-users/:id : delete the "id" storeUser.
     *
     * @param id the id of the storeUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/store-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteStoreUser(@PathVariable Long id) {
        log.debug("REST request to delete StoreUser : {}", id);
        storeUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("storeUser", id.toString())).build();
    }

}
