package com.rafa.gamefavs_back.config;

import com.rafa.gamefavs_back.model.Status;
import com.rafa.gamefavs_back.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Autowired
    private StatusRepository statusRepository;

    @PostConstruct
    public void init() {
        List<String> statusNames = Arrays.asList("favorito", "jugado", "por jugar");
        for (String statusName : statusNames) {
            if (!statusRepository.findByName(statusName).isPresent()) {
                Status status = new Status();
                status.setName(statusName);
                statusRepository.save(status);
            }
        }
    }
}
