package logica;

public class Transaccion
{
	private String id;
    private String fecha;
    private String monto;
    private String cuenta;
    private String nombre;
    private String documento;
    private String correo; 
    private String descripcion;
    private String[] medioPago;
    
    
	public Transaccion(String id, String fecha, String monto, String cuenta, String nombre, String documento,
			String correo, String descripcion, String[] medioPago)
	{
		this.id = id;
		this.fecha = fecha;
		this.monto = monto;
		this.cuenta = cuenta;
		this.nombre = nombre;
		this.documento = documento;
		this.correo = correo;
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


	public String getCuenta()
	{
		return cuenta;
	}


	public void setCuenta(String cuenta)
	{
		this.cuenta = cuenta;
	}


	public String getNombre()
	{
		return nombre;
	}


	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}


	public String getDocumento()
	{
		return documento;
	}


	public void setDocumento(String documento)
	{
		this.documento = documento;
	}


	public String getCorreo()
	{
		return correo;
	}


	public void setCorreo(String correo)
	{
		this.correo = correo;
	}


	public String getDescripcion()
	{
		return descripcion;
	}


	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}


	public String[] getMedioPago()
	{
		return medioPago;
	}


	public void setMedioPago(String[] medioPago)
	{
		this.medioPago = medioPago;
	}
    
    
	
}

