package com.app.games.service;

import com.app.games.model.LoginRequest;  
import com.app.games.model.RegisterRequest;  
import com.app.games.model.User;
import com.vaadin.flow.server.VaadinSession;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service; 
import org.springframework.web.client.RestTemplate;

import java.util.List;  
import java.util.ArrayList;  
import java.util.Arrays;    

@Service  
public class UserService {  

    private final String jsonServerUrl = "https://games-app-az0f.onrender.com/users";  

    @Autowired  
    private RestTemplate restTemplate;  

    private List<User> usuarios = new ArrayList<>();
    
    @Autowired  
    public UserService(RestTemplate restTemplate) { 
        this.restTemplate = restTemplate; 
        loadUsersFromJsonServer();  
    }  


    private void loadUsersFromJsonServer() {  
        User[] usuariosArray = restTemplate.getForObject(jsonServerUrl, User[].class);  
        if (usuariosArray != null) {  
            usuarios = Arrays.asList(usuariosArray);  
        }  
    } 

    public User findUserById(String id) {  
        User user = restTemplate.getForObject(jsonServerUrl + "/" + id, User.class);  
        return user;  
    }  
    

    public User authenticate(LoginRequest loginRequest) { 
        return usuarios.stream()  
            .filter(usuario -> usuario.getNombreUsuario().equals(loginRequest.getNombreUsuario()) &&  
                               usuario.getContrasena().equals(loginRequest.getContrasena()))  
            .findFirst()  
            .orElse(null); 
    }  

    public User register(RegisterRequest registerRequest) {  
        User newUser = new User();  
        newUser.setId(String.valueOf(usuarios.size() + 1)); 
        newUser.setNombre(registerRequest.getNombre());  
        newUser.setApellido(registerRequest.getApellido());  
        newUser.setCorreo(registerRequest.getCorreo());  
        newUser.setNombreUsuario(registerRequest.getNombreUsuario());  
        newUser.setContrasena(registerRequest.getContrasena()); 
        User createdUser = restTemplate.postForObject(jsonServerUrl, newUser, User.class);
        loadUsersFromJsonServer();
        return createdUser;   
    }  

    public void logout() {  
        VaadinSession.getCurrent().setAttribute("userId", null);  
        VaadinSession.getCurrent().close();  
    }  

    public List<User> getUsuarios() {  
        return usuarios;  
    } 

    public User updateUser(String userId, User updatedUser) {  
        try {  
            // Realiza la solicitud PUT para actualizar el usuario  
            restTemplate.put(jsonServerUrl + "/" + userId, updatedUser);  
            // Opcional: Actualiza la lista local de usuarios  
            loadUsersFromJsonServer();  
            return updatedUser; // O puedes retornar el usuario actualizado desde el servidor  
        } catch (Exception e) {  
            // Manejo de excepciones  
            throw new RuntimeException("Error al actualizar el usuario con id: " + userId, e);  
        }  
    } 
    
    public void deleteUser(String id) {  
    try {  
        restTemplate.delete(jsonServerUrl + "/" + id);
        usuarios.removeIf(usuario -> usuario.getId().equals(id)); 
    } catch (Exception e) {  
        // Manejo de excepciones, podría lanzar una RuntimeException o registrar el error  
        throw new RuntimeException("Error al eliminar el usuario con id: " + id, e);  
    }  
}  
}  