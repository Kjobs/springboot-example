package org.springproject.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springproject.security.service.UserService;
import org.springproject.security.service.dto.LoginInputDTO;
import org.springproject.security.service.dto.LoginOutputDTO;

@Controller
@RequestMapping("")
public class MainPage {

    @Autowired
    private UserService userService;

    @GetMapping(value = "test")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String test() {
        return "test";
    }

    @GetMapping(value = "login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "login")
    public LoginOutputDTO login(@RequestBody LoginInputDTO inputDTO) {
        return userService.authenticate(inputDTO);
    }
}
