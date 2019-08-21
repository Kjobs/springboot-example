package org.springproject.security.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginInputDTO implements Serializable {

    private String username;

    private String password;
}
