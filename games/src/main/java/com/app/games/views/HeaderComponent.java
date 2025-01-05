package com.app.games.views;  

import com.vaadin.flow.component.html.Anchor;  
import com.vaadin.flow.component.html.H2;  
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;   
import com.vaadin.flow.router.RouterLink;  

public class HeaderComponent extends HorizontalLayout {  

    private Anchor profileLink;  
    private RouterLink titleLink; // Hacer titleLink un atributo de clase  
    private H2 title; // Hacer title un atributo de clase  

    public HeaderComponent() {  
        // Configuraciones de estilo del header  
        addClassNames("header-content");  
        setWidthFull();  
        setPadding(false);  
        setMargin(false);  
        
        // Inicializar RouterLink para el título del encabezado  
        titleLink = new RouterLink("Games Library", HomeView.class);  
        titleLink.getStyle().set("text-decoration", "none");  
        titleLink.getStyle().set("color", "black");  
        titleLink.setTabIndex(-1);  

        // Inicializar el título del encabezado  
        title = new H2();  
        title.add(titleLink);  
        title.getStyle().set("margin", "0");  

        // Enlace a "My Profile"  
        profileLink = new Anchor("/my-profile", "My Profile");  
        profileLink.addClassName("my-profile-link");  
        profileLink.getStyle().set("color", "black");  

        // Agregar elementos al encabezado  
        add(title, profileLink);  
        
        // Estilo específico del componente Header  
        getStyle()  
            .set("background-color", "#87CEEB")  
            .set("color", "white")  
            .set("text-align", "center")  
            .set("padding", "20px")  
            .set("flex-shrink", "0")  
            .set("display", "flex")  
            .set("justify-content", "space-between")  
            .set("align-items", "center")  
            .set("width", "100%");  
    }  

    public void setProfileLinkVisible(boolean visible) {  
        profileLink.setVisible(visible);  
    }  

    public void setTitleClickable(boolean clickable) {  
        if (clickable) {  
            titleLink.setVisible(true);  
            title.removeAll();  
            title.add(titleLink);  
        } else {  
            titleLink.setVisible(false);  
            title.removeAll();  
            title.add("Games Library"); // Solo el texto, sin enlace  
        }  
    }  
}