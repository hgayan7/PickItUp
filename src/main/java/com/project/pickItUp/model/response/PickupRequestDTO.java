package com.project.pickItUp.model.response;

import com.project.pickItUp.model.PickupRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PickupRequestDTO {
    private Long id;
    private Date requestedDate;
    private String uniqueId;
    private String description;
    private UserDTO requestedBy;
    private UserDTO requestAcceptedBy;
    private EventDTO forEvent;
    private PickupRequestStatus requestStatus;
}
