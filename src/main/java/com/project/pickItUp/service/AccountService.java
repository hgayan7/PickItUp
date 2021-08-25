package com.project.pickItUp.service;

import java.util.Optional;
import java.util.UUID;
import com.project.pickItUp.entity.User;
import com.project.pickItUp.exception.EmailNotRegisteredException;
import com.project.pickItUp.exception.PasswordNotMatchingException;
import com.project.pickItUp.model.request.UserLoginRequest;
import com.project.pickItUp.model.response.TokenResponse;
import com.project.pickItUp.repository.UserRepository;
import com.project.pickItUp.security.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public TokenResponse getTokenPair(User user) {
        String jwtToken = this.jwtUtility.generateAccessToken(user.getId());
        String refreshToken = UUID.randomUUID().toString();
        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse loginUser(UserLoginRequest request) {
        Optional<User> registeredUser = this.userRepository.findByEmail(request.getEmail());
        if(!registeredUser.isPresent()) {
            throw new EmailNotRegisteredException();
        }
        if(passwordEncoder.matches(request.getPassword(), registeredUser.get().getPassword())) {
            return this.getTokenPair(registeredUser.get());
        } else {
            throw new PasswordNotMatchingException();
        }
    }
}
