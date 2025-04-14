package com.rafa.gamefavs_back.repository;

import com.rafa.gamefavs_back.model.UserVideogame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserVideogameRepository extends JpaRepository<UserVideogame, Long> {
    // Metodo para buscar un UserVideogame por su id de usuario, id de videojuego y id de estado
    void deleteByUserIdAndVideogameIdAndStatusId(Long userId, Long videogameId, Long statusId);
    // Metodo para buscar un UserVideogame por su id de usuario y nombre de estado
    List<UserVideogame> findByUserIdAndStatusName(Long userId, String statusName);
    // Metodo para buscar un UserVideogame por su id de usuario, id de videojuego y id de estado
    boolean existsByUserIdAndVideogameIdAndStatusId(Long userId, Long videogameId, Long statusId);
    // Metodo para buscar un UserVideogame por su id de usuario, id de videojuego y id de estado
    Optional<UserVideogame> findByUserIdAndVideogameIdAndStatusId(Long userId, Long videogameId, Long statusId);

}
