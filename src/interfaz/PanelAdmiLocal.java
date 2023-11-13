package interfaz;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logica.AdministradorLocal;
import logica.Empleado;
import logica.Sede;
import logica.SistemaAlquiler;

public class PanelAdmiLocal {

    static void iniciarSesionAdminLocal(Stage primaryStage, Scene escenaPrincipal, SistemaAlquiler sistema) {
        Stage stage = new Stage();
        stage.setTitle("Inicio de Sesión Administrador Local");

        // Crear elementos de la interfaz
        Label labelUsuario = new Label("Nombre de Usuario:");
        TextField txtUsuario = new TextField();
        Label labelContrasena = new Label("Contraseña:");
        PasswordField txtContrasena = new PasswordField();
        Button btnContinuar = new Button("Continuar");
        Button btnSalir = new Button("Salir");

        // Evento para el botón Continuar
        btnContinuar.setOnAction(event -> {
            String nombreUsuario = txtUsuario.getText();
            String contrasena = txtContrasena.getText();
            AdministradorLocal admiLocal = autenticarAdminLocal(nombreUsuario, contrasena, sistema, stage);
            if (admiLocal != null) {
                mostrarMenuAdminLocal(primaryStage, sistema, admiLocal);
                stage.close();  // Cerrar la ventana de inicio de sesión
            } else {
                // Mostrar un mensaje de error si la autenticación falla
                mostrarMensajeError("Error de autenticación");
            }
        });

        // Evento para el botón Salir
        btnSalir.setOnAction(event -> stage.close());

        // Diseño de la interfaz
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(labelUsuario, txtUsuario, labelContrasena, txtContrasena, btnContinuar, btnSalir);

        // Crear la escena
        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);

