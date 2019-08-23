package com.springboot.security.demo.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "sys_user_role")
public class SysUserRole {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "sys_user_id")
    private Integer sysUserId;

    @Column(name = "sys_role_id")
    private Integer sysRoleId;
}
