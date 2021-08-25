package com.project.pickItUp.service;

import java.util.Optional;

import com.project.pickItUp.entity.User;
import com.project.pickItUp.exception.EmailAlreadyRegisteredException;
import com.project.pickItUp.model.request.UserRegistrationRequest;
import com.project.pickItUp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public User registerUser(UserRegistrationRequest request) {
        Optional<User> registeredUser = this.userRepository.findByEmail(request.getEmail());
        if(registeredUser.isPresent()) {
            throw new EmailAlreadyRegisteredException();
        }
        User user = new User();
        user.setFirstName(request.getFirstName())
        .setLastName(request.getLastName())
        .setEmail(request.getEmail())
        .setContactNumber(request.getContactNumber());
        this.userRepository.save(user);
        return user;
    }
}