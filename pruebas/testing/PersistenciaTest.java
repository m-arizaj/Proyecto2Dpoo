package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import logica.Reserva;
import logica.SistemaAlquiler;
import persistencia.Persistencia;

class PersistenciaTest {
	
	

	@Test
	void testLeerVehiculos() {
		SistemaAlquiler sistema = new SistemaAlquiler();
		String rutaVehiculos = "datos/vehiculoPrueba";

		// Contenido simulado del archivo de vehículos
		List<String[]> contenidoVehiculos = Arrays.asList(
				new String[] { "Placa", "Marca", "Modelo", "Color", "Transmisión", "Categoría", "Estado", "Pasajeros",
						"Tarifa", "Observaciones", "tipoVehiculo" },
				new String[] { "ABC123", "Toyota", "Corolla", "Negro", "Automática", "Sedán", "Disponible", "5", "50",
						"Buen estado", "Automóvil" },
				new String[] { "XYZ789", "Honda", "Civic", "Rojo", "Automática", "Sedán", "Ocupado", "4", "60",
						"Algunos rasguños", "Automóvil" });

		TestUtils.escribirContenidoEnArchivo(rutaVehiculos, contenidoVehiculos);

		try {
			Persistencia.leerVehiculos(sistema, rutaVehiculos);
		} catch (Exception e) {
			fail("Error al leer vehículos: " + e.getMessage());
		}

		// Verificar que los vehículos se hayan cargado correctamente en el sistema
		assertEquals(2, sistema.getVehiculos().size());

		// Verificar propiedades del primer vehículo
		assertEquals("ABC123", sistema.getVehiculos().get(0).getPlaca());
		assertEquals("Toyota", sistema.getVehiculos().get(0).getMarca());
		assertEquals("Corolla", sistema.getVehiculos().get(0).getModelo());
		assertEquals("Negro", sistema.getVehiculos().get(0).getColor());
		assertEquals("Automática", sistema.getVehiculos().get(0).getTransmision());
		assertEquals("Sedán", sistema.getVehiculos().get(0).getCategoria());
		assertEquals("Disponible", sistema.getVehiculos().get(0).getEstado());
		assertEquals("5", sistema.getVehiculos().get(0).getPasajeros());
		assertEquals("50", sistema.getVehiculos().get(0).getTarifa());
		assertEquals("Buen estado", sistema.getVehiculos().get(0).getObservaciones());
		assertEquals("Automóvil", sistema.getVehiculos().get(0).getTipoVehiculo());

		// Verificar propiedades del segundo vehículo
		assertEquals("XYZ789", sistema.getVehiculos().get(1).getPlaca());
		assertEquals("Honda", sistema.getVehiculos().get(1).getMarca());
		assertEquals("Civic", sistema.getVehiculos().get(1).getModelo());
		assertEquals("Rojo", sistema.getVehiculos().get(1).getColor());
		assertEquals("Automática", sistema.getVehiculos().get(1).getTransmision());
		assertEquals("Sedán", sistema.getVehiculos().get(1).getCategoria());
		assertEquals("Ocupado", sistema.getVehiculos().get(1).getEstado());
		assertEquals("4", sistema.getVehiculos().get(1).getPasajeros());
		assertEquals("60", sistema.getVehiculos().get(1).getTarifa());
		assertEquals("Algunos rasguños", sistema.getVehiculos().get(1).getObservaciones());
		assertEquals("Automóvil", sistema.getVehiculos().get(1).getTipoVehiculo());
	}
	
