package com.app.proto.service.mapper;

import com.app.proto.domain.Menu;
import com.app.proto.domain.MenuCategory;
import com.app.proto.domain.MenuItem;
import com.app.proto.service.dto.MenuCategoryDTO;
import com.app.proto.service.dto.MenuItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity MenuCategory and its DTO MenuCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MenuCategoryMapper {

    @Mapping(source = "parentCategory.id", target = "parentCategoryId")
    @Mapping(source = "parentCategory.name", target = "parentCategoryName")
    @Mapping(source = "menu.id", target = "menuId")
    @Mapping(source = "menu.name", target = "menuName")
    MenuCategoryDTO menuCategoryToMenuCategoryDTO(MenuCategory menuCategory);

    List<MenuCategoryDTO> menuCategoriesToMenuCategoryDTOs(List<MenuCategory> menuCategories);

    @Mapping(source = "parentCategoryId", target = "parentCategory")
    @Mapping(target = "items", ignore = true)
    @Mapping(source = "menuId", target = "menu")
    MenuCategory menuCategoryDTOToMenuCategory(MenuCategoryDTO menuCategoryDTO);

    List<MenuCategory> menuCategoryDTOsToMenuCategories(List<MenuCategoryDTO> menuCategoryDTOs);

    default MenuCategory menuCategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setId(id);
        return menuCategory;
    }

    default Menu menuFromId(Long id) {
        if (id == null) {
            return null;
        }
        Menu menu = new Menu();
        menu.setId(id);
        return menu;
    }

    // mapping for menu-items

    @Mappings({
        @Mapping(target = "categoryId", ignore = true),
        @Mapping(target = "categoryName", ignore = true)
    })
    MenuItemDTO menuItemToMenuItemDTO(MenuItem menuItem);

    List<MenuItemDTO> menuItemsToMenuItemDTOs(Set<MenuItem> menuItems);
}
