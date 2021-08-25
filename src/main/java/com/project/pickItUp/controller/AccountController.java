package com.project.pickItUp.controller;

import com.project.pickItUp.entity.User;
import com.project.pickItUp.model.request.UserLoginRequest;
import com.project.pickItUp.model.request.UserRegistrationRequest;
import com.project.pickItUp.model.response.TokenResponse;
import com.project.pickItUp.service.AccountService;
import com.project.pickItUp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public @ResponseBody TokenResponse registerUser(@RequestBody UserRegistrationRequest request) {
        User user = this.userService.registerUser(request);
        return this.accountService.getTokenPair(user);
    }

    @PostMapping("/login")
    public @ResponseBody TokenResponse loginUser(@RequestBody UserLoginRequest request) {
        return this.accountService.loginUser(request);
    }

    @GetMapping("/test")
    public String test() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return "Test";
    }
}
