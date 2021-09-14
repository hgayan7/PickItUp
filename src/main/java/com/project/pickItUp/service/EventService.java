package com.project.pickItUp.service;

import com.project.pickItUp.controller.AddressUpdate;
import com.project.pickItUp.entity.*;
import com.project.pickItUp.exception.type.ApiRequestException;
import com.project.pickItUp.model.request.AddressUpdateRequest;
import com.project.pickItUp.model.request.EventCreationRequest;
import com.project.pickItUp.model.response.AddressDTO;
import com.project.pickItUp.model.response.EventDTO;
import com.project.pickItUp.model.response.EventDetailDTO;
import com.project.pickItUp.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "event_cache")
public class EventService implements AddressUpdate {
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
    private EventAddressRepository eventAddressRepository;
    @Autowired
    private CityRepository cityRepository;

    @Cacheable(cacheNames = "events_city", key = "#cityId", unless = "#result == null")
    public List<EventDTO> findAllEventsByCityId(Long cityId) {
        return eventRepository.findAllEventsByCityId(cityId).stream()
                .map(event -> mapper.map(event, EventDTO.class)).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "event", key = "#eventId", unless = "#result == null")
    public EventDetailDTO findEventDetailById(Long eventId) {
        List<EventAddress> eventAddresses = eventAddressRepository.findAllEventAddressesByEventId(eventId);
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
            //Event becomes active after verification/addition of event address
            event.setIsValid(0);
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

    @Override
    public String updateAddress(AddressUpdateRequest request) {
        Optional<Event> event = eventRepository.findById(request.getEntityId());
        Optional<City> city = this.cityRepository.findById(request.getCityId());
        if(event.isPresent() && city.isPresent()) {
            EventAddress address = new EventAddress();
            address.setAddress(request.getAddress());
            address.setCity(city.get());
            address.setEvent(event.get());
            eventAddressRepository.save(address);
            return "Address updated";
        } else {
            throw new ApiRequestException("Event or City not found",HttpStatus.BAD_REQUEST);
        }
    }
}
