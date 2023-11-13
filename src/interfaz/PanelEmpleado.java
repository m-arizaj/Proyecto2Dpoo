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

public class PanelEmpleado extends Application
{
	private Scene escenaPrincipal;

    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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

        // Crear el diseño de la interfaz
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(titulo, usuarioLabel, usuarioTextField, contrasenaLabel, contrasenaField, ingresarButton);
        ingresarButton.setStyle("-fx-background-color: #3498DB;");

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

        escenaPrincipal = scene;
        primaryStage.show();
    }
    
    

    private void mostrarVentanaBienvenida(Stage primaryStage, String usuario) {
        // Crear la nueva ventana de bienvenida
        Stage ventanaBienvenida = new Stage();
        ventanaBienvenida.setTitle("Sistema de Alquiler de Vehículos Empleado - BIENVENIDO " + usuario);

        Label bienvenidaLabel = new Label("BIENVENIDO " + usuario);
        Label opcionesLabel = new Label("¿Qué desea hacer?");

        // Crear botones para las opciones
        Button opcion1Button = new Button("Completar reserva alquiler");
        Button opcion2Button = new Button("Actualizar estado vehículo");
        Button opcion3Button = new Button("Reportar mantenimiento de vehículo y fecha de disponibilidad");
        Button salirButton = new Button("Salir");

        opcion1Button.setOnAction(e -> {
            mostrarVentanaCompletarReservaAlquiler(ventanaBienvenida, usuario);
            ventanaBienvenida.close();  // Cerrar la ventana actual
        });

        opcion2Button.setOnAction(e -> {
            mostrarVentanaActualizarEstadoVehiculo(ventanaBienvenida, usuario);
            ventanaBienvenida.close();  // Cerrar la ventana actual
        });

        opcion3Button.setOnAction(e -> {
            mostrarVentanaReportarMantenimiento(ventanaBienvenida, usuario);
            ventanaBienvenida.close();  // Cerrar la ventana actual
        });

        salirButton.setOnAction(event -> ventanaBienvenida.close());

        // Crear el diseño de la nueva ventana
        VBox bienvenidaRoot = new VBox(10);
        bienvenidaRoot.setPadding(new Insets(20));
        bienvenidaRoot.getChildren().addAll(bienvenidaLabel, opcionesLabel,
                opcion1Button, opcion2Button, opcion3Button, salirButton);
        
        opcion1Button.setStyle("-fx-background-color: #3498DB;"); 
        opcion2Button.setStyle("-fx-background-color: #5DADE2;"); 
        opcion3Button.setStyle("-fx-background-color: #85C1E9;");
        salirButton.setStyle("-fx-background-color: #3498DB;"); 

        // Configurar la escena y mostrar la nueva ventana
        Scene bienvenidaScene = new Scene(bienvenidaRoot, 600, 400);
        ventanaBienvenida.setScene(bienvenidaScene);
        ventanaBienvenida.show();
    }

    private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
    
    private void mostrarVentanaActualizarEstadoVehiculo(Stage primaryStage, String usuario) {
    	
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
    

    private void mostrarVentanaConfirmacionEstado(Stage ventanaPadre, String usuario, String placa, String estadoActual) 
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
    

    private void mostrarAnuncioConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
    
    private void mostrarVentanaReportarMantenimiento(Stage primaryStage, String usuario) {
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

    private void mostrarVentanaConfirmacionMantenimiento(Stage ventanaPadre, String usuario, String placa, String estadoActual) {
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

    private void mostrarVentanaExplicacionProblema(String placa) {
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

    private void mostrarVentanaFechaReintegro(String placa, String explicacionProblema) {
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


    private void mostrarVentanaCompletarReservaAlquiler(Stage primaryStage, String usuario) {
        Stage ventanaCompletarReserva = new Stage();
        ventanaCompletarReserva.setTitle("Sistema de Alquiler de Vehículos - BIENVENIDO " + usuario);

        Label completarReservaLabel = new Label("COMPLETAR ALQUILER DE RESERVA PREVIA");
        Label idReservaLabel = new Label("Ingrese ID de la reserva:");
        TextField idReservaTextField = new TextField();
        Button aceptarButton = new Button("Aceptar");

        // Configurar evento para el botón "Aceptar"
        aceptarButton.setOnAction(event -> {
            String idReserva = idReservaTextField.getText();
            String[] reserva = obtenerReservaPorID(idReserva);

            if (reserva == null) {
                mostrarMensajeError("Error", "No se encontró la reserva con el ID especificado.");
            } else {
                mostrarVentanaSegurosDisponibles(ventanaCompletarReserva, usuario, reserva);
            }
        });

        // Crear diseño para la ventana de completar reserva
        VBox completarReservaRoot = new VBox(10);
        completarReservaRoot.setPadding(new Insets(20));
        completarReservaRoot.getChildren().addAll(
                completarReservaLabel, idReservaLabel, idReservaTextField, aceptarButton);

        Scene completarReservaScene = new Scene(completarReservaRoot, 400, 200);
        ventanaCompletarReserva.setScene(completarReservaScene);
        ventanaCompletarReserva.show();
    }

    private String[] obtenerReservaPorID(String idReserva) {
        try (CSVReader reader = new CSVReader(new FileReader("./datos/reservas.csv"))) {
            String[] linea;
            while ((linea = reader.readNext()) != null) {
                if (linea.length > 0 && idReserva.equals(linea[0])) {
                    return linea; // Devuelve la fila de la reserva
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace(); // Manejo básico de errores, ajusta según tus necesidades
        }
        return null; // No se encontró la reserva con el ID especificado
    }

    private void mostrarVentanaSegurosDisponibles(Stage ventanaPadre, String usuario, String[] reserva) {
        Stage ventanaSegurosDisponibles = new Stage();
        ventanaSegurosDisponibles.setTitle("Sistema de Alquiler de Vehículos - BIENVENIDO " + usuario);

        Label segurosDisponiblesLabel = new Label("SEGUROS DISPONIBLES");
        Label opcionesLabel = new Label("Escoja una de las siguientes opciones:");

        Button seguroObligatorioButton = new Button("Seguro obligatorio - $29,000 COP");
        Button seguroProteccionTotalButton = new Button("Seguro protección total - $61,086 COP");

        // Configurar eventos para los botones de opciones de seguro
        seguroObligatorioButton.setOnAction(event -> {
            try {
				completarReservaConSeguro(reserva, "Seguro obligatorio");
			} catch (CsvException e) {
				e.printStackTrace();
			}
            mostrarVentanaPreguntaConductorAdicional(ventanaSegurosDisponibles, usuario);
        });

        seguroProteccionTotalButton.setOnAction(event -> {
            try {
				completarReservaConSeguro(reserva, "Seguro protección total");
			} catch (CsvException e) {
				e.printStackTrace();
			}
            mostrarVentanaPreguntaConductorAdicional(ventanaSegurosDisponibles, usuario);
        });

        // Crear diseño para la ventana de seguros disponibles
        VBox segurosDisponiblesRoot = new VBox(10);
        segurosDisponiblesRoot.setPadding(new Insets(20));
        segurosDisponiblesRoot.getChildren().addAll(
                segurosDisponiblesLabel, opcionesLabel, seguroObligatorioButton, seguroProteccionTotalButton);

        Scene segurosDisponiblesScene = new Scene(segurosDisponiblesRoot, 400, 200);
        ventanaSegurosDisponibles.setScene(segurosDisponiblesScene);
        ventanaSegurosDisponibles.show();
    }
// TODO CÓDIGO PARA MANDAR A PERSISTENCIA
    private void completarReservaConSeguro(String[] reserva, String tipoSeguro) throws CsvException {
        
        try {
            CSVReader reader = new CSVReader(new FileReader("./datos/alquileres.csv"));
            List<String[]> lines = reader.readAll();
            reader.close();

            for (String[] line : lines) {
                if (line.length > 0 && reserva[0].equals(line[0])) {
                    line[11] = tipoSeguro;  // Actualizar el tipo de seguro en la reserva
                    break;
                }
            }

            FileWriter writer = new FileWriter("./datos/alquileres.csv");
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeAll(lines);
            csvWriter.close();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace(); // Manejo básico de errores, ajusta según tus necesidades
        }
    }

    private void mostrarVentanaPreguntaConductorAdicional(Stage ventanaPadre, String usuario) {
        Stage ventanaPreguntaConductorAdicional = new Stage();
        ventanaPreguntaConductorAdicional.setTitle("Sistema de Alquiler de Vehículos - BIENVENIDO " + usuario);

        Label preguntaConductorAdicionalLabel = new Label("¿Desea agregar algún conductor adicional?");
        Button siButton = new Button("Sí");
        Button noButton = new Button("No");

        // Configurar eventos para los botones de opciones
        siButton.setOnAction(event -> {
            mostrarVentanaConductorAdicional(ventanaPreguntaConductorAdicional, usuario, usuario);
        });

        noButton.setOnAction(event -> {
            mostrarAnuncioConfirmacion("Cambios aplicados");
            ventanaPreguntaConductorAdicional.close();
            ventanaPadre.close();
        });

        // Crear diseño para la ventana de pregunta por conductor adicional
        VBox preguntaConductorAdicionalRoot = new VBox(10);
        preguntaConductorAdicionalRoot.setPadding(new Insets(20));
        preguntaConductorAdicionalRoot.getChildren().addAll(
                preguntaConductorAdicionalLabel, siButton, noButton);

        Scene preguntaConductorAdicionalScene = new Scene(preguntaConductorAdicionalRoot, 400, 200);
        ventanaPreguntaConductorAdicional.setScene(preguntaConductorAdicionalScene);
        ventanaPreguntaConductorAdicional.show();
    }

    private void mostrarVentanaConductorAdicional(Stage ventanaPadre, String usuario, String idReserva) {
        Stage ventanaConductorAdicional = new Stage();
        ventanaConductorAdicional.setTitle("Sistema de Alquiler de Vehículos - BIENVENIDO " + usuario);

        Label conductorAdicionalLabel = new Label("CONDUCTOR ADICIONAL");
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

            Persistencia.guardarConductorAdicional(idReserva, nombre, fechaNacimiento, nacionalidad, numeroLicencia,
                    paisExpedicionLicencia, fechaVencimientoLicencia);

            mostrarVentanaPreguntaConductorAdicional(ventanaConductorAdicional, usuario);
        });

        // Crear diseño para la ventana de conductor adicional
        GridPane conductorAdicionalRoot = new GridPane();
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

        Scene conductorAdicionalScene = new Scene(conductorAdicionalRoot, 400, 300);
        ventanaConductorAdicional.setScene(conductorAdicionalScene);
        ventanaConductorAdicional.show();
    }


   


}
