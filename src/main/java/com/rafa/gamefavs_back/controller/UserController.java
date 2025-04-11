package com.rafa.gamefavs_back.controller;

import com.rafa.gamefavs_back.model.JwUtil;
import com.rafa.gamefavs_back.model.User;
import com.rafa.gamefavs_back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:5173"})@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwUtil jwUtil;



    // Este metodo recibe un username y un password y devuelve un usuario si las credenciales son correctas
    @PostMapping("/login")
    public Optional<User> loginUser(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password);
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // Verificar si el username ya existe
            if (userService.findByUsername(user.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("El nombre de usuario ya existe");
            }

            // Verificar si el email ya existe
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("El correo electrónico ya existe");
            }

            // Guardar el usuario si no hay conflictos
            User newUser = userService.registerUser(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            // Manejar cualquier excepción inesperada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    // Este metodo verifica si el token es valido
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> verifyToken(@RequestParam String token) {
        try {
            // Extract the username from the token
            String username = jwUtil.extractUsername(token);

            // Validate the token
            boolean isValid = jwUtil.validateToken(token, username);

            // Get the user ID
            Optional<User> user = userService.findByUsername(username);
            Long userId = user.map(User::getId).orElse(null);

            // Create a response map
            Map<String, Object> response = new HashMap<>();
            response.put("isValid", isValid);
            response.put("userId", userId);

            // Return the response with token validity and user ID
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle errors (e.g., invalid or expired token)
            Map<String, Object> response = new HashMap<>();
            response.put("isValid", false);
            response.put("userId", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

}