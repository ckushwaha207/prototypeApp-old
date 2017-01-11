package com.app.proto.service;

import com.app.proto.service.dto.MenuItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing MenuItem.
 */
public interface MenuItemService {

    /**
     * Save a menuItem.
     *
     * @param menuItemDTO the entity to save
     * @return the persisted entity
     */
    MenuItemDTO save(MenuItemDTO menuItemDTO);

    /**
     *  Get all the menuItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MenuItemDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" menuItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MenuItemDTO findOne(Long id);

    /**
     *  Delete the "id" menuItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
