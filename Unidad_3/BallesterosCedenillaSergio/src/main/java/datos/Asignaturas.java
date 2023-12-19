package datos;
// Generated 19 dic 2023 18:53:27 by Hibernate Tools 5.6.15.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Asignaturas generated by hbm2java
 */
public class Asignaturas implements java.io.Serializable {

	private int codAsig;
	private Cursos cursos;
	private Departamentos departamentos;
	private String nombre;
	private Set evaluacioneses = new HashSet(0);

	public Asignaturas() {
	}

	public Asignaturas(int codAsig) {
		this.codAsig = codAsig;
	}

	public Asignaturas(int codAsig, Cursos cursos, Departamentos departamentos, String nombre, Set evaluacioneses) {
		this.codAsig = codAsig;
		this.cursos = cursos;
		this.departamentos = departamentos;
		this.nombre = nombre;
		this.evaluacioneses = evaluacioneses;
	}

	public int getCodAsig() {
		return this.codAsig;
	}

	public void setCodAsig(int codAsig) {
		this.codAsig = codAsig;
	}

	public Cursos getCursos() {
		return this.cursos;
	}

	public void setCursos(Cursos cursos) {
		this.cursos = cursos;
	}

	public Departamentos getDepartamentos() {
		return this.departamentos;
	}

	public void setDepartamentos(Departamentos departamentos) {
		this.departamentos = departamentos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getEvaluacioneses() {
		return this.evaluacioneses;
	}

	public void setEvaluacioneses(Set evaluacioneses) {
		this.evaluacioneses = evaluacioneses;
	}

}
