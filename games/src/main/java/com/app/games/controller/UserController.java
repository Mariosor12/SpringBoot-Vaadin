package com.app.games.controller;

import com.app.games.model.LoginRequest;  
import com.app.games.model.RegisterRequest;  
import com.app.games.model.User;  
import com.app.games.service.UserService;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.*;  

@RestController  
@RequestMapping("/api/users")  
public class UserController {  

    @Autowired  
    private UserService userService;  

    @PostMapping("/login")  
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {  
        User authenticatedUser = userService.authenticate(loginRequest);  
        if (authenticatedUser != null) {  
            return ResponseEntity.ok("Inicio de sesión exitoso!");  
        } else {  
            return ResponseEntity.status(401).body("Credenciales inválidas.");  
        }  
    }

    @PostMapping("/register")  
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {  
        User newUser = userService.register(registerRequest);  
        return ResponseEntity.ok(newUser);  
    }  

    @PostMapping("/logout")  
    public ResponseEntity<String> logout() {  
        userService.logout();  
        return ResponseEntity.ok("Cierre de sesión exitoso!");  
    }  
}  