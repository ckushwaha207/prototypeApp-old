package com.app.proto.service.impl;

import com.app.proto.service.OrderService;
import com.app.proto.domain.Order;
import com.app.proto.repository.OrderRepository;
import com.app.proto.service.dto.OrderDTO;
import com.app.proto.service.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Order.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    @Inject
    private OrderRepository orderRepository;

    @Inject
    private OrderMapper orderMapper;

    /**
     * Save a order.
     *
     * @param orderDTO the entity to save
     * @return the persisted entity
     */
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = orderMapper.orderDTOToOrder(orderDTO);
        order = orderRepository.save(order);
        OrderDTO result = orderMapper.orderToOrderDTO(order);
        return result;
    }

    /**
     *  Get all the orders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        Page<Order> result = orderRepository.findAll(pageable);
        return result.map(order -> orderMapper.orderToOrderDTO(order));
    }

    /**
     *  Get one order by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OrderDTO findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        Order order = orderRepository.findOne(id);
        OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
        return orderDTO;
    }

    /**
     *  Delete the  order by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.delete(id);
    }
}
