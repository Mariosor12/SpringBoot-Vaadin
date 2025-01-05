package com.app.games.model;  

public class ListItem {  
    private String id;  
    private int gameId;  
    private int userId;  
    private String fecha;  
    private int puntuacion;  
    private String comentario;  

    // Getters y Setters  
    public String getId() {  
        return id;  
    }  

    public void setId(String id) {  
        this.id = id;  
    }  

    public int getGameId() {  
        return gameId;  
    }  

    public void setGameId(int gameId) {  
        this.gameId = gameId;  
    }  

    public int getUserId() {  
        return userId;  
    }  

    public void setUserId(int userId) {  
        this.userId = userId;  
    }  

    public String getFecha() {  
        return fecha;  
    }  

    public void setFecha(String fecha) {  
        this.fecha = fecha;  
    }  

    public int getPuntuacion() {  
        return puntuacion;  
    }  

    public void setPuntuacion(int puntuacion) {  
        this.puntuacion = puntuacion;  
    }  

    public String getComentario() {  
        return comentario;  
    }  

    public void setComentario(String comentario) {  
        this.comentario = comentario;  
    }  
}