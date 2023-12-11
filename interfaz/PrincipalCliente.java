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

public class PrincipalCliente extends Application
{
	private SistemaAlquiler sistema;
    private Label labelResultado;
    private Scene escenaPrincipal;

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
	public void start(Stage primaryStage) throws CsvValidationException, NumberFormatException {
	    /* Establecemos el título de la ventana principal */
	    primaryStage.setTitle("Sistema de Alquiler de Vehículos");

	    /* Creamos el sistema y cargamos los datos */
	    sistema = new SistemaAlquiler();
	    Administrador administrador = sistema.nuevoAdministrador("grupo9", "123");
	    AdministradorLocal admiBogota = sistema.agregarAdmLocalBogota("admiBogota", "456", null);
	    AdministradorLocal admiDorado = sistema.agregarAdmLocalDorado("admiDorado", "789", null);
	    Sede sedeDorado = sistema.agregarSede("Aeropuerto El Dorado", "Avenida Calle 26 # 96A-21", admiDorado, null);
	    Sede sedeBogota = sistema.agregarSede("Nuestro Bogota", "Avenida Carrera 86 # 55A-75", admiBogota, null);
	    admiBogota.setSede(sedeBogota);
	    admiDorado.setSede(sedeDorado);

	    Persistencia persistencia = new Persistencia();
	    persistencia.cargarDatos(sistema, "datos/carros.csv", "datos/clientes.csv", "datos/empleados.csv", "datos/reservas.csv", "datos/seguros.csv");

	    /* Creamos los elementos de la interfaz */
	    Button btnRegistrarse = new Button("Registrarse");
	    Button btnIniciarSesion = new Button("Iniciar Sesión");
	    Button btnVerDisponibilidadAnual = new Button("Disponibilidad anual");
	    Button btnSalir = new Button("Salir");
	    labelResultado = new Label();

	    /* Asignamos los eventos a los botones */
	    btnRegistrarse.setOnAction(e -> abrirVentanaRegistro(primaryStage));
	    btnIniciarSesion.setOnAction(e -> abrirVentanaIniciarSesion(sistema, primaryStage));
	    btnVerDisponibilidadAnual.setOnAction(e -> verDisponibilidadAnualPorSede());
	    btnSalir.setOnAction(e -> primaryStage.close());

	    /* Diseñamos la interfaz gráfica */
	    GridPane gridPane = new GridPane();
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setVgap(10);
	    gridPane.setHgap(10);
	    gridPane.setPadding(new Insets(10));

	    Label lblTitulo = new Label("Alquiler de Vehículos");
	    lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    gridPane.add(lblTitulo, 1, 0, 5, 1);

	    btnRegistrarse.setStyle("-fx-background-color: #3498DB;");
	    btnIniciarSesion.setStyle("-fx-background-color: #5DADE2;");
	    btnVerDisponibilidadAnual.setStyle("-fx-background-color: #85C1E9;");
	    btnSalir.setStyle("-fx-background-color: #85C1E9;");

	    gridPane.add(btnRegistrarse, 4, 1);
	    gridPane.add(btnIniciarSesion, 4, 2);
	    gridPane.add(btnVerDisponibilidadAnual, 4, 3);
	    gridPane.add(btnSalir, 4, 4);

	    gridPane.setStyle("-fx-background-color: beige;");

	    /* Creamos la escena principal */
	    Scene scene = new Scene(gridPane, 400, 300);
	    primaryStage.setScene(scene);

	    /* Almacenamos la escena principal */
	    escenaPrincipal = scene;

	    /* Mostramos la interfaz gráfica */
	    primaryStage.show();
	}
    
    private void verDisponibilidadAnualPorSede() {
	    Stage ventanaDisponibilidad = new Stage();
	    ventanaDisponibilidad.setTitle("Disponibilidad Anual");

	    /* Diseñamos la interfaz de la ventana de disponibilidad */
	    VBox layoutVentanaDisponibilidad = new VBox(10);
	    layoutVentanaDisponibilidad.setAlignment(Pos.CENTER);

	    /* Botones para ver disponibilidad por sede */
	    Button btnVerBogota = new Button("Ver Sede Bogotá");
	    Button btnVerDorado = new Button("Ver Sede Dorado");
	    Button btnSalir = new Button("Salir");

	    /* Establecemos el estilo de los botones */
	    btnVerBogota.setStyle("-fx-background-color: #3498DB;");
	    btnVerDorado.setStyle("-fx-background-color: #5DADE2;");
	    btnSalir.setStyle("-fx-background-color: #85C1E9;");

	    /* Asignamos eventos a los botones */
	    btnVerBogota.setOnAction(e -> mostrarDisponibilidadSede("Bogotá"));
	    btnVerDorado.setOnAction(e -> mostrarDisponibilidadSede("Dorado"));
	    btnSalir.setOnAction(e -> ventanaDisponibilidad.close());

	    /* Agregamos elementos al diseño */
	    layoutVentanaDisponibilidad.getChildren().addAll(btnVerBogota, btnVerDorado, btnSalir);
	    layoutVentanaDisponibilidad.setStyle("-fx-background-color: beige;"); // Fondo beige

	    /* Creamos la escena de la ventana de disponibilidad */
	    Scene sceneVentanaDisponibilidad = new Scene(layoutVentanaDisponibilidad, 300, 200);

	    /* Asignamos la escena a la ventana */
	    ventanaDisponibilidad.setScene(sceneVentanaDisponibilidad);

	    /* Mostramos la ventana */
	    ventanaDisponibilidad.show();
	}

