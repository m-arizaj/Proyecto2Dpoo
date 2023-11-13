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

public class PanelEmpleado extends Application
{

    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Alquiler de Vehículos");

        // Crear elementos de la interfaz
        Label usuarioLabel = new Label("Nombre de Usuario:");
        TextField usuarioTextField = new TextField();

        Label contrasenaLabel = new Label("Contraseña:");
        PasswordField contrasenaField = new PasswordField();

        Button ingresarButton = new Button("Ingresar");

        // Crear el diseño de la interfaz
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(usuarioLabel, usuarioTextField, contrasenaLabel, contrasenaField, ingresarButton);

        // Configurar el evento para el botón "Ingresar"
        ingresarButton.setOnAction(event -> {
            String usuario = usuarioTextField.getText();
            String contrasena = contrasenaField.getText();

            // Aquí puedes agregar la lógica para verificar el usuario y contraseña en tu CSV
            boolean usuarioValido = verificarUsuario(usuario, contrasena);

            if (usuarioValido) {
                mostrarVentanaBienvenida(primaryStage, usuario);
            } else {
                mostrarMensajeError("Error de autenticación", "Usuario o contraseña incorrectos");
            }
        });

        // Configurar la escena y mostrar la ventana
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // TODO CODIGO PARA PASAR A PERSISTENCIA
    private boolean verificarUsuario(String usuario, String contrasena) {
    	try (CSVReader reader = new CSVReader(new FileReader("./datos/empleados.csv"))) {
            String[] linea;
            while ((linea = reader.readNext()) != null) {
                // Verificar la coincidencia de usuario y contraseña en cada fila del CSV
                if (linea.length >= 2 && usuario.equals(linea[0]) && contrasena.equals(linea[1])) {
                    return true; // Coincidencia encontrada
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace(); // Manejo básico de errores, ajusta según tus necesidades
        }
        return false; // No se encontró coincidencia
    }
    

    private void mostrarVentanaBienvenida(Stage primaryStage, String usuario) {
        // Crear la nueva ventana de bienvenida
        Stage ventanaBienvenida = new Stage();
        ventanaBienvenida.setTitle("Sistema de Alquiler de Vehículos - BIENVENIDO " + usuario);

        Label bienvenidaLabel = new Label("BIENVENIDO " + usuario);
        Label opcionesLabel = new Label("¿Qué desea hacer?");

        // Crear botones para las opciones
        Button opcion1Button = new Button("Completar reserva alquiler");
        Button opcion2Button = new Button("Actualizar estado vehículo");
        Button opcion3Button = new Button("Reportar mantenimiento de vehículo y fecha de disponibilidad");
        Button salirButton = new Button("Salir");
        
        opcion1Button.setOnAction(e -> mostrarVentanaCompletarReservaAlquiler(primaryStage, usuario));
        opcion2Button.setOnAction(e -> mostrarVentanaActualizarEstadoVehiculo(primaryStage, usuario));
        opcion3Button.setOnAction(e -> mostrarVentanaReportarMantenimiento(primaryStage, usuario));
        // Configurar eventos para los botones de opciones
        // Aquí deberías implementar la lógica para cada opción

        // Configurar el evento para el botón "Salir"
        salirButton.setOnAction(event -> ventanaBienvenida.close());

        // Crear el diseño de la nueva ventana
        VBox bienvenidaRoot = new VBox(10);
        bienvenidaRoot.setPadding(new Insets(20));
        bienvenidaRoot.getChildren().addAll(bienvenidaLabel, opcionesLabel,
                opcion1Button, opcion2Button, opcion3Button, salirButton);

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
        ventanaActualizarEstado.setTitle("Sistema de Alquiler de Vehículos - BIENVENIDO " + usuario);

        Label actualizarEstadoLabel = new Label("ACTUALIZAR ESTADO DE UN VEHÍCULO");
        Label placaLabel = new Label("Indique la placa del carro que desea consultar:");
        TextField placaTextField = new TextField();
        Button aceptarButton = new Button("Aceptar");

        // Configurar evento para el botón "Aceptar"
        aceptarButton.setOnAction(event -> {
            String placa = placaTextField.getText();
            String estadoActual = null;
			try {
				estadoActual = obtenerEstadoVehiculo(placa);
			} catch (CsvValidationException e) {
				e.printStackTrace();
			}

            if (estadoActual == null) {
                mostrarMensajeError("Error", "No se encontró el vehículo con la placa especificada.");
            } else {
                mostrarVentanaConfirmacionEstado(ventanaActualizarEstado, usuario, placa, estadoActual);
            }
        });

        // Crear diseño para la ventana de actualizar estado
        VBox actualizarEstadoRoot = new VBox(10);
        actualizarEstadoRoot.setPadding(new Insets(20));
        actualizarEstadoRoot.getChildren().addAll(
                actualizarEstadoLabel, placaLabel, placaTextField, aceptarButton);

        Scene actualizarEstadoScene = new Scene(actualizarEstadoRoot, 400, 200);
        ventanaActualizarEstado.setScene(actualizarEstadoScene);
        ventanaActualizarEstado.show();
    }
    //TODO CODIGO PARA PASAR A PERSISTENCIA
    private String obtenerEstadoVehiculo(String placa) throws CsvValidationException 
    {
        try (CSVReader reader = new CSVReader(new FileReader("./datos/carros.csv"))) 
        {
            String[] linea;
            while ((linea = reader.readNext()) != null) 
            {
                if (linea.length > 8 && placa.equals(linea[0])) 
                {
                    return linea[6]; // Devuelve el estado del vehículo (ajusta según tu CSV)
                }
            }
        } catch (IOException | CsvValidationException e) 
        {
            e.printStackTrace();
        }
        return null; // No se encontró el vehículo con la placa especificada
    }

    private void mostrarVentanaConfirmacionEstado(Stage ventanaPadre, String usuario, String placa, String estadoActual) 
    {
        Stage ventanaConfirmacionEstado = new Stage();
        ventanaConfirmacionEstado.setTitle("Sistema de Alquiler de Vehículos - BIENVENIDO " + usuario);

        Label actualizarEstadoLabel = new Label("ACTUALIZAR ESTADO DE UN VEHÍCULO");
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
				actualizarEstadoVehiculo(placa, "Disponible");
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
				actualizarEstadoVehiculo(placa, "Alquilado");
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

        Scene confirmacionEstadoScene = new Scene(confirmacionEstadoRoot, 400, 300);
        ventanaConfirmacionEstado.setScene(confirmacionEstadoScene);
        ventanaConfirmacionEstado.show();
    }
    //TODO CODIGO PARA PASAR A PERSISTENCIA
    private void actualizarEstadoVehiculo(String placa, String nuevoEstado) throws CsvException {
    
    	
    	try {
            CSVReader reader = new CSVReader(new FileReader("./datos/carros.csv"));
            List<String[]> lines = reader.readAll();
            reader.close();

            for (String[] line : lines) {
                if (line.length > 0 && placa.equals(line[0])) {
                    line[6] = nuevoEstado;  // Actualizar el estado
                    
                   
                    break;
                }
            }

            FileWriter writer = new FileWriter("./datos/carros.csv");
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeAll(lines);
            csvWriter.close();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace(); // Manejo básico de errores, ajusta según tus necesidades
        }
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
        Label placaLabel = new Label("Indique la placa del carro que desea consultar:");
        TextField placaTextField = new TextField();
        Button aceptarButton = new Button("Aceptar");

        // Configurar evento para el botón "Aceptar"
        aceptarButton.setOnAction(event -> {
            String placa = placaTextField.getText();
            String estadoActual = null;
			try {
				estadoActual = obtenerEstadoVehiculo(placa);
			} catch (CsvValidationException e) {
				e.printStackTrace();
			}

            if (estadoActual == null) {
                mostrarMensajeError("Error", "No se encontró el vehículo con la placa especificada.");
            } else {
                mostrarVentanaConfirmacionMantenimiento(ventanaReportarMantenimiento, usuario, placa, estadoActual);
            }
        });

        // Crear diseño para la ventana de reportar mantenimiento
        VBox reportarMantenimientoRoot = new VBox(10);
        reportarMantenimientoRoot.setPadding(new Insets(20));
        reportarMantenimientoRoot.getChildren().addAll(
                reportarMantenimientoLabel, placaLabel, placaTextField, aceptarButton);

        Scene reportarMantenimientoScene = new Scene(reportarMantenimientoRoot, 400, 200);
        ventanaReportarMantenimiento.setScene(reportarMantenimientoScene);
        ventanaReportarMantenimiento.show();
    }

    private void mostrarVentanaConfirmacionMantenimiento(Stage ventanaPadre, String usuario, String placa, String estadoActual) {
        Stage ventanaConfirmacionMantenimiento = new Stage();
        ventanaConfirmacionMantenimiento.setTitle("Sistema de Alquiler de Vehículos - BIENVENIDO " + usuario);

        Label confirmacionMantenimientoLabel = new Label("REPORTAR MANTENIMIENTO DE VEHÍCULO Y FECHA DE DISPONIBILIDAD");
        Label estadoActualLabel = new Label("Vehículo de placa " + placa + " en estado: " + estadoActual);
        Label opcionesLabel = new Label("¿Desea registrar el vehículo en mantenimiento o retirarlo?");
        Button retirarButton = new Button("Retirar");
        Button registrarButton = new Button("Registrar");

        // Configurar eventos para los botones de opciones
        retirarButton.setOnAction(event -> {
            try {
				actualizarEstadoVehiculo(placa, "Disponible");
			} catch (CsvException e) {
				e.printStackTrace();
			}
            mostrarAnuncioConfirmacion("Perfecto, ahora el vehículo se encuentra disponible");
            ventanaConfirmacionMantenimiento.close();
            ventanaPadre.close();
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

        Scene confirmacionMantenimientoScene = new Scene(confirmacionMantenimientoRoot, 400, 300);
        ventanaConfirmacionMantenimiento.setScene(confirmacionMantenimientoScene);
        ventanaConfirmacionMantenimiento.show();
    }

    private void mostrarVentanaExplicacionProblema(String placa) {
        Stage ventanaExplicacionProblema = new Stage();
        ventanaExplicacionProblema.setTitle("Sistema de Alquiler de Vehículos - Explique el problema");

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
				actualizarEstadoYFechaReintegro(placa, "En Mantenimiento", explicacionProblema, fechaReintegro);
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

    	//TODO CODIGO PARA PERSISTENCIA
    private void actualizarEstadoYFechaReintegro(String placa, String nuevoEstado, String explicacionProblema, String fechaReintegro) throws CsvException {
       
        try {
            CSVReader reader = new CSVReader(new FileReader("./datos/carros.csv"));
            List<String[]> lines = reader.readAll();
            reader.close();

            for (String[] line : lines) {
                if (line.length > 0 && placa.equals(line[0])) {
                    line[6] = nuevoEstado;  // Actualizar el estado
                    line[9] = fechaReintegro + " - " + explicacionProblema;  // Actualizar la fecha de reintegro
                    break;
                }
            }

            FileWriter writer = new FileWriter("./datos/carros.csv");
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeAll(lines);
            csvWriter.close();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace(); // Manejo básico de errores, ajusta según tus necesidades
        }
        
        
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            mostrarVentanaPreguntaConductorAdicional(ventanaSegurosDisponibles, usuario);
        });

        seguroProteccionTotalButton.setOnAction(event -> {
            try {
				completarReservaConSeguro(reserva, "Seguro protección total");
			} catch (CsvException e) {
				// TODO Auto-generated catch block
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

    private void completarReservaConSeguro(String[] reserva, String tipoSeguro) throws CsvException {
        // Implementar lógica para completar la reserva con el tipo de seguro seleccionado
        // Aquí deberías abrir el archivo CSV "reservas", buscar la fila con la reserva y actualizar el tipo de seguro
        // Considera usar una biblioteca de manejo de CSV para una implementación más robusta
//        try {
//            CSVReader reader = new CSVReader(new FileReader("./datos/reservas.csv"));
//            List<String[]> lines = reader.readAll();
//            reader.close();
//
//            for (String[] line : lines) {
//                if (line.length > 0 && reserva[0].equals(line[0])) {
//                    line[5] = tipoSeguro;  // Actualizar el tipo de seguro en la reserva
//                    break;
//                }
//            }
//
//            FileWriter writer = new FileWriter("./datos/reservas.csv");
//            CSVWriter csvWriter = new CSVWriter(writer);
//            csvWriter.writeAll(lines);
//            csvWriter.close();
//        } catch (IOException | CsvValidationException e) {
//            e.printStackTrace(); // Manejo básico de errores, ajusta según tus necesidades
//        }
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

            guardarConductorAdicional(idReserva, nombre, fechaNacimiento, nacionalidad, numeroLicencia,
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

    private void guardarConductorAdicional(String idReserva, String nombre, String fechaNacimiento, String nacionalidad,
                                           String numeroLicencia, String paisExpedicionLicencia,
                                           String fechaVencimientoLicencia) {
        // Implementar lógica para guardar el conductor adicional en el archivo CSV "conductoresAdicionales"
        // Aquí deberías abrir el archivo CSV, añadir una nueva fila con el ID de la reserva y la información del conductor adicional
        // Considera usar una biblioteca de manejo de CSV para una implementación más robusta
        try {
            FileWriter writer = new FileWriter("./datos/conductoresAdicionales.csv", true);
            CSVWriter csvWriter = new CSVWriter(writer);
            String[] nuevoConductorAdicional = {
                    idReserva, nombre, fechaNacimiento, nacionalidad, numeroLicencia,
                    paisExpedicionLicencia, fechaVencimientoLicencia
            };
            csvWriter.writeNext(nuevoConductorAdicional);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace(); // Manejo básico de errores, ajusta según tus necesidades
        }
    }
   
	static void ejecutarMenuEmpleado(SistemaAlquiler sistema, Scanner scanner) 
    {
        boolean cl_aut = false;

        while (!cl_aut)
        {
            System.out.println("Bienvenido empleado.");
            
            System.out.println("\n");
            
            System.out.println("Que desea hacer?");
            
            System.out.println("\n");
            
            System.out.println("1. Completar alquiler de reserva previa");
            System.out.println("2. Actualizar estado de un vehículo");
            System.out.println("3. Reportar mantenimiento de vehículo y reportar fecha de disponibilidad");
            System.out.println("4. Salir del menu");
            System.out.println("\n");
            
            System.out.print("Seleccione una opcion: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            if (opcion == 1) 
            {
            	System.out.println("Id de la reserva: ");
                String id = scanner.nextLine();
                sistema.completarAlquiler(id, scanner);
            }
            else if (opcion == 2) 
            {
                System.out.println("Indique la placa del carro que desea consultar: ");
                String placa = scanner.nextLine();
                sistema.agregarEventoAlHistorial(placa, "Se consultó el estado de disponibilidad del auto con placa "+placa);
                Persistencia.escribirEventosVehiculos(sistema,"datos/eventos.csv");
                String estado = Persistencia.buscarDisponible(placa);
                System.out.println(estado);
                if (estado != "Placa no encontrada")
                {
                	System.out.println("El vehículo de placa " + placa  + " se encuentra en el siguiente estado: " + estado);
                	
                	
                	System.out.println("Desea actualizar su estado a (1) = Disponible o (2) = Alquilado");
                	int respuesta = scanner.nextInt();
                	
                	//DISPONIBLE
                	if (respuesta == 1)
                	{
                		String estatus = "Disponible";
                		Persistencia.cambiarEstado(placa, estatus);
                		
                		System.out.println("\n");
                		System.out.println("\n");
                		
                		System.out.println("Ahora el vehículo se encuentra en estado de Disponible.");
                		sistema.agregarEventoAlHistorial(placa, "Se actualizo el estado del vechiculo con placa " + placa + " a disponible");
                		Persistencia.escribirEventosVehiculos(sistema,"datos/eventos.csv");
                	}
                	
                	
                
	                else if (respuesta == 2) 
	                {
	                	
	                	String estatus = "Alquilado";
	                	Persistencia.cambiarEstado(placa, estatus);
                		
                		System.out.println("\n");
                		System.out.println("\n");
                		
                		System.out.println("Ahora el vehículo se encuentra en estado de Alquilado.");
                		sistema.agregarEventoAlHistorial(placa, "Se actualizo el estado del vechiculo con placa " + placa + " a alquilado");
                		Persistencia.escribirEventosVehiculos(sistema,"datos/eventos.csv");
	                }
                	
                }
                
            }
                

            else if (opcion == 3) 
            {
            	System.out.println("Indique la placa del carro que desea consultar: ");
                String placa = scanner.nextLine();
                
                System.out.println("¿Desea (Si) = registrar en vehículo en mantenimiento e (No) = retirarlo?");
            	String tema = scanner.nextLine();
            	
            	if(tema.equals("Si"))
            	{
	                System.out.println("A continuación, explique brevemente el problema del vehículo.");
	                String observacion = scanner.nextLine();
	                sistema.agregarEventoAlHistorial(placa, "Se añadio la siguiente observacion al vehiculo con placa " + placa + ":" + observacion);
	                Persistencia.escribirEventosVehiculos(sistema,"datos/eventos.csv");
	                System.out.println("\n");
	        		
	        		System.out.println("Según su criterio, ¿cuál será la fecha de reintegro del vehículo?");
	        		
	        		System.out.println("\n");
	        		System.out.println("Entiendase reintegro como el momento en el que el carro volverá a estar disponible.");
	        		String fecha = scanner.nextLine();
	        		
	        		String estado = "Mantenimiento";
	        		
	        		Persistencia.cambiarMantenimiento(placa, estado, observacion, fecha);
	        		
	        		System.out.println("\n");
	        		System.out.println("\n");
	        		
	        		System.out.println("Listo, el vehículo ahora se encuentra registrado como en mantenimiento.");
	        		sistema.agregarEventoAlHistorial(placa, "Se actualizo el estado del vechiculo con placa " + placa + " a mantenimiento");
	        		Persistencia.escribirEventosVehiculos(sistema,"datos/eventos.csv");
            	}
            	else if (tema.equals("No"))
            	{
            	                    
                    String estatus = "Disponible";
                    
                    String observacion = "";
                    
                    String fecha = "";
                    
                    Persistencia.cambiarMantenimiento(placa, estatus, observacion, fecha);
                    
                    System.out.println("\n");
            		
            		System.out.println("Listo, el vehículo ahora se encuentra disponible.");
            		sistema.agregarEventoAlHistorial(placa, "Se actualizo el estado del vechiculo con placa " + placa + " a disponible");
            		Persistencia.escribirEventosVehiculos(sistema,"datos/eventos.csv");
            	}
            	else
            	{
            		System.out.println("No escogió una opción valida. Vuelva a realizar el proceso.");
            	}
            }

       
            else if (opcion == 4 ) {
                cl_aut = true;
            }
            else 
            {
                System.out.println("Opcion no valida. Por favor, seleccione una opcion valida.");
            }
            }
        
    }
	
	public static void completarAlquiler(String id, Scanner scanner) {
		try (CSVReader reader = new CSVReader(new FileReader("datos/reservas.csv"))) {
            
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

            
            
            String[] fila;
            while ((fila = reader.readNext()) != null) {
                String reservaId = fila[indiceId];

                if (reservaId.equals(id)) {
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
               	 	
               	 	
                    List<String> seguro= escogerSeguro(scanner);
                    System.out.println("Desea agregar conductores adicionales? Escriba 1 para si, 0 para no: ");
                    String pregunta = scanner.nextLine();
                    int numeroAdicionales= 0;
                    if (pregunta.equals("1")) {
                    numeroAdicionales= ConductorAdicional.conductoresAgregados(scanner, usuario, id);}
                    else {
                    	System.out.println("Generando archivo de alquiler");
                    }
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
                    System.out.println("El archivo de alquiler fue agregado correctamente");
                    
                   
                }
            }
            
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
	}
	
	public static List<String> escogerSeguro (Scanner scanner){
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
            int totalSedes = listaDeListas.size();

            if (totalSedes > 0) {
                System.out.println("Seguros disponibles:");
                for (int i = 0; i < totalSedes; i++) {
                    List<String> sede = listaDeListas.get(i);
                    System.out.println("Opcion " + (i + 1));
                    System.out.println("Nombre: " + sede.get(0));
                    System.out.println("Valor por dia: " + sede.get(1));
                    System.out.println("Descripcion: " + sede.get(2));
                    System.out.println();
                }

                System.out.println("Seleccione una opcion entre 1 y " + totalSedes + " dependiendo de la seleccion del cliente: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();
                
                if (opcion >= 1 && opcion <= totalSedes) {
                    return listaDeListas.get(opcion - 1);
                    
                } else {
                    System.out.println("Opcion invalida.");
                }
            }
            
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return null;
		
		
		
	}
//
//	@Override
//	public void start(Stage arg0) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
}
