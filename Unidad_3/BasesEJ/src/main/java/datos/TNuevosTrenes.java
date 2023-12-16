package datos;
// Generated 14 dic 2023 15:57:23 by Hibernate Tools 5.6.15.Final

import java.math.BigInteger;

/**
 * TNuevosTrenes generated by hbm2java
 */
public class TNuevosTrenes implements java.io.Serializable {

	private int codTren;
	private String nombre;
	private String tipo;
	private BigInteger codLinea;
	private BigInteger codCochera;

	public TNuevosTrenes() {
	}

	public TNuevosTrenes(int codTren, String nombre, String tipo, BigInteger codLinea, BigInteger codCochera) {
		this.codTren = codTren;
		this.nombre = nombre;
		this.tipo = tipo;
		this.codLinea = codLinea;
		this.codCochera = codCochera;
	}

	public int getCodTren() {
		return this.codTren;
	}

	public void setCodTren(int codTren) {
		this.codTren = codTren;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigInteger getCodLinea() {
		return this.codLinea;
	}

	public void setCodLinea(BigInteger codLinea) {
		this.codLinea = codLinea;
	}

	public BigInteger getCodCochera() {
		return this.codCochera;
	}

	public void setCodCochera(BigInteger codCochera) {
		this.codCochera = codCochera;
	}

}
