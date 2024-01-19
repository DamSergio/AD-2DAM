package vuelos;

import java.util.HashSet;
import java.util.Set;

public class Vuelo {
	private String identificador;
	private String aeropuertoorigen;
	private String aeropuertodestino;
	private Set<Pasajeros> pasajeros;
	private Set<Tripulacion> tripulacion;
	
	public Vuelo() {
		super();
		this.identificador = "";
		this.aeropuertoorigen = "";
		this.aeropuertodestino = "";
		this.pasajeros = new HashSet<>();
		this.tripulacion = new HashSet<>();
	}
	
	public Vuelo(String identificador, String aeropuertoorigen, String aeropuertodestino, Set<Pasajeros> pasajeros,
			Set<Tripulacion> tripulacion) {
		super();
		this.identificador = identificador;
		this.aeropuertoorigen = aeropuertoorigen;
		this.aeropuertodestino = aeropuertodestino;
		this.pasajeros = pasajeros;
		this.tripulacion = tripulacion;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getAeropuertoorigen() {
		return aeropuertoorigen;
	}

	public void setAeropuertoorigen(String aeropuertoorigen) {
		this.aeropuertoorigen = aeropuertoorigen;
	}

	public String getAeropuertodestino() {
		return aeropuertodestino;
	}

	public void setAeropuertodestino(String aeropuertodestino) {
		this.aeropuertodestino = aeropuertodestino;
	}

	public Set<Pasajeros> getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros(Set<Pasajeros> pasajeros) {
		this.pasajeros = pasajeros;
	}

	public Set<Tripulacion> getTripulacion() {
		return tripulacion;
	}

	public void setTripulacion(Set<Tripulacion> tripulacion) {
		this.tripulacion = tripulacion;
	}

	@Override
	public String toString() {
		return "Vuelo [identificador=" + identificador + ", aeropuertoorigen=" + aeropuertoorigen
				+ ", aeropuertodestino=" + aeropuertodestino + ", pasajeros=" + pasajeros + ", tripulacion="
				+ tripulacion + "]";
	}
}
