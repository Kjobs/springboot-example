package com.springboot.redis.demo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kobs
 * @date 2019/10/8
 */
@Data
public class TestModel implements Serializable {
    private static final long serialVersionUID = 8615939525685789362L;

    private String code;
    private String name;

    /**
     * jackson的反序列化需要无参构造函数
     */
    public TestModel() {

    }

    public TestModel(String code, String name) {
        super();
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return "{code:" + getCode() + ", name:" + getName() + "}";
    }
}
