package com.app.proto.repository;

import com.app.proto.domain.Order;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Order entity.
 */
@SuppressWarnings("unused")
public interface OrderRepository extends JpaRepository<Order,Long> {

}
