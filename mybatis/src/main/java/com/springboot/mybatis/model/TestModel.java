package com.springboot.mybatis.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author kobs
 * @date 2019/12/9
 */
@Data
@Entity
public class TestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(5)")
    private Integer modelId;

    @Column(columnDefinition = "varchar(32)")
    private String name;

    @Column(columnDefinition = "varchar(32)")
    private String description;
}
