package com.springboot.redis.demo;

import com.springboot.redis.demo.controller.RedisController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    RedisController redisController;

    @Test
    public void contextLoads() {
        System.out.println(redisController.testObject());
    }

}
