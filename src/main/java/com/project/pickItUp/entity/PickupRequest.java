package com.project.pickItUp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pickup_request_table")
public class PickupRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "requested_date")
    private Date requestedDate;

    @JoinColumn(name = "requested_by", referencedColumnName = "id")
    private User requestedBy;

    @JoinColumn(name = "request_accepted_by", referencedColumnName = "id")
    private User requestAcceptedBy;

    @JoinColumn(name = "for_event", referencedColumnName = "id")
    private Event forEvent;

    @Enumerated
    @Column(name = "request_status")
    private PickupRequestStatus requestStatus;
}

enum PickupRequestStatus {
    REQUESTED,
    WAITINGFORPICKUP,
    PICKEDUP,
    DELIVERED,
    REJECTED
}
