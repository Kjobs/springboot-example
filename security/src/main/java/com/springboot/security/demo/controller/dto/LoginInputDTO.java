package com.springboot.security.demo.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Component
public class LoginInputDTO implements Serializable {

    private static final long serialVersionUID = 425356085625432280L;

    @ApiModelProperty(value = "用户名", required = true)
    @NotNull(message = "用户名必填")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotNull(message = "密码必填")
    private String password;
}
