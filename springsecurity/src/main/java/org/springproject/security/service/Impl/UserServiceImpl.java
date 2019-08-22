package org.springproject.security.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springproject.security.config.JwtConfig;
import org.springproject.security.config.JwtUtils;
import org.springproject.security.entity.SysUser;
import org.springproject.security.repository.UserRepository;
import org.springproject.security.service.UserService;
import org.springproject.security.service.dto.LoginInputDTO;
import org.springproject.security.service.dto.LoginOutputDTO;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Qualifier("authUserDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public SysUser findOne(Integer userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public SysUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 登录认证
     * @param inputDTO 用户的实体封装
     * @return LoginOutputDTO 带有token的实体封装
     */
    @Override
    public LoginOutputDTO authenticate(LoginInputDTO inputDTO) {
        Objects.requireNonNull(inputDTO.getUsername());
        Objects.requireNonNull(inputDTO.getPassword());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(inputDTO.getUsername(),inputDTO.getPassword()));
        } catch (DisabledException e) {
            throw new RuntimeException("用户已被禁用！");
        } catch (BadCredentialsException e){
            throw new RuntimeException("用户名或密码错误！");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(inputDTO.getUsername());
        SysUser user = userRepository.findByUsername(inputDTO.getUsername());
        String token = jwtUtils.generateToken(userDetails);
        LoginOutputDTO outputDTO = new LoginOutputDTO();
        outputDTO.setHeaderName(JwtConfig.TOKEN_HEADER);
        outputDTO.setToken(token);
        outputDTO.setUser(user);
        return null;
    }

}
