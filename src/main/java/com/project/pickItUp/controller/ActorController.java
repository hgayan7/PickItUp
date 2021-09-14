package com.project.pickItUp.controller;

import com.project.pickItUp.model.GenericApiResponse;
import com.project.pickItUp.model.request.AddressUpdateRequest;
import com.project.pickItUp.model.response.AddressDTO;
import com.project.pickItUp.model.response.UserDTO;
import com.project.pickItUp.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser() {
        return new ResponseEntity<>(actorService.getUser(), HttpStatus.OK);
    }
}
