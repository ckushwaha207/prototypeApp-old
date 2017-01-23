package com.app.proto.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


/**
 * A DTO for the Menu entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Set<MenuCategoryDTO> categories;

    public Set<MenuCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<MenuCategoryDTO> categories) {
        this.categories = categories;
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
            ", name='" + name + "'" +
            '}';
    }
}
