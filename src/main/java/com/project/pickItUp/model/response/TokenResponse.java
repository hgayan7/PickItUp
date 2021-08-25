package com.project.pickItUp.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    @JsonProperty("jwt_token")
    private String jwtToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
