package com.app.games.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification; 

import java.util.List;

import com.app.games.model.Game;
import com.app.games.service.GameService;
import com.app.games.model.User;  
import com.app.games.service.UserService;  

@Route("my-profile") 
public class MyProfileView extends VerticalLayout implements BeforeEnterObserver {

    private final GameService gameService; 
    private final UserService userService;
    private String userId;
    private HeaderComponent header;
    
    public MyProfileView(GameService gameService, UserService userService) {  
        this.gameService = gameService;
        this.userService = userService;  

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
        this.userId = (String) VaadinSession.getCurrent().getAttribute("userId");
            if (userId != null) {  
            // Aquí buscamos el juego usando el ID  
            User user = userService.findUserById(userId);   
            List<Game> game = gameService.findGameByUserId(userId);  

            if (user != null) {  
                Icon settingsIcon = new Icon(VaadinIcon.COG);  
                settingsIcon.getElement().getStyle().set("vertical-align", "middle");  
                
                Dialog dialog = new Dialog();  
                dialog.add(new H3("Opciones"));  

                Button logoutButton = new Button("LogOut", e -> {  
                    userService.logout();   
                    Notification.show("Logout", 3000, Notification.Position.MIDDLE);  
                    getUI().ifPresent(ui -> ui.navigate("login"));   
                    dialog.close();   
                });  

                Button deleteAccountButton = new Button("Delete Account", e -> {  
                    String userIdToDelete = userId;  
                    userService.deleteUser(userIdToDelete);  
                    Notification.show("Usuario delete succesfully.");  
                    getUI().ifPresent(ui -> ui.navigate("login"));   
                    dialog.close();  
                });  

                Button addGameButton = new Button("Add Game", e -> {    
                    getUI().ifPresent(ui -> ui.navigate("add-game"));   
                    dialog.close();  
                }); 
                
                Button editProfileButton = new Button("Edit Profile", e -> {  
                    getUI().ifPresent(ui -> ui.navigate("edit-profile")); // Asegúrate de que exista la ruta para editar el perfil  
                    dialog.close();  
                });

                // Establecer margen para los botones del diálogo  
                addGameButton.getStyle().set("margin-left", "20px");  
                logoutButton.getStyle().set("margin-left", "20px");  
                deleteAccountButton.getStyle().set("margin-left", "20px"); 
                editProfileButton.getStyle().set("margin-left", "20px"); 

                VerticalLayout dialogLayout = new VerticalLayout(addGameButton, editProfileButton, logoutButton, deleteAccountButton);  
                dialog.add(dialogLayout);   

                // Crear un contenedor para el nombre y el icono  
                HorizontalLayout userInfoLayout = new HorizontalLayout();  
                userInfoLayout.add(new H1(user.getNombre() + " " + user.getApellido()), settingsIcon);  
                userInfoLayout.setAlignItems(Alignment.BASELINE);  
                userInfoLayout.getStyle().set("margin-left", "20px"); // Ajustar margen izquierdo para userInfoLayout  

                add(userInfoLayout);   
                
                settingsIcon.addClickListener(e -> dialog.open());  

                // Añadir margen a las etiquetas de información del usuario  
                Paragraph usernameParagraph = new Paragraph("Username: " + user.getNombreUsuario());  
                usernameParagraph.getStyle().set("margin-left", "20px");  
                add(usernameParagraph);  
                
                Paragraph emailParagraph = new Paragraph("Email: " + user.getCorreo());  
                emailParagraph.getStyle().set("margin-left", "20px");  
                add(emailParagraph);  

                H2 title = new H2("User Games List");  
                title.getStyle().set("margin-left", "20px"); // Ajustar margen izquierdo para el título  
                add(title);  

                FlexLayout flexLayout = new FlexLayout();  
                flexLayout.setFlexDirection(FlexLayout.FlexDirection.ROW);  
                flexLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);   
                
                if (game == null || game.isEmpty()) {  
                    Paragraph name = new Paragraph("You don't have any game.");
                    name.getStyle().set("margin-left", "20px");
                    add(name); 
                } else {  
                    for (Game games : game) {  
                        Div card = createGameCard(games);  
                        flexLayout.add(card);  
                    }  
                }   

                // Agregar layout flexible a la vista  
                add(flexLayout);  
            } else {  
                add(new Paragraph("User Not Found."));  
            }  
        } else {  
            add(new Paragraph("User Not found. Check the ID.")); 
            event.rerouteTo("login"); 
        }  
    }

    private Div createGameCard(Game game) {  
        // Crear una tarjeta para el juego  
        Div card = new Div();  
        card.getStyle()  
                .set("border", "1px solid #ccc")  
                .set("border-radius", "5px")  
                .set("padding", "20px")  
                .set("margin", "20px")  
                .set("background-color", "#f9f9f9")  
                .set("width", "220px"); // Definir un ancho fijo para las tarjetas  

        // Crear un RouterLink al juego  
        RouterLink link = new RouterLink(game.getNombre(), GameDetailView.class,  
            new RouteParameters("gameId", game.getId()));  
        
        // Crear botones de acción  
        Icon deleteIcon = new Icon(VaadinIcon.TRASH);  
        Button deleteButton = new Button(deleteIcon);  
        deleteButton.addClickListener(e -> {  
            gameService.deleteGame(game.getId(), this.userId); // Llamar al método delete en GameService  
            Notification.show("Game Delete: " + game.getNombre(), 3000, Notification.Position.MIDDLE);  
            
            // Opcional: Ocultar navegación o eliminación de tarjeta  
            card.setVisible(false);  
            getUI().ifPresent(ui -> ui.navigate("/"));
        });  

        Icon editIcon = new Icon(VaadinIcon.PENCIL);  
        Button editButton = new Button(editIcon);  
        editButton.addClickListener(e -> {  
            // Lógica para editar el juego  
            getUI().ifPresent(ui -> ui.navigate("edit-game/" + game.getId())); // Asegúrate de tener una ruta para editar juegos  
        });  

        // Agregar los elementos al div  
        HorizontalLayout actionsLayout = new HorizontalLayout(deleteButton, editButton);  
        actionsLayout.setAlignItems(Alignment.BASELINE);  
        actionsLayout.setJustifyContentMode(FlexLayout.JustifyContentMode.END); // Alinear a la derecha  

        card.add(link, new Paragraph("Genre: " + game.getTipo()),   
                new Paragraph("Developer: " + game.getEmpresaDesarrolladora()),  
                new Paragraph("Platforms: " + String.join(", ", game.getPlataformas())),  
                new Paragraph("Local Players: " + game.getCantidadJugadoresLocal()),  
                new Paragraph("Release Date: " + game.getFechaSalida()),   
                actionsLayout // Añadir el layout de acciones  
        );  

        return card;  
    }   
    
}
