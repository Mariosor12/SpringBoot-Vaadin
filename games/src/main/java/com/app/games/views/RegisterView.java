package com.app.games.views;  

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;  
import com.vaadin.flow.component.textfield.PasswordField;  
import com.vaadin.flow.component.textfield.TextField;  
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;  
import com.app.games.model.RegisterRequest;  
import com.app.games.model.User;  
import com.app.games.service.UserService;  

@Route("register")  
@StyleSheet("frontend/styles/styles.css")
public class RegisterView extends VerticalLayout {  
    
    private HeaderComponent header;  

    public RegisterView(UserService userService) {   

        setPadding(false);  
        setMargin(false);  
        setWidthFull();  

        header = new HeaderComponent();  
        header.setTitleClickable(false);  
        header.setProfileLinkVisible(false);  
        add(header);  

        H1 title = new H1("Register");  
        title.getStyle().set("margin-left", "20px");  

        // Crear campos de entrada  
        TextField nombre = new TextField();  
        TextField apellido = new TextField();  
        TextField correo = new TextField();  
        TextField nombreUsuario = new TextField();  
        PasswordField contrasena = new PasswordField();  

        // Crear etiquetas  
        Label nombreLabel = new Label("Name");  
        Label apellidoLabel = new Label("Last Name");  
        Label correoLabel = new Label("Email");  
        Label nombreUsuarioLabel = new Label("Username");  
        Label contrasenaLabel = new Label("Password");  

        // Crear botones  
        Button registerButton = new Button("Register");  
        Button clearButton = new Button("Clear");  
        Button goBackButton = new Button("Go Back");   

        registerButton.addClickListener(event -> {  
            RegisterRequest registerRequest = new RegisterRequest();  
            registerRequest.setNombre(nombre.getValue());  
            registerRequest.setApellido(apellido.getValue());  
            registerRequest.setCorreo(correo.getValue());  
            registerRequest.setNombreUsuario(nombreUsuario.getValue());  
            registerRequest.setContrasena(contrasena.getValue());  
            
            try {  
                User createdUser = userService.register(registerRequest);  
                Notification.show("User registered successfully: " + createdUser.getNombreUsuario());  
                getUI().ifPresent(ui -> ui.navigate("/login"));    
            } catch (Exception e) {  
                Notification.show("Error: " + e.getMessage(), 3000, Notification.Position.MIDDLE);  
            }  
        });  

        clearButton.addClickListener(event -> {  
            nombre.clear();  
            apellido.clear();  
            correo.clear();  
            nombreUsuario.clear();  
            contrasena.clear();  
        });  

        goBackButton.addClickListener(event -> {  
            getUI().ifPresent(ui -> ui.navigate("login"));  
        });  

        // Agrupar los campos y etiquetas en grupos  
        VerticalLayout nombreLayout = new VerticalLayout(nombreLabel, nombre);  
        VerticalLayout apellidoLayout = new VerticalLayout(apellidoLabel, apellido);  
        VerticalLayout correoLayout = new VerticalLayout(correoLabel, correo);  
        VerticalLayout nombreUsuarioLayout = new VerticalLayout(nombreUsuarioLabel, nombreUsuario);  
        VerticalLayout contrasenaLayout = new VerticalLayout(contrasenaLabel, contrasena);  
        
        // Agrupar dos por fila  
        HorizontalLayout inputLayout1 = new HorizontalLayout(nombreLayout, apellidoLayout);  
        HorizontalLayout inputLayout2 = new HorizontalLayout(correoLayout, nombreUsuarioLayout);  
        HorizontalLayout inputLayout3 = new HorizontalLayout(contrasenaLayout);  
        
        // Aplicar la clase CSS a los layouts de entradas  
        inputLayout1.addClassName("input-group");   
        inputLayout2.addClassName("input-group");   
        inputLayout3.addClassName("input-group");  

        // Agrupar botones en dos columnas  
        HorizontalLayout buttonsLayout = new HorizontalLayout(registerButton, clearButton);  
        buttonsLayout.addClassName("button-group"); // Añadir clase CSS a los botones  

        HorizontalLayout singleButtonLayout = new HorizontalLayout(goBackButton);  
        
        // Agrupar todo en un layout vertical  
        VerticalLayout finalButtonsLayout = new VerticalLayout(buttonsLayout, singleButtonLayout);  

        add(title, inputLayout1, inputLayout2, inputLayout3, finalButtonsLayout);  
    }   
}