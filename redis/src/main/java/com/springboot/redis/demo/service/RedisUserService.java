package com.springboot.redis.demo.service;

import com.springboot.redis.demo.model.RedisUser;
import com.springboot.redis.demo.repository.RedisUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author kobs
 * @date 2019/10/15
 */
@Service
public class RedisUserService {

    @Autowired
    RedisUserRepository redisUserRepository;

    /**
     * 获取RedisUser对象，若无缓存就去数据库查询，有则直接从缓存中读取
     * @param id 用户id
     * @return RedisUser对象
     */
    @Cacheable(cacheNames = "redisUser", key = "#id")
    public RedisUser findOne(Integer id) {
        return redisUserRepository.findByUserId(id);
    }

    @CachePut(cacheNames = "redisUser", key = "#id")
    public void updateOne(Integer id, String name) {
        RedisUser user = new RedisUser();
        user.setUserId(id);
        user.setName(name);
        redisUserRepository.save(user);
    }

    @CacheEvict(cacheNames = "redisUser", key = "#id")
    public void deleteOne(Integer id) {
        redisUserRepository.deleteById(id);
    }
}
