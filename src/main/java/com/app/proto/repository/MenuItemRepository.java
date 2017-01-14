package com.app.proto.repository;

import com.app.proto.domain.MenuItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MenuItem entity.
 */
@SuppressWarnings("unused")
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {

}
