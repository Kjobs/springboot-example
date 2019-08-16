package org.springproject.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "")
public class MainPage {

    @GetMapping(value = "test")
    public String test() {
        return "test";
    }

    @GetMapping(value = "login")
    public String login() {
        return "success";
    }
}
