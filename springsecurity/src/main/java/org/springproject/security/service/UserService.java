package org.springproject.security.service;

import org.springframework.stereotype.Service;
import org.springproject.security.entity.SysUser;
import org.springproject.security.service.dto.LoginInputDTO;
import org.springproject.security.service.dto.LoginOutputDTO;

@Service
public interface UserService {
    SysUser findOne(Integer id);

    SysUser findByUsername(String username);

    LoginOutputDTO authenticate(LoginInputDTO loginInputDTO);
}
