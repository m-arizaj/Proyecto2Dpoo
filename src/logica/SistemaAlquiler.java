package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.CSVWriter;
import java.text.SimpleDateFormat;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.ObjectUtils;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import java.util.UUID;
import interfaz.*;
import persistencia.Persistencia;


public class SistemaAlquiler {
    private List<Sede> sedes;
    private List<Vehiculo> inventario;
    private List<Cliente> clientes;
	private List<Reserva> reservas;
    private List<Seguro> seguros;
    private HashMap<String, List<String>> historialVehiculos;
    private static String subtotal;
    private static String treintapor;

    public SistemaAlquiler() {
    	List<Sede> sedes = new ArrayList<>();
    	List<Vehiculo> inventario = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        List<Reserva> reservas = new ArrayList<>();
        List<Seguro> seguros = new ArrayList<>();
        HashMap<String, List<String>> historialVehiculos = new HashMap<String, List<String>>();
    	
        this.sedes = sedes;
        this.inventario = inventario;
        this.clientes = clientes;
        this.reservas = reservas;
        this.seguros = seguros;
        this.historialVehiculos = historialVehiculos;
    }
    
    
    public AdministradorLocal agregarAdmLocalBogota(String nombreUsuario, String contrasena, Sede sede) {
    	AdministradorLocal administradorLocalNuestroBogota = new AdministradorLocal(nombreUsuario,contrasena,sede);
    	return administradorLocalNuestroBogota;
    }
    
    public AdministradorLocal agregarAdmLocalDorado(String nombreUsuario, String contrasena, Sede sede) {
    	AdministradorLocal administradorLocalDorado = new AdministradorLocal(nombreUsuario,contrasena,sede);
    	return administradorLocalDorado;
    }
    
    public Sede agregarSede(String nombre, String direccion, AdministradorLocal administradorLocal, List<Empleado> empleados) {
    	Sede sede = new Sede(nombre,direccion,administradorLocal,empleados);
    	sedes.add(sede);
		return sede;
    }
    
    
    
    public boolean autenticarCliente(String nombreUsuario, String contrasena) {
        for (Cliente cliente : clientes) {
            if (cliente.getNombreUsuario().equals(nombreUsuario) && cliente.getContrasena().equals(contrasena)) {
                return true; 
            }
        }
        return false; 
    }


    public Administrador nuevoAdministrador(String usuario,String contraseña) {
        Administrador administrador = new Administrador(usuario,contraseña);
		return administrador;
    }
    

    public void agregarVehiculo(String placa, String marca, String modelo, String color, String transmision, String categoria, 
    		String estado, String pasajeros, String tarifa, String observaciones, String ubicacion, String tipoVehiculo) {
        Vehiculo nuevoVehiculo = new Vehiculo(placa, marca, modelo, color, transmision, categoria, estado, pasajeros, tarifa, observaciones, ubicacion, tipoVehiculo);
        inventario.add(nuevoVehiculo);
        Persistencia.escribirVehiculos(this,"datos/vehiculos.csv");
        agregarEventoAlHistorial(placa, "Se elimino el auto con placa " + placa + " del invetario.");
        Persistencia.escribirEventosVehiculos(this,"datos/eventos.csv");
    }
    


    public boolean eliminarAuto(String placaEliminar) {
        Iterator<Vehiculo> iterator = inventario.iterator();
        while (iterator.hasNext()) {
            Vehiculo vehiculo = iterator.next();
            if (vehiculo.getPlaca().equals(placaEliminar)) {
                iterator.remove(); 
                Persistencia.escribirVehiculos(this,"datos/vehiculos.csv");
                agregarEventoAlHistorial(placaEliminar, "Se elimino el auto con placa " + placaEliminar + " del invetario.");
                Persistencia.escribirEventosVehiculos(this,"datos/eventos.csv");
                return true;
            }
        }
		return false;
    }


