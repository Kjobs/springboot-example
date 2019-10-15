package com.springboot.redis.demo.model;

import lombok.Data;
import reactor.util.annotation.Nullable;

import javax.persistence.*;

/**
 * @author kobs
 * @date 2019/10/15
 */
@Data
@Entity
public class RedisUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(4)")
    private Integer userId;

    @Nullable
    @Column(columnDefinition = "varchar(10)")
    private String name;
}
