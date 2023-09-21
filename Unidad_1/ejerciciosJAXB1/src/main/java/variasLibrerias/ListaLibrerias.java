package variasLibrerias;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class ListaLibrerias {
	private ArrayList<Libreria> librerias;
	
	public ListaLibrerias(ArrayList<Libreria> librerias) {
		super();
		this.librerias = librerias;
	}

	public ListaLibrerias() {
		
	}
	
	@XmlElementWrapper(name="Librerias")
	@XmlElement(name="Libreria")
	public ArrayList<Libreria> getLibrerias() {
		return librerias;
	}

	public void setLibrerias(ArrayList<Libreria> libreriaLista) {
		this.librerias = libreriaLista;
	}

}
