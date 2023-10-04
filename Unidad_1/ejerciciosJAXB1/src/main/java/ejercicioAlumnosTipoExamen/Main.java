package ejercicioAlumnosTipoExamen;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Main {
	private final static int COD_ALUM_LON = 4;
	private final static int NOM_LON = 40;
	private final static int LOC_LON = 40;
	private final static int ASIG_LON = 4;
	private final static int NOTA_MEDIA_LON = 4;
	
	private final static int ASIG_NOM_LON = 40;
	private final static int NOTA_LON = 4;
	
	private final static int ALUM_DAT_LON = COD_ALUM_LON + NOM_LON + LOC_LON + ASIG_LON + NOTA_MEDIA_LON;
	private final static int NOTAS_DAT_LON = COD_ALUM_LON + ASIG_NOM_LON + NOTA_LON;
	
	private final static String XML_FILE = "./alumnos.xml";

	public static void main(String[] args) throws IOException, JAXBException {
		Scanner t = new Scanner(System.in);
		
		RandomAccessFile alumnosRandom = new RandomAccessFile(new File(".\\Alumnos.dat"), "rw");
		RandomAccessFile notasRandom = new RandomAccessFile(new File(".\\Notas.dat"), "rw");
		
		int op = 0;
		do {
			menu();
			op = t.nextInt();
			
			switch (op) {
				case 1:
					listarAlumnos(alumnosRandom, true);
					break;
				case 2:
					listarNotas(notasRandom, true);
					break;
				case 3:
					actualizarAlumnos(alumnosRandom, notasRandom);
					break;
				case 4:
					generarFicheroXML(alumnosRandom, notasRandom);
					break;
				case 5:
					System.out.println("Adios!!! 💀💀💀");
					break;
				default:
					
			}
			
		} while(op != 5);
		t.close();
		alumnosRandom.close();
		notasRandom.close();
	}

	private static void generarFicheroXML(RandomAccessFile alumnosRandom, RandomAccessFile notasRandom) throws IOException, JAXBException {
		ArrayList<Alumno> alumnos = listarAlumnos(alumnosRandom, false);
		ArrayList<Nota> notas = listarNotas(notasRandom, false);
		
		for (Alumno alumno : alumnos) {
			for (Nota nota : notas) {
				if (nota.getCodAlum() != alumno.getNumAlum()) {
					continue;
				}
				
				Nota n = new Nota();
				n.setNota(nota.getNota());
				n.setAsignatura(nota.getAsignatura());
				n.setCodAlum(null);
				alumno.getNotas().add(n);
			}
		}
		
		ListaAlumnos lista = new ListaAlumnos(alumnos);
		JAXBContext context = JAXBContext.newInstance(ListaAlumnos.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(lista, System.out);
		m.marshal(lista, new File(XML_FILE));
	}

	private static void actualizarAlumnos(RandomAccessFile alumnosRandom, RandomAccessFile notasRandom) throws IOException {
		ArrayList<Alumno> alumnos = listarAlumnos(alumnosRandom, false);
		ArrayList<Nota> notas = listarNotas(notasRandom, false);
		
		System.out.printf("%7s %-20s %-20s %7s %10s%n", "NUMALUM", "NOMBRE", "LOCALIDAD", "NUMASIG", "NOTA MEDIA");
		System.out.printf("%7s %-20s %-20s %7s %10s%n", "-------", "--------------------", "--------------------", "-------", "----------");
		
		String alumnoMayorNota = "";
		float mayorNota = 0;
		float mediaTotal = 0;
		int pos = 0;
		
		for (Alumno alumno : alumnos) {
			for (Nota nota : notas) {
				if (nota.getCodAlum() != alumno.getNumAlum()) {
					continue;
				}
				
				alumno.getNotas().add(nota);
				alumno.setNumAsig(alumno.getNumAsig() + 1);
			}
			
			alumno.actualizarNotaMedia();
			
			pos = (alumno.getNumAlum() - 1) * ALUM_DAT_LON;
			alumnosRandom.seek(pos + COD_ALUM_LON + NOM_LON + LOC_LON);
			alumnosRandom.writeInt(alumno.getNumAsig());
			alumnosRandom.writeFloat(alumno.getNotaMedia());
			
			System.out.printf("%7s %-20s %-20s %7s %10s%n", alumno.getNumAlum(), alumno.getNombre(), alumno.getLocalidad(), alumno.getNumAsig(), alumno.getNotaMedia());
			
			if (alumno.getNotaMedia() > mayorNota) {
				mayorNota = alumno.getNotaMedia();
				alumnoMayorNota = alumno.getNombre();
			}
			
			mediaTotal += alumno.getNotaMedia();
		}
		
		mediaTotal /= alumnos.size();
		System.out.printf("%7s %-20s %-20s %7s %10s%n", "-------", "--------------------", "--------------------", "-------", "----------");
		System.out.println("Alumno con nota media maxima: " + alumnoMayorNota);
		System.out.println("Media de nota total: " + mediaTotal);
		
	}

	private static ArrayList<Nota> listarNotas(RandomAccessFile notasRandom, boolean mostrar) throws IOException {
		int pos = 0;
		ArrayList<Nota> notas = new ArrayList<>();
		
		if (mostrar) {
			System.out.printf("%7s %-20s %5s%n", "NUMALUM", "ASIGNATURA", "NOTA");
			System.out.printf("%7s %-20s %5s%n", "-------", "--------------------", "-----");
		}
		
		while (pos < notasRandom.length()) {
			notasRandom.seek(pos);
			
			Nota nota = new Nota();
			nota.setCodAlum(notasRandom.readInt());
			
			String asignatura = "";
			for (int i = 0; i < ASIG_NOM_LON / 2; i++) {
				asignatura += notasRandom.readChar();
			}
			nota.setAsignatura(asignatura.trim());
			
			nota.setNota(notasRandom.readFloat());
			
			if (mostrar) {
				System.out.printf("%7s %-20s %5s%n", nota.getCodAlum(), nota.getAsignatura(), nota.getNota());
			}
			
			pos += NOTAS_DAT_LON;
			notas.add(nota);
		}
		
		return notas;
	}

	private static ArrayList<Alumno> listarAlumnos(RandomAccessFile alumnosRandom, boolean mostrar) throws IOException {
		int pos = 0;
		ArrayList<Alumno> alumnos = new ArrayList<>();
		
		if (mostrar) {
			System.out.printf("%7s %-20s %-20s %7s %10s%n", "NUMALUM", "NOMBRE", "LOCALIDAD", "NUMASIG", "NOTA MEDIA");
			System.out.printf("%7s %-20s %-20s %7s %10s%n", "-------", "--------------------", "--------------------", "-------", "----------");
		}
		
		while (pos < alumnosRandom.length()) {
			alumnosRandom.seek(pos);
			
			int numAlumno = alumnosRandom.readInt();
			if (numAlumno == 0) {
				pos += ALUM_DAT_LON;
				continue;
			}
			
			Alumno alumno = new Alumno();
			alumno.setNumAlum(numAlumno);
			
			String nombre = "";
			for (int i = 0; i < NOM_LON / 2; i++) {
				nombre += alumnosRandom.readChar();
			}
			alumno.setNombre(nombre.trim());
			
			String localidad = "";
			for (int i = 0; i < LOC_LON / 2; i++) {
				localidad += alumnosRandom.readChar();
			}
			alumno.setLocalidad(localidad.trim());
			
			alumno.setNumAsig(alumnosRandom.readInt());
			alumno.setNotaMedia(alumnosRandom.readFloat());
			
			if (mostrar) {
				System.out.printf("%7s %-20s %-20s %7s %10s%n", alumno.getNumAlum(), alumno.getNombre(), alumno.getLocalidad(), alumno.getNumAsig(), alumno.getNotaMedia());
			}
			
			pos += ALUM_DAT_LON;
			alumnos.add(alumno);
		}
		
		return alumnos;
	}

	private static void menu() {
		System.out.println("--------------------------------------------\n");
		System.out.println("MENU DE OPERACIONES");
		System.out.println("1. Ejercicio 1: Lista alumnos");
		System.out.println("2. Ejercicio 2: Listar notas");
		System.out.println("3. Ejercicio 3: Actualizar fichero Alumnos");
		System.out.println("4. Ejercicio 4: General fichero Alumnos.xml");
		System.out.println("5. Salir");
		System.out.println("\n--------------------------------------------");
		
	}

}
