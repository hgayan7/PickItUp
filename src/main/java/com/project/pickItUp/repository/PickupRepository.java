package com.project.pickItUp.repository;

import com.project.pickItUp.entity.PickupRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PickupRepository extends JpaRepository<PickupRequest, Long> {
    Optional<PickupRequest> findByUniqueId(String uniqueId);
}
