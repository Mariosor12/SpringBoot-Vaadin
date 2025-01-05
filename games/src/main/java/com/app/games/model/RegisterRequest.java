package com.app.games.model;

public class RegisterRequest {  
    private String nombre;  
    private String apellido;  
    private String correo;  
    private String nombreUsuario;  
    private String contrasena;  

    public String getNombre() {  
        return nombre;  
    }  

    public void setNombre(String nombre) {  
        this.nombre = nombre;  
    }  

    public String getApellido() {  
        return apellido;  
    }  

    public void setApellido(String apellido) {  
        this.apellido = apellido;  
    }  

    public String getCorreo() {  
        return correo;  
    }  

    public void setCorreo(String correo) {  
        this.correo = correo;  
    }  

    public String getNombreUsuario() {  
        return nombreUsuario;  
    }  

    public void setNombreUsuario(String nombreUsuario) {  
        this.nombreUsuario = nombreUsuario;  
    }  

    public String getContrasena() {  
        return contrasena;  
    }  

    public void setContrasena(String contrasena) {  
        this.contrasena = contrasena;  
    }  
}  