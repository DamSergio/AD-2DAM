package ventasArticulos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import nuevosDep.Departamento;
import nuevosDep.NuevosDepartamentos;

public class Main {
	private final static String XML_FILE = "./ventasarticulos.xml";

	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		leerXML();

	}

	private static void leerXML() throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(VentasArticulos.class);
		Unmarshaller unmars = context.createUnmarshaller();
		VentasArticulos ventasArticulos =(VentasArticulos) unmars.unmarshal(new FileReader(XML_FILE));
		Articulo articulo = ventasArticulos.getArticulo();
		
		System.out.println(articulo.getCodigo());
		System.out.println(articulo.getDenominacion());
		System.out.println(articulo.getStock());
		System.out.println(articulo.getPrecio());
		
		ArrayList<Venta> ventas = ventasArticulos.getVentas();
		for (Venta v:ventas) {
			System.out.println("\n\t"+v.getNumVenta());
			System.out.println("\t"+v.getUnidades());
			System.out.println("\t"+v.getNombreCliente());
			System.out.println("\t"+v.getFecha());
		}
	}

}
