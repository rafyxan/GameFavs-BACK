package com.rafa.gamefavs_back.controller;

import com.rafa.gamefavs_back.model.Videogame;
import com.rafa.gamefavs_back.service.RawgApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@RequestMapping("/api/rawg")
public class RawgApiController {
    @Autowired
    private RawgApiService rawgApiService;

    // Metodo para obtener los videojuegos más populares
    @GetMapping("/games")
    public List<Videogame> getLatestGames() {
        return rawgApiService.getTopRatedGames();
    }

    // Metodo para obtener los detalles de un videojuego
    @GetMapping("/game/details/{id}")
    public Videogame getGameDetails(@PathVariable Long id) {
        return rawgApiService.getGameDetailsById(id);
    }

    // Metodo para obtener los videojuegos con filtros
    @GetMapping("/games/filtered")
    public List<Videogame> getGamesWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genres,
            @RequestParam(required = false) String platforms,
            @RequestParam(required = false) String ratingOrder) {
        return rawgApiService.getGamesWithFilters(name, genres, platforms, ratingOrder);
    }
}
