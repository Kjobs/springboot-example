package com.springboot.security.demo.controller.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class UserDTO implements Serializable {
    private static final long serialVersionUID = -3167599503730846627L;
    private String username;
    private String password;
    private String roleCode;
}
