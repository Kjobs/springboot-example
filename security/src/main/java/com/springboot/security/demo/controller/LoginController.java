package com.springboot.security.demo.controller;

import com.springboot.security.demo.controller.dto.LoginInputDTO;
import com.springboot.security.demo.controller.dto.LoginOutputDTO;
import com.springboot.security.demo.controller.dto.UserDTO;
import com.springboot.security.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@Api(tags = "用户管理接口")
public class LoginController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "首页")
    @GetMapping("")
    public String index() {
        return "index";
    }

    @ApiOperation(value = "测试页面")
    @GetMapping(value = "test")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String test() {
        return "test";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "增加用户")
    @PostMapping("/user")
    public String addUser(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
        return "success";
    }

    @ApiOperation(value = "认证")
    @PostMapping(value = "login")
    public LoginOutputDTO login(@RequestBody LoginInputDTO inputDTO) {
        return userService.authenticate(inputDTO);
    }
}
