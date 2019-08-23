package com.springboot.security.demo.service.Impl;

import com.springboot.security.demo.config.JwtConfig;
import com.springboot.security.demo.config.JwtUtils;
import com.springboot.security.demo.controller.dto.LoginInputDTO;
import com.springboot.security.demo.controller.dto.LoginOutputDTO;
import com.springboot.security.demo.controller.dto.UserDTO;
import com.springboot.security.demo.entity.SysRole;
import com.springboot.security.demo.entity.SysUser;
import com.springboot.security.demo.entity.SysUserRole;
import com.springboot.security.demo.repository.RoleRepository;
import com.springboot.security.demo.repository.UserRepository;
import com.springboot.security.demo.repository.UserRoleRepository;
import com.springboot.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Boolean containRole(String roleCode) {
        SysRole sysRole = roleRepository.findByCode(roleCode);
        if(sysRole != null) return true;
        return false;
    }

    public Boolean containUserRole(SysUser user, String roleCode) {
        List<SysRole> list = user.getSysRoles();
        return list.stream().anyMatch(role -> role.getCode().equals(roleCode));
    }

    @Override
    public void save(UserDTO userDTO) {
        SysUser user = userRepository.findByUsername(userDTO.getUsername());
        if(user != null) {
            if (containUserRole(user, userDTO.getRoleCode())) {
                throw new RuntimeException("用户角色已存在！");
            }
            if (!containRole(userDTO.getRoleCode())) {
                SysRole role = new SysRole();
                role.setCode(userDTO.getRoleCode());
                roleRepository.save(role);
            }
            SysUserRole ur = new SysUserRole();
            ur.setSysRoleId(roleRepository.findByCode(userDTO.getRoleCode()).getId());
            ur.setSysUserId(user.getId());
            userRoleRepository.save(ur);
        }
        user = new SysUser();
        user.setUsername(userDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public SysUser findOne(Integer id) {
        return userRepository.getOne(id);
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
        return outputDTO;
    }

    /**
     * 获取用户权限
     * @param user 用户
     * @return 权限信息
     */
    private Collection<GrantedAuthority> getAuthorities(SysUser user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getSysRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
        });
        return grantedAuthorities;
    }

    /**
     * 实现UserDetailsService接口，得到认证用户
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = userRepository.findByUsername(s);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }
}
