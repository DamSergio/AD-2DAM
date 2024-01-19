package vuelos;

public class Pasajeros {
	private short codigo;
	private String nombre;
	private String tlf;
	private String direccion;
	
	public Pasajeros() {
		super();
		this.codigo = 0;
		this.nombre = "";
		this.tlf = "";
		this.direccion = "";
	}
	
	public Pasajeros(short codigo, String nombre, String tlf, String direccion) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.tlf = tlf;
		this.direccion = direccion;
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

	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Pasajeros [codigo=" + codigo + ", nombre=" + nombre + ", tlf=" + tlf + ", direccion=" + direccion + "]";
	}
}
