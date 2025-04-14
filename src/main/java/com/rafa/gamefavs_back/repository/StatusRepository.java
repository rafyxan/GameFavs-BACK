package com.rafa.gamefavs_back.repository;

import com.rafa.gamefavs_back.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    // Metodo para buscar un Status por su nombre
    Optional<Status> findByName(String name);
}
