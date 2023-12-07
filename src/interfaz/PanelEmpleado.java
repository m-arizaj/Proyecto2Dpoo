package interfaz;



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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PanelEmpleado 
{
	private static Scene menuPrincipal;
	private static Scene escenaInicial;
	private static Scene idAlquiler;
	private static Scene conductores;
	private static int contador = 0;

    

    public static void start(Stage primaryStage, Scene escenaPrincipal, SistemaAlquiler sistema) {
        primaryStage.setTitle("Sistema de Alquiler de Vehículos");

        // Crear el título
        Text titulo = new Text("Iniciar sesión como empleado");
        titulo.setFont(Font.font("Arial",FontWeight.BOLD, 20));

        // Crear elementos de la interfaz
        Label usuarioLabel = new Label("Nombre de Usuario:");
        TextField usuarioTextField = new TextField();
        
        Label contrasenaLabel = new Label("Contraseña:");
        PasswordField contrasenaField = new PasswordField();

        Button ingresarButton = new Button("Ingresar");
        
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setOnAction(e -> primaryStage.setScene(escenaPrincipal));

        // Crear el diseño de la interfaz
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(titulo, usuarioLabel, usuarioTextField, contrasenaLabel, 
        		contrasenaField, ingresarButton, btnRegresar);
        ingresarButton.setStyle("-fx-background-color: #3498DB;");
        btnRegresar.setStyle("-fx-background-color: #5DADE2;");
        root.setStyle("-fx-background-color: beige;");

        // Configurar el evento para el botón "Ingresar"
        ingresarButton.setOnAction(event -> {
            String usuario = usuarioTextField.getText();
            String contrasena = contrasenaField.getText();

            // Aquí puedes agregar la lógica para verificar el usuario y contraseña en tu CSV
            boolean usuarioValido = Persistencia.verificarUsuario(usuario, contrasena);

            if (usuarioValido) {
                primaryStage.close();
                mostrarVentanaBienvenida(primaryStage, usuario);
            } else {
                mostrarMensajeError("Error de autenticación", "Usuario o contraseña incorrectos");
            }
        });

        // Configurar la escena y mostrar la ventana
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        escenaInicial = escenaPrincipal;
        
        primaryStage.show();
    }
    
    

    private static void mostrarVentanaBienvenida(Stage primaryStage, String usuario) {
        // Crear la nueva ventana de bienvenida
    	
        Label bienvenidaLabel = new Label("Bienvenido " + usuario);
        bienvenidaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label opcionesLabel = new Label("¿Qué desea hacer?");

        // Crear botones para las opciones
        Button opcion1Button = new Button("Completar reserva alquiler");
        Button opcion2Button = new Button("Actualizar estado vehículo");
        Button opcion3Button = new Button("Reportar mantenimiento de vehículo y fecha de disponibilidad");
        Button regresarButton = new Button("Regresar");

        opcion1Button.setOnAction(e -> {
            mostrarVentanaCompletarReservaAlquiler(primaryStage);
        });

        opcion2Button.setOnAction(e -> {
            mostrarVentanaActualizarEstadoVehiculo(primaryStage, usuario);
        });

        opcion3Button.setOnAction(e -> {
            mostrarVentanaReportarMantenimiento(primaryStage, usuario);
        });

        regresarButton.setOnAction(e -> primaryStage.setScene(escenaInicial));

        // Crear el diseño de la nueva ventana
        VBox bienvenidaRoot = new VBox(10);
        bienvenidaRoot.setPadding(new Insets(20));
        bienvenidaRoot.getChildren().addAll(bienvenidaLabel, opcionesLabel,
                opcion1Button, opcion2Button, opcion3Button, regresarButton);
        
        opcion1Button.setStyle("-fx-background-color: #3498DB;"); 
        opcion2Button.setStyle("-fx-background-color: #5DADE2;"); 
        opcion3Button.setStyle("-fx-background-color: #85C1E9;");
        regresarButton.setStyle("-fx-background-color: #3498DB;");
        bienvenidaRoot.setStyle("-fx-background-color: beige;");
        

        // Configurar la escena y mostrar la nueva ventana
        Scene bienvenidaScene = new Scene(bienvenidaRoot, 600, 400);
        primaryStage.setScene(bienvenidaScene);
        menuPrincipal = bienvenidaScene;
        primaryStage.show();
    }

    private static void mostrarMensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
    
    private static void mostrarVentanaActualizarEstadoVehiculo(Stage primaryStage, String usuario) {
    	
        Stage ventanaActualizarEstado = new Stage();
        ventanaActualizarEstado.setTitle("Sistema de Alquiler de Vehículos Empleado - BIENVENIDO " + usuario);

        Label actualizarEstadoLabel = new Label("ACTUALIZAR ESTADO DE UN VEHÍCULO");
        Label placaLabel = new Label("Indique la placa del carro que desea consultar:");
        actualizarEstadoLabel.setFont(Font.font("Arial",FontWeight.BOLD, 20));
        TextField placaTextField = new TextField();
        Button aceptarButton = new Button("Aceptar");

        // Configurar evento para el botón "Aceptar"
        aceptarButton.setOnAction(event -> {
            String placa = placaTextField.getText();
            String estadoActual = null;
			try {
				estadoActual = Persistencia.obtenerEstadoVehiculo(placa);
			} catch (CsvValidationException e) {
				e.printStackTrace();
			}

            if (estadoActual == null) {
                mostrarMensajeError("Error", "No se encontró el vehículo con la placa especificada.");
            } else {
            	ventanaActualizarEstado.close();
                mostrarVentanaConfirmacionEstado(ventanaActualizarEstado, usuario, placa, estadoActual);
                
            }
            
        });

        // Crear diseño para la ventana de actualizar estado
        VBox actualizarEstadoRoot = new VBox(10);
        actualizarEstadoRoot.setPadding(new Insets(20));
        actualizarEstadoRoot.getChildren().addAll(
                actualizarEstadoLabel, placaLabel, placaTextField, aceptarButton);
        
        aceptarButton.setStyle("-fx-background-color: #3498DB;"); 

        Scene actualizarEstadoScene = new Scene(actualizarEstadoRoot, 400, 200);
        ventanaActualizarEstado.setScene(actualizarEstadoScene);
        ventanaActualizarEstado.show();
    }
    

    private static void mostrarVentanaConfirmacionEstado(Stage ventanaPadre, String usuario, String placa, String estadoActual) 
    {
        Stage ventanaConfirmacionEstado = new Stage();
        ventanaConfirmacionEstado.setTitle("Sistema de Alquiler de Vehículos - BIENVENIDO " + usuario);

        Label actualizarEstadoLabel = new Label("ACTUALIZAR ESTADO DE UN VEHÍCULO");
        actualizarEstadoLabel.setFont(Font.font("Arial",FontWeight.BOLD, 20));
        Label estadoActualLabel = new Label("El vehículo de placa " + placa + " se encuentra en el siguiente estado:");
        Label estadoActualValueLabel = new Label(estadoActual);
        Label actualizarLabel = new Label("¿Desea actualizar el estado a Disponible o Alquilado?");
        Button disponibleButton = new Button("Disponible");
        Button alquiladoButton = new Button("Alquilado");

        // Configurar eventos para los botones de actualización
        disponibleButton.setOnAction(event -> 
        {
            try 
            {
				Persistencia.actualizarEstadoVehiculo(placa, "Disponible");
			} catch (CsvException e) 
            {
				e.printStackTrace();
			}
            mostrarAnuncioConfirmacion("Ahora el vehículo se encuentra en Disponible");
            ventanaConfirmacionEstado.close();
            ventanaPadre.close();
            mostrarVentanaBienvenida(ventanaConfirmacionEstado, estadoActual);
        });

        alquiladoButton.setOnAction(event -> {
            try {
				Persistencia.actualizarEstadoVehiculo(placa, "Alquilado");
			} catch (CsvException e) {
				e.printStackTrace();
			}
            mostrarAnuncioConfirmacion("Ahora el vehículo se encuentra en Alquilado");
            ventanaConfirmacionEstado.close();
            ventanaPadre.close();
            mostrarVentanaBienvenida(ventanaConfirmacionEstado, estadoActual);
        });

        // Crear diseño para la ventana de confirmación de estado
        VBox confirmacionEstadoRoot = new VBox(10);
        confirmacionEstadoRoot.setPadding(new Insets(20));
        confirmacionEstadoRoot.getChildren().addAll(
                actualizarEstadoLabel, estadoActualLabel, estadoActualValueLabel,
                actualizarLabel, disponibleButton, alquiladoButton);
        
        disponibleButton.setStyle("-fx-background-color: #3498DB;"); 
        alquiladoButton.setStyle("-fx-background-color: #5DADE2;"); 

        Scene confirmacionEstadoScene = new Scene(confirmacionEstadoRoot, 400, 300);
        ventanaConfirmacionEstado.setScene(confirmacionEstadoScene);
        ventanaConfirmacionEstado.show();
    }
    

    private static void mostrarAnuncioConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
    
    private static void mostrarVentanaReportarMantenimiento(Stage primaryStage, String usuario) {
        Stage ventanaReportarMantenimiento = new Stage();
        ventanaReportarMantenimiento.setTitle("Sistema de Alquiler de Vehículos - BIENVENIDO " + usuario);

        Label reportarMantenimientoLabel = new Label("REPORTAR FECHA DE MANTENIMIENTO Y DISPONIBILIDAD");
        reportarMantenimientoLabel.setFont(Font.font("Arial",FontWeight.BOLD, 20));
        Label placaLabel = new Label("Indique la placa del carro que desea consultar:");
        TextField placaTextField = new TextField();
        Button aceptarButton = new Button("Aceptar");

        // Configurar evento para el botón "Aceptar"
        aceptarButton.setOnAction(e -> {
            String placa = placaTextField.getText();
            String estadoActual = null;
			try {
				estadoActual = Persistencia.obtenerEstadoVehiculo(placa);
			} catch (CsvValidationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

            if (estadoActual == null) {
                mostrarMensajeError("Error", "No se encontró el vehículo con la placa especificada.");
            } else {
            	ventanaReportarMantenimiento.close();
                mostrarVentanaConfirmacionMantenimiento(ventanaReportarMantenimiento, usuario, placa, estadoActual);
            }
        });

        // Crear diseño para la ventana de reportar mantenimiento
        VBox reportarMantenimientoRoot = new VBox(10);
        reportarMantenimientoRoot.setPadding(new Insets(20));
        reportarMantenimientoRoot.getChildren().addAll(
                reportarMantenimientoLabel, placaLabel, placaTextField, aceptarButton);
        
        aceptarButton.setStyle("-fx-background-color: #3498DB;");

        Scene reportarMantenimientoScene = new Scene(reportarMantenimientoRoot, 400, 200);
        ventanaReportarMantenimiento.setScene(reportarMantenimientoScene);
        ventanaReportarMantenimiento.show();
    }

    private static void mostrarVentanaConfirmacionMantenimiento(Stage ventanaPadre, String usuario, String placa, String estadoActual) {
        Stage ventanaConfirmacionMantenimiento = new Stage();
        ventanaConfirmacionMantenimiento.setTitle("Sistema de Alquiler de Vehículos - BIENVENIDO " + usuario);

        Label confirmacionMantenimientoLabel = new Label("REPORTAR MANTENIMIENTO DE VEHÍCULO Y FECHA DE DISPONIBILIDAD");
        confirmacionMantenimientoLabel.setFont(Font.font("Arial",FontWeight.BOLD, 20));
        Label estadoActualLabel = new Label("Vehículo de placa " + placa + " en estado: " + estadoActual);
        Label opcionesLabel = new Label("¿Desea registrar el vehículo en mantenimiento o retirarlo?");
        Button retirarButton = new Button("Retirar");
        Button registrarButton = new Button("Registrar");

        // Configurar eventos para los botones de opciones
        retirarButton.setOnAction(event -> {
            try {
				Persistencia.actualizarEstadoVehiculo(placa, "Disponible");
			} catch (CsvException e) {
				e.printStackTrace();
			}
            mostrarAnuncioConfirmacion("Perfecto, ahora el vehículo se encuentra disponible.");
            ventanaConfirmacionMantenimiento.close();
            ventanaPadre.close();
            Stage primaryStage = null;
			mostrarVentanaBienvenida(primaryStage, usuario);
        });

        registrarButton.setOnAction(event -> {
            mostrarVentanaExplicacionProblema(placa);
            ventanaConfirmacionMantenimiento.close();
        });

        // Crear diseño para la ventana de confirmación de mantenimiento
        VBox confirmacionMantenimientoRoot = new VBox(10);
        confirmacionMantenimientoRoot.setPadding(new Insets(20));
        confirmacionMantenimientoRoot.getChildren().addAll(
                confirmacionMantenimientoLabel, estadoActualLabel, opcionesLabel, retirarButton, registrarButton);
        
        retirarButton.setStyle("-fx-background-color: #3498DB;"); 
        registrarButton.setStyle("-fx-background-color: #5DADE2;"); 

        Scene confirmacionMantenimientoScene = new Scene(confirmacionMantenimientoRoot, 400, 300);
        ventanaConfirmacionMantenimiento.setScene(confirmacionMantenimientoScene);
        ventanaConfirmacionMantenimiento.show();
    }

    private static void mostrarVentanaExplicacionProblema(String placa) {
        Stage ventanaExplicacionProblema = new Stage();
        ventanaExplicacionProblema.setTitle("Sistema de Alquiler de Vehículos - Explique el problema");
        
        Label confirmacionMantenimientoLabel = new Label("REPORTAR MANTENIMIENTO DE VEHÍCULO Y FECHA DE DISPONIBILIDAD");
        confirmacionMantenimientoLabel.setFont(Font.font("Arial",FontWeight.BOLD, 20));

        Label explicacionProblemaLabel = new Label("Explique brevemente el problema del vehículo:");
        TextArea explicacionProblemaTextArea = new TextArea();
        Button aceptarButton = new Button("Aceptar");

        aceptarButton.setOnAction(event -> {
            String explicacionProblema = explicacionProblemaTextArea.getText();
            mostrarVentanaFechaReintegro(placa, explicacionProblema);
            ventanaExplicacionProblema.close();
        });

        // Crear diseño para la ventana de explicación del problema
        VBox explicacionProblemaRoot = new VBox(10);
        explicacionProblemaRoot.setPadding(new Insets(20));
        explicacionProblemaRoot.getChildren().addAll(
                explicacionProblemaLabel, explicacionProblemaTextArea, aceptarButton);
        
        aceptarButton.setStyle("-fx-background-color: #3498DB;");

        Scene explicacionProblemaScene = new Scene(explicacionProblemaRoot, 400, 200);
        ventanaExplicacionProblema.setScene(explicacionProblemaScene);
        ventanaExplicacionProblema.show();
    }

    private static void mostrarVentanaFechaReintegro(String placa, String explicacionProblema) {
        Stage ventanaFechaReintegro = new Stage();
        ventanaFechaReintegro.setTitle("Sistema de Alquiler de Vehículos - Fecha de Reintegro");

        Label fechaReintegroLabel = new Label("Según su criterio, ¿cuál será la fecha de reintegro del vehículo? (YYYY-MM-DD):");
        TextField fechaReintegroTextField = new TextField();
        Button aceptarButton = new Button("Aceptar");

        aceptarButton.setOnAction(event -> {
            String fechaReintegro = fechaReintegroTextField.getText();
            try {
				Persistencia.actualizarEstadoYFechaReintegro(placa, "En Mantenimiento", explicacionProblema, fechaReintegro);
			} catch (CsvException e) {
				e.printStackTrace();
			}
            mostrarAnuncioConfirmacion("El vehículo ahora está en mantenimiento");
            ventanaFechaReintegro.close();
            Stage ventanaConfirmacionEstado = null;
			String estadoActual = null;
			mostrarVentanaBienvenida(ventanaConfirmacionEstado, estadoActual);
            
        });
        

        // Crear diseño para la ventana de fecha de reintegro
        VBox fechaReintegroRoot = new VBox(10);
        fechaReintegroRoot.setPadding(new Insets(20));
        fechaReintegroRoot.getChildren().addAll(
                fechaReintegroLabel, fechaReintegroTextField, aceptarButton);

        Scene fechaReintegroScene = new Scene(fechaReintegroRoot, 400, 200);
        ventanaFechaReintegro.setScene(fechaReintegroScene);
        ventanaFechaReintegro.show();
    }


    private static void mostrarVentanaCompletarReservaAlquiler(Stage primaryStage) {
// seguir aqui //
        Label completarReservaLabel = new Label("Completar alquiler de reserva previa");
        completarReservaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label idReservaLabel = new Label("Ingrese ID de la reserva:");
        TextField idReservaTextField = new TextField();
        Button aceptarButton = new Button("Aceptar");
        Button regresarButton = new Button("Regresar");
        
        aceptarButton.setStyle("-fx-background-color: #3498DB;"); 
        regresarButton.setStyle("-fx-background-color: #5DADE2;");

        // Configurar evento para el botón "Aceptar"
        aceptarButton.setOnAction(event -> {
            String idReserva = idReservaTextField.getText();
            String[] reserva = Persistencia.obtenerReservaPorID(idReserva);

            if (reserva == null) {
                mostrarMensajeError("Error", "No se encontró la reserva con el ID especificado.");
            } else {
                mostrarVentanaSegurosDisponibles(primaryStage, reserva);
            }
        });
        
        regresarButton.setOnAction(e -> primaryStage.setScene(menuPrincipal));

        // Crear diseño para la ventana de completar reserva
        VBox completarReservaRoot = new VBox(10);
        completarReservaRoot.setStyle("-fx-background-color: beige;");
        completarReservaRoot.setPadding(new Insets(20));
        completarReservaRoot.getChildren().addAll(
                completarReservaLabel, idReservaLabel, idReservaTextField, aceptarButton, regresarButton);

        Scene completarReservaScene = new Scene(completarReservaRoot, 400, 200);
        idAlquiler = completarReservaScene;
        primaryStage.setScene(completarReservaScene);
        primaryStage.show();
    }



    private static void mostrarVentanaSegurosDisponibles(Stage primaryStage, String[] reserva) {

        Label segurosDisponiblesLabel = new Label("Seguros disponibles");
        segurosDisponiblesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label opcionesLabel = new Label("Escoja una de las siguientes opciones:");

        Button seguroObligatorioButton = new Button("Seguro obligatorio - $29,000 COP");
        Label detallesObli = new Label("Descripción: Con este seguro se puede incurrir en un pago de un\r\n"
        		+ "deducible para cubrir una parte de los perjuicios ocasionados\r\n"
        		+ "en los casos de siniestros (totales o parciales), por responsabilidad civil\r\n"
        		+ "extracontractual, por daños o por hurto del vehículo. Esto incluye también gastos administrativos.");
        Button seguroProteccionTotalButton = new Button("Seguro protección total - $61,086 COP");
        Label detallesProt = new Label("Descripción: Este seguro es una cobertura adicional que se\r\n"
        		+ "puede tomar de acuerdo con los riesgos que se deseen asumir, cubre el 100% de la\r\n"
        		+ "pérdida total o parcial del vehículo y sus partes por daño o hurto, salvo por los casos de\r\n"
        		+ "uso indebido. Al adquirir esta protección, te evitas el pago de la participación obligatoria en caso de siniestro.");
        Button regresarButton = new Button("Regresar");
        regresarButton.setStyle("-fx-background-color: #5DADE2;");
        // Configurar eventos para los botones de opciones de seguro
        seguroObligatorioButton.setOnAction(event -> {
            mostrarVentanaPreguntaConductorAdicional(primaryStage, "Seguro obligatorio", reserva);
        });

        seguroProteccionTotalButton.setOnAction(event -> {
            mostrarVentanaPreguntaConductorAdicional(primaryStage, "Seguro proteccion total", reserva);
        });
        
        regresarButton.setOnAction(e -> primaryStage.setScene(idAlquiler));

        // Crear diseño para la ventana de seguros disponibles
        VBox segurosDisponiblesRoot = new VBox(10);
        segurosDisponiblesRoot.setStyle("-fx-background-color: beige;");
        segurosDisponiblesRoot.setPadding(new Insets(20));
        segurosDisponiblesRoot.getChildren().addAll(
                segurosDisponiblesLabel, opcionesLabel, seguroObligatorioButton, detallesObli, 
                seguroProteccionTotalButton, detallesProt, regresarButton);
        
        seguroObligatorioButton.setStyle("-fx-background-color: #3498DB;");
        seguroProteccionTotalButton.setStyle("-fx-background-color: #5DADE2;");

        Scene segurosDisponiblesScene = new Scene(segurosDisponiblesRoot, 400, 400);
        primaryStage.setScene(segurosDisponiblesScene);
        primaryStage.show();
    }


    private static void mostrarVentanaPreguntaConductorAdicional(Stage primaryStage, String seguro, String[] reserva) {

        Label preguntaConductorAdicionalLabel = new Label("¿Desea agregar algún conductor adicional?");
        preguntaConductorAdicionalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        
        Button siButton = new Button("Sí");
        Button noButton = new Button("No");

        // Configurar eventos para los botones de opciones
        siButton.setOnAction(e -> {
            mostrarVentanaConductorAdicional(primaryStage, reserva);
        });

        noButton.setOnAction(e -> {
        	
            mostrarAnuncioConfirmacion("El alquiler fue completado"); // hacer autenticacion tarjeta con otras ventanas//
            completarAlquiler(reserva,seguro);
            contador = 0;
            primaryStage.setScene(menuPrincipal);

        });

        // Crear diseño para la ventana de pregunta por conductor adicional
        VBox preguntaConductorAdicionalRoot = new VBox(10);
        preguntaConductorAdicionalRoot.setStyle("-fx-background-color: beige;");
        preguntaConductorAdicionalRoot.setPadding(new Insets(20));
        preguntaConductorAdicionalRoot.getChildren().addAll(
                preguntaConductorAdicionalLabel, siButton, noButton);
        
        siButton.setStyle("-fx-background-color: #3498DB;");
        noButton.setStyle("-fx-background-color: #5DADE2;");

        Scene preguntaConductorAdicionalScene = new Scene(preguntaConductorAdicionalRoot, 400, 200);
        conductores = preguntaConductorAdicionalScene;
        primaryStage.setScene(preguntaConductorAdicionalScene);
        primaryStage.show();
    }

    private static void mostrarVentanaConductorAdicional(Stage primaryStage, String[] Reserva) {

        Label conductorAdicionalLabel = new Label("Conductor adicional");
        conductorAdicionalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label nombreLabel = new Label("Nombre del conductor:");
        TextField nombreTextField = new TextField();
        Label fechaNacimientoLabel = new Label("Fecha de nacimiento:");
        DatePicker fechaNacimientoDatePicker = new DatePicker();
        Label nacionalidadLabel = new Label("Nacionalidad:");
        TextField nacionalidadTextField = new TextField();
        Label numeroLicenciaLabel = new Label("Número de licencia:");
        TextField numeroLicenciaTextField = new TextField();
        Label paisExpedicionLicenciaLabel = new Label("País de expedición de licencia:");
        TextField paisExpedicionLicenciaTextField = new TextField();
        Label fechaVencimientoLicenciaLabel = new Label("Fecha de vencimiento de licencia:");
        DatePicker fechaVencimientoLicenciaDatePicker = new DatePicker();
        Button aceptarButton = new Button("Aceptar");

        // Configurar evento para el botón "Aceptar"
        aceptarButton.setOnAction(event -> {
            String nombre = nombreTextField.getText();
            String fechaNacimiento = fechaNacimientoDatePicker.getValue().toString();
            String nacionalidad = nacionalidadTextField.getText();
            String numeroLicencia = numeroLicenciaTextField.getText();
            String paisExpedicionLicencia = paisExpedicionLicenciaTextField.getText();
            String fechaVencimientoLicencia = fechaVencimientoLicenciaDatePicker.getValue().toString();

            ConductorAdicional.conductoresAgregados(Reserva[0],Reserva[2], nombre, fechaNacimiento, nacionalidad, numeroLicencia,
            		paisExpedicionLicencia,fechaVencimientoLicencia);
            contador++;

            primaryStage.setScene(conductores);
        });

        // Crear diseño para la ventana de conductor adicional
        GridPane conductorAdicionalRoot = new GridPane();
        conductorAdicionalRoot.setStyle("-fx-background-color: beige;");
        conductorAdicionalRoot.setPadding(new Insets(20));
        conductorAdicionalRoot.setVgap(10);
        conductorAdicionalRoot.setHgap(10);
        conductorAdicionalRoot.add(conductorAdicionalLabel, 0, 0, 2, 1);
        conductorAdicionalRoot.add(nombreLabel, 0, 1);
        conductorAdicionalRoot.add(nombreTextField, 1, 1);
        conductorAdicionalRoot.add(fechaNacimientoLabel, 0, 2);
        conductorAdicionalRoot.add(fechaNacimientoDatePicker, 1, 2);
        conductorAdicionalRoot.add(nacionalidadLabel, 0, 3);
        conductorAdicionalRoot.add(nacionalidadTextField, 1, 3);
        conductorAdicionalRoot.add(numeroLicenciaLabel, 0, 4);
        conductorAdicionalRoot.add(numeroLicenciaTextField, 1, 4);
        conductorAdicionalRoot.add(paisExpedicionLicenciaLabel, 0, 5);
        conductorAdicionalRoot.add(paisExpedicionLicenciaTextField, 1, 5);
        conductorAdicionalRoot.add(fechaVencimientoLicenciaLabel, 0, 6);
        conductorAdicionalRoot.add(fechaVencimientoLicenciaDatePicker, 1, 6);
        conductorAdicionalRoot.add(aceptarButton, 1, 7);

        Scene conductorAdicionalScene = new Scene(conductorAdicionalRoot, 400, 400);
        primaryStage.setScene(conductorAdicionalScene);
        primaryStage.show();
        aceptarButton.setStyle("-fx-background-color: #3498DB;");
    }



	
	public static void completarAlquiler(String[] fila, String seguro_escogido) {

            int indiceId = 0;
            int indiceCategoria = 1;
            int indiceUsuario = 2;
            int indiceSedeRecogida = 3;
            int indiceSedeEntrega = 4;
            int indiceInicio = 5;
            int indiceFinal = 6;
            int indiceDias = 7;
            int indiceCostoTotal = 8;
            int indiceTreinta = 9;


            
            		String id = fila[indiceId];
                	String categoria = fila[indiceCategoria];
                	String usuario = fila[indiceUsuario];
                	String recogida = fila[indiceSedeRecogida];
                	String entrega = fila[indiceSedeEntrega];
                	String inicio = fila[indiceInicio];
                	String ffinal = fila[indiceFinal];
                	String dias = fila[indiceDias];
                	String costo = fila[indiceCostoTotal];
                	String treinta = fila[indiceTreinta];


                	List<String> escoger =Vehiculo.escogerCarro(categoria, inicio, ffinal);

               	 	String categoriaEsc = escoger.get(0);
               	 	String placa = escoger.get(1);


                    List<String> seguro= escogerSeguro(seguro_escogido);
                    int numeroAdicionales= contador;
                  
                    double valorTotal = ((Double.parseDouble(costo) - Double.parseDouble(treinta)) + (Double.parseDouble(seguro.get(1)) * Double.parseDouble(dias)));

                    String rutaCompleta = "datos/alquileres.csv";
                    boolean archivoExiste = new File(rutaCompleta).exists();

                    List<String> nuevaFila = new ArrayList<>();

                    nuevaFila.add(id);
                    nuevaFila.add(usuario);
                    nuevaFila.add(categoriaEsc);
                    nuevaFila.add(placa);
                    nuevaFila.add(recogida);
                    nuevaFila.add(entrega);
                    nuevaFila.add(inicio);
                    nuevaFila.add(ffinal);
                    nuevaFila.add(dias);
                    nuevaFila.add(Double.toString(valorTotal));
                    nuevaFila.add(Integer.toString(numeroAdicionales));
                    nuevaFila.add(seguro.get(0));


                    try (CSVWriter writer = new CSVWriter(new FileWriter(rutaCompleta, true))) {
                        if (archivoExiste==false) {
                            String[] encabezados = {"Id alquiler", "Usuario alquiler", "Categoria vehiculo",
                           		 "Placa", "Sede de recogida", "Sede de entrega", "Fecha de inicio", 
                           		 "Fecha finalizacion", "Dias en total", "Costo del excedente mas el seguro",
                           		 "Conductores adicionales","Seguro seleccionado"};
                            writer.writeNext(encabezados);
                        }
                        writer.writeNext(nuevaFila.toArray(new String[0]));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            
	}

	public static List<String> escogerSeguro (String seguro_escogido){
		try (CSVReader reader = new CSVReader(new FileReader("datos/seguros.csv"))) {
    		List<List<String>> listaDeListas = new ArrayList<>();
            boolean primeraLinea = true;

            while (true) {
                String[] linea = reader.readNext();
                if (linea == null) {
                    break; // Fin del archivo
                }

                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                List<String> lista = new ArrayList<>();
                String nombre = linea[0];
                String valor = linea[1];
                String descripcion = linea[2];


                lista.add(nombre);
                lista.add(valor);
                lista.add(descripcion);

                listaDeListas.add(lista);
            }
                if (seguro_escogido.equals("Seguro obligatorio")) {
                    return listaDeListas.get(0);

                } else {
                	return listaDeListas.get(1);
                }
            }

         catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return null;



	}





   


}