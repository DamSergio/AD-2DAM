package unalibreria;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Main {
	private static final String MIARCHIVO_XML = "./libreria.xml";

	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		crearXML();
		leerXML();
	}

	private static void leerXML() throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(Libreria.class);
		Unmarshaller unmars = context.createUnmarshaller();
		Libreria libreria =(Libreria) unmars.unmarshal(new FileReader(MIARCHIVO_XML));
		
		System.out.println(libreria.getNombre());
		System.out.println(libreria.getLugar());
		ArrayList<Libro> libros = libreria.getListaLibro();
		
		for (Libro libro:libros) {
			System.out.println("\n"+libro.getNombre());
			System.out.println("\t"+libro.getAutor());
			System.out.println("\t"+libro.getEditorial());
			System.out.println("\t"+libro.getIsbn());
		}
	}

	private static void crearXML() throws JAXBException {
		//Se crea la lista de libros
        ArrayList<Libro> libroLista = new ArrayList<Libro>();
        // Creamos dos libros y los añadimos
        Libro libro1 = new Libro("Entornos de Desarrollo", 
                 "Alicia Ramos","Garceta","978-84-1545-297-3" );
        libroLista.add(libro1);
        Libro libro2 = new Libro("Acceso a Datos","Maria Jesús Ramos",
                 "Garceta","978-84-1545-228-7" );
        libroLista.add(libro2);
 
        // Se crea La libreria y se le asigna la lista de libros
        Libreria milibreria = new Libreria();
        milibreria.setNombre("Prueba de libreria JAXB");
        milibreria.setLugar("Talavera, como no");
        milibreria.setListaLibro(libroLista);
 
        // Creamos el contexto indicando la clase raíz
        JAXBContext context = JAXBContext.newInstance(Libreria.class);
        //Creamos el Marshaller, convierte el java bean en una cadena XML
        Marshaller m = context.createMarshaller();
       //Formateamos el xml para que quede bien
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        // Lo visualizamos con system out
        m.marshal(milibreria, System.out);
         // Escribimos en el archivo
        m.marshal(milibreria, new File(MIARCHIVO_XML));
	}

}
