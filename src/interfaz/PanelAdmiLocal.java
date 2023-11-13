package interfaz;

import java.util.List;
import java.util.Scanner;

import logica.AdministradorLocal;
import logica.Empleado;
import logica.Sede;
import logica.SistemaAlquiler;
import persistencia.Persistencia;

public class PanelAdmiLocal {
	
	public static void ejecutarMenuAdministradorLocal(SistemaAlquiler sistema, Scanner scanner, AdministradorLocal admiActual) {
		
   	 boolean salir = false;

   	    while (!salir) {
   	        System.out.println("\nMenú del Administrador Local");
   	        System.out.println("1. Ver lista de empleados de su sede");
   	        System.out.println("2. Crear un nuevo empleado para su sede");
   	        System.out.println("3. Salir del menú del Administrador Local");
   	        System.out.print("Seleccione una opción: ");

   	        int opcion = scanner.nextInt();
   	        scanner.nextLine();

   	        if (opcion == 1) {
   	            System.out.println("Lista de empleados de su sede:");
   	            List<Empleado> empleadosDeSede = sistema.getEmpleadosPorSede(admiActual.getSede());
   	            for (Empleado empleado : empleadosDeSede) {
   	                System.out.println("Nombre: " + empleado.getNombre());
   	            }
   	        } else if (opcion == 2) {

   	            System.out.println("Creación de un nuevo empleado");
   	            System.out.print("Nombre de usuario: ");
   	            String nombreUsuario = scanner.nextLine();

   	            System.out.print("Contraseña: ");
   	            String contrasena = scanner.nextLine();

   	            System.out.print("Nombre: ");
   	            String nombre = scanner.nextLine();

   	            System.out.print("Cargo: ");
   	            String cargo = scanner.nextLine();
   	            
   	            Sede sedeActual = admiActual.getSede();
   	            
   	            sistema.crearEmpleado(nombreUsuario,contrasena,nombre,cargo,sedeActual);
   	            Persistencia.escribirEmpleados(sistema, "datos/empleados.csv");
   	            
   	        } else if (opcion == 3) {
   	            salir = true;
   	        } else {
   	            System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
   	        }
   	    }
   	}

}
