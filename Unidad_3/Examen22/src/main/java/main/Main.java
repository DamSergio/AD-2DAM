package main;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import datos.*;

public class Main {
	private static SessionFactory sesion;
	
	public static void main(String[] args) {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);
		sesion = Conexion.getSession(); //Creo la sessionFactory una única vez.
		
		ej1();
		
		sesion.close();
	}

	private static void ej1() {
		System.out.println("Llenar Tabla RESUMEN-CAMISETAS");
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		
		Query cons = session.createQuery("from Equipos e", Equipos.class);
		List<Equipos> equipos = cons.list();
		for (Equipos equipo : equipos) {
			System.out.printf("%-60s %8s %7s %14s%n", "Equipo : " + equipo.getCodigoequipo() + ", " + equipo.getNombreequipo(), "CAMISETA", "NºVECES", "IMPORTE PREMIO");
			System.out.println("-----------------------------------------------------------------------------------------");
			
			Set<Ciclistas> ciclistas = equipo.getCiclistases();
			if (ciclistas.isEmpty()) {
				System.out.println("No tiene");
				continue;
			}
			
			for (Ciclistas ciclista : ciclistas) {
				ArrayList<Camisetas> camisetas = new ArrayList<>();
				
				Set<Lleva> lleva = ciclista.getLlevas();
				for (Lleva l : lleva) {
					if (camisetas.contains(l.getCamisetas())) {
						continue;
					}
					camisetas.add(l.getCamisetas());
					
					int numCam = 0;
					for (Lleva c : lleva) {
						if (c.getCamisetas().getCodigocamiseta() == l.getCamisetas().getCodigocamiseta()) {
							numCam++;
						}
					}
					ResumenCamisetas r = new ResumenCamisetas();
					r.setCamisetas(l.getCamisetas());
					r.setCiclistas(ciclista);
					r.setEquipos(equipo);
					r.setImportepremio(l.getCamisetas().getImportepremio());
					r.setNumveces(BigInteger.valueOf(numCam));
					
					ResumenCamisetasId rID = new ResumenCamisetasId();
					rID.setCodigocamiseta(l.getCamisetas().getCodigocamiseta());
					rID.setCodigociclista(ciclista.getCodigociclista());
					rID.setCodigoequipo(equipo.getCodigoequipo());
					
					r.setId(rID);
					
					session.persist(r);
					
					System.out.printf("%-60s %8s %7s %14s%n", "    Insertado : " + ciclista.getCodigociclista() + " " + ciclista.getNombreciclista(), l.getCamisetas().getCodigocamiseta(), numCam, l.getCamisetas().getImportepremio());
				}
			}
		}
		
		tx.commit();
		session.close();
	}
}
