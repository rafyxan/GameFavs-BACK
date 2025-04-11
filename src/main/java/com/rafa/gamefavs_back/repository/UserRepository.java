package com.rafa.gamefavs_back.repository;

import com.rafa.gamefavs_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// This interface extends JpaRepository, which is a Spring Data interface that provides CRUD operations for the User entity.
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}