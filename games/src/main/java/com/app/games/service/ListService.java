package com.app.games.service;  

import com.app.games.model.ListReview;
import com.app.games.model.User;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.web.client.RestTemplate;  
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.ArrayList;
import java.util.Arrays;  
import java.util.List;  
import java.util.Optional;  

@Service  
public class ListService {  

    private final String BASE_URL = "https://games-app-az0f.onrender.com/list";
    private final String USERS_BASE_URL = "https://games-app-az0f.onrender.com/users";
    private final RestTemplate restTemplate;  

    @Autowired  
    public ListService(RestTemplateBuilder restTemplateBuilder) {  
        this.restTemplate = restTemplateBuilder.build();  
    }  

    public List<ListReview> getAllReviews() {  
        ListReview[] reviews = restTemplate.getForObject(BASE_URL, ListReview[].class);  
        return Arrays.asList(reviews);  
    }  

    public Optional<ListReview> getReviewById(String id) {  
        ListReview review = restTemplate.getForObject(BASE_URL + "/" + id, ListReview.class);  
        return Optional.ofNullable(review);  
    }  

    public Optional<ListReview> getReviewByGameId(String id) {  
        ListReview review = restTemplate.getForObject(BASE_URL + "?gameId=" + id, ListReview.class);  
        return Optional.ofNullable(review);  
    }  

    public List<ListReview> findReviewByGameId(String gameId) {  
        ListReview[] reviews = restTemplate.getForObject(BASE_URL + "?gameId=" + gameId, ListReview[].class);
        return Arrays.asList(reviews);  
    } 

    public List<User> findReviewByUserId(String gameId) {  
        ListReview[] reviews = restTemplate.getForObject(BASE_URL + "?gameId=" + gameId, ListReview[].class);
        List<User> reviewsWithUsers = new ArrayList<>();
        for (ListReview review : reviews) {
            User user = restTemplate.getForObject(USERS_BASE_URL + "/" + review.getUserId(), User.class);
            reviewsWithUsers.add(user);
        }   
        return reviewsWithUsers;  
    } 

    public ListReview createReview(ListReview review) {  
        return restTemplate.postForObject(BASE_URL, review, ListReview.class);  
    }  

    public ListReview updateReview(String id, ListReview reviewDetails) {  
        // Obtener la reseña existente  
        ListReview existingReview = restTemplate.getForObject(BASE_URL + "/" + id, ListReview.class);  

        if (existingReview != null) {  
            // Actualizar los campos de la reseña existente  
            existingReview.setGameId(reviewDetails.getGameId());  
            existingReview.setUserId(reviewDetails.getUserId());  
            existingReview.setFecha(reviewDetails.getFecha());  
            existingReview.setPuntuacion(reviewDetails.getPuntuacion());  
            existingReview.setComentario(reviewDetails.getComentario());  

            // Enviar la solicitud PUT para actualizar la reseña en el servidor  
            restTemplate.put(BASE_URL + "/" + id, existingReview);  
        }  

        return existingReview; // Retornar la reseña actualizada  
    }  

    public void deleteReview(String id) {  
        restTemplate.delete(BASE_URL + "/" + id);  
    }  
}