package com.app.games.views;  

import com.vaadin.flow.component.html.Anchor; // Importa Anchor  
import com.vaadin.flow.component.html.Paragraph; // Importa Paragraph  
import com.vaadin.flow.component.login.LoginForm;  
import com.vaadin.flow.component.notification.Notification;  
import com.vaadin.flow.component.orderedlayout.VerticalLayout;  
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;   
import com.vaadin.flow.router.Route;  
import com.vaadin.flow.server.VaadinSession;  
import com.app.games.model.LoginRequest;  
import com.app.games.model.User;  

import org.springframework.beans.factory.annotation.Autowired;  
import com.app.games.service.UserService;  

@Route("login")  
public class LoginView extends VerticalLayout {  

    private HeaderComponent header;   

    @Autowired  
    public LoginView(UserService userService) {   

        setPadding(false);   
        setMargin(false);   
        setWidthFull();  

        header = new HeaderComponent();  
        header.setTitleClickable(false);  
        header.setProfileLinkVisible(false);  
        add(header);  
        setAlignItems(Alignment.CENTER);   
        setJustifyContentMode(JustifyContentMode.CENTER);  

        LoginForm loginForm = new LoginForm();  
        add(loginForm);  
        loginForm.getElement().setAttribute("no-autofocus", "");  

        loginForm.setForgotPasswordButtonVisible(false);   

        loginForm.addLoginListener(e -> {  
            LoginRequest loginRequest = new LoginRequest();  
            loginRequest.setNombreUsuario(e.getUsername());  
            loginRequest.setContrasena(e.getPassword());   
            
            User authenticatedUser = userService.authenticate(loginRequest);   

            if (authenticatedUser != null) {  
                VaadinSession.getCurrent().setAttribute("userId", authenticatedUser.getId());  
                Notification.show("¡Welcome!", 3000, Notification.Position.MIDDLE);  
                getUI().ifPresent(ui -> ui.navigate(""));  
            } else {  
                loginForm.setError(true);  
                Notification.show("Incorrect User or Password", 3000, Notification.Position.MIDDLE);  
            }  
        });   

        // Crea un layout horizontal para el enlace  
        HorizontalLayout linkLayout = new HorizontalLayout();  
        
        // Crea el enlace de registro  
        Anchor registerLink = new Anchor("register", "Register");   
        registerLink.getElement().getStyle().set("text-decoration", "none"); // Estilo para quitar el subrayado  
        registerLink.getElement().getStyle().set("color", "blue"); // Cambia el color como desees  

        linkLayout.add(registerLink); // Agregar el enlace al layout  

        // Agregar el párrafo con los datos de la demostración  
        Paragraph demoData = new Paragraph("Put this data for the demo: anagarcia y abcdef");  
        add(demoData); // Agregar el párrafo a la vista  

        // Agregar el layout del enlace al formulario  
        add(linkLayout);  
    }  
}