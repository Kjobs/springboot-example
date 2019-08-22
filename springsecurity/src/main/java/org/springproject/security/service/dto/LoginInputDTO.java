package org.springproject.security.service.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class LoginInputDTO implements Serializable {

    private String username;

    private String password;
}
