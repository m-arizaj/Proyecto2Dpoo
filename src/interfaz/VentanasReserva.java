package interfaz;

import java.io.IOException;

import com.opencsv.exceptions.CsvValidationException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import logica.*;
import persistencia.*;
import java.util.List;

public class VentanasReserva
{	
	private Scene escenaMenuCliente;
	private Scene escenaReservaCategoria;
	private Scene escenaFechas;
	
	public void mostrarMenuReservas(SistemaAlquiler sistema, Stage primaryStage, String nombre, Scene escenaPrincipal) {
    	Label lblTitulo = new Label("Menú principal");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Label lblSubtitulo = new Label("Bienvenid@, "+ nombre);
        lblSubtitulo.setFont(Font.font("Arial", FontWeight.BOLD, 15));
    	Button btnReserva = new Button("Realizar una reserva");
        btnReserva.setOnAction(e -> Reservas(sistema,primaryStage, nombre));
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setOnAction(e -> primaryStage.setScene(escenaPrincipal));
        
        btnReserva.setStyle("-fx-background-color: #3498DB;"); 
        btnRegresar.setStyle("-fx-background-color: #5DADE2;");
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));
        
        gridPane.setStyle("-fx-background-color: beige;");
        
        gridPane.add(lblTitulo, 0, 0, 1, 1);
        gridPane.add(lblSubtitulo, 0, 3, 1, 1);

        gridPane.add(btnReserva, 0, 5);
        gridPane.add(btnRegresar, 0, 6);
        
        
        
        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setScene(scene);
        escenaMenuCliente = scene;
      
    }
    public void Reservas(SistemaAlquiler sistema, Stage primaryStage, String nombre) {
    	 // Crear un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Agregar un título
        Label titleLabel = new Label("Reserva");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gridPane.add(titleLabel, 0, 1, 2, 1);
        
        Label subtitleLabel = new Label("Seleccione un tipo de la siguiente lista:");
        subtitleLabel.setFont(Font.font("Arial", 15));
        gridPane.add(subtitleLabel, 0, 2, 2, 1);
        
     // Agregar una imagen
        Image image = new Image("file:datos/tablaCategorias.png");
        ImageView imageView = new ImageView(image);
        gridPane.add(imageView, 0, 3, 2, 1); // Se extiende por 2 columnas y 1 fila
        

        // Agregar un campo de entrada (input)
        Label inputLabel = new Label("Ingrese un numero de 1 a 16:");
        gridPane.add(inputLabel, 0, 4);

        TextField inputField = new TextField();
        gridPane.add(inputField, 1, 4);
        
        Button btnAceptar = new Button("Aceptar");
        Button btnRegresar = new Button("Regresar");
        btnAceptar.setStyle("-fx-background-color: #5DADE2;");
        btnRegresar.setStyle("-fx-background-color: #85C1E9;");
        btnRegresar.setOnAction(e -> primaryStage.setScene(escenaMenuCliente));
        gridPane.add(btnRegresar, 0, 5);
        gridPane.add(btnAceptar, 1, 5);
        
        
        btnAceptar.setOnAction(e -> {
            String categoriaSelecc = inputField.getText();
            ReservasFecha(sistema,primaryStage,nombre,categoriaSelecc);
            
        });
        
        gridPane.setStyle("-fx-background-color: beige;");

        // Crear la escena y mostrar la ventana
        Scene scene = new Scene(gridPane, 600, 600);
        primaryStage.setScene(scene);
        escenaReservaCategoria = scene;
    }
    
    public void ReservasFecha(SistemaAlquiler sistema, Stage primaryStage, String nombre, String categoriaSelecc) {
    	GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        Label titleLabel = new Label("Reserva");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gridPane.add(titleLabel, 0, 1, 2, 1);
        
        Label inicioLabel = new Label("Ingrese la fecha y hora de inicio del\r\n"
        		+ "alquiler (yyyy-MM-dd HH:mm):");
        inicioLabel.setFont(Font.font("Arial", 15));
        gridPane.add(inicioLabel, 0, 3);
        TextField inicioField = new TextField();
        gridPane.add(inicioField, 1, 3);
        
        Label infoLabel = new Label("Importante: Tenga en cuenta que si elige una hora de finalizacion\r\n"
        		+ "mas tarde que la de inicio se cobra un dia adicional");
        infoLabel.setFont(Font.font("Arial", FontPosture.ITALIC, 15));
        gridPane.add(infoLabel, 0, 4, 2, 1);
        
        Label finLabel = new Label("Ingrese la fecha y hora de finalizacion del\r\n"
        		+ "alquiler (yyyy-MM-dd HH:mm):");
        finLabel.setFont(Font.font("Arial", 15));
        gridPane.add(finLabel, 0, 5);
        TextField finField = new TextField();
        gridPane.add(finField, 1, 5);
        
        Button btnAceptar = new Button("Aceptar");
        Button btnRegresar = new Button("Regresar");
        btnAceptar.setStyle("-fx-background-color: #5DADE2;");
        btnRegresar.setStyle("-fx-background-color: #85C1E9;");
        btnRegresar.setOnAction(e -> primaryStage.setScene(escenaReservaCategoria));
        gridPane.add(btnRegresar, 0, 7);
        gridPane.add(btnAceptar, 1, 7);
        
        
        btnAceptar.setOnAction(e -> {
            String fechaInicio = inicioField.getText();
            String fechaFin = finField.getText();
            ReservasSede(sistema,primaryStage,nombre,categoriaSelecc,fechaInicio,fechaFin);
        });
        
        gridPane.setStyle("-fx-background-color: beige;");
        
        Scene scene = new Scene(gridPane, 500, 300);//ancho,alto
        primaryStage.setScene(scene);
        escenaFechas = scene;
        
    }
    
    public void ReservasSede(SistemaAlquiler sistema, Stage primaryStage, String nombre, String categoriaSelecc, 
    		String fechaInicio, String fechaFin)  {
    	GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        Label titleLabel = new Label("Reserva");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gridPane.add(titleLabel, 0, 1, 2, 1);
        
        Label primeraLabel = new Label("Opcion 1\r\n"
        		+ "Nombre: Aeropuerto El Dorado\r\n"
        		+ "Direccion: Avenida Calle 26 # 96A-21\r\n"
        		+ "Horario: Abierto 24H");
        primeraLabel.setFont(Font.font("Arial", 15));
        gridPane.add(primeraLabel, 0, 3, 2, 1);
        
        Label segundaLabel = new Label("Opcion 2\r\n"
        		+ "Nombre: Nuestro Bogota\r\n"
        		+ "Direccion: Avenida Carrera 86 # 55A-75\r\n"
        		+ "Horario: L-V (8:00 - 18:00), S (8:00 - 14:00)");
        segundaLabel.setFont(Font.font("Arial", 15));
        gridPane.add(segundaLabel, 0, 5, 2, 1);
        
        Label recogerLabel = new Label("En que sede le gustaria recoger el vehiculo?\r\n" + "Digite un número (1 o 2)");
        recogerLabel.setFont(Font.font("Arial", 15));
        gridPane.add(recogerLabel, 0, 7);
        TextField recogerField = new TextField();
        gridPane.add(recogerField, 1, 7);
        
        Label infoLabel = new Label("Importante: Tenga en cuenta que entregarlo en una sede diferente a\r\n"
        		+ "la cual lo recibio tiene un costo adicional");
        infoLabel.setFont(Font.font("Arial", FontPosture.ITALIC, 15));
        gridPane.add(infoLabel, 0, 8, 2, 1);
        
        Label entregarLabel = new Label("En que sede le gustaria entregar el vehiculo?\r\n" + "Digite un número (1 o 2)");
        entregarLabel.setFont(Font.font("Arial", 15));
        gridPane.add(entregarLabel, 0, 9);
        TextField entregarField = new TextField();
        gridPane.add(entregarField, 1, 9);
        
        Label info2Label = new Label("Al presionar aceptar continua el proceso de pago de la reserva");
        info2Label.setFont(Font.font("Arial", FontPosture.ITALIC, 15));
        gridPane.add(info2Label, 0, 10, 2, 1);
        
        Button btnAceptar = new Button("Aceptar");
        Button btnRegresar = new Button("Regresar");
        btnAceptar.setStyle("-fx-background-color: #5DADE2;");
        btnRegresar.setStyle("-fx-background-color: #85C1E9;");
        btnRegresar.setOnAction(e -> primaryStage.setScene(escenaFechas));
        gridPane.add(btnRegresar, 0, 11);
        gridPane.add(btnAceptar, 1, 11);
        
        btnAceptar.setOnAction(e -> {
            String recoger = recogerField.getText();
            String entregar = entregarField.getText();
            sistema.realizarReserva(nombre,categoriaSelecc,fechaInicio,fechaFin,recoger,entregar);
            try {
            Persistencia.leerReservas(sistema,"datos/reservas.csv");}
            catch (CsvValidationException ex) {
    	        ex.printStackTrace();
    	    }
            List<Reserva> list = (sistema.getReservas());
            int lon = (sistema.getReservas().size());
            Reserva selec = list.get(lon - 1);
            String[] reser = {selec.getId(),selec.getCategoria(),selec.getUsuarioCliente(), selec.getSedeRecogida(), 
            		selec.getSedeEntrega(), selec.getFechaRecogida(),selec.getFechaEntrega(),selec.getDiasFacturados(),
            		selec.getCostoParcial(),selec.getCostoTreinta()};
            PanelEmpleado.mostrarVentanaPasarelas(primaryStage, ".", reser, "reserva", escenaMenuCliente);
            
//            ReservaCompleta(sistema,primaryStage);
        });
        
        gridPane.setStyle("-fx-background-color: beige;");
        
        Scene scene = new Scene(gridPane, 500, 600);//ancho,alto
        primaryStage.setScene(scene);
        
    }
    public void ReservaCompleta(SistemaAlquiler sistema, Stage primaryStage) {
    	GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        Label titleLabel = new Label("Reserva");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gridPane.add(titleLabel, 0, 1, 2, 1);
        
        Label subtitleLabel = new Label("Su reserva fue creada exitosamente!");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gridPane.add(subtitleLabel, 0, 3, 2, 1);
        
        Label infoLabel = new Label("El valor total sin adicionales de su reserva es de: "+ sistema.getSubtotal());
        infoLabel.setFont(Font.font("Arial", 15));
        gridPane.add(infoLabel, 0, 5, 2, 1);
        
        Label info2Label = new Label("Se cargo el treinta por ciento de ese valor ("+ sistema.getTreintapor()+") a su medio de pago");
        info2Label.setFont(Font.font("Arial", 15));
        gridPane.add(info2Label, 0, 7, 2, 1);
        
        Button btnAceptar = new Button("Aceptar");
        btnAceptar.setStyle("-fx-background-color: #5DADE2;");
        gridPane.add(btnAceptar, 0, 9);
        btnAceptar.setOnAction(e -> primaryStage.setScene(escenaMenuCliente));
        
        gridPane.setStyle("-fx-background-color: beige;");
        
        Scene scene = new Scene(gridPane, 500, 400);//ancho,alto
        primaryStage.setScene(scene);
    }
}
