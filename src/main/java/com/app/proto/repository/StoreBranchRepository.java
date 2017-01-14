package com.app.proto.repository;

import com.app.proto.domain.StoreBranch;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StoreBranch entity.
 */
@SuppressWarnings("unused")
public interface StoreBranchRepository extends JpaRepository<StoreBranch,Long> {

}
