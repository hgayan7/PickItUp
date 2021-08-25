package com.project.pickItUp.controller;

import com.project.pickItUp.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    
    @Autowired
    private AccountService accountService;

    // @PostMapping("/register")
    // public @ResponseBody 
}
