package com.project.pickItUp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventCreationRequest {
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private Long organizationId;
}
