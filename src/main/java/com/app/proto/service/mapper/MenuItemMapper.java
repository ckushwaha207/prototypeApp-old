package com.app.proto.service.mapper;

import com.app.proto.domain.*;
import com.app.proto.service.dto.MenuItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MenuItem and its DTO MenuItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MenuItemMapper {

    MenuItemDTO menuItemToMenuItemDTO(MenuItem menuItem);

    List<MenuItemDTO> menuItemsToMenuItemDTOs(List<MenuItem> menuItems);

    MenuItem menuItemDTOToMenuItem(MenuItemDTO menuItemDTO);

    List<MenuItem> menuItemDTOsToMenuItems(List<MenuItemDTO> menuItemDTOs);
}
