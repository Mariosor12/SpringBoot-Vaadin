package com.app.games.views;  

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;  
import com.vaadin.flow.router.Route;  
import com.vaadin.flow.router.BeforeEnterEvent;  
import com.vaadin.flow.router.BeforeEnterObserver;

import java.util.List;

import com.app.games.model.Game;  
import com.app.games.service.GameService;  
import com.app.games.model.ListReview;
import com.app.games.model.User;
import com.app.games.service.ListService; 

@Route("game-detail/:gameId")  
public class GameDetailView extends VerticalLayout implements BeforeEnterObserver {  

    private final GameService gameService; 
    private final ListService listService; 
    private HeaderComponent header;

    public GameDetailView(GameService gameService, ListService listService) {  
        this.gameService = gameService;
        this.listService = listService;  

        setPadding(false); // Eliminar el padding  
        setMargin(false); // Eliminar el margin  
        setWidthFull();

        header = new HeaderComponent();  
        
        // Solicitando que el título no sea clickeable y ocultando el perfil  
        header.setTitleClickable(true);  
        header.setProfileLinkVisible(true);  

        // Agregar el header a la vista  
        add(header);
    }  

        @Override  
    public void beforeEnter(BeforeEnterEvent event) {  
        String gameId = event.getRouteParameters().get("gameId").orElse(null);  
        
        if (gameId != null) {  
            Game game = gameService.findGameById(gameId);  
            List<ListReview> reviews = listService.findReviewByGameId(gameId);   
            List<User> users = listService.findReviewByUserId(gameId);  
            
            if (game != null) {  
                H1 gameTitle = new H1(game.getNombre());  
                gameTitle.getStyle().set("margin-left", "20px");  
                add(gameTitle);  
                
                add(createStyledParagraph("Genre: " + game.getTipo()));  
                add(createStyledParagraph("Developer: " + game.getEmpresaDesarrolladora()));  
                add(createStyledParagraph("Platforms: " + String.join(", ", game.getPlataformas())));  
                add(createStyledParagraph("Local Players: " + game.getCantidadJugadoresLocal()));  
                add(createStyledParagraph("Release Date: " + game.getFechaSalida()));  
                
                H4 commentsHeader = new H4("Users Comentary:");  
                commentsHeader.getStyle().set("margin-left", "20px");  
                add(commentsHeader);  

                VerticalLayout commentsLayout = new VerticalLayout();  

                if (users != null && reviews != null && !reviews.isEmpty() && !users.isEmpty()) {   
                    for (int i = 0; i < reviews.size(); i++) {  
                        User user = users.get(i);  
                        ListReview review = reviews.get(i);  
                        
                        HorizontalLayout commentLayout = new HorizontalLayout();   
                        commentLayout.setAlignItems(Alignment.CENTER); // Alinear los elementos verticalmente al centro  

                        Paragraph username = new Paragraph(user.getNombreUsuario() + ":");  
                        username.getStyle().set("margin", "0");  
                        username.getStyle().set("font-weight", "bold");  
                        
                        // Agregar usuario y comentario al layout horizontal  
                        commentLayout.add(username);   
                        
                        Span comment = new Span(review.getComentario()); // Cambiar Paragraph a Span  
                        comment.getStyle().set("margin-left", "10px"); // Espacio entre usuario y comentario  
                        comment.getStyle().set("margin-top", "0"); // Asegurarse de que no haya margen superior  
                        
                        commentLayout.add(comment);  
                        commentsLayout.add(commentLayout);  
                    }  
                } else {  
                    commentsLayout.add(createStyledParagraph("No comentary."));  
                }  
                
                add(commentsLayout);  
            } else {  
                add(createStyledParagraph("Game Not Found."));  
            }  
        } else {  
            add(createStyledParagraph("Game ID Not Found."));  
        }  
    }  

    private Paragraph createStyledParagraph(String text) {  
        Paragraph paragraph = new Paragraph(text);  
        paragraph.getStyle().set("margin-left", "20px");  
        return paragraph;  
    }    
}