package org.springproject.security.service.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springproject.security.entity.SysUser;

import java.io.Serializable;

@Data
@Component
public class LoginOutputDTO implements Serializable {

    private String headerName;

    private String token;

    private SysUser user;
}
