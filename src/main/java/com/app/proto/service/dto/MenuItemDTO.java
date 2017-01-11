package com.app.proto.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the MenuItem entity.
 */
public class MenuItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private Integer preparationTime;

    private String ingredient;

    private String imageUrl;

    private String description;


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
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }
    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuItemDTO menuItemDTO = (MenuItemDTO) o;

        if ( ! Objects.equals(id, menuItemDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MenuItemDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", preparationTime='" + preparationTime + "'" +
            ", ingredient='" + ingredient + "'" +
            ", imageUrl='" + imageUrl + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
