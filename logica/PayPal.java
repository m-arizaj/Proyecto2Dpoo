package logica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PayPal implements PasarelaPago
{

	@Override
	public boolean realizarPago(Transaccion transaccion) {
		
		String[] medio = transaccion.getMedioPago();
		boolean LonNum = (medio[0].length())==16;
		boolean ConNum = medio[0].matches("\\d+");
		boolean LonCod = (medio[1].length())==3;
		boolean ConCod = medio[1].matches("\\d+");
		boolean FechMay = esFechaMayorQueActual(medio[2]);
	
		
		if (LonNum && ConNum && LonCod && ConCod && FechMay) {
			return true;
		}
		else {
			return false;	
		}
	}
	
	@Override
	public boolean bloquearCupo(Transaccion transaccion) {
		String[] medio = transaccion.getMedioPago();
		boolean LonNum = (medio[0].length())==16;
		boolean ConNum = medio[0].matches("\\d+");
		boolean LonCod = (medio[1].length())==3;
		boolean ConCod = medio[1].matches("\\d+");
		boolean FechMay = esFechaMayorQueActual(medio[2]);
		
		
		if (LonNum && ConNum && LonCod && ConCod && FechMay) {
			return true;
		}
		else {
			return false;	
		}
	}
	 
	 public boolean esFechaMayorQueActual(String fechaParametro) {
	        LocalDate fechaActual = LocalDate.now();

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        LocalDate fechaParametroLocalDate = LocalDate.parse("01/" +fechaParametro, formatter);

	        return fechaParametroLocalDate.isAfter(fechaActual);
	    }
}
