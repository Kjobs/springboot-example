package org.springproject.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springproject.security.entity.SysUser;

@Repository
public interface UserRepository extends JpaRepository<SysUser, Integer> {

    @Query("select u from SysUser u where u.username = ?1")
    SysUser findByUsername(String username);

}
