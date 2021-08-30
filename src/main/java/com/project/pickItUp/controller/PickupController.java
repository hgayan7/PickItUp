package com.project.pickItUp.controller;

import com.project.pickItUp.model.GenericApiResponse;
import com.project.pickItUp.model.request.PickupJSONRequest;
import com.project.pickItUp.service.PickupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PickupController {
    @Autowired
    private PickupService pickupService;

    @PostMapping("/create/pickupRequest")
    public ResponseEntity<GenericApiResponse> createPickupRequest(@RequestBody PickupJSONRequest request) {
        return new ResponseEntity<>(new GenericApiResponse(pickupService.createPickup(request)), HttpStatus.OK);
    }
}
