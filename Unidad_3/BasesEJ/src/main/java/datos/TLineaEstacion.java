package datos;
// Generated 14 dic 2023 15:57:23 by Hibernate Tools 5.6.15.Final

/**
 * TLineaEstacion generated by hbm2java
 */
public class TLineaEstacion implements java.io.Serializable {

	private TLineaEstacionId id;
	private TLineas TLineas;
	private TEstaciones TEstaciones;
	private int orden;

	public TLineaEstacion() {
	}

	public TLineaEstacion(TLineaEstacionId id, TLineas TLineas, TEstaciones TEstaciones, int orden) {
		this.id = id;
		this.TLineas = TLineas;
		this.TEstaciones = TEstaciones;
		this.orden = orden;
	}

	public TLineaEstacionId getId() {
		return this.id;
	}

	public void setId(TLineaEstacionId id) {
		this.id = id;
	}

	public TLineas getTLineas() {
		return this.TLineas;
	}

	public void setTLineas(TLineas TLineas) {
		this.TLineas = TLineas;
	}

	public TEstaciones getTEstaciones() {
		return this.TEstaciones;
	}

	public void setTEstaciones(TEstaciones TEstaciones) {
		this.TEstaciones = TEstaciones;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

}
