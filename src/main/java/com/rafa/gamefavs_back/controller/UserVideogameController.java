package com.rafa.gamefavs_back.controller;

import com.rafa.gamefavs_back.model.UserVideogame;
import com.rafa.gamefavs_back.model.Videogame;
import com.rafa.gamefavs_back.repository.UserVideogameRepository;
import com.rafa.gamefavs_back.service.UserVideogameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@RequestMapping("/user-videogames")
public class UserVideogameController {
    private static final Logger logger = LoggerFactory.getLogger(UserVideogameService.class);

    @Autowired
    private UserVideogameService userVideogameService;

    @Autowired
    private UserVideogameRepository userVideogameRepository;

    // Añadir un videojuego a la lista de favoritos/jugados/por jugar de un usuario y el videojuego en sí
    @PostMapping("/add")
    public UserVideogame addUserVideogame(@RequestBody Map<String, Object> payload) {
        logger.info("Adding UserVideogame with payload: {}", payload);
        logger.info("userId, videogameId, statusId: {}, {}, {}", payload.get("userId"), payload.get("videogameId"), payload.get("status"));
        int userId = Integer.parseInt(payload.get("userId").toString());
        int videogameId = Integer.parseInt(payload.get("videogameId").toString());
        int statusId = Integer.parseInt(payload.get("status").toString());
        Map<String, Object> videogameData = (Map<String, Object>) payload.get("videogame");

        // Crear un objeto Videogame a partir de los datos recibidos
        Videogame videogame = new Videogame();
        videogame.setId(Long.valueOf(videogameData.get("id").toString()));
        videogame.setName(videogameData.get("name").toString());
        videogame.setReleased(videogameData.get("released").toString());
        videogame.setBackgroundImage(videogameData.get("backgroundImage").toString());
        videogame.setRating(Float.valueOf(videogameData.get("rating").toString()));
        videogame.setPlatforms(videogameData.get("platforms").toString());
        videogame.setGenres(videogameData.get("genres").toString());
        videogame.setDeveloper(videogameData.get("developer").toString());
        videogame.setDescription(videogameData.get("description").toString());
        videogame.setWebsite(videogameData.get("website").toString());



        // Llamar al servicio para guardar el UserVideogame
        return userVideogameService.addUserVideogame(userId, videogameId, statusId, videogame);
    }

    // Eliminar un videojuego de la lista de un usuario
    @DeleteMapping("/delete")
    public void deleteUserVideogame(@RequestParam Long userId, @RequestParam Long videogameId, @RequestParam Long statusId) {
        userVideogameService.deleteUserVideogame(userId, videogameId, statusId);
    }

    @GetMapping("/status")
    public Map<String, Boolean> getUserVideogameStatuses(
            @RequestParam Long userId,
            @RequestParam Long videogameId
    ) {
        boolean isFavorite = userVideogameRepository.existsByUserIdAndVideogameIdAndStatusId(userId, videogameId, 1L);
        boolean isPlayed = userVideogameRepository.existsByUserIdAndVideogameIdAndStatusId(userId, videogameId, 2L);
        boolean isPlayLater = userVideogameRepository.existsByUserIdAndVideogameIdAndStatusId(userId, videogameId, 3L);

        Map<String, Boolean> statuses = new HashMap<>();
        statuses.put("favorite", isFavorite);
        statuses.put("played", isPlayed);
        statuses.put("playLater", isPlayLater);

        return statuses;
    }

    // Obtener los videojuegos favoritos de un usuario
    @GetMapping("/favorites")
    public List<Videogame> getFavoriteVideogames(@RequestParam Long userId) {
        return userVideogameService.getUserVideogamesByStatus(userId, "favorito")
                .stream()
                .map(UserVideogame::getVideogame) // Extract the Videogame object
                .toList();
    }

    // Obtener los videojuegos jugados de un usuario
    @GetMapping("/played")
    public List<Videogame> getPlayedVideogames(@RequestParam Long userId) {
        return userVideogameService.getUserVideogamesByStatus(userId, "jugado")
                .stream()
                .map(UserVideogame::getVideogame) // Extract the Videogame object
                .toList();
    }

    // Obtener los videojuegos por jugar de un usuario
    @GetMapping("/to-play")
    public List<Videogame> getToPlayVideogames(@RequestParam Long userId) {
        return userVideogameService.getUserVideogamesByStatus(userId, "por jugar")
                .stream()
                .map(UserVideogame::getVideogame) // Extract the Videogame object
                .toList();
    }
}
