package com.project.pickItUp.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "User login request")
public class UserLoginRequest {
    private String email;
    @Size(min = 8, message = "Password should be atleast 8 characters long")
    @ApiModelProperty(notes = "Password should be atleast 8 characters long")
    private String password;
}
