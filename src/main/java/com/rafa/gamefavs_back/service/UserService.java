package com.rafa.gamefavs_back.service;

import com.rafa.gamefavs_back.model.User;
import com.rafa.gamefavs_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.rafa.gamefavs_back.model.JwUtil;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwUtil jwUtil;

    // Metodo para logearse un usuario
    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            // Verificar la contraseña cifrada
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                String token = jwUtil.generateToken(username);
                user.get().setToken(token);
                return user;
            }
        }
        return Optional.empty(); // Credenciales inválidas
    }

    // Metodo para registrar un nuevo usuario
    public User registerUser(User user) {
        // Cifrar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
