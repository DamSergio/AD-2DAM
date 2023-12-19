package main;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import datos.*;

public class MainExamen {
	private static SessionFactory sesion;

	public static void main(String[] args) {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);
		sesion = Conexion.getSession(); //Creo la sessionFactory una única vez.
		
//		ej1();
//		ej2();
		ej3(1, 1331, 24, BigDecimal.valueOf(10));
		
		sesion.close();
	}

	private static void ej3(int codEva, int codAlum, int codAsig, BigDecimal nota) {
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		
		boolean err = false;
		
		if (codEva < 1 || codEva > 3) {
			System.out.println("Codigo de valuacion erroneo");
			err = true;
		}
		
		Alumnos alum = (Alumnos) session.get(Alumnos.class, codAlum);
		if (alum == null) {
			System.out.println("Alumno no existe");
			err = true;
		}
		
		Asignaturas asig = (Asignaturas) session.get(Asignaturas.class, codAsig);
		if (asig == null) {
			System.out.println("Asignatura no existe");
			err = true;
		}
		
		if (nota.intValue() < 1 || nota.intValue() > 10) {
			System.out.println("Nota erronea");
			err = true;
		}
		
		if (asig.getCursos().getCodCurso() != alum.getCursos().getCodCurso()) {
			System.out.println("Esa asignatura no pertenece al curso del alumno");
			err = true;
		}
		
		if (err) {
			System.out.println("No se ha podido insertar");
			return;
		}
		
		EvaluacionesId eID = new EvaluacionesId();
		eID.setCodAsig(codAsig);
		eID.setCodEvaluacion(codEva);
		eID.setNumAlumno(codAlum);
		
		Evaluaciones eva = new Evaluaciones();
		eva.setAlumnos(alum);
		eva.setAsignaturas(asig);
		eva.setId(eID);
		eva.setNota(nota);
		
		try {
			session.persist(eva);
			tx.commit();
		} catch (Exception e) {
			System.out.println("Ya existe una nota para ese alumno en esa evaluacion");
			return;
		}
		
		System.out.println("Insertado correctamente");
		
		session.close();
	}

	private static void ej2() {
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		
		Query cons = session.createQuery("from Cursos c", Cursos.class);
		List<Cursos> cursos = cons.list();
		for(Cursos curso : cursos) {
			System.out.println("COD-CURSO: " + curso.getCodCurso() + "\tNOMBRE CURSO: " + curso.getDenominacion());
			System.out.println("NOMBRE CENTRO: " + curso.getCentros().getNombre() + "\tLOCALIDAD: " + curso.getCentros().getLocalidad());
			
			Set<Alumnos> alumnos = curso.getAlumnoses();
			if (alumnos.isEmpty()) {
				System.out.println("Este centro no tiene alumnos\n\n");
				continue;
			}
			
			String nomMax = "";
			double notaMax = 0;
			
			System.out.printf("%14s %-25s %9s %9s %9s %10s%n", "    NUM_ALUMNO", "NOMBRE", "NOTA_EVA1", "NOTA_EVA2", "NOTA_EVA3", "NOTA-MEDIA");
			System.out.printf("%14s %-25s %9s %9s %9s %10s%n", "    ----------", "-------------------------", "---------", "---------", "---------", "----------");
			
			for (Alumnos alum : alumnos) {
				float med1 = 0;
				float med2 = 0;
				float med3 = 0;
				
				float cont1 = 0;
				float cont2 = 0;
				float cont3 = 0;
				
				Set<Evaluaciones> evaluaciones = alum.getEvaluacioneses();
				for (Evaluaciones eva : evaluaciones) {
					if(eva.getNota() == null) {
						continue;
					}
					
					if (eva.getId().getCodEvaluacion() == 1) {
						med1 += eva.getNota().floatValue();
						cont1++;
					}
					if (eva.getId().getCodEvaluacion() == 2) {
						med2 += eva.getNota().floatValue();
						cont2++;
					}
					if (eva.getId().getCodEvaluacion() == 23) {
						med3 += eva.getNota().floatValue();
						cont3++;
					}
				}
				
				if (cont1 == 0) {
					cont1++;
				}
				if (cont2 == 0) {
					cont2++;
				}
				if (cont3 == 0) {
					cont3++;
				}
				
				med1 /= cont1;
				med2 /= cont2;
				med3 /= cont3;
				
				float medTotal = (med1 + med2 + med3) / 3;
				if (medTotal == notaMax) {
					nomMax += alum.getNombre() + ". ";
				}
				if (medTotal > notaMax) {
					nomMax = alum.getNombre() + ". ";
					notaMax = medTotal;
				}
				
				System.out.printf("%14s %-25s %9.3f %9.3f %9.3f %10.3f%n", "    " + alum.getNumAlumno(), alum.getNombre(), med1, med2, med3, medTotal);
				
				alum.setNotaMedia(BigDecimal.valueOf(medTotal));
				session.merge(alum);
			}
			System.out.printf("%14s %-25s %9s %9s %9s %10s%n", "    ----------", "-------------------------", "---------", "---------", "---------", "----------");
			System.out.println("Alumnos/s con mas nota media: " + nomMax);
			System.out.println("\n");
		}
		
		tx.commit();
		session.close();
	}

	private static void ej1() {
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		
		Query consCentros = session.createQuery("from Centros c", Centros.class);
		List<Centros> centros = consCentros.list();
		for (Centros centro : centros) {
			long numAlum = (long) session.createQuery("select count(a) from Centros c join c.cursoses cu join cu.alumnoses a"
					+ "	where c.codCentro = :centro", long.class)
					.setParameter("centro", centro.getCodCentro())
					.uniqueResult();
			long numCursos = (long) session.createQuery("select count(cu) from Centros c join c.cursoses cu"
					+ "	where c.codCentro = :centro", long.class)
					.setParameter("centro", centro.getCodCentro())
					.uniqueResult();
			
			centro.setNumAlumnos((int) numAlum);
			centro.setNumCursos((int) numCursos);
			
			session.merge(centro);
		}
		System.out.println("Centros actualizados correctamente");
		
		Query consCursos = session.createQuery("from Cursos c", Cursos.class);
		List<Cursos> cursos = consCursos.list();
		for (Cursos curso : cursos) {
			long numAlum = (long) session.createQuery("select count(a) from Cursos c join c.alumnoses a"
					+ "	where c.codCurso = :curso", long.class)
					.setParameter("curso", curso.getCodCurso())
					.uniqueResult();
			
			curso.setNumAlumnos((int) numAlum);
			
			session.merge(curso);
		}
		System.out.println("Cursos actualizados correctamente");
		
		Query consDepart = session.createQuery("from Departamentos d", Departamentos.class);
		List<Departamentos> departamentos = consDepart.list();
		for(Departamentos dep : departamentos) {
			long numAsig = (long) session.createQuery("select count(a) from Departamentos d join d.asignaturases a"
					+ "	where d.codDepar = :dept", long.class)
					.setParameter("dept", dep.getCodDepar())
					.uniqueResult();
			
			dep.setNumAsig((int) numAsig);
			
			session.merge(dep);
		}
		System.out.println("Departamentos actualizados correctamente");
		
		tx.commit();
		session.close();
	}

}