    public void realizarReserva(String nombre, String categoriaSelecc, 
    		String fechaInicio, String fechaFin, String recoger, String entregar) {
    		           
            int tiposDiferentes = 0;
            Set<String> tiposImpresos = new HashSet<>();
            List<List<String>> listaDeListas = new ArrayList<>();
            List<Vehiculo> inventario = getVehiculos();
            
            for (int i = 0; i< inventario.size(); i++) {
            	Vehiculo objeto = inventario.get(i);
            	List<String> lista = new ArrayList<>();
            	String categoria = objeto.getCategoria();
            	if (!tiposImpresos.contains(categoria)&& tiposDiferentes < 16) {
                    String numeroPasajeros = objeto.getPasajeros();
                    String tarifaDiaria = objeto.getTarifa();
                    lista.add(categoria);
                    lista.add(tarifaDiaria);
                    lista.add(numeroPasajeros);
                    
                    tiposDiferentes++;
                    tiposImpresos.add(categoria);
                    listaDeListas.add(lista);
            	}
            	
            }
            PanelCliente.completarDatosReserva(nombre, listaDeListas, categoriaSelecc, 
            		fechaInicio, fechaFin, recoger, entregar);
            

    }
    
    
    
    public static String getSubtotal()
	{
		return subtotal;
	}


	public static String getTreintapor()
	{
		return treintapor;
	}


    public void devolverVehiculo(Vehiculo vehiculo) {

    }

	public List<Vehiculo> getVehiculos() {
		return inventario;
	}
	
	public List<Seguro> getSeguros() {
		return seguros;
	}

	public void agregarSeguro(String nombreSeguro, String precioSeguro, String detalles) {
		Seguro nuevoSeguro = new Seguro(nombreSeguro, precioSeguro, detalles);
        seguros.add(nuevoSeguro);
	}
	
	public boolean eliminarSeguro(String nombreSeguro) {
	    for (Seguro seguro : seguros) {
	        if (seguro.getNombre().equalsIgnoreCase(nombreSeguro)) {
	            seguros.remove(seguro);
	            return true;
	        }
	    }
		return false;
	}



	public List<Sede> getSedes() {
	    return sedes;
	}

	
	public List<AdministradorLocal> getAdministradoresLocales() {
        List<AdministradorLocal> administradoresLocales = new ArrayList<>();
        for (Sede sede : sedes) {
            AdministradorLocal administradorLocal = sede.getAdministradorLocal();
            if (administradorLocal != null) {
                administradoresLocales.add(administradorLocal);
            }
        }
        return administradoresLocales;
    }
	
	
	public AdministradorLocal autenticarAdmiLocal(List<AdministradorLocal> administradoresLocales, String nombreUsuario, String contrasena) {
	    for (AdministradorLocal adminLocal : administradoresLocales) {
	        if (nombreUsuario.equals(adminLocal.getNombreUsuario()) && contrasena.equals(adminLocal.getContrasena())) {
	            return adminLocal;
	        }
	    }
	    return null;
	}


	public List<Empleado> getEmpleadosPorSede(Sede sede) {
        List<Empleado> empleadosPorSede = new ArrayList<>();
        for (Empleado empleado : sede.getEmpleados()) {
            if (empleado.getSede().getNombre().equals(sede.getNombre())) {
                empleadosPorSede.add(empleado);
            }
        }
        return empleadosPorSede;
    }



	public void crearEmpleado(String nombreUsuario, String contrasena, String nombre, String cargo, Sede sede) {
		
		Empleado nuevoEmpleado = new Empleado(nombreUsuario,contrasena,nombre,cargo,sede);
		sede.agregarEmpleado(nuevoEmpleado);
		
		
	}



	public void agregarReserva(String id, String categoria, String cliente, String sedeRecogida, String sedeEntrega,
			String fechaRecogida, String fechaEntrega,String diasFacturados, String costoParcial, String costoTreinta) {
		
		Reserva nuevaReserva = new Reserva(id, categoria, cliente, sedeRecogida, sedeEntrega, fechaRecogida, fechaEntrega,diasFacturados, costoParcial,costoTreinta);
        reservas.add(nuevaReserva);
	}
	
	public void agregarCliente(String nombreUsuario, String contrasena, String nombre, String numeroTelefonico,String correo,
    		String fechaNacimiento, String nacionalidad,
            String numeroLicencia, String paisExpedicionLicencia,
            String fechaVencimientoLicencia, String datosTarjetaCredito) {
		
		Cliente cliente = new Cliente(nombreUsuario, contrasena, nombre, numeroTelefonico, correo, fechaNacimiento, nacionalidad, numeroLicencia, paisExpedicionLicencia, fechaVencimientoLicencia, datosTarjetaCredito);
        clientes.add(cliente);
	}


