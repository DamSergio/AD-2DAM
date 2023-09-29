package ejercicioAlumnosTipoExamen;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType()
public class Alumno {
	private int numAlum;
	private String nombre;
	private String localidad;
	private int numAsig;
	private float notaMedia;
	private ArrayList<Nota> notas;
	
	public Alumno(int numAlum, String nombre, String localidad, int numAsig, float notaMedia, ArrayList<Nota> notas) {
		super();
		this.numAlum = numAlum;
		this.nombre = nombre;
		this.localidad = localidad;
		this.numAsig = numAsig;
		this.notaMedia = notaMedia;
		this.notas = notas;
	}
	
	public Alumno() {
		super();
		this.numAlum = 0;
		this.nombre = "";
		this.localidad = "";
		this.numAsig = 0;
		this.notaMedia = 0;
		this.notas = new ArrayList<>();
	}

	public int getNumAlum() {
		return numAlum;
	}

	public void setNumAlum(int numAlum) {
		this.numAlum = numAlum;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public int getNumAsig() {
		return numAsig;
	}

	public void setNumAsig(int numAsig) {
		this.numAsig = numAsig;
	}

	public float getNotaMedia() {
		return notaMedia;
	}

	public void setNotaMedia(float notaMedia) {
		this.notaMedia = notaMedia;
	}
	
	@XmlElementWrapper(name = "notas")
	@XmlElement(name = "nota")
	public ArrayList<Nota> getNotas() {
		return notas;
	}

	public void setNotas(ArrayList<Nota> notas) {
		this.notas = notas;
	}
	
	public void actualizarNotaMedia() {
		float media = 0;
		
		for (Nota nota:this.notas) {
			media += nota.getNota();
		}
		
		this.notaMedia = media / this.notas.size();
	}
}
