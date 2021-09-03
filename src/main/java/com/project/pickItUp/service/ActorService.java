package com.project.pickItUp.service;

import com.project.pickItUp.entity.*;
import com.project.pickItUp.exception.type.ApiRequestException;
import com.project.pickItUp.model.ParentType;
import com.project.pickItUp.model.request.AddressUpdateRequest;
import com.project.pickItUp.model.response.UserDTO;
import com.project.pickItUp.repository.*;
import org.modelmapper.ModelMapper;
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
    private UserAddressRepository userAddressRepository;
    @Autowired
    private OrganizationAddressRepository organizationAddressRepository;
    @Autowired
    private EventAddressRepository eventAddressRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ModelMapper mapper;

    public UserDTO getUser() {
        long userId = accountService.getUserId();
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return mapper.map(user.get(), UserDTO.class);
        } else {
            throw new ApiRequestException("Invalid user id", HttpStatus.BAD_REQUEST);
        }
    }

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
            UserAddress address = new UserAddress();
            address.setAddress(request.getAddress());
            address.setCity(city.get());
            address.setUser(user.get());
            userAddressRepository.save(address);
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
            OrganizationAddress address = new OrganizationAddress();
            address.setAddress(request.getAddress());
            address.setCity(city.get());
            address.setOrganization(org.get());
            organizationAddressRepository.save(address);
            return "Address updated";
        } else {
            throw new ApiRequestException("Organization or City not found",HttpStatus.BAD_REQUEST);
        }
    }

    private String updateEventAddress(AddressUpdateRequest request) {
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
