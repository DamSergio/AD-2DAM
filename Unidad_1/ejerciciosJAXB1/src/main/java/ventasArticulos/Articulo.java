package ventasArticulos;

import javax.xml.bind.annotation.XmlType;

@XmlType()
public class Articulo {
	private String codigo;
	private String denominacion;
	private String stock;
	private String precio;
	
	public Articulo(String codigo, String denominacion, String stock, String precio) {
		super();
		this.codigo = codigo;
		this.denominacion = denominacion;
		this.stock = stock;
		this.precio = precio;
	}
	
	public Articulo() {
		super();
		this.codigo = "";
		this.denominacion = "";
		this.stock = "";
		this.precio = "";
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}
}
