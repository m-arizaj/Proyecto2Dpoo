package logica;

public class Transaccion
{
	private String id;
    private String fecha;
    private String monto;
    private String numeroCuenta;
    private String descripcion;
    private String medioPago;
    
    
	public Transaccion(String id, String fecha, String monto, String numeroCuenta, String descripcion, String medioPago)
	{
		this.id = id;
		this.fecha = fecha;
		this.monto = monto;
		this.numeroCuenta = numeroCuenta;
		this.descripcion = descripcion;
		this.medioPago = medioPago;
	}


	public String getId()
	{
		return id;
	}


	public void setId(String id)
	{
		this.id = id;
	}


	public String getFecha()
	{
		return fecha;
	}


	public void setFecha(String fecha)
	{
		this.fecha = fecha;
	}


	public String getMonto()
	{
		return monto;
	}


	public void setMonto(String monto)
	{
		this.monto = monto;
	}


	public String getNumeroCuenta()
	{
		return numeroCuenta;
	}


	public void setNumeroCuenta(String numeroCuenta)
	{
		this.numeroCuenta = numeroCuenta;
	}


	public String getDescripcion()
	{
		return descripcion;
	}


	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}


	public String getMedioPago()
	{
		return medioPago;
	}


	public void setMedioPago(String medioPago)
	{
		this.medioPago = medioPago;
	}
}

