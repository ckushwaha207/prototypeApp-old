package com.app.proto.service.impl;

import com.app.proto.service.MenuItemService;
import com.app.proto.domain.MenuItem;
import com.app.proto.repository.MenuItemRepository;
import com.app.proto.service.dto.MenuItemDTO;
import com.app.proto.service.mapper.MenuItemMapper;
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
 * Service Implementation for managing MenuItem.
 */
@Service
@Transactional
public class MenuItemServiceImpl implements MenuItemService{

    private final Logger log = LoggerFactory.getLogger(MenuItemServiceImpl.class);
    
    @Inject
    private MenuItemRepository menuItemRepository;

    @Inject
    private MenuItemMapper menuItemMapper;

    /**
     * Save a menuItem.
     *
     * @param menuItemDTO the entity to save
     * @return the persisted entity
     */
    public MenuItemDTO save(MenuItemDTO menuItemDTO) {
        log.debug("Request to save MenuItem : {}", menuItemDTO);
        MenuItem menuItem = menuItemMapper.menuItemDTOToMenuItem(menuItemDTO);
        menuItem = menuItemRepository.save(menuItem);
        MenuItemDTO result = menuItemMapper.menuItemToMenuItemDTO(menuItem);
        return result;
    }

    /**
     *  Get all the menuItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<MenuItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MenuItems");
        Page<MenuItem> result = menuItemRepository.findAll(pageable);
        return result.map(menuItem -> menuItemMapper.menuItemToMenuItemDTO(menuItem));
    }

    /**
     *  Get one menuItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MenuItemDTO findOne(Long id) {
        log.debug("Request to get MenuItem : {}", id);
        MenuItem menuItem = menuItemRepository.findOne(id);
        MenuItemDTO menuItemDTO = menuItemMapper.menuItemToMenuItemDTO(menuItem);
        return menuItemDTO;
    }

    /**
     *  Delete the  menuItem by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MenuItem : {}", id);
        menuItemRepository.delete(id);
    }
}
