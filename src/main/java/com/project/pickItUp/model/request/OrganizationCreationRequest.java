package com.project.pickItUp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationCreationRequest {
    private String name;
    private String websiteUrl;
    private String creatorId;
}
