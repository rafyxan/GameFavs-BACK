package com.rafa.gamefavs_back.repository;

import com.rafa.gamefavs_back.model.Videogame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideogameRepository  extends JpaRepository<Videogame, Long> {
}
