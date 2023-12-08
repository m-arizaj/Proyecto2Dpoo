package logica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PayU implements PasarelaPago
{
	@Override
	public boolean realizarPago(Transaccion transaccion) {
		
		String medio = transaccion.getMedioPago();
		String[] partes = medio.split("-");
		boolean LonNum = (partes[0].length())==16;
		boolean ConNum = esConvertibleAInt(partes[0]);
		boolean LonCod = (partes[1].length())==3;
		boolean ConCod = esConvertibleAInt(partes[1]);
		boolean FechMay = esFechaMayorQueActual(partes[2]);
		
		
		if (LonNum && ConNum && LonCod && ConCod && FechMay) {
			return true;
		}
		else {
			return false;	
		}
	}
	
	@Override
	public boolean bloquearCupo(Transaccion transaccion) {
		String medio = transaccion.getMedioPago();
		String[] partes = medio.split("-");
		boolean LonNum = (partes[0].length())==16;
		boolean ConNum = esConvertibleAInt(partes[0]);
		boolean LonCod = (partes[1].length())==3;
		boolean ConCod = esConvertibleAInt(partes[1]);
		boolean FechMay = esFechaMayorQueActual(partes[2]);
		
		
		if (LonNum && ConNum && LonCod && ConCod && FechMay) {
			return true;
		}
		else {
			return false;	
		}
	}
	
	 public boolean esConvertibleAInt(String str) {
	        try {
	            Integer.parseInt(str);
	            return true; 
	        } catch (NumberFormatException e) {
	            return false; 
	        }
	    }
	 
	 public boolean esFechaMayorQueActual(String fechaParametro) {
	        LocalDate fechaActual = LocalDate.now();

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
	        LocalDate fechaParametroLocalDate = LocalDate.parse(fechaParametro, formatter);

	        return fechaParametroLocalDate.isAfter(fechaActual);
	    }
}
