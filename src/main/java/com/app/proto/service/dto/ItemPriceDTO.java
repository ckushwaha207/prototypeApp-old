package com.app.proto.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the ItemPrice entity.
 */
public class ItemPriceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String productId;

    @NotNull
    private Double listPrice;

    @NotNull
    private Double salePrice;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }
    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemPriceDTO itemPriceDTO = (ItemPriceDTO) o;

        if ( ! Objects.equals(id, itemPriceDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ItemPriceDTO{" +
            "id=" + id +
            ", productId='" + productId + "'" +
            ", listPrice='" + listPrice + "'" +
            ", salePrice='" + salePrice + "'" +
            '}';
    }
}
