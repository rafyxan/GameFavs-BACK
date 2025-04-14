package com.rafa.gamefavs_back.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafa.gamefavs_back.model.Videogame;
import com.rafa.gamefavs_back.repository.VideogameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class RawgApiService {
    // URL de la API de RAWG
    private static final String RAWG_API_URL = "https://api.rawg.io/api/games";
    // API key de RAWG
    private static final String API_KEY = "1e48647219f84d02b336ae8e100a2cd5"; // Reemplaza con tu API key

    @Autowired
    VideogameRepository videogameRepository;

    // Método para obtener los 20 juegos mejor valorados
    public List<Videogame> getTopRatedGames() {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(RAWG_API_URL)
                .queryParam("key", API_KEY)
                .queryParam("ordering", "-rating")  // Ordenar por mayor puntuación
                .queryParam("page_size", 20)  // Obtener solo los 20 mejores juegos
                .queryParam("dates", "1900-01-01,2025-03-24"); // Rango amplio de fechas

        String response = restTemplate.getForObject(uriBuilder.toUriString(), String.class);
        List<Videogame> videogames = mapResponseToVideogames(response);
        // videogameRepository.saveAll(videogames);
        return videogames;
    }

    // Método para obtener los detalles de un juego por su ID
    public Videogame getGameDetailsById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(RAWG_API_URL + "/" + id)
                .queryParam("key", API_KEY);

        String response = restTemplate.getForObject(uriBuilder.toUriString(), String.class);
        return mapResponseToVideogame(response);
    }

    // Método para obtener juegos con filtros seleccionados
    public List<Videogame> getGamesWithFilters(String name, String genres, String platforms, String ratingOrder) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(RAWG_API_URL)
                .queryParam("key", API_KEY);

        // Si se proporciona un nombre de juego, lo añadimos al parámetro 'name'
        if (name != null && !name.trim().isEmpty()) {
            uriBuilder.queryParam("search", name);
        }

        // Si se proporciona un género, lo añadimos al parámetro 'genres'
        if (genres != null && !genres.trim().isEmpty()) {
            uriBuilder.queryParam("genres", genres);
        }

        // Si se proporciona una plataforma, la añadimos al parámetro 'platforms'
        if (platforms != null && !platforms.trim().isEmpty()) {
            uriBuilder.queryParam("platforms", platforms);
        }

        // Si se proporciona una opción de orden de puntuación, la añadimos
        if (ratingOrder != null && !ratingOrder.trim().isEmpty()) {
            uriBuilder.queryParam("ordering", ratingOrder);  // Ejemplo: "rating" o "-rating" (ascendente/descendente)
        }

        // Limitar el número de juegos (si lo deseas)
        uriBuilder.queryParam("page_size", 60); // O el número que desees

        // Realizar la solicitud GET
        String response = restTemplate.getForObject(uriBuilder.toUriString(), String.class);

        // Mapear la respuesta a la lista de videojuegos
        List<Videogame> videogames = mapResponseToVideogames(response);

        return videogames;
    }


    // Método para mapear la respuesta JSON a un objeto Videogame
    private Videogame mapResponseToVideogame(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(response);
            Videogame videogame = new Videogame();
            videogame.setId(node.path("id").asLong());
            videogame.setName(node.path("name").asText());
            videogame.setReleased(node.path("released").asText());
            videogame.setBackgroundImage(node.path("background_image").asText());
            videogame.setRating(node.path("rating").floatValue());
            videogame.setPlatforms(mapPlatforms(node.path("platforms")));
            videogame.setGenres(mapGenres(node.path("genres")));
            videogame.setDeveloper(mapDevelopers(node.path("developers")));
            videogame.setDescription(node.path("description").asText(""));
            videogame.setWebsite(node.path("website").asText(""));
            return videogame;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para mapear la respuesta JSON a una lista de objetos Videogame
    private List<Videogame> mapResponseToVideogames(String response) {
        List<Videogame> videogames = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response);
            JsonNode results = root.path("results");
            for (JsonNode node : results) {
                Videogame videogame = new Videogame();
                videogame.setId(node.path("id").asLong());
                videogame.setName(node.path("name").asText());
                videogame.setReleased(node.path("released").asText());
                videogame.setBackgroundImage(node.path("background_image").asText());
                videogame.setRating(node.path("rating").floatValue());
                videogame.setPlatforms(mapPlatforms(node.path("platforms")));
                videogame.setGenres(mapGenres(node.path("genres")));
                videogames.add(videogame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videogames;
    }


    // Método para mapear los nodos JSON a Strings
    private String mapPlatforms(JsonNode platformsNode) {
        StringBuilder platforms = new StringBuilder();
        for (int i = 0; i < platformsNode.size(); i++) {
            if (i > 0) {
                platforms.append(" - ");
            }
            platforms.append(platformsNode.get(i).path("platform").path("name").asText());
        }
        return platforms.toString();
    }

    // Método para mapear los nodos JSON a Strings
    private String mapGenres(JsonNode genresNode) {
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < genresNode.size(); i++) {
            if (i > 0) {
                genres.append(" - ");
            }
            genres.append(genresNode.get(i).path("name").asText());
        }
        return genres.toString();
    }

    // Método para mapear los nodos JSON a Strings
    private String mapDevelopers(JsonNode developersNode) {
        StringBuilder developers = new StringBuilder();
        for (int i = 0; i < developersNode.size(); i++) {
            if (i > 0) {
                developers.append(" - ");
            }
            developers.append(developersNode.get(i).path("name").asText());
        }
        return developers.toString();
    }
}
