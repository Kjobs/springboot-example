package org.springproject.security.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "sys_role")
public class SysRole {

    @Id
    private Integer id;

    @NotNull
    private String code;
}
