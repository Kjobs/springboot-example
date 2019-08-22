package org.springproject.security.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springproject.security.entity.SysUser;
import org.springproject.security.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private Collection<GrantedAuthority> getAuthorities(SysUser user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getSysRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
        });
        return grantedAuthorities;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = userRepository.findByUsername(s);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }
}
