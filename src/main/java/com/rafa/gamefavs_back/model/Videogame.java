package com.rafa.gamefavs_back.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "videogames")
@JsonIgnoreProperties({"userVideogames"}) // Ignora la relación con UserVideogame
public class Videogame {
    @Id
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    private String released;
    @Column(length = 255)
    private String backgroundImage;

    private float rating;

    @Column(length = 255)
    private String platforms;

    @Column(length = 255)
    private String genres;

    @Column(length = 255)
    private String developer;

    @Column(nullable = false) // Increased length
    private String description;

    @Column(length = 255)
    private String website;
    @OneToMany(mappedBy = "videogame", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserVideogame> userVideogames;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Set<UserVideogame> getUserVideogames() {
        return userVideogames;
    }

    public void setUserVideogames(Set<UserVideogame> userVideogames) {
        this.userVideogames = userVideogames;
    }
}
