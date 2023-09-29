package ventasArticulos2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Main {
	private final static String XML_FILE = "./ventasarticulosdos.xml";

	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		leerXML();

	}

	private static void leerXML() throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(ListaVentasArticulos.class);
		Unmarshaller unmars = context.createUnmarshaller();
		ListaVentasArticulos listaVentasArticulos = (ListaVentasArticulos) unmars.unmarshal(new FileReader(XML_FILE));
		
		ArrayList<VentasArticulos> articulos = listaVentasArticulos.getVentas();
		System.out.println("Numero de articulos: " + articulos.size());
		
		for (VentasArticulos art:articulos) {
			Articulo articulo = art.getArticulo();
			System.out.println("Codigo: " + articulo.getCodigo() + "\tNombre: " + articulo.getDenominacion() + "\tPVP: " + articulo.getPrecio());
			System.out.printf("%9s %11s %20s %8s %7s%n", "NUM VENTA", "FECHA VENTA", "NOM_CLIENTE", "UNIDADES", "IMPORTE");
			System.out.printf("%9s %11s %20s %8s %7s%n", "---------", "-----------", "--------------------", "--------", "-------");
			//System.out.println(articulo.getStock());
			ArrayList<Venta> ventas = art.getVentas();
			int unidades = 0;
			float totalImp = 0;
			for (Venta v:ventas) {
				float importe = v.getUnidades() * articulo.getPrecio();
				unidades += v.getUnidades();
				totalImp += importe;
				System.out.printf("%9s %11s %20s %8s %7s%n", v.getNumVenta(), v.getFecha(), v.getNombreCliente(), v.getUnidades(), importe);
			}
			
			System.out.printf("%9s %11s %20s %8s %7s%n", "---------", "-----------", "--------------------", "--------", "-------");
			System.out.printf("%9s %11s %20s %8s %7s%n", "TOTALES", " ", " ", unidades, totalImp);
			System.out.println();
			
		}
	}

}
