package datos2;
// Generated 27 nov 2023 19:56:51 by Hibernate Tools 5.6.15.Final

import java.util.HashSet;
import java.util.Set;

/**
 * TLineas generated by hbm2java
 */
public class TLineas implements java.io.Serializable {

	private int codLinea;
	private String nombre;
	private Set TTreneses = new HashSet(0);
	private Set TLineaEstacions = new HashSet(0);

	public TLineas() {
	}

	public TLineas(int codLinea, String nombre) {
		this.codLinea = codLinea;
		this.nombre = nombre;
	}

	public TLineas(int codLinea, String nombre, Set TTreneses, Set TLineaEstacions) {
		this.codLinea = codLinea;
		this.nombre = nombre;
		this.TTreneses = TTreneses;
		this.TLineaEstacions = TLineaEstacions;
	}

	public int getCodLinea() {
		return this.codLinea;
	}

	public void setCodLinea(int codLinea) {
		this.codLinea = codLinea;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getTTreneses() {
		return this.TTreneses;
	}

	public void setTTreneses(Set TTreneses) {
		this.TTreneses = TTreneses;
	}

	public Set getTLineaEstacions() {
		return this.TLineaEstacions;
	}

	public void setTLineaEstacions(Set TLineaEstacions) {
		this.TLineaEstacions = TLineaEstacions;
	}

}