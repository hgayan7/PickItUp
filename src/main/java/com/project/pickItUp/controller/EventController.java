package com.project.pickItUp.controller;

import com.project.pickItUp.model.GenericApiResponse;
import com.project.pickItUp.model.request.EventCreationRequest;
import com.project.pickItUp.model.response.EventDTO;
import com.project.pickItUp.model.response.EventDetailDTO;
import com.project.pickItUp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/create/event")
    public ResponseEntity<GenericApiResponse> createEvent(@RequestBody EventCreationRequest request) {
        return new ResponseEntity<>(new GenericApiResponse(eventService.createEvent(request)), HttpStatus.OK);
    }

    @PostMapping("/add/eventVolunteer/userId/{userId}/eventId/{eventId}")
    public ResponseEntity<GenericApiResponse> addEventVolunteer(@PathVariable Long userId, @PathVariable Long eventId) {
        return new ResponseEntity<>(new GenericApiResponse(eventService.addEventVolunteer(userId, eventId)), HttpStatus.OK);
    }

    @PostMapping("/delete/eventVolunteer/userId/{userId}/eventId/{eventId}")
    public ResponseEntity<GenericApiResponse> removeEventVolunteer(@PathVariable Long userId, @PathVariable Long eventId) {
        return new ResponseEntity<>(new GenericApiResponse(eventService.removeEventVolunteer(userId, eventId)), HttpStatus.OK);
    }

    @GetMapping("/events/cityId/{cityId}")
    public ResponseEntity<List<EventDTO>> getEventsByCity(@PathVariable Long cityId) {
        return new ResponseEntity<>(eventService.findAllEventsByCityId(cityId), HttpStatus.OK);
    }

    @GetMapping("/events/detail/eventId/{eventId}")
    public ResponseEntity<EventDetailDTO> getEventDetailById(@PathVariable Long eventId) {
        return new ResponseEntity<>(eventService.findEventDetailById(eventId), HttpStatus.OK);
    }
}
