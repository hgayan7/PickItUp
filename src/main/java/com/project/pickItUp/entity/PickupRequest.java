package com.project.pickItUp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pickup_request_table")
public class PickupRequest {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

//    @Column(name = "requested_date")
//    private Date requestedDate;

//    @OneToOne
//    @JoinColumn(name = "requested_by", referencedColumnName = "id")
//    private User requestedBy;
//
//    @OneToOne
//    @JoinColumn(name = "request_accepted_by", referencedColumnName = "id")
//    private User requestAcceptedBy;
//
//    @OneToOne
//    @JoinColumn(name = "for_event", referencedColumnName = "id")
//    private Event forEvent;
//
////    @Enumerated
//    @Column(name = "request_status")
//    private Integer requestStatus;
}

enum PickupRequestStatus {
    REQUESTED,
    WAITINGFORPICKUP,
    PICKEDUP,
    DELIVERED,
    REJECTED
}
