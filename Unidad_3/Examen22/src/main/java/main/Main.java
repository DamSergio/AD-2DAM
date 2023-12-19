package main;

import java.math.BigInteger;
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
//		ej2(BigInteger.valueOf(8));
		
		sesion.close();
	}

	private static void ej2(BigInteger codEqui) {
		Session session = sesion.openSession();
		
		Equipos equipo = (Equipos) session.get(Equipos.class, codEqui);
		if(equipo == null) {
			System.out.println("El equipo no existe");
			return;
		}
		
		System.out.println("COD-EQUIPO: " + equipo.getCodigoequipo() + "\tNOMBRE: " + equipo.getNombreequipo());
		System.out.println("PAIS: " + equipo.getPais() + "\tJEFE DE EQUIPO: " + equipo.getDirector());
		System.out.println("--------------------------------------------------------------------------------------");
		
		Query cons = session.createQuery("from Ciclistas e where e.equipos = :equipo order by e.codigociclista", Ciclistas.class).setParameter("equipo", equipo);
		List<Ciclistas> ciclistas = cons.list();
		if (ciclistas.size() == 0) {
			System.out.println("No hay ciclistas");
			return;
		}
		
		int maxEtapa = 0;
		String nomMaxEtapa = "No Hay";
		
		int maxMon = 0;
		String nomMaxMon = "No Hay";
		
		System.out.printf("%6s %-50s %12s %14s %16s%n", "CODIGO", "NOMBRE", "ETAP GANADAS", "TRAMOS GANADOS", "Nº VECESCAMISETAS");
		System.out.printf("%6s %-50s %12s %14s %16s%n", "======", "==================================================", "============", "==============", "================");
		for (Ciclistas c : ciclistas) {
			if (c.getEtapases().size() == maxEtapa && c.getEtapases().size() != 0) {
				nomMaxEtapa += c.getNombreciclista() + ". ";
			}
			if (c.getEtapases().size() > maxEtapa) {
				nomMaxEtapa = c.getNombreciclista() + ". ";
				maxEtapa = c.getEtapases().size();
			}
			
			if (c.getTramospuertoses().size() == maxMon && c.getTramospuertoses().size() != 0) {
				nomMaxMon += c.getNombreciclista() + ". ";
			}
			if (c.getTramospuertoses().size() > maxMon) {
				nomMaxMon = c.getNombreciclista() + ". ";
				maxMon = c.getTramospuertoses().size();
			}
			
			System.out.printf("%6s %-50s %12s %14s %16s%n", c.getCodigociclista(), c.getNombreciclista(), c.getEtapases().size(), c.getTramospuertoses().size(), c.getResumenCamisetases().size());
		}
		
		System.out.printf("%6s %-50s %12s %14s %16s%n", "======", "==================================================", "============", "==============", "================");
		System.out.println("Nombre/s de corredor/es con más etapas ganadas (si los hay): " + nomMaxEtapa);
		System.out.println("Nombre/s de corredor/es con más tramos de montaña ganados (si los hay): " + nomMaxMon);
		
		session.close();
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
				System.out.println("No tiene ciclistas");
				continue;
			}
			
			Query ciclis = session.createQuery("select c.codigociclista, l.camisetas.codigocamiseta, count(l.camisetas) from Ciclistas c join c.llevas l"
					+ "	where c.equipos.codigoequipo = :equipo"
					+ "	group by c.codigociclista, l.camisetas.codigocamiseta"
					+ "	order by c.codigociclista").setParameter("equipo", equipo.getCodigoequipo());
			
			List datos = ciclis.list();
			for (Object o : datos) {
				Object[] data = (Object[]) o;
				Ciclistas c = (Ciclistas) session.get(Ciclistas.class, data[0]);
				Camisetas ca = (Camisetas) session.get(Camisetas.class, data[1]);
				long veces = (long) data[2];
				
				ResumenCamisetas r = new ResumenCamisetas();
				r.setCamisetas(ca);
				r.setCiclistas(c);
				r.setEquipos(equipo);
				r.setImportepremio(ca.getImportepremio());
				r.setNumveces(BigInteger.valueOf(veces));
				
				ResumenCamisetasId rID = new ResumenCamisetasId();
				rID.setCodigocamiseta(ca.getCodigocamiseta());
				rID.setCodigociclista(c.getCodigociclista());
				rID.setCodigoequipo(equipo.getCodigoequipo());
				
				r.setId(rID);
				
				session.persist(r);
				
				System.out.printf("%-60s %8s %7s %14s%n", "    Insertado : " + c.getCodigociclista() + " " + c.getNombreciclista(), ca.getCodigocamiseta(), veces, ca.getImportepremio());
			}
		}
		
		tx.commit();
		session.close();
	}
}
