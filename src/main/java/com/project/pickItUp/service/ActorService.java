package com.project.pickItUp.service;

import com.project.pickItUp.entity.*;
import com.project.pickItUp.exception.type.ApiRequestException;
import com.project.pickItUp.model.ParentType;
import com.project.pickItUp.model.request.AddressUpdateRequest;
import com.project.pickItUp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ActorService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CityRepository cityRepository;

    public String updateAddress(AddressUpdateRequest request) {
        switch (request.getParentType()) {
            case 0: return updateUserAddress(request);
            case 1: return updateOrganizationAddress(request);
            case 2: return updateEventAddress(request);
            default: throw new ApiRequestException("Invalid parentType", HttpStatus.BAD_REQUEST);
        }
    }

    private String updateUserAddress(AddressUpdateRequest request) {
        Optional<User> user = this.userRepository.findById(accountService.getUserId());
        Optional<City> city = this.cityRepository.findById(request.getCityId());
        if(user.isPresent() && city.isPresent()) {
            Address address = new Address();
            address.setAddress(request.getAddress());
            address.setCity(city.get());
            address.setUser(user.get());
            address.setParentType(ParentType.USER);
            addressRepository.save(address);
            return "Address updated";
        } else {
            throw new ApiRequestException("User or City not found",HttpStatus.BAD_REQUEST);
        }
    }

    private String updateOrganizationAddress(AddressUpdateRequest request) {
        Optional<Organization> org = organizationRepository.findById(request.getEntityId());
        Optional<City> city = this.cityRepository.findById(request.getCityId());
        Optional<User> requestingUser = this.userRepository.findById(accountService.getUserId());
        if(org.isPresent() && city.isPresent() && requestingUser.isPresent()) {
            if(!organizationService.isUserPartOfOrganization(requestingUser.get().getId(), request.getEntityId())) {
                throw new ApiRequestException("You cannot access this resource", HttpStatus.FORBIDDEN);
            }
            Address address = new Address();
            address.setAddress(request.getAddress());
            address.setCity(city.get());
            address.setOrganization(org.get());
            address.setParentType(ParentType.ORGANIZATION);
            addressRepository.save(address);
            return "Address updated";
        } else {
            throw new ApiRequestException("Organization or City not found",HttpStatus.BAD_REQUEST);
        }
    }

    private String updateEventAddress(AddressUpdateRequest request) {
        Optional<Event> event = eventRepository.findById(request.getEntityId());
        Optional<City> city = this.cityRepository.findById(request.getCityId());
        if(event.isPresent() && city.isPresent()) {
            Address address = new Address();
            address.setAddress(request.getAddress());
            address.setCity(city.get());
            address.setEvent(event.get());
            address.setParentType(ParentType.EVENT);
            addressRepository.save(address);
            return "Address updated";
        } else {
            throw new ApiRequestException("Event or City not found",HttpStatus.BAD_REQUEST);
        }
    }
}
