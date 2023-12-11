	package interfaz;
	
	import javafx.geometry.Insets;
	import javafx.geometry.Pos;
	import javafx.scene.Scene;
	import javafx.scene.control.Alert;
	import javafx.scene.control.Button;
	import javafx.scene.control.Label;
	import javafx.scene.control.TextField;
	import javafx.scene.layout.GridPane;
	import javafx.scene.layout.VBox;
	import javafx.scene.text.Font;
	import javafx.scene.text.FontWeight;
	import javafx.stage.Stage;
	import javafx.scene.image.Image;
	import javafx.scene.image.ImageView;
	import java.time.LocalDateTime;
	import java.time.format.DateTimeFormatter;
	import java.lang.reflect.InvocationTargetException;
	import java.awt.Desktop;
	import java.io.IOException;
	import logica.*;
	
	import com.itextpdf.text.Document;
	import com.itextpdf.text.DocumentException;
	import com.itextpdf.text.Paragraph;
	import com.itextpdf.text.pdf.PdfWriter;
	
	import java.io.FileOutputStream;
	import com.itextpdf.text.FontFactory;
	
	
	
	public class VentanasPayPal
	{
		
		public void mostrarMenuPago(Stage primaryStage, String seguro, String[] reserva, String tipo, Scene menu) {
			Image logoPayPal = new Image("file:datos/paypalLogo.png");  // Reemplaza con la ruta correcta de tu logo
	        ImageView imageView = new ImageView(logoPayPal);
	        double nuevoTamano = 150; // Ajusta el tamaño según tus necesidades
	        imageView.setFitWidth(nuevoTamano);
	        imageView.setFitHeight(nuevoTamano);
	    	Label lblTitulo = new Label("Realizar pago");
	        lblTitulo.setFont(Font.font("PayPal Sans",FontWeight.BOLD, 18));
	        LocalDateTime fechaHoraActual = LocalDateTime.now();
	        String fechaHoraComoString = fechaHoraActual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	        Label lblSubtitulo = new Label("Fecha: " + fechaHoraComoString);
	        lblSubtitulo.setFont(Font.font("PayPal Sans", 15));
	        double monto = 0;
	        String descripcion = "Alquiler de vehiculo de categoria " + reserva[1] + " desde " + reserva[5] + " hasta " + reserva[6];
	        if (seguro.equals("Seguro obligatorio") && tipo.equals("alquiler")){
	        monto = ((Double.parseDouble(reserva[8]) - Double.parseDouble(reserva[9])) + (29000 * Double.parseDouble(reserva[7])));
	        }
	        else if (seguro.equals("Seguro proteccion total") && tipo.equals("alquiler")){
	        monto = ((Double.parseDouble(reserva[8]) - Double.parseDouble(reserva[9])) + (61086 * Double.parseDouble(reserva[7])));
	        }
	        else if (tipo.equals("reserva")){
	        monto = Double.parseDouble(reserva[9]);
	        }
	        String montoStr = Double.toString(monto);
	        Label lblSubtitulo2 = new Label("Monto total transaccion: " + montoStr);
	        lblSubtitulo2.setFont(Font.font("PayPal Sans", 15));
	        Label lblSubtitulo3 = new Label("Concepto: " + descripcion);
	        lblSubtitulo3.setFont(Font.font("PayPal Sans", 15));
	        String cuenta = reserva[2];
	        Label nombre = new Label("Nombre completo:");
	        nombre.setFont(Font.font("PayPal Sans", 15));
	        TextField nombreField = new TextField();
	        Label documento = new Label("Numero de documento (CC/CE):");
	        documento.setFont(Font.font("PayPal Sans", 15));
	        TextField documentoField = new TextField();
	        Label correo = new Label("Correo electronico:");
	        correo.setFont(Font.font("PayPal Sans", 15));
	        TextField correoField = new TextField();
	        Label numero = new Label("Numero tarjeta credito:");
	        numero.setFont(Font.font("PayPal Sans", 15));
	        TextField numeroField = new TextField();
	        Label ccv = new Label("CCV:");
	        ccv.setFont(Font.font("PayPal Sans", 15));
	        TextField ccvField = new TextField();
	        Label vencimiento = new Label("Fecha de vencimiento tarjeta (mm/yyyy):");
	        vencimiento.setFont(Font.font("PayPal Sans", 15));
	        TextField vencimientoField = new TextField();
	        
	        Button btnAceptar = new Button("Aceptar");
	        btnAceptar.setOnAction(e -> {
	        	String texto = "Tarjeta de credito";
	        	if (numeroField.getText().length()==16) {
	        	texto = "Tarjeta terminada en "+ (numeroField.getText()).substring(12, 16);
	        	}
	            String[] tarjeta = {numeroField.getText(),ccvField.getText(),vencimientoField.getText()};
	            Transaccion transaccion = new Transaccion(reserva[0],fechaHoraComoString, montoStr,cuenta,nombreField.getText(),
	        		documentoField.getText(),correoField.getText(),descripcion,tarjeta);
	        
	        	
	            PasarelaPago obj = new PayPal();
	            boolean bandera = obj.realizarPago(transaccion);
	            if (bandera == true){
	            	mostrarConfirmacion(primaryStage, seguro, reserva ,transaccion, texto, tipo, menu);
	            }
	            else {
	            	mostrarMensajeInicioFallido();
	            }
	        
	        });
	        
	        btnAceptar.setStyle("-fx-background-color: #5DADE2;");
	        
	        GridPane gridPane = new GridPane();
	        gridPane.setAlignment(Pos.CENTER);
	        gridPane.setVgap(10);
	        gridPane.setHgap(10);
	        gridPane.setPadding(new Insets(10));
	        
	        gridPane.setStyle("-fx-background-color: white;");
	        
	        gridPane.add(imageView, 0, 0, 2, 1);
	        gridPane.add(lblTitulo, 0, 2, 2, 1);
	        gridPane.add(lblSubtitulo, 0, 3, 2, 1);
	        gridPane.add(lblSubtitulo2, 0, 4, 2, 1);
	        gridPane.add(lblSubtitulo3, 0, 5, 2, 1);
	        gridPane.add(nombre, 0, 6);
	        gridPane.add(nombreField, 1, 6);
	        gridPane.add(documento, 0, 7);
	        gridPane.add(documentoField, 1, 7);
	        gridPane.add(correo, 0, 8);
	        gridPane.add(correoField, 1, 8);
	        gridPane.add(numero, 0, 9);
	        gridPane.add(numeroField, 1, 9);
	        gridPane.add(ccv, 0, 10);
	        gridPane.add(ccvField, 1, 10);
	        gridPane.add(vencimiento, 0, 11);
	        gridPane.add(vencimientoField, 1, 11);
	        
	        gridPane.add(btnAceptar, 0, 12);
	        
	        Scene scene = new Scene(gridPane, 900, 600);
	        primaryStage.setScene(scene);
	      
	    }
		
		public void mostrarConfirmacion(Stage primaryStage, String seguro, String[] reserva, Transaccion transaccion, String texto,
				String tipo, Scene menu) {
			Label preguntaLabel = new Label("Su transaccion fue procesada correctamente!");
	        preguntaLabel.setFont(Font.font("PayPal Sans", FontWeight.BOLD, 15));
	        Label lblSubtitulo = new Label("Su factura fue generada. Presione aceptar para volver al sitio web del comerciante");
	        lblSubtitulo.setFont(Font.font("PayPal Sans", 15));
	        Button continuar = new Button("Aceptar");
	        
	        continuar.setOnAction(e -> {
	
	            String rutaArchivo = "miarchivo.pdf"; // Cambia el nombre del archivo a un nombre de tu elección
	
	        	
	        	if (tipo.equals("reserva")){
	        	rutaArchivo = "recibos/adelanto_"+transaccion.getId()+".txt";
	        	}
	        	else if (tipo.equals("alquiler")){
	        	rutaArchivo = "recibos/"+transaccion.getId()+".txt";	
	        	}
	
	
	            try {
	                // Crear el archivo y escribir contenido
	
	                escribirEnArchivoPDF(rutaArchivo, transaccion);
	
	//                escribirEnArchivoPDF(rutaArchivo, "Alquiler de vehiculos\r\n"
	//                		+ "Id factura: "+ transaccion.getId()+ "\r\n"
	//                		+ "Fecha: " + transaccion.getFecha()+"\r\n"
	//                		+ "Monto: "+transaccion.getMonto()+ "\r\n"
	//                		+ "Cuenta: "+transaccion.getCuenta()+ "\r\n"
	//                		+ "Nombre completo: " +transaccion.getNombre()+ "\r\n"
	//                		+ "Documento: "+ transaccion.getDocumento() + "\r\n"
	//                		+ "Correo: "+transaccion.getCorreo()+"\r\n"
	//                		+ "Descripcion: "+ transaccion.getDescripcion()+ "\r\n"
	//                		+ "Medio de pago: "+ texto +"\r\n"
	//                		+ "----Cuenta cerrada----");
	
	
	                // Abrir el archivo con la aplicación predeterminada
	
	                abrirPDF(rutaArchivo);
	
	                PanelEmpleado.mostrarVentanaExito(primaryStage, seguro, reserva, menu, rutaArchivo);
	
	            } catch (IOException | DocumentException evv) {
	
	                abrirPDF(rutaArchivo);
	                PanelEmpleado.mostrarVentanaExito(primaryStage, seguro, reserva, menu, tipo);
	                
	            }
	        });
	        
	        VBox preguntaAdicionalRoot = new VBox(10);
	        preguntaAdicionalRoot.setStyle("-fx-background-color: white;");
	        preguntaAdicionalRoot.setPadding(new Insets(20));
	        preguntaAdicionalRoot.getChildren().addAll(preguntaLabel,lblSubtitulo, continuar);
	        
	        continuar.setStyle("-fx-background-color: #5DADE2;");
	       
	        Scene preguntaConductorAdicionalScene = new Scene(preguntaAdicionalRoot, 600, 200);
	        primaryStage.setScene(preguntaConductorAdicionalScene);
	        primaryStage.show();
			
		}
		
	
		public void escribirEnArchivoPDF(String rutaArchivo, Transaccion transaccion) throws IOException, DocumentException {
		    Document document = new Document();
		    PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
		    document.open();
	
		    // Agregar contenido al PDF
		    agregarContenidoPDF(document, transaccion);
	
		    // Cerrar el documento manualmente
		    document.close();
		}
	
	
		private void agregarContenidoPDF(Document document, Transaccion transaccion) throws DocumentException {
		    // Agregar contenido al PDF
		    com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 16);
		    com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.HELVETICA, 12);
	
		    // Título
		    Paragraph title = new Paragraph("SISTEMA DE ALQUILER DE VEHÍCULOS", titleFont);
		    title.setAlignment(Paragraph.ALIGN_CENTER);
		    document.add(title);
	
		    document.add(new Paragraph("ID Factura: " + transaccion.getId(), font));
		    document.add(new Paragraph("Fecha: " + transaccion.getFecha(), font));
		    document.add(new Paragraph("Monto: " + transaccion.getMonto(), font));
		    document.add(new Paragraph("Cuenta: " + transaccion.getCuenta(), font));
		    document.add(new Paragraph("Nombre Completo: " + transaccion.getNombre(), font));
		    document.add(new Paragraph("Documento: " + transaccion.getDocumento(), font));
		    document.add(new Paragraph("Correo: " + transaccion.getCorreo(), font));
		    document.add(new Paragraph("Descripción: " + transaccion.getDescripcion(), font));
		    document.add(new Paragraph("Medio de Pago: " + transaccion.getMedioPago(), font));
		    document.add(new Paragraph("----Cuenta cerrada----", font));
		}
	
	
	
		private void abrirPDF(String pdfPath) {
		    try {
		        // Abre el PDF con el programa predeterminado del sistema
		        Desktop.getDesktop().open(java.nio.file.Paths.get(pdfPath).toFile());
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		 
		 public void mostrarMensajeInicioFallido() {
			    Alert alert = new Alert(Alert.AlertType.INFORMATION);
			    alert.setTitle("Érror");
			    alert.setHeaderText(null);
			    alert.setContentText("Medio de pago no valido. Intente nuevamente.");
			    alert.showAndWait();
			}
		 
		 
		 
	}

