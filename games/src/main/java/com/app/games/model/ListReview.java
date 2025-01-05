package com.app.games.model;

public class ListReview {  

    private String id;
    private Integer gameId;
    private Integer userId;  
    private String fecha; 
    private Integer puntuacion;  
    private String comentario;  
 
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

    @Override  
    public String toString() {  
        return "ListReview{" +  
                "id='" + id + '\'' +  
                ", gameId=" + gameId +  
                ", userId=" + userId +  
                ", fecha='" + fecha + '\'' +  
                ", puntuacion=" + puntuacion +  
                ", comentario='" + comentario + '\'' +  
                '}';  
    }  
}  