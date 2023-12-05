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
	    Button btnOpcionesAvanzadas = new Button("Opciones Avanzadas");

	    btnIniciarEmpleado = new Button("Iniciar Sesión como Empleado");
	    btnIniciarAdmin = new Button("Iniciar Sesión como Administrador");
	    btnIniciarAdminLocal = new Button("Iniciar Sesión como Administrador Local");
	    Button btnSalir = new Button("Salir");
	    labelResultado = new Label();

	    /* Asignamos los eventos a los botones */
	    btnIniciarEmpleado.setOnAction(e -> iniciarSesionEmpleado(primaryStage, sistema));
	    btnIniciarAdmin.setOnAction(e -> iniciarSesionAdmin(primaryStage, sistema));
	    btnIniciarAdminLocal.setOnAction(e -> iniciarSesionAdminLocal(primaryStage, sistema));
	    btnSalir.setOnAction(e -> primaryStage.close());

	    /* Diseñamos la interfaz gráfica */
	    GridPane gridPane = new GridPane();
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setVgap(10);
	    gridPane.setHgap(10);
	    gridPane.setPadding(new Insets(10));

	    Label lblTitulo = new Label("Alquiler de Vehículos");
	    lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    gridPane.add(lblTitulo, 4, 0, 5, 1);

	    btnIniciarEmpleado.setStyle("-fx-background-color: #3498DB;");
	    btnIniciarAdmin.setStyle("-fx-background-color: #5DADE2;");
	    btnIniciarAdminLocal.setStyle("-fx-background-color: #85C1E9;");
	    btnSalir.setStyle("-fx-background-color: #85C1E9;");

	    gridPane.add(btnIniciarEmpleado, 4, 1);
	    gridPane.add(btnIniciarAdmin, 4, 2);
	    gridPane.add(btnIniciarAdminLocal, 4, 3);
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

	/* Creamos un botón para regresar a la escena principal */
	private Button crearBtnRegresar(Stage primaryStage) {
	    Button btnRegresar = new Button("Regresar");
	    btnRegresar.setStyle("-fx-background-color: #85C1E9;");
	    btnRegresar.setOnAction(e -> primaryStage.setScene(escenaPrincipal)); // Restaurar la escena principal
	    return btnRegresar;
	}

	/* Lógica para iniciar sesión como empleado */
	private void iniciarSesionEmpleado(Stage primaryStage, SistemaAlquiler sistema) {
	    PanelEmpleado panelEmpleado = new PanelEmpleado();
	    PanelEmpleado.start(primaryStage, escenaPrincipal, sistema);
	}

	/* Lógica para iniciar sesión como administrador */
	private void iniciarSesionAdmin(Stage primaryStage, SistemaAlquiler sistema) {
	    PanelAdmi panelAdmi = new PanelAdmi();
	    PanelAdmi.iniciarSesionAdmin(primaryStage, escenaPrincipal, sistema);
	}

	/* Lógica para iniciar sesión como administrador local */
	private void iniciarSesionAdminLocal(Stage primaryStage, SistemaAlquiler sistema) {
	    PanelAdmiLocal panelAdmiLocal = new PanelAdmiLocal();
	    PanelAdmiLocal.iniciarSesionAdminLocal(primaryStage, escenaPrincipal, sistema);
	}

    
}

