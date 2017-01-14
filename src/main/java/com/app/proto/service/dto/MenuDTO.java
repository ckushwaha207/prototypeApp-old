package com.app.proto.service.dto;

import com.app.proto.domain.MenuCategory;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Menu entity.
 */
public class MenuDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Set<MenuCategory> categories;

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

    public Set<MenuCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<MenuCategory> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuDTO menuDTO = (MenuDTO) o;

        if ( ! Objects.equals(id, menuDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", categories=" + categories +
            '}';
    }
}
