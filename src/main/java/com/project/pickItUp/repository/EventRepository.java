package com.project.pickItUp.repository;

import com.project.pickItUp.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(
            nativeQuery = true,
            value = "select distinct e.id, e.name, e.description," +
                    " e.start_date, e.end_date, e.is_valid, e.organization_id from event_table e" +
                    " join event_address_table a on e.id = a.event_id && a.city_id = ?1"
    )
    List<Event> findAllEventsByCityId(Long cityId);

}
