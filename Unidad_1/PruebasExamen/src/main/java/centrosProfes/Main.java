package centrosProfes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Main {
	private final static String XML_FILE = ".\\centrosprofes.xml";
	private final static int LONG_CENTROS = 4 + 80 + 90 + 70 + 4 + 4 + 4;
	
	public static void main(String[] args) throws JAXBException, IOException {
		Scanner sc = new Scanner(System.in);
		RandomAccessFile centros = new RandomAccessFile(new File("./centrosprofes.dat"), "rw");
		
		int op = 0;
		do {
			menu();
			
			op = sc.nextInt();
			switch (op) {
			case 1:
				crearFicheroProfes(centros);
				break;
			case 2:
				leerCentrosDat(centros);
				break;
			case 0:
				break;
			default:
			}
		} while (op != 0);
		sc.close();
	}
	
	private static void leerCentrosDat(RandomAccessFile centros) throws IOException {
		int pos = 0;
		System.out.printf("%6s %-40s %-45s %-35s %7s %8s %7s%n", "CODIGO", "NOMBRE_CENTRO", "DIRECCION", "NOMBRE_DIRECTOR", "SALARIO", "NUM_PROF", "MED_SAL");
		System.out.printf("%6s %-40s %-45s %-35s %7s %8s %7s%n", "------", "----------------------------------------", "---------------------------------------------", "-----------------------------------", "-------", "--------", "-------");
		while (pos < centros.length()) {
			centros.seek(pos);
			int cod = centros.readInt();
			if (cod == 0) {
				pos += LONG_CENTROS;
				continue;
			}
			
			String nom = "";
			for (int i = 0; i < 40; i++) {
				nom += centros.readChar();
			}
			
			String dir = "";
			for (int i = 0; i < 45; i++) {
				dir += centros.readChar();
			}
			
			String nomDir = "";
			for (int i = 0; i < 35; i++) {
				nomDir += centros.readChar();
			}
			
			float sal = centros.readFloat();
			int numP = centros.readInt();
			float medSal = centros.readFloat();
			
			System.out.printf("%6s %40s %45s %35s %7s %8s %7s%n", cod, nom, dir, nomDir, sal, numP, medSal);
			pos += LONG_CENTROS;
		}
	}

	private static void crearFicheroProfes(RandomAccessFile centrosDat) throws JAXBException, IOException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller u = jaxbContext.createUnmarshaller();	
		JAXBElement jaxbElement = (JAXBElement) u.unmarshal (new FileInputStream(XML_FILE));
		Marshaller m = jaxbContext.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(jaxbElement, System.out);
		
		DatoscentroType dt = (DatoscentroType) jaxbElement.getValue();
		List<UnCentro> listaCentros = dt.getUncentro();
		for (UnCentro c : listaCentros) {
			Centros cen = c.getCentro();
			int codigo = cen.getCodigocentro().intValue();
			
			StringBuffer nombre = new StringBuffer(cen.getNombrecentro());
			nombre.setLength(40);
			
			StringBuffer dirr = new StringBuffer(cen.getDireccion());
			dirr.setLength(45);
			
			Profe director = cen.getDirector();
			StringBuffer direcNom = new StringBuffer(director.getNombreprofe());
			direcNom.setLength(35);
			
			float salario = director.getSalario();
			
			Profesores p = c.getProfesores();
			List<Profe> profes = p.getProfe();
			int numProfe = profes.size();
			
			float mediaSal = 0;
			for (Profe pro : profes) {
				mediaSal += pro.getSalario();
			}
			if (numProfe > 0) {
				mediaSal /= numProfe;
			}
			
			int pos = (codigo - 1) * LONG_CENTROS;
			centrosDat.seek(pos);
			
			centrosDat.writeInt(codigo);
			centrosDat.writeChars(nombre.toString());
			centrosDat.writeChars(dirr.toString());
			centrosDat.writeChars(direcNom.toString());
			centrosDat.writeFloat(salario);
			centrosDat.writeInt(numProfe);
			centrosDat.writeFloat(mediaSal);
		}
	}

	public static void menu() {
		System.out.println("------------------------------");
		System.out.println("1. generar fihero directo");
		System.out.println("2. listar centros");
		System.out.println("0. Salir");
		System.out.println("------------------------------");
	}
}
