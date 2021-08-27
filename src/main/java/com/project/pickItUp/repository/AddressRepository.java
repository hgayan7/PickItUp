package com.project.pickItUp.repository;

import com.project.pickItUp.entity.Address;
import com.project.pickItUp.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
