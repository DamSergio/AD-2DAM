package ejercicioAlumnosTipoExamen;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType()
public class Nota {
	private Integer codAlum;
	private String asignatura;
	private float nota;
	
	public Nota(Integer codAlum, String asignatura, float nota) {
		super();
		this.codAlum = codAlum;
		this.asignatura = asignatura;
		this.nota = nota;
	}
	
	public Nota() {
		super();
		this.codAlum = 0;
		this.asignatura = "";
		this.nota = 0;
	}

	@XmlElement(name = "cod", required = false)
	public Integer getCodAlum() {
		return codAlum;
	}

	public void setCodAlum(Integer codAlum) {
		this.codAlum = codAlum;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}
}
