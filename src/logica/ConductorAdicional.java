package logica;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVWriter;

public class ConductorAdicional {
    private String nombre;
    private String documentoIdentidad;
    private Alquiler alquiler;

    public ConductorAdicional(String nombre, String documentoIdentidad) {
        this.nombre = nombre;
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }
    public Alquiler getAlquiler() {
		return alquiler;
	}

	public void setAlquiler(Alquiler alquiler) {
		this.alquiler = alquiler;
	}
    
    
    public static int conductoresAgregados (Scanner scanner, String usuario, String id) {
    	
    	boolean bandera = false;
    	int contador= 0;
    	
    	while (!bandera) {
    		
        System.out.print("Nombre conductor: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Fecha de nacimiento (yyyy-MM-dd): ");
        String fechaNacimiento = scanner.nextLine();

        System.out.print("Nacionalidad: ");
        String nacionalidad = scanner.nextLine();

        String imagenDocumento = usuario + ".pdf";

        System.out.print("Numero de la licencia de conduccion: ");
        String numeroLicencia = scanner.nextLine();

        System.out.print("Pais de expedicion de la licencia de conduccion: ");
        String paisExpedicionLicencia = scanner.nextLine();

        System.out.print("Fecha de vencimiento de la licencia de conduccion (yyyy-MM-dd): ");
        String fechaVencimientoLicencia = scanner.nextLine();
        
        String rutaCompleta = "datos/conductoresAdicionales.csv";
   	 	
        
        boolean archivoExiste = new File(rutaCompleta).exists();

        List<String> nuevaFila = new ArrayList<>();
        
        nuevaFila.add(id);
        nuevaFila.add(usuario);
        nuevaFila.add(nombre);
        nuevaFila.add(fechaNacimiento);
        nuevaFila.add(nacionalidad);
        nuevaFila.add(imagenDocumento);
        nuevaFila.add(numeroLicencia);
        nuevaFila.add(paisExpedicionLicencia);
        nuevaFila.add(fechaVencimientoLicencia);
     

        try (CSVWriter writer = new CSVWriter(new FileWriter(rutaCompleta, true))) {
            if (archivoExiste==false) {
                String[] encabezados = {"Id alquiler", "Usuario alquiler", "Nombre Completo",
               		 "Fecha De Nacimiento", "Nacionalidad", "Archivo Documentos", "Numero Licencia Conduccion", 
               		 "Pais Expedicion Licencia", "Fecha Vencimiento Licencia"};
                writer.writeNext(encabezados);
            }
            writer.writeNext(nuevaFila.toArray(new String[0]));
            contador++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Desea agregar otro conductor adicional? Escriba 1 para si, 0 para no: ");
        String respuesta = scanner.nextLine();
    	
        if (respuesta.equals("0")) {
        	bandera = true;
        }
        else {
        	bandera = false;
        }
    }
    	return contador;}
   
}
