package com.project.pickItUp.service;

import java.util.Optional;

import com.project.pickItUp.entity.Address;
import com.project.pickItUp.entity.City;
import com.project.pickItUp.entity.User;
import com.project.pickItUp.exception.type.ApiRequestException;
import com.project.pickItUp.model.ParentType;
import com.project.pickItUp.model.request.UserRegistrationRequest;
import com.project.pickItUp.repository.CityRepository;
import com.project.pickItUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CityRepository cityRepository;
    
    public User registerUser(UserRegistrationRequest request) {
        Optional<User> registeredUser = this.userRepository.findByEmail(request.getEmail());
        if(registeredUser.isPresent()) {
            throw new ApiRequestException("Email already registered", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setContactNo(request.getContactNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Optional<City> city = cityRepository.findById(request.getCityId());
        if(!request.getAddress().isEmpty() && city.isPresent()) {
            Address address = new Address();
            address.setAddress(request.getAddress());
            address.setCity(city.get());
            address.setParentType(ParentType.USER);
            address.setUser(user);
            user.setAddress(address);
        }
        this.userRepository.save(user);
        return user;
    }

}
