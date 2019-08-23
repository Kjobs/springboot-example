package com.springboot.security.demo.controller.dto;

import com.springboot.security.demo.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class LoginOutputDTO implements Serializable {

    private static final long serialVersionUID = 554777465725799600L;

    @ApiModelProperty(value = "token的header键名字")
    private String headerName;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "用户信息")
    private SysUser user;
}
