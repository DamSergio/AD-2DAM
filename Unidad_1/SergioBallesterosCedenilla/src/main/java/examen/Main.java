package examen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Main {
	private final static int VIA_COD = 4;
	private final static int VIA_NOM = 60;
	private final static int VIA_PVP = 4;
	private final static int VIA_PLA = 4;
	private final static int VIA_NUM = 4;
	private final static int VIA_LON = VIA_COD + VIA_NOM + VIA_PVP + VIA_PLA + VIA_NUM;
	
	private final static int RES_NOM = 40;
	private final static int RES_COD = 4;
	private final static int RES_RES = 4;
	private final static int RES_LON = RES_NOM + RES_COD + RES_RES;
	
	private final static String XML_FILE = "viajes.xml";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int op = 0;
		
		try {
			RandomAccessFile viajes = new RandomAccessFile(new File("./Viajes.dat"), "rw");
			RandomAccessFile reservas = new RandomAccessFile(new File("./Reservas.dat"), "rw");
			
			do {
				menu();
				op = sc.nextInt();
				
				switch (op) {
				case 1:
					mostrarReservas(reservas);
					break;
				case 2:
					actualizarViajes(viajes, reservas);
					break;
				case 3:
					crearXML(viajes, reservas);
					break;
				case 0:
					break;
				}
			} while (op != 0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Alguno(s) de los archivos .dat no existen");
		}
	}
	
	private static void crearXML(RandomAccessFile viajes, RandomAccessFile reservas) {
		int posVia = 0;
		ArrayList<Viaje> vjs = new ArrayList<>();
		
		try {
			while (posVia < viajes.length()) {
				viajes.seek(posVia);
				int codVia = viajes.readInt();
				if (codVia == 0) {
					posVia += VIA_LON;
					continue;
				}
				
				Viaje viaje = new Viaje();
				viaje.setCodViaje(codVia);
				
				String nombre = "";
				for (int i = 0; i < VIA_NOM / 2; i++) {
					nombre += viajes.readChar();
				}
				viaje.setNombre(nombre.trim());
				
				viaje.setPvp(viajes.readInt());
				viaje.setNumPlazas(viajes.readInt());
				viaje.setNumReservas(viajes.readInt());
				viaje.setTotalImporte(viaje.getNumReservas() * viaje.getPvp());
				
				int posRes = 0;
				while (posRes < reservas.length()) {
					reservas.seek(posRes);
					Reserva reserva = new Reserva();
					String nomCli = "";
					for (int i = 0; i < RES_NOM / 2; i++) {
						nomCli += reservas.readChar();
					}
					
					int codRes = reservas.readInt();
					if (codRes != codVia) {
						posRes += RES_LON;
						continue;
					}
					
					reserva.setNombreCliente(nomCli.trim());
					reserva.setPlazasReservadas(reservas.readInt());
					viaje.getListaReservas().add(reserva);
					posRes += RES_LON;
				}
				
				vjs.add(viaje);
				posVia += VIA_LON;
			}
			
			ListaViajes listaViajes = new ListaViajes(vjs);
			
			JAXBContext context = JAXBContext.newInstance(ListaViajes.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(listaViajes, System.out);
			m.marshal(listaViajes, new File(XML_FILE));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void mostrarReservas(RandomAccessFile reservas) {
		int pos = 0;
		System.out.printf("%5s %-20s %7s %6s%n", "REGIS", "Nom Cliente", "CodViaj", "Plazas");
		System.out.printf("%5s %-20s %7s %6s%n", "-----", "--------------------", "-------", "------");
		int reg = 1;
		try {
			while (pos < reservas.length()) {
				reservas.seek(pos);
				
				String nombre = "";
				for (int i = 0; i < RES_NOM / 2; i++) {
					nombre += reservas.readChar();
				}
				
				int cod = reservas.readInt();
				int pla = reservas.readInt();
				
				System.out.printf("%5s %-20s %7s %6s%n", reg, nombre, cod, pla);
				reg++;
				pos += RES_LON;
			}
			System.out.printf("%5s %-20s %7s %6s%n", "-----", "--------------------", "-------", "------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void actualizarViajes(RandomAccessFile viajes, RandomAccessFile reservas) {
		int posVia = 0;
		System.out.printf("%8s %-30s %3s %6s %8s %7s %-30s%n", "CodViaje", "Nombre", "PVP", "PLAZAS", "RESERVAS", "IMPORTE", "Situacion");
		System.out.printf("%8s %-30s %3s %6s %8s %7s %-30s%n", "--------", "------------------------------", "---", "------", "--------", "-------", "---------");
		
		try {
			String nomMaxRes = "";
			int maxRes = 0;
			while (posVia < viajes.length()) {
				viajes.seek(posVia);
				int codVia = viajes.readInt();
				
				if (codVia == 0) {
					posVia += VIA_LON;
					continue;
				}
				
				int posRes = 0;
				int reserva = 0;
				while (posRes < reservas.length()) {
					reservas.seek(posRes);
					reservas.skipBytes(RES_NOM);
					
					int codRes = reservas.readInt();
					if (codRes != codVia) {
						posRes += RES_LON;
						continue;
					}
					
					reserva += reservas.readInt();
					posRes += RES_LON;
				}
				
				String nombre = "";
				for (int i = 0; i < VIA_NOM / 2; i++) {
					nombre += viajes.readChar();
				}
				
				int pvp = viajes.readInt();
				int plazas = viajes.readInt();
				viajes.writeInt(reserva);
				
				int importe = reserva * pvp;
				String situacion = "Correcto";
				if (reserva > plazas) {
					situacion = "Reservas Excedidas en " + (plazas - reserva);
				}
				
				if (reserva > maxRes) {
					maxRes = reserva;
					nomMaxRes = nombre;
				}
				
				System.out.printf("%8s %-30s %3s %6s %8s %7s %-30s%n", codVia, nombre, pvp, plazas, reserva, importe, situacion);
				posVia += VIA_LON;
			}
			
			System.out.printf("%8s %-30s %3s %6s %8s %7s %-30s%n", "--------", "------------------------------", "---", "------", "--------", "-------", "---------");
			System.out.println("Viaje con mas reservas: " + nomMaxRes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("No existe fichero");
		}
		
	}

	private static void menu() {
		System.out.println("1. Ejericio1: Mostrar Reservas");
		System.out.println("2. Ejericio1: Actualizar Viajes");
		System.out.println("3. Ejercicio2: Crear XML");
		System.out.println("0. Salir");
		System.out.println("Elije una opcion: ");
	}
}
