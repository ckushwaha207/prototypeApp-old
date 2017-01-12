package com.app.proto.service.mapper;

import com.app.proto.domain.*;
import com.app.proto.service.dto.CommerceItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CommerceItem and its DTO CommerceItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommerceItemMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "order.id", target = "orderId")
    CommerceItemDTO commerceItemToCommerceItemDTO(CommerceItem commerceItem);

    List<CommerceItemDTO> commerceItemsToCommerceItemDTOs(List<CommerceItem> commerceItems);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "orderId", target = "order")
    CommerceItem commerceItemDTOToCommerceItem(CommerceItemDTO commerceItemDTO);

    List<CommerceItem> commerceItemDTOsToCommerceItems(List<CommerceItemDTO> commerceItemDTOs);

    default Product productFromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }

    default Order orderFromId(Long id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
