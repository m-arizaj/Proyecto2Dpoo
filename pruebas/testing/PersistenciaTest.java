package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import logica.SistemaAlquiler;
import persistencia.Persistencia;

class PersistenciaTest {

	@Test
	void testLeerVehiculos() {
		SistemaAlquiler sistema = new SistemaAlquiler();
		String rutaVehiculos = "ruta/al/archivo/vehiculos.csv";

		// Contenido simulado del archivo de vehículos
		List<String[]> contenidoVehiculos = List.of(
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
}