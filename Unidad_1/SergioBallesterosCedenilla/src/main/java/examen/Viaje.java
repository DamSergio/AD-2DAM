package examen;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"codViaje", "nombre", "pvp", "numPlazas", "numReservas", "totalImporte", "listaReservas"})
public class Viaje {
	private int codViaje;
	private String nombre;
	private int pvp;
	private int numPlazas;
	private int numReservas;
	private int totalImporte;
	private ArrayList<Reserva> listaReservas;
	
	public Viaje(int codViaje, String nombre, int pvp, int numPlazas, int numReservas, int totalImporte,
			ArrayList<Reserva> listaReservas) {
		super();
		this.codViaje = codViaje;
		this.nombre = nombre;
		this.pvp = pvp;
		this.numPlazas = numPlazas;
		this.numReservas = numReservas;
		this.totalImporte = totalImporte;
		this.listaReservas = listaReservas;
	}
	
	public Viaje() {
		super();
		this.codViaje = 0;
		this.nombre = "";
		this.pvp = 0;
		this.numPlazas = 0;
		this.numReservas = 0;
		this.totalImporte = 0;
		this.listaReservas = new ArrayList<>();
	}

	public int getCodViaje() {
		return codViaje;
	}

	public void setCodViaje(int codViaje) {
		this.codViaje = codViaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPvp() {
		return pvp;
	}

	public void setPvp(int pvp) {
		this.pvp = pvp;
	}

	public int getNumPlazas() {
		return numPlazas;
	}

	public void setNumPlazas(int numPlazas) {
		this.numPlazas = numPlazas;
	}

	public int getNumReservas() {
		return numReservas;
	}

	public void setNumReservas(int numReservas) {
		this.numReservas = numReservas;
	}

	public int getTotalImporte() {
		return totalImporte;
	}

	public void setTotalImporte(int totalImporte) {
		this.totalImporte = totalImporte;
	}
	
	@XmlElementWrapper(name = "ListaReservas")
	@XmlElement(name = "reserva")
	public ArrayList<Reserva> getListaReservas() {
		return listaReservas;
	}

	public void setListaReservas(ArrayList<Reserva> listaReservas) {
		this.listaReservas = listaReservas;
	}
}
