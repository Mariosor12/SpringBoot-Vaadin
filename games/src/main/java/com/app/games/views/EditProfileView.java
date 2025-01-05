package com.app.games.views;  

import com.app.games.model.User;  
import com.app.games.service.UserService;  
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H3;  
import com.vaadin.flow.component.notification.Notification;  
import com.vaadin.flow.component.textfield.PasswordField;  
import com.vaadin.flow.component.textfield.TextField;  
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;  
import com.vaadin.flow.component.orderedlayout.VerticalLayout;  
import com.vaadin.flow.router.BeforeEnterEvent;  
import com.vaadin.flow.router.BeforeEnterObserver;  
import com.vaadin.flow.router.Route;  
import com.vaadin.flow.server.VaadinSession;  
import org.springframework.beans.factory.annotation.Autowired;  

@Route("edit-profile")  
@StyleSheet("frontend/styles/styles.css")
public class EditProfileView extends VerticalLayout implements BeforeEnterObserver {  

    private final UserService userService;  
    private TextField nombreField;  
    private TextField apellidoField;  
    private TextField correoField;  
    private TextField nombreUsuarioField;  
    private PasswordField contrasenaField;  
    private String userId;  

    private HeaderComponent header;  

    @Autowired  
    public EditProfileView(UserService userService) {  
        this.userService = userService;  

        setPadding(false);  
        setMargin(false);  
        setWidthFull();  

        header = new HeaderComponent();  
        header.setTitleClickable(true);  
        header.setProfileLinkVisible(true);  
        add(header);  

        H3 title = new H3("Edit Profile");  
        title.getStyle().set("margin-left", "20px");  
        add(title);  

        // Crear los campos de entrada  
        nombreField = new TextField("Name");  
        apellidoField = new TextField("Last Name");  
        correoField = new TextField("Email");  
        nombreUsuarioField = new TextField("Username");  
        contrasenaField = new PasswordField("Password");  

        // Crear botones  
        Button backButton = new Button("Go Back");  
        backButton.getStyle().set("margin-left", "20px");  

        Button saveButton = new Button("Confirm Changes");  
        saveButton.getStyle().set("margin-left", "20px");  

        // Agrupar los campos en filas  
        HorizontalLayout row1 = new HorizontalLayout(nombreField, apellidoField); 
        HorizontalLayout row2 = new HorizontalLayout(correoField, nombreUsuarioField);
        HorizontalLayout row3 = new HorizontalLayout(contrasenaField);
        HorizontalLayout button = new HorizontalLayout(saveButton, backButton); 

        // Añadir clase CSS para manejar el estilo  
        row1.addClassName("input-group");  
        row2.addClassName("input-group");  
        row3.addClassName("input-group"); 
        button.addClassName("input-group");  

        // Agrupar los layouts de filas y agregar  
        VerticalLayout inputLayout = new VerticalLayout(row1, row2, row3);  
        inputLayout.getStyle().set("margin-left", "20px").set("width", "auto");  

        // Crear un layout para los botones  
        VerticalLayout buttonLayout = new VerticalLayout(button);  
        buttonLayout.getStyle().set("margin-top", "20px").set("width", "auto");  

        // Añadir todos los layouts al componente principal  
        add(inputLayout, buttonLayout);  

        // Añadir listeners para los botones  
        saveButton.addClickListener(e -> saveUserProfile());  
        backButton.addClickListener(e -> navigateBack());  
    }  

    private void navigateBack() {  
        // Redirige a la vista anterior  
        getUI().ifPresent(ui -> ui.navigate("/my-profile"));  
    }  

    @Override  
    public void beforeEnter(BeforeEnterEvent event) {  
        // Obtiene el userId de la sesión actual  
        this.userId = (String) VaadinSession.getCurrent().getAttribute("userId");  

        // Si no se encuentra el usuario, redirigir al login  
        if (this.userId != null) {  
            User user = userService.findUserById(this.userId);  
            if (user != null) {  
                populateForm(user);  
            } else {  
                Notification.show("No se encontró el usuario. Por favor, inicia sesión.", 3000, Notification.Position.MIDDLE);  
                event.rerouteTo("login");  
            }  
        } else {  
            Notification.show("No se encontró el usuario. Por favor, inicia sesión.", 3000, Notification.Position.MIDDLE);  
            event.rerouteTo("login");  
        }  
    }  

    private void populateForm(User user) {  
        nombreField.setValue(user.getNombre());  
        apellidoField.setValue(user.getApellido());  
        correoField.setValue(user.getCorreo());  
        nombreUsuarioField.setValue(user.getNombreUsuario());  
        contrasenaField.setValue(user.getContrasena()); // Considera si deseas mostrar la contraseña  
    }  

    private void saveUserProfile() {  
        User user = new User();  

        user.setNombre(nombreField.getValue());  
        user.setApellido(apellidoField.getValue());  
        user.setCorreo(correoField.getValue());  
        user.setNombreUsuario(nombreUsuarioField.getValue());  
        user.setContrasena(contrasenaField.getValue());  

        userService.updateUser(userId, user); // Asegúrate de modificar el método para recibir el userId  
        Notification.show("Perfil actualizado exitosamente!", 3000, Notification.Position.MIDDLE);  
        navigateBack(); // Regresar a la vista anterior  
    }  
}