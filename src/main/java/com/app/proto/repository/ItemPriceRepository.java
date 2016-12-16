package com.app.proto.repository;

import com.app.proto.domain.ItemPrice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ItemPrice entity.
 */
@SuppressWarnings("unused")
public interface ItemPriceRepository extends JpaRepository<ItemPrice,Long> {

}
