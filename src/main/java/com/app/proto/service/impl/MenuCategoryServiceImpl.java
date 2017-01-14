package com.app.proto.service.impl;

import com.app.proto.service.MenuCategoryService;
import com.app.proto.domain.MenuCategory;
import com.app.proto.repository.MenuCategoryRepository;
import com.app.proto.service.dto.MenuCategoryDTO;
import com.app.proto.service.mapper.MenuCategoryMapper;
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

/**
 * Service Implementation for managing MenuCategory.
 */
@Service
@Transactional
public class MenuCategoryServiceImpl implements MenuCategoryService{

    private final Logger log = LoggerFactory.getLogger(MenuCategoryServiceImpl.class);
    
    @Inject
    private MenuCategoryRepository menuCategoryRepository;

    @Inject
    private MenuCategoryMapper menuCategoryMapper;

    /**
     * Save a menuCategory.
     *
     * @param menuCategoryDTO the entity to save
     * @return the persisted entity
     */
    public MenuCategoryDTO save(MenuCategoryDTO menuCategoryDTO) {
        log.debug("Request to save MenuCategory : {}", menuCategoryDTO);
        MenuCategory menuCategory = menuCategoryMapper.menuCategoryDTOToMenuCategory(menuCategoryDTO);
        menuCategory = menuCategoryRepository.save(menuCategory);
        MenuCategoryDTO result = menuCategoryMapper.menuCategoryToMenuCategoryDTO(menuCategory);
        return result;
    }

    /**
     *  Get all the menuCategories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<MenuCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MenuCategories");
        Page<MenuCategory> result = menuCategoryRepository.findAll(pageable);
        return result.map(menuCategory -> menuCategoryMapper.menuCategoryToMenuCategoryDTO(menuCategory));
    }

    /**
     *  Get one menuCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MenuCategoryDTO findOne(Long id) {
        log.debug("Request to get MenuCategory : {}", id);
        MenuCategory menuCategory = menuCategoryRepository.findOne(id);
        MenuCategoryDTO menuCategoryDTO = menuCategoryMapper.menuCategoryToMenuCategoryDTO(menuCategory);
        return menuCategoryDTO;
    }

    /**
     *  Delete the  menuCategory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MenuCategory : {}", id);
        menuCategoryRepository.delete(id);
    }
}
