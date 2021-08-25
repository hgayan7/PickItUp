package com.project.pickItUp.controller;

import com.project.pickItUp.model.request.OrganizationCreationRequest;
import com.project.pickItUp.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/createOrganization")
    public String createOrganization(@RequestBody OrganizationCreationRequest request) {
        return this.organizationService.createOrganization(request);
    }

    @PostMapping("/addOrganizationMember/orgId/{orgId}/userId/{userId}")
    public String addOrganizationMember(@PathVariable Long orgId, @PathVariable Long userId) {
        return this.organizationService.addOrganizationMember(orgId, userId);
    }
}
