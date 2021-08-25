package com.project.pickItUp.security;

import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JWTUtility {
    
    @Value("${jwt.server.secret}")
    private String SERVER_SECRET;

    @Value("${jwt.server.tokenExpiryTime}")
    private long ACCESS_TOKEN_EXPIRY_TIME;
    
    public String generateAccessToken(long userId) {
        return JWT.create().withSubject(String.valueOf(userId))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY_TIME*1000))
                .sign(Algorithm.HMAC256(SERVER_SECRET));
    }

    public Authentication getAuthentication(String token) {
        String username;
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SERVER_SECRET.getBytes()))
            .build().verify(token);
            username = jwt.getSubject();
        } catch (JWTVerificationException exception){
            System.out.println(exception);
            return null;
        }
        long userId;
        try {
            userId = Long.valueOf(username);
        } catch(NumberFormatException exception) {
            System.out.println(exception);
            return null;
        }
        return new JWTAuthentication(userId);
    }
}
