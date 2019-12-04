package com.springboot.aop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kobs
 * @date 2019/12/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestModel {
    /**
     * 整型数据
     */
    private Integer number;

    /**
     * 字符串数据
     */
    private String string;
}
