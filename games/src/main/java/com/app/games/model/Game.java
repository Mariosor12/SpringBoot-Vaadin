package com.app.games.model;  

import java.util.List;  

public class Game {  
    private String id; // aunque no lo mencionaste antes, parece que está en el JSON  
    private String nombre;  
    private String tipo;  
    private String empresaDesarrolladora;  
    private List<String> plataformas; // Asegúrate de que esto sea List<String>  
    private String cantidadJugadoresLocal; // Cambia a String  
    private String fechaSalida;  
    private boolean coOpLocal;  
    private boolean coOpOnline;  

    // Getters y Setters  
    public String getId() {  
        return id;  
    }  

    public void setId(String id) {  
        this.id = id;  
    }  

    public String getNombre() {  
        return nombre;  
    }  

    public void setNombre(String nombre) {  
        this.nombre = nombre;  
    }  

    public String getTipo() {  
        return tipo;  
    }  

    public void setTipo(String tipo) {  
        this.tipo = tipo;  
    }  

    public String getEmpresaDesarrolladora() {  
        return empresaDesarrolladora;  
    }  

    public void setEmpresaDesarrolladora(String empresaDesarrolladora) {  
        this.empresaDesarrolladora = empresaDesarrolladora;  
    }  

    public List<String> getPlataformas() {  
        return plataformas;  
    }  

    public void setPlataformas(List<String> plataformas) {  
        this.plataformas = plataformas;  
    }  

    public String getCantidadJugadoresLocal() {  
        return cantidadJugadoresLocal;  
    }  

    public void setCantidadJugadoresLocal(String cantidadJugadoresLocal) {  
        this.cantidadJugadoresLocal = cantidadJugadoresLocal;  
    }  

    public String getFechaSalida() {  
        return fechaSalida;  
    }  

    public void setFechaSalida(String fechaSalida) {  
        this.fechaSalida = fechaSalida;  
    }  

    public boolean isCoOpLocal() {  
        return coOpLocal;  
    }  

    public void setCoOpLocal(boolean coOpLocal) {  
        this.coOpLocal = coOpLocal;  
    }  

    public boolean isCoOpOnline() {  
        return coOpOnline;  
    }  

    public void setCoOpOnline(boolean coOpOnline) {  
        this.coOpOnline = coOpOnline;  
    }  

    @Override  
    public String toString() {  
        return "Game{" +  
                "id='" + id + '\'' +  
                ", nombre='" + nombre + '\'' +  
                ", tipo='" + tipo + '\'' +  
                ", empresaDesarrolladora='" + empresaDesarrolladora + '\'' +  
                ", plataformas=" + plataformas +  
                ", cantidadJugadoresLocal='" + cantidadJugadoresLocal + '\'' +  
                ", fechaSalida='" + fechaSalida + '\'' +  
                ", coOpLocal=" + coOpLocal +  
                ", coOpOnline=" + coOpOnline +  
                '}';  
    }  
}