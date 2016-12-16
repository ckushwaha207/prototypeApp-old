package com.app.proto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ItemPrice.
 */
@Entity
@Table(name = "item_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "product_id", length = 20, nullable = false)
    private String productId;

    @NotNull
    @Column(name = "list_price", nullable = false)
    private Double listPrice;

    @NotNull
    @Column(name = "sale_price", nullable = false)
    private Double salePrice;

    @OneToOne(mappedBy = "price")
    @JsonIgnore
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public ItemPrice productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public ItemPrice listPrice(Double listPrice) {
        this.listPrice = listPrice;
        return this;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public ItemPrice salePrice(Double salePrice) {
        this.salePrice = salePrice;
        return this;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Product getProduct() {
        return product;
    }

    public ItemPrice product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemPrice itemPrice = (ItemPrice) o;
        if (itemPrice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, itemPrice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ItemPrice{" +
            "id=" + id +
            ", productId='" + productId + "'" +
            ", listPrice='" + listPrice + "'" +
            ", salePrice='" + salePrice + "'" +
            '}';
    }
}
