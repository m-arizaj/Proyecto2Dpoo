
 package logica;

import java.util.Date;

public class Cliente implements Usuario {
    private String nombreUsuario;
    private String contrasena;
    private String nombre;
    private String numeroTelefonico;
    private String correo;
    private String fechaNacimiento;
    private String nacionalidad;
    private String numeroLicencia;
    private String paisExpedicionLicencia;
    private String fechaVencimientoLicencia;

    public Cliente(String nombreUsuario, String contrasena, String nombre, String numeroTelefonico,String correo,
    		String fechaNacimiento, String nacionalidad,
                   String numeroLicencia, String paisExpedicionLicencia,
                   String fechaVencimientoLicencia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.numeroTelefonico = numeroTelefonico;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.numeroLicencia = numeroLicencia;
        this.paisExpedicionLicencia = paisExpedicionLicencia;
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
    }

    @Override
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    @Override
    public String getContrasena() {
        return contrasena;
    }

	public String getNombre()
	{
		return nombre;
	}

	public String getNumeroTelefonico()
	{
		return numeroTelefonico;
	}

	public String getCorreo()
	{
		return correo;
	}

	public String getFechaNacimiento()
	{
		return fechaNacimiento;
	}

	public String getNacionalidad()
	{
		return nacionalidad;
	}

	public String getNumeroLicencia()
	{
		return numeroLicencia;
	}

	public String getPaisExpedicionLicencia()
	{
		return paisExpedicionLicencia;
	}

	public String getFechaVencimientoLicencia()
	{
		return fechaVencimientoLicencia;
	}
    
    
    
}
