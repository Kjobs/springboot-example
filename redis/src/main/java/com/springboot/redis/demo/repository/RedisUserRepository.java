package com.springboot.redis.demo.repository;

import com.springboot.redis.demo.model.RedisUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kobs
 * @date 2019/10/15
 */
@Repository
public interface RedisUserRepository extends JpaRepository<RedisUser, Integer>, CrudRepository<RedisUser, Integer> {

    RedisUser findByUserId(Integer id);
}
