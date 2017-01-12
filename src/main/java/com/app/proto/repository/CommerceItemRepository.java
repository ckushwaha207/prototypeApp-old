package com.app.proto.repository;

import com.app.proto.domain.CommerceItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CommerceItem entity.
 */
@SuppressWarnings("unused")
public interface CommerceItemRepository extends JpaRepository<CommerceItem,Long> {

}
