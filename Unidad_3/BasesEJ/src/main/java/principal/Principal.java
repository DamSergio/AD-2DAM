package principal;

import java.math.BigInteger;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import datos.*;

public class Principal {
	private static SessionFactory sesion;

	public static void main(String[] args) {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);
		sesion = Conexion.getSession(); //Creo la sessionFactory una única vez.
		
//		System.out.println("----------------------------------");
//		insertarDepartamento();
//		System.out.println("----------------------------------");
//		insertarEmpleado();
//		System.out.println("----------------------------------");
//		cargarDeparReference(BigInteger.valueOf(10));
//		cargarDeparGet(BigInteger.valueOf(10));
//		System.out.println("----------------------------------");
//		cargarDeparReference(BigInteger.valueOf(88));
//		cargarDeparGet(BigInteger.valueOf(88));
//		System.out.println("----------------------------------");
//		insertModifDepart(BigInteger.valueOf(10), "Nuevo 10", "Nueva loc");
//		insertModifDepart(BigInteger.valueOf(15), "Nuevo 15", "Nueva loc");
//		System.out.println("----------------------------------");
//		actualizardepalempleado(BigInteger.valueOf(123), BigInteger.valueOf(30));
//		actualizardepalempleado(BigInteger.valueOf(123), BigInteger.valueOf(200));
//		actualizardepalempleado(BigInteger.valueOf(12345), BigInteger.valueOf(20));
//		System.out.println("----------------------------------");
//		Departamentos dd = new Departamentos();
//		dd.setDeptNo(BigInteger.valueOf(25));
//		dd.setDnombre("Nuevo 25");
//		dd.setLoc("Nueva loc 25");
//		
//		actualizaEmple(BigInteger.valueOf(123), dd);
//		System.out.println("----------------------------------");
//		insertaEmpleadoAlSetDeDepartamento(BigInteger.valueOf(10), BigInteger.valueOf(7900));
//		System.out.println("----------------------------------");
//		listarDepartamentos();
		System.out.println("----------------------------------");
		consultaUniqueResult();
		System.out.println("----------------------------------");
		
