package com.hello.__suzy_kang_community_be.user.repository;

import com.hello.__suzy_kang_community_be.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
