package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Estudiantes {
	private int codestudiante;
	private String nombre;
	private String direccion;
	private String tlf;
	private Date fechaalta;
	private Set<Participa> participaen;
	
	public Estudiantes() {
		super();
		this.codestudiante = 0;
		this.nombre = "";
		this.direccion = "";
		this.tlf = "";
		this.fechaalta = new Date();
		this.participaen = new HashSet<>();
	} 
	
	public Estudiantes(int codestudiante, String nombre, String direccion, String tlf, Date fechaalta,
			Set<Participa> participaen) {
		super();
		this.codestudiante = codestudiante;
		this.nombre = nombre;
		this.direccion = direccion;
		this.tlf = tlf;
		this.fechaalta = fechaalta;
		this.participaen = participaen;
	}

	public int getCodestudiante() {
		return codestudiante;
	}

	public void setCodestudiante(int codestudiante) {
		this.codestudiante = codestudiante;
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

	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public Date getFechaalta() {
		return fechaalta;
	}

	public void setFechaalta(Date fechaalta) {
		this.fechaalta = fechaalta;
	}

	public Set<Participa> getParticipaen() {
		return participaen;
	}

	public void setParticipaen(Set<Participa> participaen) {
		this.participaen = participaen;
	} 
}