	/*
	 * Mostramos una nueva ventana con la imagen de la sede seleccionada.
	 */
	private void mostrarDisponibilidadSede(String nombreSede) {
	    /* Creamos una nueva ventana */
	    Stage ventanaImagen = new Stage();
	    ventanaImagen.setTitle("Imagen de la Sede");

	    /* Creamos un ImageView para mostrar la imagen */
	    ImageView imageView = new ImageView();

	    /* Cargamos la imagen según la sede seleccionada */
	    Image imagen;
	    if (nombreSede.equalsIgnoreCase("Bogotá")) {
	        imagen = new Image("file:datos/nuestroBogota.png"); // Ruta de la imagen de Bogotá
	    } else if (nombreSede.equalsIgnoreCase("Dorado")) {
	        imagen = new Image("file:datos/dorado.png"); // Ruta de la imagen de Dorado
	    } else {
	        // Podemos manejar otros casos o mostrar un mensaje de error
	        return;
	    }


	    /* Configuramos el ImageView con la imagen cargada */
	    imageView.setImage(imagen);
	    imageView.setFitWidth(500); // Ajustamos el ancho de la imagen según tus necesidades
	    imageView.setPreserveRatio(true);

	    /* Diseñamos la interfaz de la ventana de imagen */
	    VBox layoutVentanaImagen = new VBox(10);
	    layoutVentanaImagen.setAlignment(Pos.CENTER);
	    layoutVentanaImagen.getChildren().addAll(imageView);
	    layoutVentanaImagen.setStyle("-fx-background-color: beige;"); // Fondo beige

	    /* Creamos la escena de la ventana de imagen */
	    Scene sceneVentanaImagen = new Scene(layoutVentanaImagen);

	    /* Asignamos la escena a la ventana */
	    ventanaImagen.setScene(sceneVentanaImagen);

	    /* Mostramos la ventana */
	    ventanaImagen.show();
	}
	
	/* Creamos la ventana de registro */
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
	    Label lblNacimiento = new Label("Fecha de nacimiento:");
	    DatePicker fechaNacimientoField = new DatePicker();
	    Label lblNacionalidad = new Label("Nacionalidad:");
	    TextField nacionalidadField = new TextField();
	    Label lblNumeroLic = new Label("Numero licencia de conduccion:");
	    TextField numeroLicConField = new TextField();
	    Label lblPaisEx = new Label("Pais de expedicion de la licencia de conduccion:");
	    TextField paisExLicField = new TextField();
	    Label lblVencimiento = new Label("Fecha de vencimiento de la licencia de conduccion:");
	    DatePicker fechaVenLicField = new DatePicker();

	    Button btnAceptar = new Button("Aceptar");
	    btnAceptar.setOnAction(e -> {
	        String nombreUsuario = nombreUsuarioField.getText();
	        String contrasena = contrasenaField.getText();
	        String nombre = nombreField.getText();
	        String numero = numeroField.getText();
	        String correo = correoField.getText();
	        String fechaNacimiento = fechaNacimientoField.getValue().toString();
	        String nacionalidad = nacionalidadField.getText();
	        String numeroLicencia = numeroLicConField.getText();
	        String expedicion = paisExLicField.getText();
	        String vencimiento = fechaVenLicField.getValue().toString();
	        sistema.agregarCliente(nombreUsuario, contrasena, nombre, numero, correo, fechaNacimiento, 
	                nacionalidad, numeroLicencia, expedicion, vencimiento);
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
	    layout.add(crearBtnRegresar(primaryStage), 0, 11);
	    layout.add(btnAceptar, 1, 11);

	    layout.setStyle("-fx-background-color: beige;");
	    Scene scene = new Scene(layout, 600, 500);
	    primaryStage.setScene(scene);
	}

    
	/* Mostramos un mensaje de éxito en el registro */
	private void mostrarMensajeExitoRegistro() {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Éxito");
	    alert.setHeaderText(null);
	    alert.setContentText("Su cuenta fue creada satisfactoriamente.");
	    alert.showAndWait();
	}

	/* Mostramos un mensaje en caso de inicio de sesión fallido */
	private void mostrarMensajeInicioFallido() {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Érror");
	    alert.setHeaderText(null);
	    alert.setContentText("Credenciales incorrectas. Intente nuevamente.");
	    alert.showAndWait();
	}

	/* Abrimos la ventana de inicio de sesión */
	private void abrirVentanaIniciarSesion(SistemaAlquiler sistema, Stage primaryStage) {
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
	        boolean verif = sistema.autenticarCliente(nombreUsuario, contrasena);
	        if (verif) {
	            VentanasReserva ventanas = new VentanasReserva();
	            ventanas.mostrarMenuReservas(sistema, primaryStage, nombreUsuario, escenaPrincipal);
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
	
	private Button crearBtnRegresar(Stage primaryStage) {
	    Button btnRegresar = new Button("Regresar");
	    btnRegresar.setStyle("-fx-background-color: #85C1E9;");
	    btnRegresar.setOnAction(e -> primaryStage.setScene(escenaPrincipal)); // Restaurar la escena principal
	    return btnRegresar;
	}
}
