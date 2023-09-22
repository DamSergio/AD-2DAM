package nuevosDep;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Main {
	private final static String XML_FILE = "./NuevosDep.xml";

	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		leerXML();
	}

	private static void leerXML() throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(NuevosDepartamentos.class);
		Unmarshaller unmars = context.createUnmarshaller();
		NuevosDepartamentos listaDep =(NuevosDepartamentos) unmars.unmarshal(new FileReader(XML_FILE));
		ArrayList<Departamento> departamentos = listaDep.getNuevosDepartamentos();
		
		for (Departamento dep:departamentos) {
			System.out.println(dep.getDeptno());
			System.out.println(dep.getDnombre());
			System.out.println(dep.getLocalidad()+"\n");
		}
	}
}
