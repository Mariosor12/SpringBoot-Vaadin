package com.app.games.service;  

import com.app.games.model.CustomListItem;
import com.app.games.model.Database;  
import com.app.games.model.ListReview;  
import com.app.games.model.User;  
import com.fasterxml.jackson.core.type.TypeReference;  
import com.fasterxml.jackson.databind.ObjectMapper;  
import org.springframework.stereotype.Service;  

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;  
import java.util.List;  
import java.util.Optional;  

@Service  
public class ListService {  

    private final String dbFilePath = "db.json"; // Ruta a tu archivo db.json  
    private List<ListReview> reviews = new ArrayList<>();  
    private List<CustomListItem> lists = new ArrayList<>();
    private List<User> users = new ArrayList<>();  

    public ListService() {  
        loadDataFromJsonFile();  
    }  

    private void loadDataFromJsonFile() {  
        try {  
            ObjectMapper objectMapper = new ObjectMapper();  
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dbFilePath); // Carga desde classpath  
            if (inputStream == null) {  
                throw new FileNotFoundException("No se encontró el archivo db.json en el classpath.");  
            }  
            Database db = objectMapper.readValue(inputStream, Database.class);  
            reviews = db.getReviews();
            lists = db.getList(); 
            users = db.getUsers();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 

    public List<CustomListItem> getAllReviews() {  
        return lists;  
    }  

    public Optional<ListReview> getReviewById(String id) {  
        return reviews.stream().filter(review -> review.getId().equals(id)).findFirst();  
    }  

    public Optional<ListReview> getReviewByGameId(String id) {  
        return reviews.stream().filter(review -> review.getGameId().toString().equals(id)).findFirst();  
    }  

    public List<ListReview> findReviewByGameId(String gameId) {  
        List<ListReview> gameReviews = new ArrayList<>();  
        for (ListReview review : reviews) {  
            if (review.getGameId().toString().equals(gameId)) {  
                gameReviews.add(review);  
            }  
        }  
        return gameReviews;  
    }   

    public List<User> findReviewByUserId(String userId) {  
        List<User> reviewsWithUsers = new ArrayList<>();  
        for (CustomListItem review : lists) {  
            if (review.getUserId().toString().equals(userId)) {  
                // Buscar usuario por ID  
                Optional<User> user = users.stream().filter(u -> u.getId().equals(userId)).findFirst();  
                user.ifPresent(reviewsWithUsers::add);  
            }  
        }   
        return reviewsWithUsers;  
    } 
    
    public List<CustomListItem> findReviewsByUserId(String userId) {  
        List<CustomListItem> userReviews = new ArrayList<>();  
        for (CustomListItem review : lists) {  
            if (review.getUserId().toString().equals(userId)) {  
                userReviews.add(review);  
            }  
        }  
        return userReviews;  
    } 

    public CustomListItem createReview(CustomListItem review) {  
        review.setId(String.valueOf(lists.size() + 1)); // Asigna un nuevo ID  
        lists.add(review); // Añadir la nueva reseña a la lista  
        
        // Guardar las reseñas en el archivo JSON  
        saveDataToJsonFile();  
        
        return review;  
    }  

    public ListReview updateReview(String id, ListReview reviewDetails) {  
        Optional<ListReview> existingReviewOpt = getReviewById(id);  
        if (existingReviewOpt.isPresent()) {  
            ListReview existingReview = existingReviewOpt.get();  
            // Actualizar los campos de la reseña existente  
            existingReview.setGameId(reviewDetails.getGameId());  
            existingReview.setUserId(reviewDetails.getUserId());  
            existingReview.setFecha(reviewDetails.getFecha());  
            existingReview.setPuntuacion(reviewDetails.getPuntuacion());  
            existingReview.setComentario(reviewDetails.getComentario());  

            // Guardar las reseñas actualizadas en el archivo JSON  
            saveDataToJsonFile();  
            return existingReview; // Retornar la reseña actualizada  
        }  
        return null;  
    }  

    public void deleteReview(String id) {  
        reviews.removeIf(review -> review.getId().equals(id));  
        // Guardar cambios en el archivo JSON  
        saveDataToJsonFile();  
    }  

    private void saveDataToJsonFile() {  
        try {  
            ObjectMapper objectMapper = new ObjectMapper();  
            Database db = new Database();  
            db.setList(lists); // Asegúrate de que la clase Database tenga un método setReviews  
            db.setUsers(users); // Asignar la lista de usuarios  
            objectMapper.writeValue(new File(dbFilePath), db);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 
    
    public void deleteReviewsByGameId(String gameId, String userId) {  
        lists.removeIf(review -> review.getGameId().toString().equals(gameId) && review.getUserId().toString().equals(userId));  
        saveDataToJsonFile();  
    }  
}