package examen;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ListaViajes")
public class ListaViajes {
	private ArrayList<Viaje> listaViajes;

	public ListaViajes(ArrayList<Viaje> listaViajes) {
		super();
		this.listaViajes = listaViajes;
	}
	
	public ListaViajes() {
		super();
		this.listaViajes = new ArrayList<>();
	}
	
	@XmlElement(name = "viaje")
	public ArrayList<Viaje> getListaViajes() {
		return listaViajes;
	}

	public void setListaViajes(ArrayList<Viaje> listaViajes) {
		this.listaViajes = listaViajes;
	}
}
