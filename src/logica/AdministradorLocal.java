package logica;

public class AdministradorLocal implements Usuario{

	public AdministradorLocal(String nombreUsuario, String contrasena, Sede sede) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.sede = sede;
	}

	private String nombreUsuario;
    private String contrasena;
    private Sede sede;

	@Override
	public String getNombreUsuario() {
        return nombreUsuario;
    }

    @Override
    public String getContrasena() {
        return contrasena;
    }
	
    public Sede getSede() {
		return sede;
	}
    
	public void setSede(Sede sede) {
		this.sede = sede;
	}
}
