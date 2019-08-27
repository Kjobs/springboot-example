package com.springboot.security.demo.config;

import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    /**
     * 加密salt值
     */
    public final static String TOKEN_SECRET = "secret";

    /**
     * 过期时间
     */
    public final static Integer TOKEN_EXPIRATION = 604800;

    /**
     * 头部标识
     */
    public final static String TOKEN_HEADER = "header";

    /**
     * bearer
     */
    public final static String TOKEN_BEARER = "bearer";

}
