package org.springproject.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springproject.security.entity.SysUser;

@Repository
public interface UserRepository extends JpaRepository<SysUser, Integer> {

    SysUser findByUsername(String username);
}
