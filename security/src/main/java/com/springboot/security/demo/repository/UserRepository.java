package com.springboot.security.demo.repository;

import com.springboot.security.demo.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SysUser, Integer> {
    SysUser findByUsername(String username);
}
