package interfaz;

import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import logica.SistemaAlquiler;

public class PanelAdmi {

    static public void iniciarSesionAdmin(Stage primaryStage, Scene escenaPrincipal, SistemaAlquiler sistema) {
    Stage stage = new Stage();
    stage.setTitle("Inicio de Sesión Administrador");

    /* Creamos elementos de la interfaz */
    Label labelUsuario = new Label("Nombre de Usuario:");
    TextField txtUsuario = new TextField();
    Label labelContrasena = new Label("Contraseña:");
    PasswordField txtContrasena = new PasswordField();
    Button btnContinuar = new Button("Continuar");
    Button btnSalir = new Button("Salir");
    
    

    /* Evento para el botón Continuar */
    btnContinuar.setOnAction(e -> {
        String nombreUsuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();
        if (autenticarAdmin(nombreUsuario, contrasena)) {
            /* Si la autenticación es exitosa, mostramos el menú del administrador y actualizamos la ventana principal */
            mostrarMenuAdmin(primaryStage, escenaPrincipal, sistema);
            stage.close();  /* Cerramos la ventana de inicio de sesión */
        } else {
            /* Mostramos un mensaje de error si la autenticación falla */
            mostrarMensajeError("Error de autenticación");
        }
    });


        /* Evento para el botón Salir */
    btnSalir.setOnAction(e -> stage.close());

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(10));
    layout.setStyle("-fx-background-color: #f0f0f0;"); // Cambia el color de fondo de la ventana

    // Cambia el estilo de los botones
    btnContinuar.setStyle("-fx-background-color: #4caf50; -fx-text-fill: #ffffff;");
    btnSalir.setStyle("-fx-background-color: #f44336; -fx-text-fill: #ffffff;");

