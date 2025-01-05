package com.app.games.views;  

import com.vaadin.flow.component.orderedlayout.VerticalLayout;  
import com.vaadin.flow.component.UI;  
import com.vaadin.flow.component.html.Div;  
import com.vaadin.flow.component.html.H2;  
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;  
import com.vaadin.flow.router.Route;  
import com.vaadin.flow.router.RouteParameters;  
import com.vaadin.flow.router.RouterLink;  
import com.app.games.model.Game;  
import com.app.games.service.GameService;   
import com.vaadin.flow.server.VaadinSession;   

import java.util.List;  

@Route("")  
public class HomeView extends VerticalLayout {  

    private String userId;  
    private HeaderComponent header;  

    public HomeView(GameService gameService) {   
        this.userId = (String) VaadinSession.getCurrent().getAttribute("userId");  
        
        // Configuraciones del layout  
        setPadding(false); // Eliminar el padding  
        setMargin(false); // Eliminar el margin  
        setWidthFull(); // Asegurar que el VerticalLayout ocupe todo el ancho  

        // Crear y agregar el encabezado  
        header = new HeaderComponent();  
        header.setWidthFull(); // Asegúrate de que el encabezado ocupe todo el ancho  
        add(header);  
        
        if (userId != null) {  
            header.setTitleClickable(true);  
            header.setProfileLinkVisible(true);
            
            VerticalLayout layout = new VerticalLayout();  
            layout.setAlignItems(FlexComponent.Alignment.CENTER);
            
            // Título de la vista  
            H2 title = new H2("Games List");   
            layout.add(title);  

            add(layout); 

            // Obtener todos los juegos  
            List<Game> games = gameService.getAllGames();  

            // Crear un layout flexible para acomodar las tarjetas  
            FlexLayout flexLayout = new FlexLayout();  
            flexLayout.setFlexDirection(FlexLayout.FlexDirection.ROW);  
            flexLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP); 
            flexLayout.setJustifyContentMode(FlexLayout.JustifyContentMode.CENTER); // Centrar horizontalmente   
            
            // Crear tarjetas para cada juego  
            for (Game game : games) {  
                Div card = createGameCard(game);  
                flexLayout.add(card);  
            }  

            // Agregar layout flexible a la vista  
            add(flexLayout);  
        } else {  
            header.setProfileLinkVisible(false); // Oculta el enlace "My Profile"  
            UI currentUI = UI.getCurrent();  
            if (currentUI != null) {  
                currentUI.getPage().setLocation("login");   
            } else {  
                System.out.println("UI no disponible, no se puede redirigir.");  
            }  
        }  
    }  

    private Div createGameCard(Game game) {  
        // Crear una tarjeta para el juego  
        Div card = new Div();  
        card.getStyle()  
                .set("border", "1px solid #ccc")  
                .set("border-radius", "5px")  
                .set("padding", "10px")  
                .set("margin", "10px")  
                .set("background-color", "#f9f9f9")  
                .set("width", "240px");  

        // Crear un RouterLink al juego  
        RouterLink link = new RouterLink(game.getNombre(), GameDetailView.class,   
        new RouteParameters("gameId", game.getId()));   
        card.add(link);  
        card.add(new Paragraph("Game Genre: " + game.getTipo()));  
        card.add(new Paragraph("Developer: " + game.getEmpresaDesarrolladora()));  
        card.add(new Paragraph("Platforms: " + String.join(", ", game.getPlataformas())));  
        card.add(new Paragraph("Local CO-OP: " + game.getCantidadJugadoresLocal()));  
        card.add(new Paragraph("Release Date: " + game.getFechaSalida()));  

        return card;  
    }    
}