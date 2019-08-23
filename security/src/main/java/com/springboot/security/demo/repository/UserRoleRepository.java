package com.springboot.security.demo.repository;

import com.springboot.security.demo.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<SysUserRole, Integer> {

    @Query("select u.sysRoleId from SysUserRole u where sysUserId = ?1")
    List<Integer> findRoleIdBySysUserId(Integer sysUserId);
}
