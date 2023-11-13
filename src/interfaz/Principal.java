package interfaz;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logica.*;
import persistencia.Persistencia;
import com.opencsv.exceptions.CsvValidationException;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Principal extends Application {

    private SistemaAlquiler sistema;
    private Label labelResultado;
    private Button btnIniciarEmpleado;
    private Button btnIniciarAdmin;
    private Button btnIniciarAdminLocal;
    private Scene escenaPrincipal;

    public static void main(String[] args) {
        launch(args);
    }
    


	@Override
    public void start(Stage primaryStage) throws CsvValidationException, NumberFormatException {
        primaryStage.setTitle("Sistema de Alquiler de Vehículos");

        // Crear el sistema y cargar datos
        sistema = new SistemaAlquiler();
        Administrador administrador = sistema.nuevoAdministrador("grupo9", "123");
        AdministradorLocal admiBogota=sistema.agregarAdmLocalBogota("admiBogota", "456",null);
        AdministradorLocal admiDorado=sistema.agregarAdmLocalDorado("admiDorado", "789",null);
        Sede sedeDorado=sistema.agregarSede("Aeropuerto El Dorado", "Avenida Calle 26 # 96A-21", admiDorado, null);
        Sede sedeBogota=sistema.agregarSede("Nuestro Bogota", "Avenida Carrera 86 # 55A-75", admiBogota, null);
        admiBogota.setSede(sedeBogota);
        admiDorado.setSede(sedeDorado);
        
        Persistencia persistencia = new Persistencia();
        persistencia.cargarDatos(sistema, "datos/carros.csv", "datos/clientes.csv", "datos/empleados.csv", "datos/reservas.csv", "datos/seguros.csv"); 

        // Elementos de la interfaz
        Button btnRegistrarse = new Button("Registrarse");
        Button btnIniciarSesion = new Button("Iniciar Sesión");
        Button btnOpcionesAvanzadas = new Button("Opciones Avanzadas");
        
        btnIniciarEmpleado = new Button("Iniciar Sesión como Empleado");
        btnIniciarAdmin = new Button("Iniciar Sesión como Administrador");
        btnIniciarAdminLocal = new Button("Iniciar Sesión como Administrador Local");
        Button btnSalir = new Button("Salir");
        labelResultado = new Label();

        // Eventos de los botones
        btnRegistrarse.setOnAction(e -> abrirVentanaRegistro(primaryStage));
        btnIniciarSesion.setOnAction(e -> abrirVentanaIniciarSesion(sistema,primaryStage));
        btnOpcionesAvanzadas.setOnAction(e -> mostrarOpcionesAvanzadas(primaryStage));
        btnIniciarEmpleado.setOnAction(e -> iniciarSesionEmpleado());
        btnIniciarAdmin.setOnAction(e -> iniciarSesionAdmin(primaryStage, sistema));
        btnIniciarAdminLocal.setOnAction(e -> iniciarSesionAdminLocal(primaryStage, sistema));
        btnSalir.setOnAction(e -> primaryStage.close());      

     // Diseño de la interfaz
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));

        Label lblTitulo = new Label("Alquiler de Vehículos");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gridPane.add(lblTitulo, 4, 0, 5, 1); 
        
        btnRegistrarse.setStyle("-fx-background-color: #3498DB;"); 
        btnIniciarSesion.setStyle("-fx-background-color: #5DADE2;"); 
        btnSalir.setStyle("-fx-background-color: #85C1E9;");
        btnOpcionesAvanzadas.setStyle("-fx-background-color: #85C1E9;");

        gridPane.add(btnRegistrarse, 4, 1);
        gridPane.add(btnIniciarSesion, 4, 2);
        gridPane.add(btnSalir, 4, 3);

        gridPane.add(btnOpcionesAvanzadas, 5, 5, 1, 1);
        GridPane.setValignment(btnOpcionesAvanzadas, VPos.BOTTOM);
        
        gridPane.setStyle("-fx-background-color: beige;");

        // Escena
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);

        // Almacenar la escena principal
        escenaPrincipal = scene;

        // Mostrar la interfaz
        primaryStage.show();
    }

    private void abrirVentanaRegistro(Stage primaryStage) {
    	GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(10));
        
        Label lblTitulo = new Label("Registro de Cliente");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label lblNombreUsuario = new Label("Nombre de usuario:");
        TextField nombreUsuarioField = new TextField();
        Label lblContrasena = new Label("Contraseña:");
        PasswordField contrasenaField = new PasswordField();
        Label lblNombre = new Label("Nombre:");
        TextField nombreField = new TextField();
        Label lblNumero = new Label("Numero telefonico:");
        TextField numeroField = new TextField();
        Label lblCorreo = new Label("Correo electronico:");
        TextField correoField = new TextField();
        Label lblNacimiento = new Label("Fecha de nacimiento (yyyy-MM-dd):");
        TextField fechaNacimientoField = new TextField();
        Label lblNacionalidad = new Label("Nacionalidad:");
        TextField nacionalidadField = new TextField();
        Label lblNumeroLic = new Label("Numero licencia de conduccion:");
        TextField numeroLicConField = new TextField();
        Label lblPaisEx = new Label("Pais de expedicion de la licencia de conduccion:");
        TextField paisExLicField = new TextField();
        Label lblVencimiento = new Label("Fecha de vencimiento de la licencia de conduccion (yyyy-MM-dd):");
        TextField fechaVenLicField = new TextField();
        Label lblTarjeta = new Label("Datos de la tarjeta de credito (numero-cvv-MM/yyyy):");
        TextField datosTarjetaField = new TextField();


        Button btnAceptar = new Button("Aceptar");
        btnAceptar.setOnAction(e -> {
            String nombreUsuario = nombreUsuarioField.getText();
            String contrasena = contrasenaField.getText();
            String nombre = nombreField.getText();
            String numero = numeroField.getText();
            String correo = correoField.getText();
            String fechaNacimiento = fechaNacimientoField.getText();
            String nacionalidad = nacionalidadField.getText();
            String numeroLicencia = numeroLicConField.getText();
            String expedicion = paisExLicField.getText();
            String vencimiento = fechaVenLicField.getText();
            String tarjeta = datosTarjetaField.getText();
            sistema.agregarCliente(nombreUsuario, contrasena, nombre, numero, correo, fechaNacimiento, 
            		nacionalidad, numeroLicencia, expedicion, vencimiento, tarjeta);
            Persistencia.escribirClientes(sistema, "datos/clientes.csv");
            mostrarMensajeExitoRegistro();
            primaryStage.setScene(escenaPrincipal);
        });
        
       
        layout.add(lblTitulo, 0, 0, 2, 1); // Columna 0, Fila 0, Span 2 columnas
        layout.add(lblNombreUsuario, 0, 1);
        layout.add(nombreUsuarioField, 1, 1);
        layout.add(lblContrasena, 0, 2);
        layout.add(contrasenaField, 1, 2);
        layout.add(lblNombre, 0, 3);
        layout.add(nombreField, 1, 3);
        layout.add(lblNumero, 0, 4);
        layout.add(numeroField, 1, 4);
        layout.add(lblCorreo, 0, 5);
        layout.add(correoField, 1, 5);
        layout.add(lblNacimiento, 0, 6);
        layout.add(fechaNacimientoField, 1, 6);
        layout.add(lblNacionalidad, 0, 7);
        layout.add(nacionalidadField, 1, 7);
        layout.add(lblNumeroLic, 0, 8);
        layout.add(numeroLicConField, 1, 8);
        layout.add(lblPaisEx, 0, 9);
        layout.add(paisExLicField, 1, 9);
        layout.add(lblVencimiento, 0, 10);
        layout.add(fechaVenLicField, 1, 10);
        layout.add(lblTarjeta, 0, 11);
        layout.add(datosTarjetaField, 1, 11);
        layout.add(crearBtnRegresar(primaryStage), 0, 12);
        layout.add(btnAceptar, 1, 12);
        
        layout.setStyle("-fx-background-color: beige;");
        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setScene(scene);
    }
    
    private void mostrarMensajeExitoRegistro() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("Su cuenta fue creada satisfactoriamente.");
        alert.showAndWait();
    }
    
    private void mostrarMensajeInicioFallido() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Érror");
        alert.setHeaderText(null);
        alert.setContentText("Credenciales incorrectas. Intente nuevamente.");
        alert.showAndWait();
    }

    private void abrirVentanaIniciarSesion(SistemaAlquiler sistema,Stage primaryStage) {
        // Lógica para abrir la ventana de inicio de sesión
        labelResultado.setText("Ventana de Inicio de Sesión");
        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(10));
        
        Label lblTitulo = new Label("Iniciar sesión");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label lblNombreUsuario = new Label("Nombre de usuario:");
        TextField nombreUsuarioField = new TextField();
        Label lblContrasena = new Label("Contraseña:");
        PasswordField contrasenaField = new PasswordField();
        
        
        Button btnAceptar = new Button("Ingresar");
        btnAceptar.setOnAction(e -> {
            String nombreUsuario = nombreUsuarioField.getText();
            String contrasena = contrasenaField.getText();
            boolean verif= sistema.autenticarCliente(nombreUsuario, contrasena);
            if (verif) {
            	VentanasReserva ventanas= new VentanasReserva();
                ventanas.mostrarMenuReservas(sistema,primaryStage,nombreUsuario, escenaPrincipal);
            } else {
        	mostrarMensajeInicioFallido();
            }
            
            
        });
        
        layout.add(lblTitulo, 0, 0, 2, 1); // Columna 0, Fila 0, Span 2 columnas
        layout.add(lblNombreUsuario, 0, 1);
        layout.add(nombreUsuarioField, 1, 1);
        layout.add(lblContrasena, 0, 2);
        layout.add(contrasenaField, 1, 2);
        layout.add(crearBtnRegresar(primaryStage), 0, 3);
        layout.add(btnAceptar, 1, 3);
        
        
        layout.setStyle("-fx-background-color: beige;");
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        
       
    }
    
    

    private void mostrarOpcionesAvanzadas(Stage primaryStage) {
        // Lógica para mostrar los botones de opciones avanzadas
        VBox opcionesAvanzadasLayout = new VBox(10);
        opcionesAvanzadasLayout.setAlignment(Pos.CENTER); // Centrar el VBox
        opcionesAvanzadasLayout.setPadding(new Insets(10));
        
        btnIniciarEmpleado.setStyle("-fx-background-color: #3498DB;");
        btnIniciarAdmin.setStyle("-fx-background-color: #5DADE2;");
        btnIniciarAdminLocal.setStyle("-fx-background-color: #85C1E9;");
        
        opcionesAvanzadasLayout.getChildren().addAll(btnIniciarEmpleado, btnIniciarAdmin, btnIniciarAdminLocal, crearBtnRegresar(primaryStage));
        opcionesAvanzadasLayout.setStyle("-fx-background-color: beige;"); // Establecer color de fondo
        Scene opcionesAvanzadasScene = new Scene(opcionesAvanzadasLayout, 400, 200);

        // Actualizar la escena actual
        primaryStage.setScene(opcionesAvanzadasScene);
    }

    private Button crearBtnRegresar(Stage primaryStage) {
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setStyle("-fx-background-color: #85C1E9;"); 
        btnRegresar.setOnAction(e -> primaryStage.setScene(escenaPrincipal));  // Restaurar la escena principal
        return btnRegresar;
    }

    private void iniciarSesionEmpleado() {
        // Lógica para iniciar sesión como empleado
        labelResultado.setText("Iniciar Sesión como Empleado");
    }

    private void iniciarSesionAdmin(Stage primaryStage, SistemaAlquiler sistema) {
        PanelAdmi panelAdmi = new PanelAdmi();
        PanelAdmi.iniciarSesionAdmin(primaryStage, escenaPrincipal,sistema);
    }

    private void iniciarSesionAdminLocal(Stage primaryStage, SistemaAlquiler sistema) {
    	PanelAdmiLocal panelAdmiLocal = new PanelAdmiLocal();
    	PanelAdmiLocal.iniciarSesionAdminLocal(primaryStage, escenaPrincipal,sistema);
    }
    
}

