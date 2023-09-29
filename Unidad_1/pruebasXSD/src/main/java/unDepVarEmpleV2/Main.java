package unDepVarEmpleV2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Main {
	private static final String XML_FILE = "./undeparvariosemples.xml";

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
			
			DatosempledepartType misEmpleados = (DatosempledepartType) jaxbElement.getValue();
			Departamento dep = misEmpleados.getDepartamento();
			ListaEmpleados listaEmple = misEmpleados.getEmpleados();
			List<Empleado> empleados = listaEmple.getEmple();
			
			System.out.println("---------------------------- ");
			System.out.println("---VISUALIZAR LOS OBJETOS--- ");
			System.out.println("---------------------------- ");
			System.out.println("Nombre: " + dep.getNombredep());
			System.out.println("Codigo: " + dep.getCodigodep());
			System.out.println("Localidad: " + dep.getLocalidad());
			System.out.println("Presupuesto: " + dep.getPresupuesto());
			
			for (Empleado emple : empleados) {
				System.out.println("Empleado: " + emple.getEmpno() + ", nombre: " + emple.getApellido() + ", oficio: " + emple.getOficio() + ", sueldo: " + emple.getSalario());
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
