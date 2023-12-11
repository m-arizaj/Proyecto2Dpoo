package logica;

public interface PasarelaPago
{
	boolean realizarPago(Transaccion transaccion);
    boolean bloquearCupo(Transaccion transaccion);
}
