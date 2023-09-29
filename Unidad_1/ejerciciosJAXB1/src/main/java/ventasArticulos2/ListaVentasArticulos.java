package ventasArticulos2;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ventasarticulos")
public class ListaVentasArticulos {
	private ArrayList<VentasArticulos> ventas;

	public ListaVentasArticulos(ArrayList<VentasArticulos> ventas) {
		super();
		this.ventas = ventas;
	}
	
	public ListaVentasArticulos() {
		super();
		this.ventas = new ArrayList<>();
	}
	
	@XmlElement(name="articulo")
	public ArrayList<VentasArticulos> getVentas() {
		return ventas;
	}

	public void setVentas(ArrayList<VentasArticulos> ventas) {
		this.ventas = ventas;
	}
}
