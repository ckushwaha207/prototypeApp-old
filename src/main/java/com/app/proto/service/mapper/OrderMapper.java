package com.app.proto.service.mapper;

import com.app.proto.domain.*;
import com.app.proto.service.dto.OrderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Order and its DTO OrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderMapper {

    OrderDTO orderToOrderDTO(Order order);

    List<OrderDTO> ordersToOrderDTOs(List<Order> orders);

    @Mapping(target = "commerceItems", ignore = true)
    Order orderDTOToOrder(OrderDTO orderDTO);

    List<Order> orderDTOsToOrders(List<OrderDTO> orderDTOs);
}
