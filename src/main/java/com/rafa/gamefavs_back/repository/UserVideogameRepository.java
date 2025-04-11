package com.rafa.gamefavs_back.repository;

import com.rafa.gamefavs_back.model.UserVideogame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserVideogameRepository extends JpaRepository<UserVideogame, Long> {
    void deleteByUserIdAndVideogameIdAndStatusId(Long userId, Long videogameId, Long statusId);
    List<UserVideogame> findByUserIdAndStatusName(Long userId, String statusName);

    boolean existsByUserIdAndVideogameIdAndStatusId(Long userId, Long videogameId, Long statusId);

    Optional<UserVideogame> findByUserIdAndVideogameIdAndStatusId(Long userId, Long videogameId, Long statusId);

}
