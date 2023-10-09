package examen;

public class Reserva {
	private String nombreCliente;
	private int plazasReservadas;
	
	public Reserva(String nombreCliente, int plazasReservadas) {
		super();
		this.nombreCliente = nombreCliente;
		this.plazasReservadas = plazasReservadas;
	}
	
	public Reserva() {
		super();
		this.nombreCliente = "";
		this.plazasReservadas = 0;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public int getPlazasReservadas() {
		return plazasReservadas;
	}

	public void setPlazasReservadas(int plazasReservadas) {
		this.plazasReservadas = plazasReservadas;
	}
}
