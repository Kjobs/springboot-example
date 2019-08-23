package com.springboot.security.demo.service;

import com.springboot.security.demo.controller.dto.LoginInputDTO;
import com.springboot.security.demo.controller.dto.LoginOutputDTO;
import com.springboot.security.demo.controller.dto.UserDTO;
import com.springboot.security.demo.entity.SysUser;

public interface UserService {

    void save(UserDTO userDTO);

    SysUser findOne(Integer id);

    SysUser findByUsername(String username);

    LoginOutputDTO authenticate(LoginInputDTO inputDTO);
}
