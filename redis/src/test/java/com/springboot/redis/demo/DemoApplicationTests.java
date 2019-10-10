package com.springboot.redis.demo;

import com.springboot.redis.demo.controller.RedisController;
import com.springboot.redis.demo.model.TestModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    RedisController redisController;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void contextLoads() {
        System.out.println(redisController.testHashOptions());
//        System.out.println(redisTemplate.opsForValue().get("model"));
        TestModel model = (TestModel) redisTemplate.opsForValue().get("model3");
        System.out.println(model);
    }

}
