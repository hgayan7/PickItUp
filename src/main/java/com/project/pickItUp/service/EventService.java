package com.project.pickItUp.service;

import com.project.pickItUp.entity.Event;
import com.project.pickItUp.entity.Organization;
import com.project.pickItUp.entity.User;
import com.project.pickItUp.exception.type.ApiRequestException;
import com.project.pickItUp.model.request.EventCreationRequest;
import com.project.pickItUp.repository.EventRepository;
import com.project.pickItUp.repository.OrganizationRepository;
import com.project.pickItUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    public String createOrganization(EventCreationRequest request) {
        if(organizationService.isUserPartOfOrganization(accountService.getUserId(), request.getOrganizationId())) {
            Event event = new Event();
            event.setName(request.getName());
            event.setDescription(request.getDescription());
            event.setIsValid(1);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Optional<Organization> org = organizationRepository.findById(request.getOrganizationId());
            if(!org.isPresent()) {
                throw new ApiRequestException("Invalid organization id", HttpStatus.BAD_REQUEST);
            }
            event.setOrganization(org.get());
            try {
                Date startDate = formatter.parse(request.getStartDate());
                Date endDate = formatter.parse(request.getEndDate());
                event.setStartDate(startDate);
                event.setEndDate(endDate);
            } catch (Exception e) {
                throw new ApiRequestException("Start date or end date format invalid", HttpStatus.BAD_REQUEST);
            }
            eventRepository.save(event);
            return "Event created";
        } else {
            throw new ApiRequestException("Not authorized", HttpStatus.FORBIDDEN);
        }
    }

    public String addEventVolunteer(Long userId, Long eventId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Event> event = eventRepository.findById(eventId);
        if(user.isPresent() && event.isPresent() && !isUserVolunteerOfEvent(userId, eventId)) {
            event.get().getEventVolunteers().add(user.get());
            eventRepository.save(event.get());
            return "Volunteer added";
        } else {
            throw new ApiRequestException("Invalid user or event id", HttpStatus.BAD_REQUEST);
        }
    }

    public boolean isUserVolunteerOfEvent(Long userId, Long eventId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Event> event = eventRepository.findById(eventId);
        if(!user.isPresent() || !event.isPresent()) {
            return false;
        }
        long isUserVolunteer = event.get().getEventVolunteers().stream()
                .filter(item -> Objects.equals(item.getId(), userId)).count();
        return isUserVolunteer > 0;
    }
}