	@Test
	void testLeerReservas() {
	    SistemaAlquiler sistema = new SistemaAlquiler();
	    String rutaReservas = "datos/reservasPrueba";

	    // Contenido simulado del archivo de reservas
	    List<String[]> contenidoReservas = Arrays.asList(
	            new String[]{"Id reserva", "Categoria escogida", "Usuario del cliente", "Sede de recogida", "Sede de entrega",
	                    "Fecha de inicio alquiler", "Fecha fin alquiler", "Dias facturados", "Costo sin adicionales", "Treinta por ciento costo"},
	            new String[]{"95f1aef9-e9a0-4daa-b1de-89cac118b4ce", "PX - 4x4 especial automatico", "laura118",
	                    "Aeropuerto El Dorado", "Aeropuerto El Dorado", "10/15/2023 13:00", "10/20/2023 13:00", "5", "1827250", "548175"},
	            new String[]{"512da6a5-441e-4f5c-814b-8ac9551c288e", "LY -Electrico automatico", "laura118",
	                    "Aeropuerto El Dorado", "Aeropuerto El Dorado", "10/17/2023 12:30", "10/23/2023 11:30", "6", "1500000", "450000"}
	    );

	    TestUtils.escribirContenidoEnArchivo(rutaReservas, contenidoReservas);

	    try {
	        Persistencia.leerReservas(sistema, rutaReservas);
	    } catch (Exception e) {
	        fail("Error al leer reservas: " + e.getMessage());
	    }

	    // Verificar que las reservas se hayan cargado correctamente en el sistema
	    assertEquals(2, sistema.getReservas().size());

	    // Verificar propiedades de la primera reserva
	    Reserva reserva1 = sistema.getReservas().get(0);
	    assertEquals("95f1aef9-e9a0-4daa-b1de-89cac118b4ce", reserva1.getId());
	    assertEquals("PX - 4x4 especial automatico", reserva1.getCategoria());
	    assertEquals("laura118", reserva1.getUsuarioCliente());
	    assertEquals("Aeropuerto El Dorado", reserva1.getSedeRecogida());
	    assertEquals("Aeropuerto El Dorado", reserva1.getSedeEntrega());
	    assertEquals("10/15/2023 13:00", reserva1.getFechaRecogida());
	    assertEquals("10/20/2023 13:00", reserva1.getFechaEntrega());
	    assertEquals("5", reserva1.getDiasFacturados());
	    assertEquals("1827250", reserva1.getCostoParcial());
	    assertEquals("548175", reserva1.getCostoTreinta());

	    // Verificar propiedades de la segunda reserva
	    Reserva reserva2 = sistema.getReservas().get(1);
	    assertEquals("512da6a5-441e-4f5c-814b-8ac9551c288e", reserva2.getId());
	    assertEquals("LY -Electrico automatico", reserva2.getCategoria());
	    assertEquals("laura118", reserva2.getUsuarioCliente());
	    assertEquals("Aeropuerto El Dorado", reserva2.getSedeRecogida());
	    assertEquals("Aeropuerto El Dorado", reserva2.getSedeEntrega());
	    assertEquals("10/17/2023 12:30", reserva2.getFechaRecogida());
	    assertEquals("10/23/2023 11:30", reserva2.getFechaEntrega());
	    assertEquals("6", reserva2.getDiasFacturados());
	    assertEquals("1500000", reserva2.getCostoParcial());
	    assertEquals("450000", reserva2.getCostoTreinta());
	}

	
	@Test
	void testLeerClientes() {
	    SistemaAlquiler sistema = new SistemaAlquiler();
	    String rutaClientes = "datos/clientePrueba";

	    // Contenido simulado del archivo de clientes
	    List<String[]> contenidoClientes = Arrays.asList(
	            new String[]{"NombreUsuario", "Contrasena", "Nombre", "NumeroTelefonico", "Correo", "FechaNacimiento",
	                    "Nacionalidad", "NumeroLicencia", "PaisExpedicionLicencia", "FechaVencimientoLicencia",
	                    "DatosTarjetaCredito"},
	            new String[]{"laura118", "92347", "Laura Mendez", "3014587963", "laura.mendez@outlook.com", "24/12/1995",
	                    "Colombiana", "1000652839", "Colombia", "11/10/2027", "1234567891011121-123-11/2030"},
	            new String[]{"andres241", "78506", "Andres Soto", "3123456789", "andres.soto@outlook.com", "5/3/1998",
	                    "Colombiana", "1000028765", "Colombia", "11/2/2031", "3486512397641234-965-12/2029"}
	    );

	    TestUtils.escribirContenidoEnArchivo(rutaClientes, contenidoClientes);

	    try {
	        Persistencia.leerClientes(sistema, rutaClientes);
	    } catch (Exception e) {
	        fail("Error al leer clientes: " + e.getMessage());
	    }

	    // Verificar que los clientes se hayan cargado correctamente en el sistema
	    assertEquals(2, sistema.getClientes().size());

	    // Verificar propiedades del primer cliente
	    assertEquals("laura118", sistema.getClientes().get(0).getNombreUsuario());
	    assertEquals("92347", sistema.getClientes().get(0).getContrasena());
	    assertEquals("Laura Mendez", sistema.getClientes().get(0).getNombre());
	    assertEquals("3014587963", sistema.getClientes().get(0).getNumeroTelefonico());
	    assertEquals("laura.mendez@outlook.com", sistema.getClientes().get(0).getCorreo());
	    assertEquals("24/12/1995", sistema.getClientes().get(0).getFechaNacimiento());
	    assertEquals("Colombiana", sistema.getClientes().get(0).getNacionalidad());
	    assertEquals("1000652839", sistema.getClientes().get(0).getNumeroLicencia());
	    assertEquals("Colombia", sistema.getClientes().get(0).getPaisExpedicionLicencia());
	    assertEquals("11/10/2027", sistema.getClientes().get(0).getFechaVencimientoLicencia());
	    assertEquals("1234567891011121-123-11/2030", sistema.getClientes().get(0).getDatosTarjetaCredito());

	    // Verificar propiedades del segundo cliente
	    assertEquals("andres241", sistema.getClientes().get(1).getNombreUsuario());
	    assertEquals("78506", sistema.getClientes().get(1).getContrasena());
	    assertEquals("Andres Soto", sistema.getClientes().get(1).getNombre());
	    assertEquals("3123456789", sistema.getClientes().get(1).getNumeroTelefonico());
	    assertEquals("andres.soto@outlook.com", sistema.getClientes().get(1).getCorreo());
	    assertEquals("5/3/1998", sistema.getClientes().get(1).getFechaNacimiento());
	    assertEquals("Colombiana", sistema.getClientes().get(1).getNacionalidad());
	    assertEquals("1000028765", sistema.getClientes().get(1).getNumeroLicencia());
	    assertEquals("Colombia", sistema.getClientes().get(1).getPaisExpedicionLicencia());
	    assertEquals("11/2/2031", sistema.getClientes().get(1).getFechaVencimientoLicencia());
	    assertEquals("3486512397641234-965-12/2029", sistema.getClientes().get(1).getDatosTarjetaCredito());
	}

	
	@Test
	void testLeerEmpleados() {
	    SistemaAlquiler sistema = new SistemaAlquiler();
	    String rutaEmpleados = "datos/empleadosPrueba";

	    // Contenido simulado del archivo de empleados
	    List<String[]> contenidoEmpleados = Arrays.asList(
	            new String[]{"NombreUsuario", "Contrasena", "NombreCompleto", "Cargo", "SedeNombre"},
	            new String[]{"juan123", "45678", "Juan Perez", "AgenteVentas", "Aeropuerto El Dorado"},
	            new String[]{"ana789", "98765", "Ana Rodriguez", "GerenteSucursal", "Nuestro Bogota"}
	    );

	    TestUtils.escribirContenidoEnArchivo(rutaEmpleados, contenidoEmpleados);

	    try {
	        Persistencia.leerEmpleados(sistema, rutaEmpleados);
	    } catch (Exception e) {
	        fail("Error al leer empleados: " + e.getMessage());
	    }

	    // Verificar que los empleados se hayan cargado correctamente en el sistema
	    assertEquals(2, sistema.getEmpleados().size());

	    // Verificar propiedades del primer empleado
	    assertEquals("juan123", sistema.getEmpleados().get(0).getNombreUsuario());
	    assertEquals("45678", sistema.getEmpleados().get(0).getContrasena());
	    assertEquals("Juan Perez", sistema.getEmpleados().get(0).getNombre());
	    assertEquals("AgenteVentas", sistema.getEmpleados().get(0).getCargo());
	    assertEquals("Sucursal1", sistema.getEmpleados().get(0).getSede().getNombre());

	    // Verificar propiedades del segundo empleado
	    assertEquals("ana789", sistema.getEmpleados().get(1).getNombreUsuario());
	    assertEquals("98765", sistema.getEmpleados().get(1).getContrasena());
	    assertEquals("Ana Rodriguez", sistema.getEmpleados().get(1).getNombre());
	    assertEquals("GerenteSucursal", sistema.getEmpleados().get(1).getCargo());
	    assertEquals("Sucursal2", sistema.getEmpleados().get(1).getSede().getNombre());
	}

	
	@Test
	void testLeerSeguros() {
	    SistemaAlquiler sistema = new SistemaAlquiler();
	    String rutaSeguros = "datos/segurosPrueba";

	    // Contenido simulado del archivo de seguros
	    List<String[]> contenidoSeguros = Arrays.asList(
	            new String[]{"Nombre", "Precio", "Detalles"},
	            new String[]{"Seguro1", "50000", "Cobertura básica"},
	            new String[]{"Seguro2", "75000", "Cobertura completa"}
	    );

	    TestUtils.escribirContenidoEnArchivo(rutaSeguros, contenidoSeguros);

	    try {
	        Persistencia.leerSeguros(sistema, rutaSeguros);
	    } catch (Exception e) {
	        fail("Error al leer seguros: " + e.getMessage());
	    }

	    // Verificar que los seguros se hayan cargado correctamente en el sistema
	    assertEquals(2, sistema.getSeguros().size());

	    // Verificar propiedades del primer seguro
	    assertEquals("Seguro1", sistema.getSeguros().get(0).getNombre());
	    assertEquals("50000", sistema.getSeguros().get(0).getPrecio());
	    assertEquals("Cobertura básica", sistema.getSeguros().get(0).getDetalles());

	    // Verificar propiedades del segundo seguro
	    assertEquals("Seguro2", sistema.getSeguros().get(1).getNombre());
	    assertEquals("75000", sistema.getSeguros().get(1).getPrecio());
	    assertEquals("Cobertura completa", sistema.getSeguros().get(1).getDetalles());
	}
}