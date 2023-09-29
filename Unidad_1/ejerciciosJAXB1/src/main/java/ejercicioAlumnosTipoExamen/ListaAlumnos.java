package ejercicioAlumnosTipoExamen;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "alumnos")
public class ListaAlumnos {
	private ArrayList<Alumno> alumnos;

	public ListaAlumnos(ArrayList<Alumno> alumnos) {
		super();
		this.alumnos = alumnos;
	}
	
	public ListaAlumnos() {
		super();
		this.alumnos = new ArrayList<>();
	}
	
	@XmlElement(name = "alumno")
	public ArrayList<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(ArrayList<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
}
