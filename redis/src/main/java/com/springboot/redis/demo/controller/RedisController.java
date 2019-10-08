package com.springboot.redis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kobs
 * @date 2019/10/8
 */
@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping(value = "testString")
    public String testString() {
        stringRedisTemplate.opsForValue().set("name", "kobs");
        return "test by " + stringRedisTemplate.opsForValue().get("name");
    }
}
