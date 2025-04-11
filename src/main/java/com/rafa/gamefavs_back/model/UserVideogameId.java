package com.rafa.gamefavs_back.model;

import java.io.Serializable;
import java.util.Objects;

public class UserVideogameId implements Serializable {

    private static final long serialVersionUID = 1L;

    private int user_id;
    private int videogame_id;
    private int status_id;

    // Default constructor
    public UserVideogameId() {}

    // Constructor with arguments
    public UserVideogameId(int user_id, int videogame_id, int status_id) {
        this.user_id = user_id;
        this.videogame_id = videogame_id;
        this.status_id = status_id;
    }

    // Getters and Setters
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVideogame_id() {
        return videogame_id;
    }

    public void setVideogame_id(int videogame_id) {
        this.videogame_id = videogame_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVideogameId that = (UserVideogameId) o;
        return user_id == that.user_id &&
                videogame_id == that.videogame_id &&
                status_id == that.status_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, videogame_id, status_id);
    }
}