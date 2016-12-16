package com.app.proto.service.mapper;

import com.app.proto.domain.*;
import com.app.proto.service.dto.ItemPriceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ItemPrice and its DTO ItemPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ItemPriceMapper {

    ItemPriceDTO itemPriceToItemPriceDTO(ItemPrice itemPrice);

    List<ItemPriceDTO> itemPricesToItemPriceDTOs(List<ItemPrice> itemPrices);

    @Mapping(target = "product", ignore = true)
    ItemPrice itemPriceDTOToItemPrice(ItemPriceDTO itemPriceDTO);

    List<ItemPrice> itemPriceDTOsToItemPrices(List<ItemPriceDTO> itemPriceDTOs);
}
