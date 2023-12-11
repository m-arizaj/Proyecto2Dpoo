package logica;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

public class Reserva {
    private String id;
    private String categoria;
    private String usuarioCliente;
    private String sedeRecogida;
    private String sedeEntrega;
    private String fechaRecogida;
    private String fechaEntrega;
    private String diasFacturados;
    private String costoParcial;
    private String costoTreinta;
    

    public Reserva(String id, String categoria, String usuarioCliente, String sedeRecogida, String sedeEntrega,
                   String fechaRecogida, String fechaEntrega,String diasFacturados, String costoParcial, String costoTreinta) {
        this.id = id;
        this.usuarioCliente = usuarioCliente;
        this.categoria = categoria;
        this.sedeRecogida = sedeRecogida;
        this.sedeEntrega = sedeEntrega;
        this.fechaRecogida = fechaRecogida;
        this.fechaEntrega = fechaEntrega;
        this.diasFacturados = diasFacturados;
        this.costoParcial = costoParcial;
        this.costoTreinta = costoTreinta;
    }

    public String getId() {
        return id;
    }

    public String getUsuarioCliente() {
        return usuarioCliente;
    }

    public String getSedeRecogida() {
        return sedeRecogida;
    }

    public String getSedeEntrega() {
        return sedeEntrega;
    }

    public String getFechaRecogida() {
        return fechaRecogida;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

	public String getDiasFacturados()
	{
		return diasFacturados;
	}

	public String getCategoria()
	{
		return categoria;
	}

	public String getCostoParcial()
	{
		return costoParcial;
	}

	public String getCostoTreinta() {
		return costoTreinta;
	}

	public void setCostoTreinta(String costoTreinta) {
		this.costoTreinta = costoTreinta;
	}
}
