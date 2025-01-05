package com.app.games.views;  

import com.app.games.model.Game;  
import com.app.games.service.GameService;  
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;  
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;  
import com.vaadin.flow.router.BeforeEnterEvent;  
import com.vaadin.flow.router.BeforeEnterObserver;  
import com.vaadin.flow.router.Route;  

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;  

@Route("edit-game/:gameId")  
@StyleSheet("frontend/styles/styles.css")
public class EditGameView extends VerticalLayout implements BeforeEnterObserver {  

    private final GameService gameService;  
    private TextField nombreField;  
    private TextField tipoField;  
    private TextField empresaDesarrolladoraField;  
    private TextField plataformasField;  
    private TextField cantidadJugadoresLocalField;  
    private TextField fechaSalidaField;  
    private Checkbox coOpLocalCheckbox;  
    private Checkbox coOpOnlineCheckbox;   
    private String gameId;  

    private HeaderComponent header;  

    @Autowired  
    public EditGameView(GameService gameService) {  
        this.gameService = gameService;   

        setPadding(false);  
        setMargin(false);  
        setWidthFull();  

        header = new HeaderComponent();  
        header.setTitleClickable(true);  
        header.setProfileLinkVisible(true);  
        add(header);  
        
        H3 title = new H3("Edit Game");  
        title.getStyle().set("margin-left", "20px");   
        add(title);  

        // Crear los campos de entrada  
        nombreField = new TextField("Name");  
        tipoField = new TextField("Genre");  
        empresaDesarrolladoraField = new TextField("Developer");  
        plataformasField = new TextField("Platforms (separated by comma)");  
        cantidadJugadoresLocalField = new TextField("Quantity Local CO-OP");  
        fechaSalidaField = new TextField("Release Date");  
        coOpLocalCheckbox = new Checkbox("Local Co-Op");  
        coOpOnlineCheckbox = new Checkbox("Online Co-Op");  

        Button backButton = new Button("Go Back");   
        backButton.getStyle().set("margin-left", "20px");  

        Button saveButton = new Button("Confirm Changes");   
        saveButton.getStyle().set("margin-left", "20px");   

        // Agrupar los campos en verticales  
        VerticalLayout nombreLayout = new VerticalLayout(nombreField);  
        VerticalLayout tipoLayout = new VerticalLayout(tipoField);  
        VerticalLayout empresaLayout = new VerticalLayout(empresaDesarrolladoraField);  
        VerticalLayout plataformasLayout = new VerticalLayout(plataformasField);  
        VerticalLayout cantidadJugadoresLayout = new VerticalLayout(cantidadJugadoresLocalField);  
        VerticalLayout fechaSalidaLayout = new VerticalLayout(fechaSalidaField);  
        
        // Agrupar dos por fila en horizontal  
        HorizontalLayout inputLayout1 = new HorizontalLayout(nombreLayout, tipoLayout);  
        HorizontalLayout inputLayout2 = new HorizontalLayout(empresaLayout, plataformasLayout);  
        HorizontalLayout inputLayout3 = new HorizontalLayout(cantidadJugadoresLayout, fechaSalidaLayout);  
        HorizontalLayout checkboxLayout = new HorizontalLayout(coOpLocalCheckbox, coOpOnlineCheckbox);  

        // Añadir la clase CSS a los layouts para los inputs  
        inputLayout1.addClassName("input-group");  
        inputLayout2.addClassName("input-group");  
        inputLayout3.addClassName("input-group");  
        checkboxLayout.addClassName("input-group");  

        // Crear un layout para los botones utilizando VerticalLayout  
        VerticalLayout buttonLayout = new VerticalLayout(saveButton, backButton);  
        buttonLayout.getStyle().set("margin-bottom", "20px");
        
        buttonLayout.addClassName("button-group");

        // Añadir todos los layouts al componente principal  
        add(title, inputLayout1, inputLayout2, inputLayout3, checkboxLayout, buttonLayout);   

        // Añadir listener para el botón de guardar  
        saveButton.addClickListener(e -> saveGame());  

        // Añadir listener para el botón de regresar  
        backButton.addClickListener(e -> navigateBack());   
    }  

    private void navigateBack() {  
        getUI().ifPresent(ui -> ui.navigate("/my-profile"));  
    }  

    @Override  
    public void beforeEnter(BeforeEnterEvent event) {   
        this.gameId = event.getRouteParameters().get("gameId").orElse("");  
        Optional<Game> optionalGame = gameService.getGameById(gameId);  
        optionalGame.ifPresent(game -> populateForm(game));  
    }  

    private void populateForm(Game game) {  
        nombreField.setValue(game.getNombre());  
        tipoField.setValue(game.getTipo());  
        empresaDesarrolladoraField.setValue(game.getEmpresaDesarrolladora());  
        plataformasField.setValue(String.join(", ", game.getPlataformas()));  
        cantidadJugadoresLocalField.setValue(game.getCantidadJugadoresLocal());  
        fechaSalidaField.setValue(game.getFechaSalida());  
        coOpLocalCheckbox.setValue(game.isCoOpLocal());  
        coOpOnlineCheckbox.setValue(game.isCoOpOnline());  
    }  

    private void saveGame() {  
        // Implementar la lógica para guardar los cambios en el juego  
        Game game = new Game();  
        game.setNombre(nombreField.getValue());  
        game.setTipo(tipoField.getValue());  
        game.setEmpresaDesarrolladora(empresaDesarrolladoraField.getValue());  
        game.setPlataformas(Arrays.asList(plataformasField.getValue().split(",")));  
        game.setCantidadJugadoresLocal(cantidadJugadoresLocalField.getValue());  
        game.setFechaSalida(fechaSalidaField.getValue());  
        game.setCoOpLocal(coOpLocalCheckbox.getValue());  
        game.setCoOpOnline(coOpOnlineCheckbox.getValue());  
        
        // Guardar el juego utilizando el servicio (Ejemplo)  
        gameService.updateGame(gameId, game);  
        
        // Navegar de vuelta después de guardar cambios  
        navigateBack();  
    }  
}  