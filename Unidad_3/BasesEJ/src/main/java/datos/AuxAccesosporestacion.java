package datos;

public class AuxAccesosporestacion {
	private Integer codEstacion;
	private String nombre;
	private String direccion;
	private Long cuenta;
	
	public AuxAccesosporestacion(Integer codEstacion, String nombre, String direccion, Long cuenta) {
		super();
		this.codEstacion = codEstacion;
		this.nombre = nombre;
		this.direccion = direccion;
		this.cuenta = cuenta;
	}
	
	public AuxAccesosporestacion() {
		super();
		this.codEstacion = 0;
		this.nombre = "";
		this.direccion = "";
		this.cuenta = 0l;
	}

	public Integer getCodEstacion() {
		return codEstacion;
	}

	public void setCodEstacion(Integer codEstacion) {
		this.codEstacion = codEstacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Long getCuenta() {
		return cuenta;
	}

	public void setCuenta(Long cuenta) {
		this.cuenta = cuenta;
	}

	@Override
	public String toString() {
		return "AuxAccesosporestacion [codEstacion=" + codEstacion + ", nombre=" + nombre + ", direccion=" + direccion
				+ ", cuenta=" + cuenta + "]";
	}
}
