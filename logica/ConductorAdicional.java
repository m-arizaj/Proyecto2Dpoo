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
    
    
    public static void conductoresAgregados (String id, String usuario, String nombre, String fechaNacimiento, String nacionalidad,
    		String numeroLicencia, String paisExpedicionLicencia, String fechaVencimientoLicencia) {
    	
        
        String rutaCompleta = "datos/conductoresAdicionales.csv";
   	 	
        
        boolean archivoExiste = new File(rutaCompleta).exists();

        List<String> nuevaFila = new ArrayList<>();
        
        
        
        nuevaFila.add(id);
        nuevaFila.add(usuario);
        nuevaFila.add(nombre);
        nuevaFila.add(fechaNacimiento);
        nuevaFila.add(nacionalidad);
        nuevaFila.add(usuario + ".pdf");
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
 
        } catch (IOException e) {
            e.printStackTrace();
        }
     
    
    	}
   
}
