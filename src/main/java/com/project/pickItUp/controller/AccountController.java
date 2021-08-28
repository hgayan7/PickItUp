package com.project.pickItUp.controller;

import com.project.pickItUp.entity.User;
import com.project.pickItUp.model.GenericApiResponse;
import com.project.pickItUp.model.request.UserLoginRequest;
import com.project.pickItUp.model.request.UserRegistrationRequest;
import com.project.pickItUp.model.response.TokenResponse;
import com.project.pickItUp.service.AccountService;
import com.project.pickItUp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registerUser(@RequestBody UserRegistrationRequest request) {
        User user = this.userService.registerUser(request);
        return new ResponseEntity<>(this.accountService.getTokenPair(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody UserLoginRequest request) {
        return new ResponseEntity<>(this.accountService.loginUser(request), HttpStatus.OK);
    }

    @DeleteMapping("/delete/user")
    public ResponseEntity<GenericApiResponse> deleteUser() {
        return new ResponseEntity<>(new GenericApiResponse(this.accountService.deleteUser()), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return "Test";
    }
}
