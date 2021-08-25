package com.project.pickItUp.service;

import java.util.UUID;

import com.project.pickItUp.entity.User;
import com.project.pickItUp.model.request.UserRegistrationRequest;
import com.project.pickItUp.model.response.TokenResponse;
import com.project.pickItUp.security.JWTFilter;
import com.project.pickItUp.security.JWTUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    
    @Autowired
    private JWTUtility jwtUtility;

    public TokenResponse getTokenPair(User user) {
        String jwtToken = this.jwtUtility.generateAccessToken(user.getId());
        String refreshToken = UUID.randomUUID().toString();
        return TokenResponse(jwtToken, refreshToken);
    }
}
