package com.project.pickItUp.service;

import com.project.pickItUp.entity.*;
import com.project.pickItUp.exception.type.ApiRequestException;
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
    private AccountService accountService;
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

}