        // Mostrar la ventana de inicio de sesión del administrador local
        stage.show();
    }

    private static void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private static AdministradorLocal autenticarAdminLocal(String nombreUsuario, String contrasena, SistemaAlquiler sistema, Stage primaryStage) {
        AdministradorLocal admiLocal = sistema.autenticarAdmiLocal(sistema.getAdministradoresLocales(), nombreUsuario, contrasena);
        if (admiLocal != null) {
            return admiLocal;
        }
        return null;
    }

    private static void mostrarMenuAdminLocal(Stage primaryStage, SistemaAlquiler sistema, AdministradorLocal admiLocal) {
        // Lógica para mostrar el menú del administrador local
        Stage stage = new Stage();
        stage.setTitle("Menú Administrador Local");

        // Crear elementos de la interfaz
        Label labelTitulo = new Label("Bienvenido, " + admiLocal.getNombreUsuario() + " - " + admiLocal.getSede().getNombre());
        ListView<String> listViewEmpleados = new ListView<>();

        // Obtener la lista de empleados de la sede del administrador local
        List<Empleado> empleadosDeSede = sistema.getEmpleadosPorSede(admiLocal.getSede());

        // Crear una lista de nombres de empleados
        List<String> nombresEmpleados = empleadosDeSede.stream().map(Empleado::getNombre).collect(Collectors.toList());
        
        ObservableList<String> empleadosObservable = FXCollections.observableArrayList(nombresEmpleados);
        listViewEmpleados.setItems(empleadosObservable);

        // Botones
        Button btnVerDetalles = new Button("Ver Detalles");
        Button btnAgregarEmpleado = new Button("Agregar Empleado");
        Button btnSalir = new Button("Salir");
        btnVerDetalles.setStyle("-fx-background-color: #3498DB;");
        btnAgregarEmpleado.setStyle("-fx-background-color: #5DADE2;");
        btnSalir.setStyle("-fx-background-color: #85C1E9;");

        // Evento para el botón Ver Detalles
        btnVerDetalles.setOnAction(e -> {
            String empleadoSeleccionado = listViewEmpleados.getSelectionModel().getSelectedItem();
            if (empleadoSeleccionado != null) {
                // Puedes buscar el objeto Empleado correspondiente al nombre seleccionado si es necesario
                Empleado empleadoEncontrado = empleadosDeSede.stream()
                        .filter(empleado -> empleado.getNombre().equals(empleadoSeleccionado))
                        .findFirst()
                        .orElse(null);

                if (empleadoEncontrado != null) {
                    // Mostrar ventana con detalles del empleado
                    mostrarDetallesEmpleado(empleadoEncontrado, primaryStage);
                }
            }
        });

        // Evento para el botón Agregar Empleado
        btnAgregarEmpleado.setOnAction(e -> {
            // Mostrar ventana para agregar nuevo empleado
            mostrarVentanaAgregarEmpleado(sistema, admiLocal, empleadosObservable,primaryStage);
        });

        // Evento para el botón Salir
        btnSalir.setOnAction(e -> stage.close());

        // Diseño de la interfaz
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(labelTitulo, listViewEmpleados, btnVerDetalles, btnAgregarEmpleado, btnSalir);
        BackgroundFill backgroundFill = new BackgroundFill(Color.BEIGE, null, null);
        Background background = new Background(backgroundFill);
        layout.setBackground(background);
        
        // Crear la escena
        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);

        // Mostrar la ventana
        stage.show();
    }



    private static void mostrarDetallesEmpleado(Empleado empleado, Stage primaryStage) {
        // Crear una nueva ventana para mostrar los detalles
        Stage detallesStage = new Stage();
        detallesStage.setTitle("Detalles del Empleado");

        // Crear elementos de la interfaz
        Label labelNombre = new Label("Nombre: " + empleado.getNombre());
        Label labelUsuario = new Label("Nombre de Usuario: " + empleado.getNombreUsuario());
        Label labelCargo = new Label("Cargo: " + empleado.getCargo());
        Label labelSede = new Label("Sede: " + empleado.getSede().getNombre());

        // Diseño de la interfaz
        VBox layout = new VBox(10);
        layout.getChildren().addAll(labelNombre, labelUsuario, labelCargo, labelSede);

        // Crear la escena
        Scene scene = new Scene(layout, 300, 200);
        detallesStage.setScene(scene);

        // Bloquear interacción con la ventana principal mientras esta esté abierta
        detallesStage.initModality(Modality.WINDOW_MODAL);
        detallesStage.initOwner(primaryStage);

        // Mostrar la ventana de detalles
        detallesStage.show();
    }

    private static void mostrarVentanaAgregarEmpleado(SistemaAlquiler sistema, AdministradorLocal admiLocal, ObservableList<String> empleadosObservable, Stage primaryStage) {
        Stage agregarEmpleadoStage = new Stage();
        agregarEmpleadoStage.setTitle("Agregar Empleado");

        // Crear elementos de la interfaz para agregar un nuevo empleado
        Label labelNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();
        Label labelUsuario = new Label("Nombre de Usuario:");
        TextField txtUsuario = new TextField();
        Label labelContrasena = new Label("Contraseña:");
        PasswordField txtContrasena = new PasswordField();
        Label labelCargo = new Label("Cargo:");
        TextField txtCargo = new TextField();
        
        // Botón para registrar el nuevo empleado
        Button btnRegistrar = new Button("Registrar Empleado");

        // Evento para el botón Registrar Empleado
        btnRegistrar.setOnAction(e -> {
            // Obtener los valores ingresados por el usuario
            String nombre = txtNombre.getText();
            String nombreUsuario = txtUsuario.getText();
            String contrasena = txtContrasena.getText();
            String cargo = txtCargo.getText();

            // Obtener la sede actual del administrador local
            Sede sedeActual = admiLocal.getSede();

            // Crear el nuevo empleado
            sistema.crearEmpleado(nombreUsuario, contrasena, nombre, cargo, sedeActual);

            // Actualizar la lista observable de empleados
            List<Empleado> empleadosDeSedeActualizados = sistema.getEmpleadosPorSede(sedeActual);
            List<String> nombresEmpleadosActualizados = empleadosDeSedeActualizados.stream().map(Empleado::getNombre).collect(Collectors.toList());
            empleadosObservable.setAll(nombresEmpleadosActualizados);

            // Cerrar la ventana de agregar empleado
            agregarEmpleadoStage.close();
        });

        // Diseño de la interfaz
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(labelNombre, txtNombre, labelUsuario, txtUsuario, labelContrasena, txtContrasena, labelCargo, txtCargo, btnRegistrar);

        // Crear la escena
        Scene scene = new Scene(layout, 400, 300);
        agregarEmpleadoStage.setScene(scene);

        // Bloquear interacción con la ventana principal mientras esta esté abierta
        agregarEmpleadoStage.initModality(Modality.WINDOW_MODAL);
        agregarEmpleadoStage.initOwner(primaryStage);

        // Mostrar la ventana de agregar empleado
        agregarEmpleadoStage.show();
    }
}
