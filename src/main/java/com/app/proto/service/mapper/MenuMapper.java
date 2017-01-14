package com.app.proto.service.mapper;

import com.app.proto.domain.*;
import com.app.proto.service.dto.MenuDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Menu and its DTO MenuDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MenuMapper {

    @Mappings({
        @Mapping(target = "categories", source = "categories"),
    })
    MenuDTO menuToMenuDTO(Menu menu);

    List<MenuDTO> menusToMenuDTOs(List<Menu> menus);

    @Mapping(target = "categories", ignore = true)
    Menu menuDTOToMenu(MenuDTO menuDTO);

    List<Menu> menuDTOsToMenus(List<MenuDTO> menuDTOs);
}