	public List<Empleado> getEmpleados() {
        List<Empleado> todosLosEmpleados = new ArrayList<>();
        for (Sede sede : sedes) {
            todosLosEmpleados.addAll(sede.getEmpleados());
        }

        return todosLosEmpleados;
    }


	public List<Cliente> getClientes() {
	    List<Cliente> todosLosClientes = new ArrayList<>();

	    for (Cliente cliente : clientes) {
	        todosLosClientes.add(cliente);
	    }

	    return todosLosClientes;
	}


	public List<Reserva> getReservas() {
        return reservas;
    }
	
	public static void cargarReserva(String id, List<String> datos, String cliente, List<String> recogida, List<String> entregada, 
    		String inicio, String fin, double dias) {
    	String rutaCompleta = "datos/reservas.csv";
    	boolean archivoExiste = new File(rutaCompleta).exists();
        List<String> nuevaFila = new ArrayList<>();
    	
        String tarifa_diaria=datos.get(1);
    	int valorsinext= Integer.parseInt(tarifa_diaria)* (int)dias;
    	double treinta = valorsinext * 0.30;
    	String categoria = datos.get(0);
    	String rec = recogida.get(0);
    	String ent = entregada.get(0);
    	
    	nuevaFila.add(id);
    	nuevaFila.add(categoria);
    	nuevaFila.add(cliente);
    	nuevaFila.add(rec);
    	nuevaFila.add(ent);
    	nuevaFila.add(inicio);
    	nuevaFila.add(fin);
    	nuevaFila.add(Double.toString(dias));
    	nuevaFila.add(Integer.toString(valorsinext));
    	nuevaFila.add(Double.toString(treinta));
    	
    	
    	
    	try (CSVWriter writer = new CSVWriter(new FileWriter(rutaCompleta, true))) {
            if (archivoExiste==false) {
                String[] encabezados = {"Id reserva", "Categoria escogida", "Usuario del cliente", "Sede de recogida", "Sede de entrega",
               		 "Fecha de inicio alquiler", "Fecha fin alquiler","Dias facturados", "Costo sin adicionales", " Treinta por ciento costo"};
                writer.writeNext(encabezados);
            }
            writer.writeNext(nuevaFila.toArray(new String[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	subtotal = Integer.toString(valorsinext);
    	treintapor = Integer.toString((int)treinta);
    	
    	
    	
    }
	




	 public boolean autenticarEmpleado(String nombreUsuario, String contrasena) {
	        for (Sede sede : sedes) {
	            List<Empleado> empleadosSede = sede.getEmpleados();
	            for (Empleado empleado : empleadosSede) {
	                if (empleado.getNombreUsuario().equals(nombreUsuario) && empleado.getContrasena().equals(contrasena)) {
	                    return true; 
	                }
	            }
	        }
	        return false; 
	    }


	 public void agregarEventoAlHistorial(String placaVehiculo, String evento) {
	        if (historialVehiculos.containsKey(placaVehiculo)) {
	            List<String> historial = historialVehiculos.get(placaVehiculo);
	            historial.add(evento);
	        } else {
	            List<String> nuevoHistorial = new ArrayList<>();
	            nuevoHistorial.add(evento);
	            historialVehiculos.put(placaVehiculo, nuevoHistorial);
	        }    
	    }
	 
	 public HashMap<String, List<String>> getEventosVehiculos() {
			return historialVehiculos;
		}


	public void setHistorialVehiculos(HashMap<String, List<String>> historialVehiculos) {
		this.historialVehiculos = historialVehiculos;
	}


	 public List<String> buscarEventosPorPlaca(String placa) {
	        if (historialVehiculos.containsKey(placa)) {
	            return historialVehiculos.get(placa);
	        } else {
	            return new ArrayList<>();
	        }
	    }


	 public Map<String, String> obtenerSeguros() {
	        Map<String, String> mapaSeguros = new HashMap<>();
	        for (Seguro seguro : seguros) {
	            mapaSeguros.put(seguro.getNombre(), seguro.getPrecio());
	        }
	        return mapaSeguros;
	    }
	 
}



		



