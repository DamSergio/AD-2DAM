package ventasArticulos2;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="artic")
public class Articulo {
	private String codigo;
	private String denominacion;
	private float stock;
	private float precio;
	
	public Articulo(String codigo, String denominacion, float stock, float precio) {
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
		this.stock = 0;
		this.precio = 0;
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

	public float getStock() {
		return stock;
	}

	public void setStock(float stock) {
		this.stock = stock;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}
}
