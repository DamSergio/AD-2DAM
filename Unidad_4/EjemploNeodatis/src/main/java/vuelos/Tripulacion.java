package vuelos;

public class Tripulacion {
	private short codigo;
	private String nombre;
	private String categoria;
	
	public Tripulacion() {
		super();
		this.codigo = 0;
		this.nombre = "";
		this.categoria = "";
	}
	
	public Tripulacion(short codigo, String nombre, String categoria) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.categoria = categoria;
	}

	public short getCodigo() {
		return codigo;
	}

	public void setCodigo(short codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Tripulacion [codigo=" + codigo + ", nombre=" + nombre + ", categoria=" + categoria + "]";
	}
}
