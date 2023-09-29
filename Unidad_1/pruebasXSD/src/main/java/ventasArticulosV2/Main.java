package ventasArticulosV2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Main {
	private static final String XML_FILE = "./ventasarticulos.xml";

	public static void main(String[] args) {
		leerXML();
	}

	private static void leerXML() {
		System.out.println("------------------------------ ");
		System.out.println("-------VISUALIZAR XML--------- ");
		System.out.println("------------------------------ ");
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();	
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal (new FileInputStream(XML_FILE));
			Marshaller m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(jaxbElement, System.out);
			
			VentasType miVenta = (VentasType) jaxbElement.getValue();
			Ventas vent = miVenta.getVentas();
			List<Venta> listaVentas = vent.getVenta();
			
			DatosArtic miArtic = (DatosArtic) miVenta.getArticulo();
			System.out.println("---------------------------- ");
			System.out.println("---VISUALIZAR LOS OBJETOS--- ");
			System.out.println("---------------------------- ");
			System.out.println("Nombre art: " + miArtic.getDenominacion());
			System.out.println("Coodigo art: " + miArtic.getCodigo());
			System.out.println("Ventas  del artículo , hay: " + listaVentas.size());
			
			for (Venta ve : listaVentas) {
				System.out.println("Número de venta: " + ve.getNumventa() + ". Nombre cliente: " + ve.getNombrecliente() + ", unidades: " + ve.getUnidades() + ", fecha: " + ve.getFecha());
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
	}

}
