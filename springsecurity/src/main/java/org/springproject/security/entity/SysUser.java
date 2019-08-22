package org.springproject.security.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "sys_user")
public class SysUser {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",
            joinColumns = {@JoinColumn(name = "sys_user_id")},
            inverseJoinColumns = {@JoinColumn(name = "sys_role_id")})
    private List<SysRole> sysRoles;
}
