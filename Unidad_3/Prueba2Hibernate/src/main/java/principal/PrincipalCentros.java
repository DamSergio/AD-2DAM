package principal;

import java.math.BigInteger;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.hibernate.SessionFactory;

import datos.*;

public class PrincipalCentros {
	private static SessionFactory sesion;

	public static void main(String[] args) {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);
		sesion = Conexion.getSession(); //Creo la sessionFactory una única vez.
		
//		System.out.println("--------------------");
//		consultarProfesor((short) 1000);
//		System.out.println("--------------------");
//		consultarProfesor((short) 3000);
//		System.out.println("--------------------");
//		consultarProfesor((short) 444);
//		System.out.println("--------------------");
//		consultarProfesor((short) 1011);
//		System.out.println("--------------------");
//		consultarProfesor((short) 1010);
		System.out.println("--------------------");
		consultarCentro((short) 1000);
		System.out.println("--------------------");
		
		sesion.close();
	}

	private static void consultarCentro(short codC) {
		Session session = sesion.openSession();
		C1Centros centro = (C1Centros) session.get(C1Centros.class, codC);
		if (centro == null) {
			System.out.println("El centro con cod: " + codC + " no existe");
			return;
		}
		
		System.out.println("Los datos del centro con codigo: " + codC);
		System.out.println("Cod Centro: " + codC + "\tNombre: " + centro.getNomCentro());
		
		String nDir = "NO TIENE DIRECTOR";
		if (centro.getDirector() != null) {
			C1Profesores dir = (C1Profesores) session.get(C1Profesores.class, centro.getDirector());
			nDir = dir.getNombreApe();
		}
		System.out.println("Director: " + nDir);
		
		Set<C1Profesores> profs = centro.getC1Profesoreses();
		if (profs.size() > 0) {
			String nMax = "";
			int max = 0;
			
			System.out.println("Lista de profesores del centro");
			System.out.printf("%4s %-30s %-30s %-30s %7s%n", "CodP", "NombreProfesor", "NombrEspecialidad", "Nombre Jefe", "NumAsig");
			System.out.printf("%4s %-30s %-30s %-30s %7s%n", "----", "------------------------------", "------------------------------", "------------------------------", "-------");
			for (C1Profesores p : profs) {
				String jefe = "NO TIENE JEFE";
				if (p.getC1Profesores() != null) {
					jefe = p.getC1Profesores().getNombreApe();
				}
				
				if (p.getC1Asignaturases().size() > max) {
					max = p.getC1Asignaturases().size();
				}
				
				System.out.printf("%4s %-30s %-30s %-30s %7s%n", p.getCodProf(), p.getNombreApe(), p.getC1Especialidad().getNombreEspe(), jefe, p.getC1Asignaturases().size());
			}
			
			for (C1Profesores p : profs) {
				if (p.getC1Asignaturases().size() == max) {
					nMax += p.getNombreApe() + ". ";
				}
			}
			
			System.out.printf("%4s %-30s %-30s %-30s %7s%n", "----", "------------------------------", "------------------------------", "------------------------------", "-------");
			System.out.println("El/los profesor/es con mas asignaturas es/son: " + nMax);
		} else {
			System.out.println("NO TIENE PROFESORES");
		}
		
	}

	private static void consultarProfesor(short codp) {
		 Session session = sesion.openSession();
		 C1Profesores pr = (C1Profesores) session.get(C1Profesores.class, codp);
		 if (pr == null) {
			 System.out.println("El profesor con cod: " + codp + " no existe");
		 } else {
			 System.out.println("Los datos del profesor con codigo: " + codp);
			 System.out.println("Nombre: " + pr.getNombreApe()); 
			 
			 String jefe = "NO TIENE JEFE";
			 if (pr.getC1Profesores() != null) {
				 jefe = pr.getC1Profesores().getNombreApe();
			 }
			 
			 System.out.println("Nombre jefe: " + jefe);
			 System.out.println("Nombre centro: " + pr.getC1Centros().getNomCentro());
			 System.out.println("Nombre especialidad: " + pr.getC1Especialidad().getNombreEspe());
			 
			 System.out.println("Num asignaturas que imparte: " + pr.getC1Asignaturases().size());
			 Set<C1Asignaturas> asigs = pr.getC1Asignaturases();
			 for (C1Asignaturas a : asigs) {
				 System.out.println("\tNombre asignatura: " + a.getNombreAsi() + ", CodAsig: " + a.getCodAsig());
			 }
			 
			 System.out.println("Num prof subordunados: " + pr.getC1Profesoreses().size());
			 Set<C1Profesores> profs = pr.getC1Profesoreses();
			 for (C1Profesores p : profs) {
				 System.out.println("\tCodigo prof: " + p.getCodProf() + ", Nombre prof: " + p.getNombreApe());
			 }
		 }
	}

}
