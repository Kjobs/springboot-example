package org.springproject.security.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_role")
public class SysRole {

    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_code")
    private String roleCode;
}
