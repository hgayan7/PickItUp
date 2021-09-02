package com.project.pickItUp.repository;

import com.project.pickItUp.entity.Address;
import com.project.pickItUp.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(
            nativeQuery = true,
            value = "select * from address_table a where a.event_id = ?1"
    )
    List<Address> findAllEventAddressesByEventId(Long eventId);
}
