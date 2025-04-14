package com.rafa.gamefavs_back.repository;

import com.rafa.gamefavs_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// This interface extends JpaRepository, which is a Spring Data interface that provides CRUD operations for the User entity.
public interface UserRepository extends JpaRepository<User, Long> {
    // Metodo para buscar un usuario por su nombre de usuario y contraseña
    Optional<User> findByUsernameAndPassword(String username, String password);
    // Metodo para buscar un usuario por su nombre de usuario
    Optional<User> findByUsername(String username);
    // Metodo para buscar un usuario por su correo electronico
    Optional<User> findByEmail(String email);
}