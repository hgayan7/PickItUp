package com.project.pickItUp.controller;

import com.project.pickItUp.model.GenericApiResponse;
import com.project.pickItUp.model.request.AddressUpdateRequest;
import com.project.pickItUp.model.request.OrganizationCreationRequest;
import com.project.pickItUp.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("organizations/create")
    public ResponseEntity<GenericApiResponse> createOrganization(@RequestBody OrganizationCreationRequest request) {
        return new ResponseEntity<>(new GenericApiResponse(this.organizationService.createOrganization(request)),
                HttpStatus.OK);
    }

    @PostMapping("/organizations/{orgId}/addMember/userEmail/{userEmail}")
    public ResponseEntity<GenericApiResponse> addOrganizationMember(@PathVariable Long orgId, @PathVariable String userEmail) {
        return ResponseEntity.ok(new GenericApiResponse(this.organizationService.addOrganizationMember(orgId, userEmail)));
    }

    @PostMapping("/organizations/address")
    public ResponseEntity<GenericApiResponse> updateAddress(@RequestBody AddressUpdateRequest request) {
        return new ResponseEntity<>(new GenericApiResponse(this.organizationService.updateAddress(request)), HttpStatus.OK);
    }
}
