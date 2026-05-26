package com.barcafe.user.repository;

import com.barcafe.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository <User, UUID>{
    Optional<User> findByEmailAndDeletedFalse(String email);
    boolean existsByEmail(String email);
}