    layout.getChildren().addAll(labelUsuario, txtUsuario, labelContrasena, txtContrasena, btnContinuar, btnSalir);
    Scene scene = new Scene(layout, 300, 200);
    stage.setScene(scene);
    stage.show();
}

    private static void mostrarMenuAdmin(Stage primaryStage, Scene escenaPrincipal, SistemaAlquiler sistema) {
        // Creamos la disposición para el menú del administrador con un VBox y un espaciado de 10.
        // También aplicamos un relleno de 10 unidades.
        VBox menuAdminLayout = new VBox(10);
        menuAdminLayout.setPadding(new Insets(10));

        // Configuramos el menú del administrador, incluyendo etiquetas y botones específicos.
        Label labelMenuAdministrador = new Label("Menú de Administrador");
        Button btnRegistrarCompra = new Button("Registrar compra de nuevos vehículos");
        Button btnDarDeBaja = new Button("Dar de baja un vehículo");
        Button btnConfigurarSeguros = new Button("Configurar seguros");
        Button btnArchivoLog = new Button("Archivo log de vehículo");
        Button btnSalirMenuAdmin = new Button("Salir del menú de administrador");

        // Aplicamos el estilo de fondo beige al VBox
        BackgroundFill backgroundFill = new BackgroundFill(Color.BEIGE, null, null);
        Background background = new Background(backgroundFill);
        menuAdminLayout.setBackground(background);

        btnRegistrarCompra.setStyle("-fx-background-color: #3498DB;");
        btnDarDeBaja.setStyle("-fx-background-color: #5DADE2;");
        btnConfigurarSeguros.setStyle("-fx-background-color: #85C1E9;");
        btnArchivoLog.setStyle("-fx-background-color: #5DADE2;");
        btnSalirMenuAdmin.setStyle("-fx-background-color: #3498DB;");


        // Configuramos las acciones de los botones del menú de administrador,
        // asociándolos a las respectivas ventanas o funciones del sistema.
        btnRegistrarCompra.setOnAction(e -> {
            VentanaRegistroCompra ventanaRegistro = new VentanaRegistroCompra();
            ventanaRegistro.mostrar(sistema);
        });
        btnDarDeBaja.setOnAction(e -> {
            VentanaDarDeBaja ventanaDarDeBaja = new VentanaDarDeBaja();
            ventanaDarDeBaja.mostrar(sistema);
        });
        btnConfigurarSeguros.setOnAction(e -> {
            VentanaConfigurarSeguros ventanaConfigurarSeguros = new VentanaConfigurarSeguros();
            ventanaConfigurarSeguros.mostrar(sistema);
        });
        btnArchivoLog.setOnAction(e -> {
            VentanaArchivoLog ventanaArchivoLog = new VentanaArchivoLog();
            VentanaArchivoLog.mostrar(sistema);
        });
        btnSalirMenuAdmin.setOnAction(e -> {
            // Volver a la escena principal
            primaryStage.setScene(escenaPrincipal);
        });

        // Agregamos los nodos al diseño del menú de administrador
        menuAdminLayout.getChildren().addAll(
                labelMenuAdministrador, btnRegistrarCompra, btnDarDeBaja,
                btnConfigurarSeguros, btnArchivoLog, btnSalirMenuAdmin
        );

        // Centramos el VBox en la escena
        menuAdminLayout.setAlignment(Pos.CENTER);

        // Creamos la nueva escena del menú de administrador
        Scene menuAdministradorScene = new Scene(menuAdminLayout, 400, 300);

        // Establecemos la nueva escena
        primaryStage.setScene(menuAdministradorScene);
    }


    private static boolean autenticarAdmin(String nombreUsuario, String contrasena) {
    	if (nombreUsuario.equals("grupo9") && contrasena.equals("123")) 
    	{
            return true;
            }
		return false;
    	
            
    }

    private static void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public static class VentanaRegistroCompra {

        public void mostrar(SistemaAlquiler sistema) {
            Stage stage = new Stage();
            stage.setTitle("Registrar Compra de Vehículos");

            // Crear elementos de la interfaz
            Label labelPlaca = new Label("Placa:");
            TextField txtPlaca = new TextField();

            Label labelMarca = new Label("Marca:");
            TextField txtMarca = new TextField();

            Label labelModelo = new Label("Modelo:");
            TextField txtModelo = new TextField();

            Label labelColor = new Label("Color:");
            TextField txtColor = new TextField();

            Label labelTransmision = new Label("Transmisión:");
            TextField txtTransmision = new TextField();

            Label labelCategoria = new Label("Categoría:");
            TextField txtCategoria = new TextField();

            Label labelTipoVehiculo = new Label("Tipo de Vehículo:");
            TextField txtTipoVehiculo = new TextField(); // Agregar el campo para el tipo de vehículo

            Button btnAgregar = new Button("Agregar");
            Button btnRegresar = new Button("Regresar");

            // Evento para el botón Agregar
            btnAgregar.setOnAction(e -> {
                String placa = txtPlaca.getText();
                String marca = txtMarca.getText();
                String modelo = txtModelo.getText();
                String color = txtColor.getText();
                String transmision = txtTransmision.getText();
                String categoria = txtCategoria.getText();
                String tipoVehiculo = txtTipoVehiculo.getText(); // Obtener el tipo de vehículo

                sistema.agregarVehiculo(placa, marca, modelo, color, transmision, categoria, null, null, null, null, null, tipoVehiculo);

                mostrarMensajeExito("El vehículo se añadió con éxito");

                // Cerrar la ventana
                stage.close();
            });

            // Evento para el botón Regresar
            btnRegresar.setOnAction(e -> stage.close());

            // Diseño de la interfaz
            GridPane layout = new GridPane();
            layout.setVgap(10);
            layout.setHgap(10);
            layout.setPadding(new Insets(10));

            layout.addRow(0, labelPlaca, txtPlaca);
            layout.addRow(1, labelMarca, txtMarca);
            layout.addRow(2, labelModelo, txtModelo);
            layout.addRow(3, labelColor, txtColor);
            layout.addRow(4, labelTransmision, txtTransmision);
            layout.addRow(5, labelCategoria, txtCategoria);
            layout.addRow(6, labelTipoVehiculo, txtTipoVehiculo); // Nueva fila para el tipo de vehículo
            layout.addRow(7, btnAgregar, btnRegresar);

            // Crear la escena
            Scene scene = new Scene(layout, 400, 350); // Ajustar el alto de la ventana

            stage.setScene(scene);

            // Mostrar la ventana
            stage.show();
        }
    }
    
    public static class VentanaDarDeBaja {

        public void mostrar(SistemaAlquiler sistema) {
            Stage stage = new Stage();
            stage.setTitle("Dar de Baja Vehículo");

            // Crear elementos de la interfaz
            Label labelPlacaEliminar = new Label("Placa del vehículo a dar de baja:");
            TextField txtPlacaEliminar = new TextField();

            Button btnDarDeBaja = new Button("Dar de baja");
            Button btnRegresar = new Button("Regresar");

            // Evento para el botón Dar de Baja
            btnDarDeBaja.setOnAction(e -> {
                String placaEliminar = txtPlacaEliminar.getText();
                boolean vehiculoEncontrado = sistema.eliminarAuto(placaEliminar);

                if (vehiculoEncontrado) {
                    mostrarMensajeExito("Vehículo dado de baja con éxito");
                } else {
                    mostrarMensajeError("No se encontró el vehículo");
                }

                // Cerrar la ventana
                stage.close();
            });

            // Evento para el botón Regresar
            btnRegresar.setOnAction(e -> stage.close());

            // Diseño de la interfaz
            GridPane layout = new GridPane();
            layout.setVgap(10);
            layout.setHgap(10);
            layout.setPadding(new Insets(10));

            // Alinear elementos en las columnas correspondientes
            layout.add(labelPlacaEliminar, 0, 0, 2, 1); // Columna 0, Fila 0, Ancho 2 columnas
            layout.add(txtPlacaEliminar, 0, 1, 2, 1); // Columna 0, Fila 1, Ancho 2 columnas
            layout.add(btnRegresar, 0, 2); // Columna 0, Fila 2
            layout.add(btnDarDeBaja, 1, 2); // Columna 1, Fila 2

            // Crear la escena
            Scene scene = new Scene(layout, 400, 200);
            stage.setScene(scene);

            // Mostrar la ventana
            stage.show();
        }
    }
    
    static public class VentanaConfigurarSeguros {

    	private ObservableList<String> listaSeguros; // Lista observable para los seguros

        public void mostrar(SistemaAlquiler sistema) {
            // Crear un diálogo para configurar seguros
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Configurar Seguros");
            dialog.setHeaderText(null);

            // Crear los elementos de la interfaz
            Label labelOpciones = new Label("Seleccione una opción:");

            // Obtener el mapa de seguros del sistema
            Map<String, String> segurosMap = sistema.obtenerSeguros();

            // Crear la lista observable de seguros para mostrar en la ventana
            listaSeguros = FXCollections.observableArrayList();
            listaSeguros.addAll(segurosMap.keySet());

            // Crear el ListView para mostrar la lista de seguros
            ListView<String> listViewSeguros = new ListView<>(listaSeguros);
            listViewSeguros.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            ButtonType btnVerSeguros = new ButtonType("Ver detalles del seguro");
            ButtonType btnAgregarSeguro = new ButtonType("Agregar nuevo seguro");
            ButtonType btnEliminarSeguro = new ButtonType("Eliminar seguro");
            ButtonType btnRegresar = new ButtonType("Regresar");

            // Agregar opciones al diálogo
            dialog.getDialogPane().getButtonTypes().addAll(btnVerSeguros, btnAgregarSeguro, btnEliminarSeguro, btnRegresar);

            // Crear el diseño del diálogo
            GridPane layout = new GridPane();
            layout.setVgap(10);
            layout.setHgap(10);

            layout.addRow(0, labelOpciones);
            layout.addRow(1, listViewSeguros); // Agregar el ListView al diseño

            // Establecer el diseño en el diálogo
            dialog.getDialogPane().setContent(layout);

            // Eventos para las opciones
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnVerSeguros) {
                    verSegurosActuales(sistema, listViewSeguros);
                } else if (dialogButton == btnAgregarSeguro) {
                    agregarNuevoSeguro(sistema);
                } else if (dialogButton == btnEliminarSeguro) {
                    eliminarSeguro(listViewSeguros, sistema);
                } else if (dialogButton == btnRegresar) {
                    regresar(dialog);
                }
                return null;
            });

            // Manejar el evento de cierre de la ventana
            dialog.setOnCloseRequest(event -> regresar(dialog));

            // Mostrar el diálogo
            dialog.showAndWait();
        }


        private void verSegurosActuales(SistemaAlquiler sistema, ListView<String> listViewSeguros) {
            // Obtener el seguro seleccionado
            String selectedSeguro = listViewSeguros.getSelectionModel().getSelectedItem();

            if (selectedSeguro != null) {
                // Obtener la información del seguro seleccionado del sistema
                String precioSeguro = sistema.obtenerSeguros().get(selectedSeguro);

                // Mostrar información sobre el seguro seleccionado
                mostrarMensajeExito("Seguro: " + selectedSeguro + "\nPrecio: $" + precioSeguro);
            } else {
                // Si no se selecciona ningún seguro, mostrar un mensaje de error
                mostrarMensajeError("Seleccione un seguro para ver detalles.");
            }
        }

        private void agregarNuevoSeguro(SistemaAlquiler sistema) {
            // Crear una nueva ventana para agregar seguro
            Stage stage = new Stage();
            stage.setTitle("Agregar Nuevo Seguro");

            // Crear elementos de la interfaz
            Label labelNombreSeguro = new Label("Nombre del Seguro:");
            TextField txtNombreSeguro = new TextField();

            Label labelPrecioSeguro = new Label("Precio del Seguro:");
            TextField txtPrecioSeguro = new TextField();

            Button btnAceptar = new Button("Aceptar");
            Button btnCancelar = new Button("Cancelar");

            // Evento para el botón Aceptar
            btnAceptar.setOnAction(e -> {
                String nombreSeguro = txtNombreSeguro.getText();
                String precioSeguro = txtPrecioSeguro.getText();

                // Validar que ambos campos estén llenos
                if (!nombreSeguro.isEmpty() && !precioSeguro.isEmpty()) {
                    // Llamar a la función agregarSeguro del sistema
                    // (Asegúrate de tener esta función en tu clase SistemaAlquiler)
                    sistema.agregarSeguro(nombreSeguro, precioSeguro, null);

                    // Actualizar la lista observable y, por lo tanto, la ListView
                    listaSeguros.add(nombreSeguro);

                    // Cerrar la ventana
                    stage.close();

                    // Mostrar mensaje de éxito
                    mostrarMensajeExito("Seguro agregado con éxito.");
                } else {
                    // Mostrar mensaje de error si algún campo está vacío
                    mostrarMensajeError("Por favor, complete ambos campos.");
                }
            });

            // Evento para el botón Cancelar
            btnCancelar.setOnAction(e -> stage.close());

            // Diseño de la interfaz
            GridPane layout = new GridPane();
            layout.setVgap(10);
            layout.setHgap(10);
            layout.setPadding(new Insets(10));

            layout.addRow(0, labelNombreSeguro, txtNombreSeguro);
            layout.addRow(1, labelPrecioSeguro, txtPrecioSeguro);
            layout.addRow(2, btnAceptar, btnCancelar);

            // Crear la escena
            Scene scene = new Scene(layout, 300, 200);
            stage.setScene(scene);

            // Mostrar la ventana
            stage.show();
        }



        private void eliminarSeguro(ListView<String> listViewSeguros,SistemaAlquiler sistema) {
            // Obtener el nombre del seguro seleccionado en el ListView
            String seguroSeleccionado = listViewSeguros.getSelectionModel().getSelectedItem();

            // Verificar que se haya seleccionado un seguro
            if (seguroSeleccionado != null) {
                // Llamar a la función eliminarSeguro del sistema
                // (Asegúrate de tener esta función en tu clase SistemaAlquiler)
                sistema.eliminarSeguro(seguroSeleccionado);

                // Actualizar la lista observable y, por lo tanto, la ListView
                listaSeguros.remove(seguroSeleccionado);

                // Mostrar mensaje de éxito
                mostrarMensajeExito("Seguro eliminado con éxito.");
            } else {
                // Mostrar mensaje de error si no se seleccionó ningún seguro
                mostrarMensajeError("Por favor, seleccione un seguro.");
            }
        }

        private void regresar(Dialog<Pair<String, String>> dialog) {
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.close();
        }
    }
    
    
    
    private static void mostrarMensajeExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public static class VentanaArchivoLog {

        public static void mostrar(SistemaAlquiler sistema) {
            Stage stage = new Stage();
            stage.setTitle("Archivo Log de Vehículo");

            // Crear elementos de la interfaz
            Label labelPlaca = new Label("Placa del vehículo:");
            TextField txtPlaca = new TextField();

            Button btnGenerar = new Button("Generar");
            Button btnRegresar = new Button("Regresar");

            // Evento para el botón Generar
            btnGenerar.setOnAction(e -> {
                String placa = txtPlaca.getText();

                // Llamar a la función buscarEventosPorPlaca del sistema
                // (Asegúrate de tener esta función en tu clase SistemaAlquiler)
                List<String> eventos = sistema.buscarEventosPorPlaca(placa);

                if (eventos.isEmpty()) {
                    // Mostrar ventana de que no hay eventos
                    mostrarMensajeExito("No hay eventos para la placa ingresada.");
                } else {
                    // Mostrar ventana con eventos
                    mostrarEventos(placa, eventos);
                }
            });

            // Evento para el botón Regresar
            btnRegresar.setOnAction(e -> stage.close());

            // Diseño de la interfaz
            GridPane layout = new GridPane();
            layout.setVgap(10);
            layout.setHgap(10);
            layout.setPadding(new Insets(10));

            layout.addRow(0, labelPlaca, txtPlaca);
            layout.addRow(1, btnGenerar, btnRegresar);

            // Crear la escena
            Scene scene = new Scene(layout, 300, 200);
            stage.setScene(scene);

            // Mostrar la ventana
            stage.show();
        }

        private static void mostrarEventos(String placa, List<String> eventos) {
            // Crear una nueva ventana para mostrar los eventos
            Stage stage = new Stage();
            stage.setTitle("Eventos para la Placa: " + placa);

            // Crear elementos de la interfaz
            Label labelTitulo = new Label("Eventos para la Placa: " + placa);
            ListView<String> listViewEventos = new ListView<>(FXCollections.observableArrayList(eventos));
            Button btnCerrar = new Button("Cerrar");

            // Evento para el botón Cerrar
            btnCerrar.setOnAction(e -> stage.close());

            // Diseño de la interfaz
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10));
            layout.getChildren().addAll(labelTitulo, listViewEventos, btnCerrar);

            // Crear la escena
            Scene scene = new Scene(layout, 400, 300);
            stage.setScene(scene);

            // Mostrar la ventana
            stage.show();
        }
    }
}
