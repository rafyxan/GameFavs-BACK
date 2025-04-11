// UserVideogame.java
package com.rafa.gamefavs_back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "user_videogames", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "videogame_id", "status_id"}))
@IdClass(UserVideogameId.class)
@JsonIgnoreProperties({"user", "videogame", "status"})
public class UserVideogame implements Serializable {

    @Id
    private int user_id;

    @Id
    private int videogame_id;

    @Id
    private int status_id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "videogame_id", insertable = false, updatable = false)
    private Videogame videogame;

    @ManyToOne
    @JoinColumn(name = "status_id", insertable = false, updatable = false)
    private Status status;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Videogame getVideogame() {
        return videogame;
    }

    public void setVideogame(Videogame videogame) {
        this.videogame = videogame;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}