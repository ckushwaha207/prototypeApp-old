package com.app.proto.service.mapper;

import com.app.proto.domain.*;
import com.app.proto.service.dto.ProductDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper {

    @Mapping(source = "price.id", target = "priceId")
    @Mapping(source = "price.listPrice", target="listPrice")
    @Mapping(source = "price.salePrice", target="salePrice")
    @Mapping(source = "category.id", target = "categoryId")
    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productsToProductDTOs(List<Product> products);

    @Mapping(source = "priceId", target = "price")
    @Mapping(source = "categoryId", target = "category")
    Product productDTOToProduct(ProductDTO productDTO);

    List<Product> productDTOsToProducts(List<ProductDTO> productDTOs);

    default ItemPrice itemPriceFromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setId(id);
        return itemPrice;
    }

    default Category categoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
