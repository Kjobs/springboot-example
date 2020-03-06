package com.springboot.redis.demo.controller;

import com.springboot.redis.demo.model.RedisUser;
import com.springboot.redis.demo.service.RedisUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kobs
 * @date 2019/10/15
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private RedisUserService redisUserService;

    @GetMapping(value = "{id}")
    public RedisUser getUser(@PathVariable(value = "id") Integer id) {
        return redisUserService.findOne(id);
    }

    @GetMapping(value = "")
    public void updateOne(Integer id, String name) {
        redisUserService.updateOne(id, name);
    }

    @DeleteMapping(value = "id")
    public void deleteOne(Integer id) {
        redisUserService.deleteOne(id);
    }
}
