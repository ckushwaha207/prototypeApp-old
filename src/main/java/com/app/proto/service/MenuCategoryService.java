package com.app.proto.service;

import com.app.proto.service.dto.MenuCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing MenuCategory.
 */
public interface MenuCategoryService {

    /**
     * Save a menuCategory.
     *
     * @param menuCategoryDTO the entity to save
     * @return the persisted entity
     */
    MenuCategoryDTO save(MenuCategoryDTO menuCategoryDTO);

    /**
     *  Get all the menuCategories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MenuCategoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" menuCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MenuCategoryDTO findOne(Long id);

    /**
     *  Delete the "id" menuCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
