package com.project.pickItUp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickupJSONRequest {
    private String requestedDate;
    private Long requestedBy;
    private Long forEvent;
    private String description;
}
