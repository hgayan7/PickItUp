package com.project.pickItUp.controller;

import com.project.pickItUp.model.GenericApiResponse;
import com.project.pickItUp.model.request.AddressUpdateRequest;
import com.project.pickItUp.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActorController {

    @Autowired
    private ActorService actorService;

    @PostMapping("/update/address")
    public ResponseEntity<GenericApiResponse> updateAddress(@RequestBody AddressUpdateRequest request) {
        return new ResponseEntity<>(new GenericApiResponse(this.actorService.updateAddress(request)), HttpStatus.OK);
    }

}
