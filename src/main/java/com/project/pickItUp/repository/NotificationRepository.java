package com.project.pickItUp.repository;

import com.project.pickItUp.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(
            nativeQuery = true,
            value = "select * from notification_table where user_id = ?1"
    )
    List<Notification> findNotificationByUserId(Long userId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from notification_table where pickup_request_id = ?1"
    )
    void deleteNotificationByPickupRequestId(Long requestId);
}
