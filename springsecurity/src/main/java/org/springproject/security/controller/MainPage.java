package org.springproject.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springproject.security.service.dto.LoginInputDTO;

@Controller
public class MainPage {

    @RequestMapping("")
    public String index() {
        return "index";
    }

    @GetMapping(value = "test")
    public String test() {
        return "test";
    }

    @GetMapping(value = "login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "login")
    public String login(LoginInputDTO loginInputDTO) {
        return "success";
    }
}
