package com.app.games.service;  

import com.app.games.model.Database;
import com.app.games.model.LoginRequest;  
import com.app.games.model.RegisterRequest;  
import com.app.games.model.User;  
import com.fasterxml.jackson.core.type.TypeReference;  
import com.fasterxml.jackson.databind.ObjectMapper;  
import com.vaadin.flow.server.VaadinSession;  

import org.springframework.stereotype.Service;  

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;  
import java.util.List;  
import java.util.Optional;  

@Service  
public class UserService {  

    private final String dbFilePath = "db.json"; // Ruta a tu archivo db.json  
    private List<User> usuarios = new ArrayList<>();  

    public UserService() {   
        loadUsersFromJsonFile();  
    }  

    private void loadUsersFromJsonFile() {  
        try {  
            ObjectMapper objectMapper = new ObjectMapper();  
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dbFilePath); // Carga desde classpath  
            if (inputStream == null) {  
                throw new FileNotFoundException("No se encontró el archivo db.json en el classpath.");  
            }  
            Database db = objectMapper.readValue(inputStream, Database.class); 
            usuarios = db.getUsers(); // Asegúrate de que la clase Database esté definida correctamente   
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 

    public User findUserById(String id) { 
        return usuarios.stream()  
                .filter(user -> user.getId().equals(id))  
                .findFirst()  
                .orElse(null);  
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

        // Añadir el nuevo usuario a la lista local  
        usuarios.add(newUser);  
        
        // Opcional: Guardar el nuevo usuario en el archivo JSON  
        saveUsersToJsonFile();  
        
        return newUser;   
    }  

    public void logout() {  
        VaadinSession.getCurrent().setAttribute("userId", null);  
        VaadinSession.getCurrent().close();  
    }  

    public List<User> getUsuarios() {  
        return usuarios;  
    }   

    public User updateUser(String userId, User updatedUser) {  
        Optional<User> existingUserOpt = usuarios.stream()  
                .filter(user -> user.getId().equals(userId))  
                .findFirst();  

        if (existingUserOpt.isPresent()) {  
            User existingUser = existingUserOpt.get();  
            existingUser.setNombre(updatedUser.getNombre());  
            existingUser.setApellido(updatedUser.getApellido());  
            existingUser.setCorreo(updatedUser.getCorreo());  
            existingUser.setNombreUsuario(updatedUser.getNombreUsuario());  
            existingUser.setContrasena(updatedUser.getContrasena()); 

            // Opcional: Guardar los cambios en el archivo JSON  
            saveUsersToJsonFile();  
            return existingUser;   
        } else {  
            throw new RuntimeException("Usuario no encontrado: " + userId);  
        }  
    }   
    
    public void deleteUser(String id) {  
        try {  
            usuarios.removeIf(usuario -> usuario.getId().equals(id));   
            // Opcional: Guardar los cambios en el archivo JSON  
            saveUsersToJsonFile();  
        } catch (Exception e) {  
            throw new RuntimeException("Error al eliminar el usuario con id: " + id, e);  
        }  
    }  

    private void saveUsersToJsonFile() {  
        try {  
            ObjectMapper objectMapper = new ObjectMapper();  
            Database db = new Database();  
            db.setUsers(usuarios); // Asegúrate de que la clase Database tenga un método setUsers  
            objectMapper.writeValue(new File(dbFilePath), db);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}