package com.app.proto.service.mapper;

import com.app.proto.domain.Menu;
import com.app.proto.domain.MenuCategory;
import com.app.proto.domain.MenuItem;
import com.app.proto.service.dto.MenuCategoryDTO;
import com.app.proto.service.dto.MenuDTO;
import com.app.proto.service.dto.MenuItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity Menu and its DTO MenuDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MenuMapper {

    MenuDTO menuToMenuDTO(Menu menu);

    List<MenuDTO> menusToMenuDTOs(List<Menu> menus);

    @Mapping(target = "categories", ignore = true)
    Menu menuDTOToMenu(MenuDTO menuDTO);

    List<Menu> menuDTOsToMenus(List<MenuDTO> menuDTOs);


    // mapping for menu-categories

    @Mappings({
        @Mapping(target = "menuId", ignore = true),
        @Mapping(target = "menuName", ignore = true),
        @Mapping(target = "parentCategoryId", ignore = true),
        @Mapping(target = "parentCategoryName", ignore = true)
    })
    MenuCategoryDTO menuCategoryToMenuCategoryDTO(MenuCategory menuCategory);

    List<MenuCategoryDTO> menuCategoriesToMenuCategoryDTOs(Set<MenuCategory> menuCategories);

    // mapping for menu-items

    @Mappings({
        @Mapping(target = "categoryId", ignore = true),
        @Mapping(target = "categoryName", ignore = true)
    })
    MenuItemDTO menuItemToMenuItemDTO(MenuItem menuItem);

    List<MenuItemDTO> menuItemsToMenuItemDTOs(Set<MenuItem> menuItems);
}
