package com.app.proto.repository;

import com.app.proto.domain.MenuCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MenuCategory entity.
 */
@SuppressWarnings("unused")
public interface MenuCategoryRepository extends JpaRepository<MenuCategory,Long> {

}
