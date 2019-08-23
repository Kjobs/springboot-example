package com.springboot.security.demo.repository;

import com.springboot.security.demo.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<SysRole, Integer> {
    SysRole findByCode(String code);
}
