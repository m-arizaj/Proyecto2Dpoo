package logica;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.ObjectUtils;
import java.io.FileReader;

public class Sede {
	
    public Sede(String nombre, String direccion, AdministradorLocal administradorLocal, List<Empleado> empleados) {
		super();
		this.nombre = nombre;
		this.direccion = direccion;
		this.administradorLocal = administradorLocal;
		this.empleados = (empleados != null) ? empleados : new ArrayList<>();
		
	}


	private String nombre;
    private String direccion;
    private AdministradorLocal administradorLocal;
    private List<Empleado> empleados;

    
    public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public AdministradorLocal getAdministradorLocal() {
        return this.administradorLocal;
    }



	public void setAdmininistradorLocal(AdministradorLocal admininistradorLocal) {
		this.administradorLocal = admininistradorLocal;
	}


	public List<Empleado> getEmpleados() {
		return empleados;
	}


	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}


	public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }
    	
    
    

}
