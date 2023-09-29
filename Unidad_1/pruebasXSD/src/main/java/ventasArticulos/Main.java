package ventasArticulos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Main {
	private static final String XML_FILE = "./ventasarticulos.xml";

	public static void main(String[] args) throws JAXBException {
		leerXML();
		
		insertarVenta(13, "Nuevo Cli", 10, "10-10-2021");
		insertarVenta(10, "Nuevo Cli", 10, "10-10-2021");
		
		if (borrarVenta(13)) {
			System.out.println("Borrada");
		} else {
			System.out.println("No borrada");
		}
		if (borrarVenta(13)) {
			System.out.println("Borrada");
		} else {
			System.out.println("No borrada");
		}
		
		if (modificarStock(20)) {
			System.out.println("Modificada");
		} else {
			System.out.println("No modificada");
		}
		
		if (cambiarDatos(11, 40, "10/10/2000")) {
			System.out.println("Cambiada");
		} else {
			System.out.println("No cambiada");
		}
		if (cambiarDatos(15, 40, "10/10/2000")) {
			System.out.println("Cambiada");
		} else {
			System.out.println("No cambiada");
		}
	}

	private static boolean cambiarDatos(int numVenta, int stock, String fecha) {
		System.out.println("---------------------------- ");
		System.out.println("-------CAMBIA VENTA--------- ");
		System.out.println("---------------------------- ");
		
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream(XML_FILE));
			VentasType miventa = (VentasType) jaxbElement.getValue();
			Ventas vent = miventa.getVentas();
			List<Ventas.Venta> listaVentas = vent.getVenta();
			
			for (Ventas.Venta venta : listaVentas) {
				if (numVenta == venta.getNumventa().intValue()) {
					venta.setUnidades(stock);
					venta.setFecha(fecha);
					Marshaller m = jaxbContext.createMarshaller();
			    	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
			    	m.marshal(jaxbElement, new FileOutputStream(XML_FILE));
			    	return true;
				}
			}

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	private static void leerXML(){
		System.out.println("------------------------------ ");
		System.out.println("-------VISUALIZAR XML--------- ");
		System.out.println("------------------------------ ");
		try {
			//Creamos el contexto
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		 	Unmarshaller u = jaxbContext.createUnmarshaller();	
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal (new FileInputStream(XML_FILE));
			Marshaller m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// Visualiza por consola
			m.marshal(jaxbElement, System.out);
			//Cargamos ahora el documento en los tipos
			VentasType miventa = (VentasType) jaxbElement.getValue();
			//Obtenemos una instancia para obtener todas las ventas
			Ventas vent = miventa.getVentas();
			// Guardamos las ventas en la lista
			List<Ventas.Venta> listaVentas = new ArrayList();
			listaVentas = vent.getVenta();

			System.out.println("---------------------------- ");
			System.out.println("---VISUALIZAR LOS OBJETOS--- ");
			System.out.println("---------------------------- ");
			// Cargamos los datos del artículo
			DatosArtic miartic = (DatosArtic) miventa.getArticulo();
			System.out.println("Nombre art: " + miartic.getDenominacion());
			System.out.println("Coodigo art: " + miartic.getCodigo());
			System.out.println("Ventas  del artículo , hay: " + listaVentas.size());
			//Visualizamos las ventas del artículo
			for (int i = 0; i < listaVentas.size(); i++) {
				Ventas.Venta ve = (Ventas.Venta) listaVentas.get(i);
				System.out.println("Número de venta: " + ve.getNumventa() + ". Nombre cliente: " + ve.getNombrecliente() + ", unidades: " + ve.getUnidades() + ", fecha: " + ve.getFecha());
			}
		} catch (JAXBException je) {
			System.out.println(je.getCause());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	private static void insertarVenta(int numVenta, String nomCli, int uni, String fecha) {
		System.out.println("---------------------------- ");
		System.out.println("-------AÑADIR VENTA--------- ");
		System.out.println("---------------------------- ");
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream(XML_FILE));
			VentasType miventa = (VentasType) jaxbElement.getValue();
			Ventas vent = miventa.getVentas();
			List<Ventas.Venta> listaVentas = vent.getVenta();
			
			int existe = 0; 
		    for (int i = 0; i < listaVentas.size(); i++) {
		    	Ventas.Venta ve = (Ventas.Venta) listaVentas.get(i);
		    	if (ve.getNumventa().intValue() == numVenta) {
		    		existe = 1;	
		    		break;
		    	}	
		    }
		    
		    if (existe == 0) {
		    	Ventas.Venta ve = new Ventas.Venta();
		    	ve.setNombrecliente(nomCli);
		    	ve.setFecha(fecha); 
		    	ve.setUnidades(uni);
		    	BigInteger nume = BigInteger.valueOf(numVenta);
		    	ve.setNumventa(nume);
		    	// Se añade la venta a la lista
		    	listaVentas.add(ve);
		    	//Se crea el Marshaller, volcar la lista al fichero XML
		    	Marshaller m = jaxbContext.createMarshaller();
		    	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
		    	m.marshal(jaxbElement, new FileOutputStream(XML_FILE));
		    	System.out.println("Venta añadida: " + numVenta);
		    } else {
		    	System.out.println("En número de venta ya existe: " + numVenta);
		    }


		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static boolean borrarVenta(int numVenta) {
		System.out.println("---------------------------- ");
		System.out.println("-------BORRAR VENTA--------- ");
		System.out.println("---------------------------- ");
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream(XML_FILE));
			VentasType miventa = (VentasType) jaxbElement.getValue();
			Ventas vent = miventa.getVentas();
			List<Ventas.Venta> listaVentas = vent.getVenta();
			
			for (Ventas.Venta venta : listaVentas) {
				if (venta.getNumventa().intValue() == numVenta) {
					listaVentas.remove(venta);
					Marshaller m = jaxbContext.createMarshaller();
			    	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
			    	m.marshal(jaxbElement, new FileOutputStream(XML_FILE));
			    	return true;
				}
			}
			
			return false;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private static boolean modificarStock(int stock) {
		System.out.println("---------------------------- ");
		System.out.println("-------MODIFI STOCK--------- ");
		System.out.println("---------------------------- ");
		
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream(XML_FILE));
			VentasType miventa = (VentasType) jaxbElement.getValue();
			DatosArtic artic = miventa.getArticulo();
			
			int st = artic.getStock().intValue() + stock;
			artic.setStock(BigInteger.valueOf(st));
			
			Marshaller m = jaxbContext.createMarshaller();
	    	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
	    	m.marshal(jaxbElement, new FileOutputStream(XML_FILE));
			
			return true;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
		}
		return false;
	}
}
