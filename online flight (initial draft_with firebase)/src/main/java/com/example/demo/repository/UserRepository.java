package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Additional custom query methods can be defined here

    // Example: Find a user by username
    Optional<User> findByUserName(String userName);

    // Example: Check if a user exists by email
    boolean existsByEmail(String email);

    User findByUid(String uid);
}
