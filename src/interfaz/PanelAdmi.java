package interfaz;

import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

import logica.SistemaAlquiler;
import persistencia.Persistencia;
import logica.Cliente;
import logica.Empleado;
import logica.Sede;
import logica.Seguro;
import logica.Administrador;
import logica.AdministradorLocal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;

public class PanelAdmi {
	
	public static void ejecutarMenuAdministrador(SistemaAlquiler sistema) throws CsvValidationException, NumberFormatException {

        Scanner scanner = new Scanner(System.in);

        boolean salir = false;
        while (!salir) {
            System.out.println("\nBienvenido administrador");

            System.out.println("1. Registrar compra de nuevos vehículos");
            System.out.println("2. Dar de baja un vehículo");
            System.out.println("3. Configurar seguros");
            System.out.println("4. Generar un archivo de log con historial de un vehiculo");
            System.out.println("5. Salir del menú de administrador\n");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) {

                System.out.println("Ingrese los detalles del nuevo vehículo:\n");

                System.out.print("Placa: ");
                String placa = scanner.nextLine();

                System.out.print("Marca: ");
                String marca = scanner.nextLine();

                System.out.print("Modelo: ");
                String modelo = scanner.nextLine();

                System.out.print("Color: ");
                String color = scanner.nextLine();

                System.out.print("Transmisión: ");
                String transmision = scanner.nextLine();

                System.out.print("Categoría: ");
                String categoria = scanner.nextLine();

                System.out.print("Estado: ");
                String estado = scanner.nextLine();

                System.out.print("Pasajeros: ");
                String pasajeros = scanner.nextLine();

                System.out.print("Tarifa: ");
                String tarifa = scanner.nextLine();
                
                System.out.print("Observaciones: ");
                String observaciones = scanner.nextLine();
                
                System.out.print("Ubicacion: ");
                String ubicacion = scanner.nextLine();

                sistema.agregarVehiculo(placa, marca, modelo, color, transmision, categoria, estado, pasajeros, tarifa, observaciones,
                		ubicacion);
                sistema.agregarEventoAlHistorial(placa, "Se anadio el vechiculo con placa " + placa + " al invetario.");
                Persistencia.escribirVehiculos(sistema,"datos/vehiculos.csv");
                Persistencia.escribirEventosVehiculos(sistema,"datos/eventos.csv");
                
            } else if (opcion == 2) {
                
                System.out.print("Ingrese la placa del vehículo que desea eliminar: ");
                String placaAEliminar = scanner.nextLine();
                sistema.eliminarAuto(placaAEliminar);
                Persistencia.escribirVehiculos(sistema,"datos/vehiculos.csv");
                sistema.agregarEventoAlHistorial(placaAEliminar, "Se elimino el auto con placa " + placaAEliminar + " del invetario.");
                Persistencia.escribirEventosVehiculos(sistema,"datos/eventos.csv");
                
            } else if (opcion == 3) {
                ejecutarMenuSeguros(sistema);
            } else if (opcion == 4) {
            	System.out.print("Digite la placa del vehiculo a consultar: ");
                String placa = scanner.nextLine();
                List<String> eventosAuto = sistema.buscarEventosPorPlaca(placa);
                Persistencia.archivoLog(eventosAuto,"datos");
            } else if (opcion == 5) {
                salir = true;
            }else {
                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
    }
	
private static void ejecutarMenuSeguros(SistemaAlquiler sistema) throws CsvValidationException, NumberFormatException {
        
        Scanner scanner = new Scanner(System.in);
        
        boolean salirSeguros = false;

        while (!salirSeguros) {
            System.out.println("\nGestión de Seguros");
            System.out.println("1. Ver seguros actuales");
            System.out.println("2. Agregar nuevo seguro");
            System.out.println("3. Eliminar seguro");
            System.out.println("4. Volver al menú principal\n");
            System.out.println("Seleccione una opción: ");

            int opcionSeguros = scanner.nextInt();
            scanner.nextLine();

            if (opcionSeguros == 1) {
                
                if (sistema.getSeguros().isEmpty()) {
                    System.out.println("No hay seguros registrados en el sistema.");
                } else {
                    System.out.println("Lista de seguros:");
                    for (Seguro seguro : sistema.getSeguros()) {
                        System.out.println("Nombre: " + seguro.getNombre() + ", Precio: " + seguro.getPrecio());
                    }
                }
            } else if (opcionSeguros == 2) {
                
            	System.out.print("Nombre del nuevo seguro: ");
                String nombreSeguro = scanner.nextLine();
                System.out.print("Precio del nuevo seguro: ");
                String precioSeguro = scanner.nextLine();
                System.out.print("Detalles del nuevo seguro: ");
                String detallesSeguro = scanner.nextLine();
                scanner.nextLine();
                sistema.agregarSeguro(nombreSeguro,precioSeguro,detallesSeguro);
                Persistencia.escribirSeguros(sistema, "datos/seguros.csv");
                
                System.out.println("Nuevo seguro agregado con éxito.");
            } else if (opcionSeguros == 3) {
                
                System.out.print("Nombre del seguro a eliminar: ");
                String nombreSeguroEliminar = scanner.nextLine();
                boolean seguroEliminado = sistema.eliminarSeguro(nombreSeguroEliminar);
                Persistencia.escribirSeguros(sistema, "datos/seguros.csv");
                //Persistencia.leerSeguros(sistema, "datos/seguros.csv");
                if (seguroEliminado) {
                    System.out.println("Seguro eliminado con éxito.");
                } else {
                    System.out.println("No se encontró un seguro con ese nombre.");
                }
            } else if (opcionSeguros == 4) {
                salirSeguros = true; 
            } else {
                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
    }
}
