package variasLibrerias;

import java.util.ArrayList;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Main {
	private static final String XML_FILE = "./librerias.xml";

	public static void main(String[] args) {
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
        
        
        //Creamos el Marshaller, convierte el java bean en una cadena XML
        
         // Escribimos en el archivo
        try {
        	JAXBContext context = JAXBContext.newInstance(ListaLibrerias.class);
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
