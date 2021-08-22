package com.project.pickItUp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pickup_request_table")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "is_read")
    private int isRead;

    @JoinColumn(name = "pickup_request_id", referencedColumnName = "id")
    private PickupRequest request;
}
