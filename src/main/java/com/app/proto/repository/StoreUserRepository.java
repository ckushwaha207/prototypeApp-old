package com.app.proto.repository;

import com.app.proto.domain.StoreUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StoreUser entity.
 */
@SuppressWarnings("unused")
public interface StoreUserRepository extends JpaRepository<StoreUser,Long> {

}
