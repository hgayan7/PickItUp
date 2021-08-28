package com.project.pickItUp.controller;

import com.project.pickItUp.model.GenericApiResponse;
import com.project.pickItUp.model.request.EventCreationRequest;
import com.project.pickItUp.model.response.EventResponse;
import com.project.pickItUp.model.response.EventsByCityResponse;
import com.project.pickItUp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/create/event")
    public ResponseEntity<GenericApiResponse> createEvent(@RequestBody EventCreationRequest request) {
        return new ResponseEntity<>(new GenericApiResponse(eventService.createOrganization(request)), HttpStatus.OK);
    }

    @PostMapping("/add/eventVolunteer/userId/{userId}/eventId/{eventId}")
    public ResponseEntity<GenericApiResponse> addEventVolunteer(@PathVariable Long userId, @PathVariable Long eventId) {
        return new ResponseEntity<>(new GenericApiResponse(eventService.addEventVolunteer(userId, eventId)), HttpStatus.OK);
    }

    @GetMapping("/events/cityId/{cityId}")
    public ResponseEntity<EventsByCityResponse> getEventsByCity() {
        List<EventResponse> eventList = new ArrayList<>();
        EventResponse response = new EventResponse(Long.valueOf(1),"Charity","Event for charity");
        eventList.add(response);
        return new ResponseEntity<>(new EventsByCityResponse(eventList),HttpStatus.OK);
    }
}
