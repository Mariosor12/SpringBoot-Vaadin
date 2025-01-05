package com.app.games.views; 

import com.app.games.model.Game;  
import com.app.games.service.GameService; 
import com.vaadin.flow.component.button.Button;  
import com.vaadin.flow.component.checkbox.Checkbox;  
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;  
import com.vaadin.flow.component.orderedlayout.VerticalLayout;  
import com.vaadin.flow.component.notification.Notification;  
import com.vaadin.flow.component.textfield.TextField;  
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;  
import java.util.List;  

@Route("add-game")  
@StyleSheet("frontend/styles/styles.css")
public class AddGameView extends VerticalLayout {  

    private final GameService gameService;  

    // Componentes de la UI  
    private TextField nombreField = new TextField();  
    private TextField tipoField = new TextField();  
    private TextField empresaDesarrolladoraField = new TextField();  
    private TextField plataformasField = new TextField();  
    private TextField cantidadJugadoresLocalField = new TextField();  
    private DatePicker fechaSalidaField = new DatePicker();  
    private Checkbox coOpLocalCheckbox = new Checkbox("Co-Op Local");  
    private Checkbox coOpOnlineCheckbox = new Checkbox("Co-Op Online"); 
    private TextField puntuacionField = new TextField(); 
    private TextField comentarioField = new TextField(); 
    private Button saveButton = new Button("Add Game");  
    private Button backButton = new Button("Go Back"); 
    
    private HeaderComponent header;

    public AddGameView(GameService gameService) {  
        this.gameService = gameService; 

        setPadding(false); // Eliminar el padding  
        setMargin(false); // Eliminar el margin  
        setWidthFull();

        header = new HeaderComponent();  
        
        // Solicitando que el título no sea clickeable y ocultando el perfil  
        header.setTitleClickable(true);  
        header.setProfileLinkVisible(true);  

        // Agregar el header a la vista  
        add(header);
        
        H3 title = new H3("Add Game");   
        title.getStyle().set("margin-left", "20px").set("width", "auto");   
        add(title);   

        // Cambiar HorizontalLayout por VerticalLayout para hacer que los elementos estén uno debajo del otro  
        nombreField = new TextField("Name");  
        tipoField = new TextField("Genre");  
        empresaDesarrolladoraField = new TextField("Developer");  
        plataformasField = new TextField("Platforms (separated by comma)");  
        cantidadJugadoresLocalField = new TextField("Quantity Local CO-OP");  
        fechaSalidaField = new DatePicker("Release Date");  
        coOpLocalCheckbox = new Checkbox("Local Co-Op");  
        coOpOnlineCheckbox = new Checkbox("Online Co-Op");
        puntuacionField = new TextField("Review"); 
        comentarioField = new TextField("Comentary");   

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
        VerticalLayout cantidadReview = new VerticalLayout(puntuacionField);  
        VerticalLayout comentarioLayout = new VerticalLayout(comentarioField); 
        
        // Agrupar dos por fila en horizontal  
        HorizontalLayout inputLayout1 = new HorizontalLayout(nombreLayout, tipoLayout);  
        HorizontalLayout inputLayout2 = new HorizontalLayout(empresaLayout, plataformasLayout);  
        HorizontalLayout inputLayout3 = new HorizontalLayout(cantidadJugadoresLayout, fechaSalidaLayout);
        HorizontalLayout inputLayout4 = new HorizontalLayout(cantidadReview, comentarioLayout);  
        HorizontalLayout checkboxLayout = new HorizontalLayout(coOpLocalCheckbox, coOpOnlineCheckbox);  

        // Añadir la clase CSS a los layouts para los inputs  
        inputLayout1.addClassName("input-group");  
        inputLayout2.addClassName("input-group");  
        inputLayout3.addClassName("input-group");  
        inputLayout4.addClassName("input-group"); 
        checkboxLayout.addClassName("input-group");  

        // Crear un layout para los botones utilizando VerticalLayout  
        VerticalLayout buttonLayout = new VerticalLayout(saveButton, backButton);  
        buttonLayout.getStyle().set("margin-bottom", "20px");
        
        buttonLayout.addClassName("button-group");

        // Añadir todos los layouts al componente principal  
        add(title, inputLayout1, inputLayout2, inputLayout3, checkboxLayout, inputLayout4, buttonLayout);   

        // Añadir listener para el botón de guardar  
        saveButton.addClickListener(e -> saveGame());  

        // Añadir listener para el botón de regresar  
        backButton.addClickListener(e -> navigateBack());  
    }  

    private HorizontalLayout createRow(String labelText, TextField textField) {  
        textField.setLabel(labelText);  
        return new HorizontalLayout(textField);  
    }  

    private HorizontalLayout createRow(String labelText, DatePicker datePicker) {  
        datePicker.setLabel(labelText);  
        return new HorizontalLayout(datePicker);  
    }  

    private HorizontalLayout createRow(Checkbox... checkboxes) {  
        HorizontalLayout layout = new HorizontalLayout();  
        for (Checkbox checkbox : checkboxes) {  
            layout.add(checkbox);  
        }  
        return layout;  
    }  

    private void saveGame() {  
        String userId = (String) VaadinSession.getCurrent().getAttribute("userId");
        Game game = new Game();  
        game.setNombre(nombreField.getValue());  
        game.setTipo(tipoField.getValue());  
        game.setEmpresaDesarrolladora(empresaDesarrolladoraField.getValue());  

        // Convertir la lista de plataformas de String a List<String>  
        String plataformasInput = plataformasField.getValue();  
        List<String> plataformasList = new ArrayList<>();  
        if (!plataformasInput.isEmpty()) {  
            for (String plataforma : plataformasInput.split(",")) {  
                plataformasList.add(plataforma.trim());  
            }  
        }  
        game.setPlataformas(plataformasList);  

        game.setCantidadJugadoresLocal(cantidadJugadoresLocalField.getValue());  

        // Obtener la fecha desde el DatePicker, si no hay valor se establece null  
        game.setFechaSalida(fechaSalidaField.getValue() != null ? fechaSalidaField.getValue().toString() : null);  
        
        game.setCoOpLocal(coOpLocalCheckbox.getValue());  
        game.setCoOpOnline(coOpOnlineCheckbox.getValue()); 
        
        String puntuacion = puntuacionField.getValue();
        String comentarios = comentarioField.getValue();

        // Llamar al servicio para crear el juego  
        Game createdGame = gameService.createGame(game, userId, puntuacion, comentarios);  
        
        // Notificar al usuario  
        Notification.show("Juego agregado: " + createdGame.getNombre());  

        // Limpiar los campos  
        clearFields();  

        getUI().ifPresent(ui -> ui.navigate("/"));
    }  

    private void clearFields() {  
        nombreField.clear();  
        tipoField.clear();  
        empresaDesarrolladoraField.clear();  
        plataformasField.clear();  
        cantidadJugadoresLocalField.clear();  
        fechaSalidaField.clear();  
        coOpLocalCheckbox.clear();  
        coOpOnlineCheckbox.clear();  
    }  

    private void navigateBack() {  
        // Redirige a la vista anterior, cambiar "main" con la ruta de tu vista principal  
        getUI().ifPresent(ui -> ui.navigate("/my-profile"));  
    }  
}  