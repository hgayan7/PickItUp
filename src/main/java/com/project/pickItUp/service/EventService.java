package com.project.pickItUp.service;

import com.project.pickItUp.entity.Address;
import com.project.pickItUp.entity.Event;
import com.project.pickItUp.entity.Organization;
import com.project.pickItUp.entity.User;
import com.project.pickItUp.exception.type.ApiRequestException;
import com.project.pickItUp.model.request.EventCreationRequest;
import com.project.pickItUp.model.response.AddressDTO;
import com.project.pickItUp.model.response.EventDTO;
import com.project.pickItUp.model.response.EventDetailDTO;
import com.project.pickItUp.repository.AddressRepository;
import com.project.pickItUp.repository.EventRepository;
import com.project.pickItUp.repository.OrganizationRepository;
import com.project.pickItUp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AddressRepository addressRepository;

    public List<EventDTO> findAllEventsByCityId(Long cityId) {
        return eventRepository.findAllEventsByCityId(cityId).stream()
                .map(event -> mapper.map(event, EventDTO.class)).collect(Collectors.toList());
    }

    public EventDetailDTO findEventDetailById(Long eventId) {
        List<Address> eventAddresses = addressRepository.findAllEventAddressesByEventId(eventId);
        Optional<Event> event = eventRepository.findById(eventId);
        if(event.isPresent()) {
            EventDTO eventDTO = mapper.map(event.get(), EventDTO.class);
            List<AddressDTO> addressDTOS = eventAddresses.stream()
                    .map(address -> mapper.map(address, AddressDTO.class)).collect(Collectors.toList());
            return new EventDetailDTO(eventDTO, addressDTOS);
        } else {
            throw new ApiRequestException("Invalid event id", HttpStatus.BAD_REQUEST);
        }
    }

    public String createEvent(EventCreationRequest request) {
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

    public String removeEventVolunteer(Long userId, Long eventId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Event> event = eventRepository.findById(eventId);
        if(user.isPresent() && event.isPresent() && isUserVolunteerOfEvent(userId, eventId)) {
            event.get().getEventVolunteers().remove(user.get());
            eventRepository.save(event.get());
            return "Volunteer Removed";
        } else {
            throw new ApiRequestException("User not a volunteer of event", HttpStatus.BAD_REQUEST);
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