		sesion.close();
	}
	
	private static void consultaUniqueResult() {
		Session session = sesion.openSession();
		Departamentos depart = (Departamentos) session.createQuery("from Departamentos as dep where dep.deptNo = 10", Departamentos.class).uniqueResult();
		if (depart == null) {
			
		}
	
		System.out.println(depart.getLoc() +"*"+depart.getDnombre());
		
		//Visualiza los datos del departamento con nombre CONTABILIDAD
		System.out.println("------------------------");
		depart = (Departamentos) session.createQuery("from Departamentos as dep where dep.dnombre = 'Nuevo 10'", Departamentos.class).uniqueResult();
		System.out.println(depart.getLoc() +"*"+depart.getDeptNo());

		System.out.println("------------------------");
		Long cont =  (Long) session.createQuery("select count(*) from Empleados ", Long.class).uniqueResult();
		System.out.println("Número de empleados: " + cont);

		System.out.println("------------------------");
		Double media =  (double) session.createQuery("select coalesce(avg(salario), 0) from Empleados e where e.departamentos.deptNo = 50", Double.class).uniqueResult();
		System.out.println("Media de salario de empleados: " + media);

			
		System.out.println("------------------------");
		Double maxi =  (double) session.createQuery("select max(salario) from Empleados ", Double.class).uniqueResult();
		System.out.println("Máximo de salario de empleados: " + maxi);

		session.close();
	}

	private static void listarDepartamentos() {
		Session session = sesion.openSession();
		Query<Departamentos> q = session.createQuery("from Departamentos d order by d.deptNo", Departamentos.class);
		
		List<Departamentos> lista = q.list();
		System.out.println("Número de departamentos: " + lista.size());
		for (Departamentos d : lista) {
			System.out.println("Num dep: " + d.getDeptNo() + "\tNombre dep: " + d.getDnombre() + "\tLocalidad: " + d.getLoc() + "\tNumero empleados: " + d.getEmpleadoses().size());
			
			if (d.getEmpleadoses().size() == 0) {
				System.out.println("No tiene empleados");
				System.out.println("\n");
				continue;
			}
			
			System.out.printf("%5s %-10s %-10s %-10s %7s%n", "EMPNO", "APELLIDOS", "OFICIO", "FECHAALTA", "SALARIO");
			System.out.printf("%5s %-10s %-10s %-10s %7s%n", "-----", "----------", "----------", "----------", "-------");
			
			double sal = 0;
			
			Set<Empleados> emples = d.getEmpleadoses();
			for (Empleados e : emples) {
				System.out.printf("%5s %-10s %-10s %-10s %7s%n", e.getEmpNo(), e.getApellido(), e.getOficio(), e.getFechaAlt(), e.getSalario());
				sal += e.getSalario();
			}
			
			System.out.printf("%5s %-10s %-10s %-10s %7s%n", "-----", "----------", "----------", "----------", "-------");
			System.out.printf("%-38s %7s%n", "Total salario:", sal);
			System.out.printf("%5s %-10s %-10s %-10s %7s%n", "-----", "----------", "----------", "----------", "-------");
			System.out.println("\n");
		}
		
		session.close();
	}

	private static void insertaEmpleadoAlSetDeDepartamento(BigInteger nu, BigInteger emp) {
		Session session = sesion.openSession();
		Departamentos dep = (Departamentos) session.get(Departamentos.class, nu);
		if (dep == null) {
			System.out.println("El departamento no existe. No se puede insertar.");
		} else {
			// compruebo empleado
			Empleados emple = (Empleados) session.get(Empleados.class, emp);
			if (emple == null) {
				System.out.println("El Empleado no existe. No se puede insertar.");
			} else {
				// lo añado al set
				Transaction tx = session.beginTransaction();
				dep.getEmpleadoses().add(emple);
				System.out.println("Empleado " + emp + " añadido al departamento " + nu);
				session.merge(dep);          
				tx.commit();
			}
		}
		session.close();

	}

	
	private static void actualizaEmple(BigInteger emp, Departamentos dep) {
		Session session = sesion.openSession();
		Empleados emple = (Empleados) session.get(Empleados.class, emp);
		if (emple == null) {
			System.out.println("El Empleado no existe. No se puede actualizar.");
		} else {
			Transaction tx = session.beginTransaction();
			emple.setDepartamentos(dep);
			System.out.println("Empleado " + emp + " actualizado al departamento " + dep.getDnombre());
			session.merge(emple);
			tx.commit();
		}
		session.close();
	}

	private static void actualizardepalempleado(BigInteger emp, BigInteger nu) {
		Session session = sesion.openSession();
		Empleados emple = (Empleados) session.get(Empleados.class, emp);
		if (emple == null) {
			System.out.println("El Empleado no existe. No se puede actualizar.");
		} else {
			Departamentos dep = (Departamentos) session.get(Departamentos.class, nu);
			if (dep == null) 
				System.out.println("El departamento no existe. No se puede actualizar.");
			else {
				Transaction tx = session.beginTransaction();
				emple.setDepartamentos(dep);
				System.out.println("Empleado " + emp + " actualizado al departamento " + nu);
				session.merge(emple);
				tx.commit();
			}
		}
		session.close();
	}

	private static void insertModifDepart(BigInteger cod, String nom, String loc) {
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		System.out.println("Cargo departamento.");
		Departamentos dep = (Departamentos) session.get(Departamentos.class, cod);
		System.out.println("==============================");
		System.out.println("DATOS DEL DEPARTAMENTO " + cod);
		if (dep == null) {
			System.out.println("El departamento no existe, LO CREO");
			dep = new Departamentos();
			dep.setDeptNo(cod);
			dep.setDnombre(nom);
			dep.setLoc(loc);
			session.persist(dep);
		} else {
			System.out.println("El departamento existe, LO MODIFICO");
			dep.setDnombre(nom);
			dep.setLoc(loc);
			session.persist(dep);
		}
		tx.commit();
		session.close();
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
