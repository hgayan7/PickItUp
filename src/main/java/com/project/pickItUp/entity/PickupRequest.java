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
    private Long id;

   @Column(name = "requested_date")
   private Date requestedDate;

   @ManyToOne
   @JoinColumn(name = "requested_by", referencedColumnName = "id")
   private User requestedBy;

   @ManyToOne
   @JoinColumn(name = "request_accepted_by", referencedColumnName = "id")
   private User requestAcceptedBy;

   @ManyToOne
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
