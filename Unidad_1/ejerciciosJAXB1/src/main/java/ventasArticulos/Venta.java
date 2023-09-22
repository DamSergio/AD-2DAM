package ventasArticulos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType()
public class Venta {
	private String numventa;
	private String unidades;
	private String nombrecliente;
	private String fecha;
	
	public Venta(String numVenta, String unidades, String nombreCliente, String fecha) {
		super();
		this.numventa = numVenta;
		this.unidades = unidades;
		this.nombrecliente = nombreCliente;
		this.fecha = fecha;
	}
	
	public Venta() {
		super();
		this.numventa = "";
		this.unidades = "";
		this.nombrecliente = "";
		this.fecha = "";
	}
	
	@XmlElement(name="numventa")
	public String getNumVenta() {
		return numventa;
	}

	public void setNumVenta(String numVenta) {
		this.numventa = numVenta;
	}

	public String getUnidades() {
		return unidades;
	}

	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}
	
	@XmlElement(name="nombrecliente")
	public String getNombreCliente() {
		return nombrecliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombrecliente = nombreCliente;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
