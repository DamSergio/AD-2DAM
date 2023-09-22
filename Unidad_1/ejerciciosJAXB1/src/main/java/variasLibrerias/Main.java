package variasLibrerias;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Main {
	private static final String XML_FILE = "./librerias.xml";

	public static void main(String[] args) throws JAXBException {
		crearXML();
		leerXML();
	}

	private static void leerXML() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(ListaLibrerias.class);
		Unmarshaller unmars = context.createUnmarshaller();
		try {
			ListaLibrerias listaLibrerias =(ListaLibrerias) unmars.unmarshal(new FileReader(XML_FILE));
			ArrayList<Libreria> librerias = listaLibrerias.getLibrerias();
			
			for (Libreria lib:librerias) {
				System.out.println(lib.getNombre());
				System.out.println(lib.getLugar());
				ArrayList<Libro> libros = lib.getListaLibro();
				
				for (Libro libro:libros) {
					System.out.println("\n"+libro.getNombre());
					System.out.println("\t"+libro.getAutor());
					System.out.println("\t"+libro.getEditorial());
					System.out.println("\t"+libro.getIsbn());
				}
			}
		} catch (FileNotFoundException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void crearXML() {
		ArrayList<Libro> libroLista = new ArrayList<Libro>();
		ArrayList<Libreria> libreriaLista = new ArrayList<>();
		
		Libro libro1 = new Libro("Entornos de Desarrollo", "Alicia Ramos","Garceta","978-84-1545-297-3" );
		libroLista.add(libro1);
		Libro libro2 = new Libro("Acceso a Datos","Maria Jesús Ramos","Garceta","978-84-1545-228-7" );
		libroLista.add(libro2);
		
		Libreria milibreria = new Libreria();
        milibreria.setNombre("Prueba de libreria JAXB");
        milibreria.setLugar("Talavera, como no");
        milibreria.setListaLibro(libroLista);
        libreriaLista.add(milibreria);
        
        Libreria milibreria2 = new Libreria();
        milibreria2.setNombre("Prueba 2 de libreria JAXB");
        milibreria2.setLugar("Talavera 2, como no");
        milibreria2.setListaLibro(libroLista);
        libreriaLista.add(milibreria2);
        
        ListaLibrerias librerias = new ListaLibrerias();
        librerias.setLibrerias(libreriaLista);
        
    	JAXBContext context;
		try {
			context = JAXBContext.newInstance(ListaLibrerias.class);
			Marshaller m = context.createMarshaller();
	    	//Formateamos el xml para que quede bien
	    	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    	// Lo visualizamos con system out
			m.marshal(librerias, System.out);
			m.marshal(librerias, new File(XML_FILE));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
