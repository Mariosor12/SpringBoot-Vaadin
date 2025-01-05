package com.app.games.model;  

import java.util.List;  

public class Database {  
    private List<User> users;             // Lista para almacenar usuarios  
    private List<Game> games;              // Lista para almacenar juegos  
    private List<ListReview> reviews;      // Lista para almacenar reseñas  
    private List<CustomListItem> list;     // Cambiado de List<ListItem> a List<CustomListItem>  

    // Getter y Setter para la lista de usuarios  
    public List<User> getUsers() {  
        return users;  
    }  

    public void setUsers(List<User> users) {  
        this.users = users;  
    }  

    // Getter y Setter para la lista de juegos  
    public List<Game> getGames() {  
        return games;  
    }  

    public void setGames(List<Game> games) {  
        this.games = games;  
    }  

    // Getter y Setter para la lista de reseñas  
    public List<ListReview> getReviews() {  
        return reviews;  
    }  

    public void setReviews(List<ListReview> reviews) {  
        this.reviews = reviews;  
    }  

    // Getter y Setter para la lista personalizada  
    public List<CustomListItem> getList() {  
        return list;  
    }  

    public void setList(List<CustomListItem> list) {  
        this.list = list;  
    }  
}