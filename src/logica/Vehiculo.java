package logica;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

public class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private String transmision;
    private String categoria;
    private String estado;
    private String pasajeros;
    private String tarifa;
    private String observaciones;
    private String ubicacion;

    public Vehiculo(String placa, String marca, String modelo, String color, String transmision, String categoria, String estado, String pasajeros,
    		String tarifa, String observaciones, String ubicacion) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.transmision = transmision;
        this.categoria = categoria;
        this.estado = estado;
        this.pasajeros = pasajeros;
        this.tarifa = tarifa;
        this.observaciones = observaciones;
        this.ubicacion = ubicacion;
        
    }

    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getPasajeros()
	{
		return pasajeros;
	}
    
    public String getTarifa()
	{
		return tarifa;
	}
    public void setPasajeros(String pasajeros)
	{
		this.pasajeros = pasajeros;
	}

	public void setTarifa(String tarifa)
	{
		this.tarifa = tarifa;
	}
	
	public String getObservaciones()
	{
		return observaciones;
	}

	public void setObservaciones(String observaciones)
	{
		this.observaciones = observaciones;
	}

	public String getUbicacion()
	{
		return ubicacion;
	}

	public void setUbicacion(String ubicacion)
	{
		this.ubicacion = ubicacion;
	}
    
    // METODOS

	public void reservar(Date fechaRecogida, Date fechaEntrega) {
        if (estado.equals("DISPONIBLE")) {
            estado = "RESERVADO";
        }
    }

    public void alquilar() {
        if (estado.equals("RESERVADO")) {
            estado = "ALQUILADO";
        }
    }

    public void devolver() {
        if (estado.equals("ALQUILADO")) {
            estado = "DISPONIBLE";
        }
    }

    public void realizarMantenimiento() {
        estado = "EN_MANTENIMIENTO";

    }

    public void finalizarMantenimiento() {
        estado = "DISPONIBLE";
    }

	public static List<String> escogerCarro(String categoria, String inicio, String ffinal) {
		String rutaCompleta = "datos/carros.csv";
   	 	String estado = "Disponible";
   	 	String categoriaEsc = null;
   	 	String placa = null;
   	 	List<String> retorno = new ArrayList<>();
   	 	List<String[]> allRows = null;
   	 	
   	 	
   	 	
   	 //escoger carro y cambiar el estado	
   	 try (CSVReader reader2 = new CSVReader(new FileReader(rutaCompleta))) {
   		try {
   	        allRows = reader2.readAll();
   	    } catch (CsvException csvException) {
   	        csvException.printStackTrace();
   	    }
         boolean categoryFound = false;
         List<String> availableCategories = new ArrayList<>();
         Map<String, Double> categoryRates = new HashMap<>();
         double originalCategoryRate = 0;

         for (String[] row : allRows) {
             if (!categoryFound && row[5].equals(categoria) && row[6].equals(estado)) {
                 placa = row[0];
                 categoriaEsc = categoria;
                 categoryFound = true;
             } else if (row[5].equals(categoria)) {
                 originalCategoryRate = Double.parseDouble(row[8]);
             }
         }

         if (!categoryFound) {
             for (String[] row : allRows) {
                 if (row[6].equals("Disponible")) {
                     availableCategories.add(row[5]);
                     categoryRates.put(row[5], Double.parseDouble(row[8]));
                 }
             }
             Collections.sort(availableCategories, (cat1, cat2) -> Double.compare(categoryRates.get(cat1), categoryRates.get(cat2)));

             for (String category : availableCategories) {
                 if (!categoryFound && categoryRates.get(category) > originalCategoryRate) {
                     categoriaEsc = category;
                     break;
                 }
             }
         }
     } catch (IOException e) {
         e.printStackTrace();
     }

     boolean bandera = false;
     try (CSVWriter writer = new CSVWriter(new FileWriter(rutaCompleta))) {
         for (String[] row : allRows) {
             if (row[5].equals(categoriaEsc) && row[6].equals(estado) && !bandera) {
                 String[] newRow = {placa, row[1], row[2], row[3], row[4], row[5], "Alquilado", row[7], row[8],
                         "Alquilado desde " + inicio + " hasta " + ffinal, "Fuera de las sedes"};
                 writer.writeNext(newRow);
                 bandera= true;
             } else {
                 writer.writeNext(row);
             }
         }
     } catch (IOException e) {
         e.printStackTrace();
     }

     retorno.add(categoriaEsc);
     retorno.add(placa);
     return retorno;
	}
}
	
	

