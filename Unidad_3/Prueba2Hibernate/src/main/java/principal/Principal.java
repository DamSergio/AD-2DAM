package principal;

import java.math.BigInteger;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import datos2.*;

public class Principal {
	private static SessionFactory sesion;

	public static void main(String[] args) {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);
		sesion = Conexion.getSession(); //Creo la sessionFactory una única vez.
		
		System.out.println("----------------------------------");
		insertarDepartamento();
		System.out.println("----------------------------------");
		insertarEmpleado();
		System.out.println("----------------------------------");
		cargarDeparReference(BigInteger.valueOf(10));
		cargarDeparGet(BigInteger.valueOf(10));
		System.out.println("----------------------------------");
		cargarDeparReference(BigInteger.valueOf(88));
		cargarDeparGet(BigInteger.valueOf(88));
		System.out.println("----------------------------------");
		
		
		sesion.close();
	}
	
	private static void cargarDeparGet(BigInteger nu) {
		Session session = sesion.openSession();
		Departamentos dep = (Departamentos) session.get(Departamentos.class, nu);
		if (dep == null) {
			System.out.println("El departamento no existe");
		} else {
			System.out.println("Nombre Dep:" + dep.getDnombre());
			System.out.println("Localidad:" + dep.getLoc());
		}
		session.close();
	}

	
	private static void cargarDeparReference(BigInteger nu) {
		Session session = sesion.openSession();
		try {
			Departamentos dep = (Departamentos) session.getReference(Departamentos.class, nu);
			System.out.println("Nombre: " + dep.getDnombre());
			System.out.println("Localidad: " + dep.getLoc());
		} catch (ObjectNotFoundException ob) {
			System.out.println("NO EXISTE EL DEPARTAMENTO.");
		}
		session.close();
	}


	private static void insertarEmpleado() {
		Session session = sesion.openSession(); //creo una sesión de trabajo
		Transaction tx = session.beginTransaction();

		System.out.println("Inserto un EMPLEADO EN EL DEPARTAMENTO 10.");

		double salario = new Float(1500);// inicializo el salario
		double comision = new Float(10); // inicializo la comisión

		Empleados em = new Empleados(); // creo un objeto empleados
		em.setEmpNo(BigInteger.valueOf(4455)); // el número de empleado es 4455
		em.setApellido("PEPE");
		em.setOficio("VENDEDOR");
		em.setSalario(salario);
		em.setComision(comision);

		Departamentos d = new Departamentos(); // creo un objeto Departamentos
		d.setDeptNo(BigInteger.valueOf(10)); // el número de dep es 10
		em.setDepartamentos(d);

		// fecha de alta
		java.util.Date hoy = new java.util.Date();
		java.sql.Date fecha = new java.sql.Date(hoy.getTime());
		em.setFechaAlt(fecha);

		try {
			session.persist(em);
			tx.commit();
		} catch (jakarta.persistence.PersistenceException e) {
			if (e.getMessage().contains("org.hibernate.exception.ConstraintViolationException")) {
				System.out.println("CLAVE DUPLICADA. DEPARTAMENTO YA EXISTE");
			} else if (e.getMessage().contains("org.hibernate.exception.DataException")) {
				System.out.println("ERROR EN LOS DATOS DE DEPARTAMENTO, DEMASIADOS CARACTERES");
			} else if (e.getMessage().contains("org.hibernate.exception.GenericJDBCException")) {
				System.out.println("ERROR JDBC. NO SE HA PODIDO EJECUATR LA CONSULTA");
			} else
				System.out.println("HA ocurrido un error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("ERROR NO CONTROLADO....");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		session.close();

	}

	private static void insertarDepartamento() {
		Session session = sesion.openSession(); //creo una sesión de trabajo
		Transaction tx = session.beginTransaction();
		
		Departamentos dep = new Departamentos();
		dep.setDeptNo(BigInteger.valueOf(62));
		dep.setDnombre("MARKETs");
		dep.setLoc("GUADALAJARA");
		
		try {
			session.persist(dep);
			tx.commit();
		    System.out.println("Reg INSERTADO.");
		} catch (jakarta.persistence.PersistenceException e) {
			if (e.getMessage().contains("org.hibernate.exception.ConstraintViolationException")) {
				System.out.println("CLAVE DUPLICADA. DEPARTAMENTO YA EXISTE");
			} else if (e.getMessage().contains("org.hibernate.exception.DataException")) {
				System.out.println("ERROR EN LOS DATOS DE DEPARTAMENTO, DEMASIADOS CARACTERES");
			} else if (e.getMessage().contains("org.hibernate.exception.GenericJDBCException")) {
				System.out.println("ERROR JDBC. NO SE HA PODIDO EJECUATR LA CONSULTA");
			} else
				System.out.println("HA ocurrido un error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("ERROR NO CONTROLADO....");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		session.close(); //cierro la sesión de trabajo

	}

}
