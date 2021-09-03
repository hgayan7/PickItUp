package com.project.pickItUp.repository;

import com.project.pickItUp.entity.EventAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventAddressRepository extends JpaRepository<EventAddress, Long> {
    @Query(
            nativeQuery = true,
            value = "select * from address_table a where a.event_id = ?1"
    )
    List<EventAddress> findAllEventAddressesByEventId(Long eventId);
}
