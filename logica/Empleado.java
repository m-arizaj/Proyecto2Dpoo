package logica;
import logica.Vehiculo;
import javax.swing.JOptionPane;

public class Empleado implements Usuario {
    private String nombreUsuario;
    private String contrasena;
    private String nombre;
    private String cargo;
	private Sede sede;

    public Empleado(String nombreUsuario, String contrasena, String nombre, String cargo, Sede sede) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.cargo = cargo;
        this.sede = sede;
    }

    @Override
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    @Override
    public String getContrasena() {
        return contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCargo() {
        return cargo;
    }
    
    public static String ActualizarEstadoVehiculo(String placa)
    {
    	String input = JOptionPane.showInputDialog("Por favor, ingresa la placa del carro:");
    	
		return input;
    	
    			
    }

	public Sede getSede() {
		return sede;
	}
}
