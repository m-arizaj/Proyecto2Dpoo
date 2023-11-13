package logica;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Seguro {
	
	private String nombre;
	private String precio;
	private String detalles;
	
	public Seguro(String nombre, String precio, String detalles) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.detalles = detalles;
	}
	
	

	public String getDetalles()
	{
		return detalles;
	}

	public void setDetalles(String detalles)
	{
		this.detalles = detalles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
	
}
