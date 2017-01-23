package com.app.proto.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


/**
 * A DTO for the MenuCategory entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    private Long parentCategoryId;


    private String parentCategoryName;

    private Long menuId;


    private String menuName;

    private Set<MenuItemDTO> items;

    public Set<MenuItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<MenuItemDTO> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long menuCategoryId) {
        this.parentCategoryId = menuCategoryId;
    }


    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String menuCategoryName) {
        this.parentCategoryName = menuCategoryName;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }


    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuCategoryDTO menuCategoryDTO = (MenuCategoryDTO) o;

        if ( ! Objects.equals(id, menuCategoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MenuCategoryDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
