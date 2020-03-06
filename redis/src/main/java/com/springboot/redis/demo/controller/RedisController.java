package com.springboot.redis.demo.controller;

import com.springboot.redis.demo.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author kobs
 * @date 2019/10/8
 */
@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Cacheable
    @GetMapping(value = "testString")
    public String testString() {
        stringRedisTemplate.opsForValue().set("name", "kobs");
        return "test by " + stringRedisTemplate.opsForValue().get("name");
    }

    @GetMapping(value = "testObject")
    public String testObject() {
        StringBuilder result = new StringBuilder();
        TestModel model = new TestModel("1", "redis");
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set("model", model);
        operations.set("model.timeout", model, 1, TimeUnit.SECONDS);
        result.append("过期前：\n");
        result.append("model=").append(operations.get("model")).append("\n");
        result.append("model.timeout=").append(operations.get("model.timeout")).append("\n");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result.append("过期后：\n");
        result.append("model=").append(operations.get("model")).append("\n");
        result.append("model.timeout=").append(operations.get("model.timeout")).append("\n");
        return result.toString();
    }

    @GetMapping(value = "testHashOptions")
    public String testHashOptions() {
        StringBuilder result = new StringBuilder();
        HashOperations<String, String, String> operations = redisTemplate.opsForHash();
        operations.put("hashValues", "hk1", "hv1");
        operations.put("hashValues", "hk2", "hv2");
        result.append("获取指定key的value:\n");
        result.append(operations.get("hashValues", "hk1"));
        result.append("获取hk的Set集合:\n");
        result.append(operations.keys("hashValues")).append("\n");
        result.append("获取hv的List集合:\n");
        result.append(operations.values("hashValues")).append("\n");
        result.append("获取hashValues的键值对:\n");
        result.append(operations.entries("hashValues")).append("\n");
        return result.toString();
    }
}
