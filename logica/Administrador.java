package logica;

public class Administrador implements Usuario {
    private String nombreUsuario;
    private String contrasena;

    public Administrador(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    @Override
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    @Override
    public String getContrasena() {
        return contrasena;
    }

    
}





