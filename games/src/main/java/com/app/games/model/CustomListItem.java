package com.app.games.model;  

public class CustomListItem {  
    private String id;         // ID de la reseña  
    private Integer gameId;       // ID del juego  
    private Integer userId;       // ID del usuario  
    private String fecha;      // Fecha de la reseña  
    private Integer puntuacion;    // Puntuación de la reseña  
    private String comentario;  // Comentario de la reseña  

    // Constructor  
    public CustomListItem() {}  

    // Getters y Setters  
    public String getId() {  
        return id;  
    }  

    public void setId(String id) {  
        this.id = id;  
    }  

    public Integer getGameId() {  
        return gameId;  
    }  

    public void setGameId(Integer gameId) {  
        this.gameId = gameId;  
    }  

    public Integer getUserId() {  
        return userId;  
    }  

    public void setUserId(Integer userId) {  
        this.userId = userId;  
    }  

    public String getFecha() {  
        return fecha;  
    }  

    public void setFecha(String fecha) {  
        this.fecha = fecha;  
    }  

    public Integer getPuntuacion() {  
        return puntuacion;  
    }  

    public void setPuntuacion(Integer puntuacion) {  
        this.puntuacion = puntuacion;  
    }  

    public String getComentario() {  
        return comentario;  
    }  

    public void setComentario(String comentario) {  
        this.comentario = comentario;  
    }  
}