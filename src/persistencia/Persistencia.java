package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVReader;

import logica.Cliente;
import logica.Empleado;
import logica.Reserva;
import logica.Sede;
import logica.Seguro;
import logica.SistemaAlquiler;
import logica.Vehiculo;

public class Persistencia {
	
	public void cargarDatos(SistemaAlquiler sistema,String rutaVehiculos, String rutaClientes, String rutaEmpleados, String rutaReservas, String rutaSeguros) throws CsvValidationException, NumberFormatException {
        
		leerVehiculos(sistema,rutaVehiculos);
        leerClientes(sistema,rutaClientes);
        leerEmpleados(sistema,rutaEmpleados);
        leerReservas(sistema,rutaReservas);
        leerSeguros(sistema,rutaSeguros);

}

	public static void leerSeguros(SistemaAlquiler sistema, String rutaSeguros) throws CsvValidationException, NumberFormatException {
	    try {
	        CSVReader csvReader = new CSVReaderBuilder(new FileReader(rutaSeguros))
	            .withSkipLines(1)
	            .build();
	        String[] linea;
	        while ((linea = csvReader.readNext()) != null) {
	            String nombre = linea[0];
	            String precio = linea[1];
	            String detalles = linea[2];
	            sistema.agregarSeguro(nombre, precio, detalles);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	public static void leerReservas(SistemaAlquiler sistema, String rutaReservas) throws CsvValidationException {
	    try {
	        CSVReader csvReader = new CSVReaderBuilder(new FileReader(rutaReservas))
	            .withSkipLines(1)
	            .build();
	        String[] linea;
	        while ((linea = csvReader.readNext()) != null) {
	            String id = linea[0];
	            String categoria = linea[1];
	            String usuarioCliente = linea[2];
	            String sedeRecogida = linea[3];
	            String sedeEntrega = linea[4];
	            String fechaRecogida = linea[5];
	            String fechaEntrega = linea[6];
	            String diasFacturados = linea[7];
	            String costoParcial = linea[8];
	            String costoTreinta = linea[9];
	            sistema.agregarReserva(id, categoria, usuarioCliente, sedeRecogida, sedeEntrega, fechaRecogida, fechaEntrega,diasFacturados, costoParcial,costoTreinta);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static void leerEmpleados(SistemaAlquiler sistema, String rutaEmpleados) {
	    try {
	        CSVReader csvReader = new CSVReaderBuilder(new FileReader(rutaEmpleados))
	            .withSkipLines(1)
	            .build();
	        String[] linea;

	        while ((linea = csvReader.readNext()) != null) {
	            String nombreUsuario = linea[0];
	            String contrasena = linea[1];
	            String nombreCompleto = linea[2];
	            String cargo = linea[3];
	            String sedeNombre = linea[4];
	            
	            Sede sede = null;
	            for (Sede s : sistema.getSedes()) {
	                if (s.getNombre().equals(sedeNombre)) {
	                    sede = s;
	                    break;
	                }
	            }

	            if (sede != null) {
	                Empleado empleado = new Empleado(nombreUsuario, contrasena, nombreCompleto, cargo, sede);
	                sede.agregarEmpleado(empleado);
	            } else {
	                System.out.println("No se encontró la sede correspondiente para el empleado: " + nombreUsuario);
	            }
	        }
	    } catch (IOException | CsvValidationException e) {
	        e.printStackTrace();
	    }
	}

	public static void leerClientes(SistemaAlquiler sistema, String rutaClientes) throws CsvValidationException {
	    try {
	        CSVReader csvReader = new CSVReaderBuilder(new FileReader(rutaClientes))
	            .withSkipLines(1)
	            .build();
	        String[] linea;
	        while ((linea = csvReader.readNext()) != null) {
	            String nombreUsuario = linea[0];
	            String contrasena = linea[1];
	            String nombre = linea[2];
	            String numeroTelefonico = linea[3];
	            String correo = linea[4];
	            String fechaNacimiento = linea[5];
	            String nacionalidad = linea[6];
	            String numeroLicencia = linea[7];
	            String paisExpedicionLicencia = linea[8];
	            String fechaVencimientoLicencia = linea[9];
	            String datosTarjetaCredito = linea[10];
	            sistema.agregarCliente(nombreUsuario, contrasena, nombre, numeroTelefonico, correo, fechaNacimiento, nacionalidad, numeroLicencia, paisExpedicionLicencia, fechaVencimientoLicencia, datosTarjetaCredito);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static void leerVehiculos(SistemaAlquiler sistema, String rutaVehiculos) {
	    try {
	        CSVReader csvReader = new CSVReaderBuilder(new FileReader(rutaVehiculos))
	            .withSkipLines(1)
	            .build();
	        String[] linea;
	        while ((linea = csvReader.readNext()) != null) {
	            String placa = linea[0];
	            String marca = linea[1];
	            String modelo = linea[2];
	            String color = linea[3];
	            String transmision = linea[4];
	            String categoria = linea[5];
	            String estado = linea[6];
	            String cantidadPasajeros = linea[7];
	            String tarifaDiaria = linea[8];
	            String observaciones = linea[9];
	            String ubicacion = linea[10];
	            String tipoVehiculo = linea[11];
	            
	            sistema.agregarVehiculo(placa, marca, modelo, color, transmision, categoria, estado, cantidadPasajeros,
	                    tarifaDiaria, observaciones, ubicacion, tipoVehiculo);
	        }
	    } catch (IOException | CsvValidationException e) {
	        e.printStackTrace();
	    }
	}


	public static void escribirSeguros(SistemaAlquiler sistema, String rutaSeguros) {
        try {
            FileWriter fileWriter = new FileWriter(rutaSeguros);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            String[] header = {"Nombre", "Precio", "Detalles"};
            csvWriter.writeNext(header);

            List<Seguro> seguros = sistema.getSeguros(); 

            for (Seguro seguro : seguros) {
                String nombre = seguro.getNombre();
                String precio = seguro.getPrecio();
                String detalles = seguro.getDetalles();

                String[] data = {nombre, precio, detalles};
                csvWriter.writeNext(data);
            }

            csvWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static void escribirReservas(SistemaAlquiler sistema, String rutaReservas) {
	        try {

	            FileWriter fileWriter = new FileWriter(rutaReservas);


	            CSVWriter csvWriter = new CSVWriter(fileWriter);


	            String[] header = {
	            		"Id reserva", "Categoria escogida", "Usuario del cliente", "Sede de recogida", "Sede de entrega",
	               		 "Fecha de inicio alquiler", "Fecha fin alquiler","Dias facturados", "Costo sin adicionales", " Treinta por ciento costo"
	            };

	            csvWriter.writeNext(header);


	            for (Reserva reserva : sistema.getReservas()) {
	                String[] data = {
	                    reserva.getId(),
	                    reserva.getCategoria(),
	                    reserva.getUsuarioCliente(),
	                    reserva.getSedeRecogida(),
	                    reserva.getSedeEntrega(),
	                    reserva.getFechaRecogida(),
	                    reserva.getFechaEntrega(),
	                    reserva.getDiasFacturados(),
	                    reserva.getCostoParcial(),
	                    reserva.getCostoTreinta()
	                };

	                csvWriter.writeNext(data);
	            }
	            csvWriter.close();
	            fileWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	public static void escribirClientes(SistemaAlquiler sistema, String rutaClientes) {
	    try {

	        FileWriter fileWriter = new FileWriter(rutaClientes);

	        CSVWriter csvWriter = new CSVWriter(fileWriter);

	        String[] header = {
	            "NombreUsuario", "Contrasena", "Nombre", "NumeroTelefonico", "Correo",
	            "FechaNacimiento", "Nacionalidad", "NumeroLicencia", "PaisExpedicionLicencia",
	            "FechaVencimientoLicencia"
	        };
	        csvWriter.writeNext(header);

	        for (Cliente cliente : sistema.getClientes()) {
	            String[] data = {
	                cliente.getNombreUsuario(),
	                cliente.getContrasena(),
	                cliente.getNombre(),
	                cliente.getNumeroTelefonico(),
	                cliente.getCorreo(),
	                cliente.getFechaNacimiento(),
	                cliente.getNacionalidad(),
	                cliente.getNumeroLicencia(),
	                cliente.getPaisExpedicionLicencia(),
	                cliente.getFechaVencimientoLicencia()
	            };
	            csvWriter.writeNext(data);
	        }

	        csvWriter.close();
	        fileWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static void escribirEmpleados(SistemaAlquiler sistema, String rutaEmpleados) {
    try {

        FileWriter fileWriter = new FileWriter(rutaEmpleados);


        CSVWriter csvWriter = new CSVWriter(fileWriter);


        String[] header = {"NombreUsuario", "Contrasena", "Nombre", "Cargo", "SedeNombre"};
        csvWriter.writeNext(header);

        for (Empleado empleado : sistema.getEmpleados()) {
            String[] data = {
                empleado.getNombreUsuario(),
                empleado.getContrasena(),
                empleado.getNombre(),
                empleado.getCargo(),
                (empleado.getSede() != null) ? empleado.getSede().getNombre() : "" 
            };
            csvWriter.writeNext(data);
        }

        csvWriter.close();
        fileWriter.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

	public static void escribirVehiculos(SistemaAlquiler sistema, String rutaVehiculos) {
		try {

	        FileWriter fileWriter = new FileWriter(rutaVehiculos);


	        CSVWriter csvWriter = new CSVWriter(fileWriter);


	        String[] header = {"Placa", "Marca", "Modelo", "Color", "Transmisión", "Categoría", "Estado", "Pasajeros", "Tarifa", 
	        		"Observaciones", "Ubicacion", "Tipo"};

	        		
	        csvWriter.writeNext(header);


	        for (Vehiculo vehiculo : sistema.getVehiculos()) {
	            String[] data = {
	                vehiculo.getPlaca(),
	                vehiculo.getMarca(),
	                vehiculo.getModelo(),
	                vehiculo.getColor(),
	                vehiculo.getTransmision(),
	                vehiculo.getCategoria(),
	                vehiculo.getEstado(),
	                vehiculo.getPasajeros(),
	                vehiculo.getTarifa(),
	                vehiculo.getObservaciones(),
	                vehiculo.getUbicacion(),
	                vehiculo.getTipoVehiculo()
	            };
	            csvWriter.writeNext(data);
	        }


	        csvWriter.close();
	        fileWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	 public static void escribirEventosVehiculos(SistemaAlquiler sistema, String rutaEventos) {
	        try {
	            FileWriter fileWriter = new FileWriter(rutaEventos);
	            CSVWriter csvWriter = new CSVWriter(fileWriter);

	            String[] header = {"placa", "evento"};
	            csvWriter.writeNext(header);
	            Map<String, List<String>> eventos = sistema.getEventosVehiculos();

	            for (Map.Entry<String, List<String>> entry : eventos.entrySet()) {
	                String placa = entry.getKey();
	                List<String> eventosPlaca = entry.getValue();
	                for (String evento : eventosPlaca) {
	                    String[] data = {placa, evento};
	                    csvWriter.writeNext(data);
	                }
	            }

	            csvWriter.close();
	            fileWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	 public static void archivoLog(List<String> eventos, String rutaDirectorio) {
		    try {
		        String rutaArchivo = rutaDirectorio + File.separator + "archivoLog.csv";
		        FileWriter fileWriter = new FileWriter(rutaArchivo);
		        CSVWriter csvWriter = new CSVWriter(fileWriter);
		        
		        String[] header = {"eventos"};
		        csvWriter.writeNext(header);

		        for (String evento : eventos) {
		            String[] data = {evento};
		            csvWriter.writeNext(data);
		        }

		        csvWriter.close();
		        fileWriter.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

	
	public static boolean verificarUsuario(String usuario, String contrasena) {
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
	
	public static String obtenerEstadoVehiculo(String placa) throws CsvValidationException 
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
	
public static void actualizarEstadoVehiculo(String placa, String nuevoEstado) throws CsvException {
    
    	
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

public static void actualizarEstadoYFechaReintegro(String placa, String nuevoEstado, String explicacionProblema, String fechaReintegro) throws CsvException {
    
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

public static void guardarConductorAdicional(String idReserva, String nombre, String fechaNacimiento,
		String nacionalidad, String numeroLicencia, String paisExpedicionLicencia, String fechaVencimientoLicencia) {

	try {
		FileWriter writer = new FileWriter("./datos/conductoresAdicionales.csv", true);
		CSVWriter csvWriter = new CSVWriter(writer);
		String[] nuevoConductorAdicional = { idReserva, nombre, fechaNacimiento, nacionalidad, numeroLicencia,
				paisExpedicionLicencia, fechaVencimientoLicencia };
		csvWriter.writeNext(nuevoConductorAdicional);
		csvWriter.close();
	} catch (IOException e) {
		e.printStackTrace(); // Manejo básico de errores, ajusta según tus necesidades
	}
}

public static String[] obtenerReservaPorID(String idReserva) {
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

public static void completarReservaConSeguro(String[] reserva, String tipoSeguro) throws CsvException {
    
    try {
        CSVReader reader = new CSVReader(new FileReader("/datos/alquileres.csv"));
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

	
}