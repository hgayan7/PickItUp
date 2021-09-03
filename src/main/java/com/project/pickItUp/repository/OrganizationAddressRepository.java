package com.project.pickItUp.repository;

import com.project.pickItUp.entity.OrganizationAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationAddressRepository extends JpaRepository<OrganizationAddress, Long> {
}
