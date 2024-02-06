package main;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.layers.layer2.meta.AttributeValuesMap;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;
import org.neodatis.tool.wrappers.list.IOdbList;

import dto.*;

public class Main {
	private static ODB bd;
	private final static String NEODATIS_BD = "proyectos.dat";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "PROYECTOS", "proyectos");

			bd = ODBFactory.open(NEODATIS_BD);
			
			int op;
			do {
				menu();
				op = sc.nextInt();
				
				switch (op) {
				case 1:
					crearBD(conexion);
					break;
				case 2:
					listarProyecto(15);
					break;
				case 3:
					insertarParticipacion(1, 15, "Becario", 2);
					break;
				case 0:
					System.out.println("Bye bye 🖐🖐🖐");
					break;
				default:
					System.out.println("Opcion invalida");
				}
				
				System.out.println("\n----------------------------------\n");
			} while (op != 0);
			
			bd.close();
			sc.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void insertarParticipacion(int codEst, int codPro, String tipo, int numAport) {
		boolean err = false;
		
		//Comprobar y guardar estudiante
		Estudiantes est = null;
		try {
			IQuery consulta = new CriteriaQuery(Estudiantes.class, Where.equal("codestudiante", codEst));
			est = (Estudiantes) bd.getObjects(consulta).getFirst();		
		} catch (IndexOutOfBoundsException e) {
			err = true;
			System.out.println("Estudiante no existe");
		}
		
		//Comprobar y guardar proyecto
		Proyectos pro = null;
		try {
			IQuery consulta = new CriteriaQuery(Proyectos.class, Where.equal("codigoproyecto", codPro));
			pro = (Proyectos) bd.getObjects(consulta).getFirst();		
		} catch (IndexOutOfBoundsException e) {
			err = true;
			System.out.println("Proyecto no existe");
		}
		
		//Comprobar que ya haya una participacion para ese proyecto de ese estudiante
		if (!err) {
			try {
				ICriterion where = new And().add(Where.equal("estudiante.codestudiante", codEst)).add(Where.equal("proyecto.codigoproyecto", codPro));
				IQuery consulta = new CriteriaQuery(Participa.class, where);
				bd.getObjects(consulta).getFirst();
				
				err = true;
				System.out.println("Ya existe una participacion de ese estudiante en ese proyecto");
			} catch (IndexOutOfBoundsException e) {
				
			}
		}
		
		if (err) {
			System.out.println("No se ha podido insertar");
			return;
		}
		
		Values vCodPartMax = bd.getValues(new ValuesCriteriaQuery(Participa.class).max("codparticipacion"));
		ObjectValues oCodPartMax = vCodPartMax.nextValues();
		BigDecimal codPart = (BigDecimal) oCodPartMax.getByAlias("codparticipacion");
		
		Participa p = new Participa();
		p.setCodparticipacion(codPart.intValue() + 1);
		p.setEstudiante(est);
		p.setProyecto(pro);
		p.setTipoparticipacion(tipo);
		p.setNumaportaciones(numAport);
		
		est.getParticipaen().add(p);
		pro.getParticipantes().add(p);
		
		bd.store(p);
		bd.store(est);
		bd.store(pro);
		
		bd.commit();
		
		System.out.println("Participacion insertada correctamente");
	}

	private static void listarProyecto(int cod) {
		try {
			IQuery qPro = new CriteriaQuery(Proyectos.class, Where.equal("codigoproyecto", cod));
			Proyectos p = (Proyectos) bd.getObjects(qPro).getFirst();
			
			System.out.println("--------------------- ------------- -----------------------------");
			System.out.println("Codigo de proyecto: " + cod + "\t\tNombre: " + p.getNombre());
			System.out.println("Fecha inicio: " + p.getFechainicio() + "\tFecha fin: " + p.getFechafin());
			System.out.println("Presupuesto: " + p.getPresupuesto() + "\t\tExtraaportacion: " + p.getExtraaportacion());
			System.out.println("--------------------- --------------------- -----------------------");
			
			if (p.getParticipantes().size() <= 0) {
				System.out.println("Este proyecto no tiene ningun participante");
				return;
			}
			
			System.out.println("Participantes en el proyecto: ");
			System.out.println("----------------------------");
			System.out.printf("%16s %13s %30s %20s %15s %7s%n", "CODPARTICIPACION", "CODESTUDIANTE", "NOMBREESTUDIANTE", "TIPAPORTACION", "NUMAPORTACIONES", "IMPORTE");
			System.out.printf("%16s %13s %30s %20s %15s %7s%n", "----------------", "-------------", "------------------------------", "--------------------", "---------------", "-------");
			
			float importeT = 0;
			int aportacionesT = 0;
			for (Participa par : p.getParticipantes()) {
				float importe = par.getNumaportaciones() * p.getExtraaportacion();
				importeT += importe;
				aportacionesT += par.getNumaportaciones();
				
				System.out.printf("%16s %13s %-30s %-20s %15s %7s%n", par.getCodparticipacion(), par.getEstudiante().getCodestudiante(), par.getEstudiante().getNombre(), par.getTipoparticipacion(), par.getNumaportaciones(), importe);
			}
			
			System.out.printf("%16s %13s %30s %20s %15s %7s%n", "----------------", "-------------", "------------------------------", "--------------------", "---------------", "-------");
			System.out.printf("%16s %13s %30s %20s %15s %7s%n", "TOTALES: ", "", "", "", aportacionesT, importeT);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("El proyecto con codigo: " + cod + ", no existe");
		}
	}

	private static void crearBD(Connection conexion) {
		insertarEstudiantes(conexion);
		insertarProyectos(conexion);
		insertarParticipa(conexion);
		try {
			llenarSetEstudiantes();
			llenarSetProyectos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		bd.commit();
		
		System.out.println("Base de datos creada correctamente");
	}

	private static void llenarSetProyectos() {
		Objects<Proyectos> proyectos = bd.getObjects(Proyectos.class);
		while (proyectos.hasNext()) {
			Proyectos p = proyectos.next();
			
			IQuery query = new CriteriaQuery(Participa.class, Where.equal("proyecto.codigoproyecto", p.getCodigoproyecto()));
			Objects<Participa> part = bd.getObjects(query);
			for (Participa par : part) {
				p.getParticipantes().add(par);
			}
			
			bd.store(p);
		}
		
		bd.commit();
	}

	private static void llenarSetEstudiantes() throws SQLException {
		Objects<Estudiantes> estudiantes = bd.getObjects(Estudiantes.class);
		while (estudiantes.hasNext()) {
			Estudiantes e = estudiantes.next();
			
			IQuery query = new CriteriaQuery(Participa.class, Where.equal("estudiante.codestudiante", e.getCodestudiante()));
			Objects<Participa> part = bd.getObjects(query);
			for (Participa par : part) {
				e.getParticipaen().add(par);
			}
			
			
			bd.store(e);
		}
		
		bd.commit();
	}

	private static void insertarParticipa(Connection conexion) {
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM participa");
			while (rs.next()) {
				if (comprobarPar(rs.getInt(1))) {
					continue;
				}
				
				Participa p = new Participa();
				p.setCodparticipacion(rs.getInt(1));
				p.setTipoparticipacion(rs.getString(4));
				p.setNumaportaciones(rs.getInt(5));
				
				IQuery queryEst = new CriteriaQuery(Estudiantes.class, Where.equal("codestudiante", rs.getInt(2)));
				Estudiantes e = (Estudiantes) bd.getObjects(queryEst).getFirst();
				p.setEstudiante(e);
				
				IQuery queryPro = new CriteriaQuery(Proyectos.class, Where.equal("codigoproyecto", rs.getInt(3)));
				Proyectos pro = (Proyectos) bd.getObjects(queryPro).getFirst();
				p.setProyecto(pro);
				
				bd.store(p);
			}
			
			bd.commit();
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void insertarProyectos(Connection conexion) {
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM proyectos");
			while (rs.next()) {
				if (comprobarPro(rs.getInt(1))) {
					continue;
				}
				
				Proyectos p =  new Proyectos();
				p.setCodigoproyecto(rs.getInt(1));
				p.setNombre(rs.getString(2));
				p.setFechainicio(rs.getDate(3));
				p.setFechafin(rs.getDate(4));
				p.setPresupuesto(rs.getFloat(5));
				p.setExtraaportacion(rs.getFloat(6));
				
				bd.store(p);
			}
			
			bd.commit();
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void insertarEstudiantes(Connection conexion) {
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM estudiantes");
			while (rs.next()) {
				if (comprobarEst(rs.getInt(1))) {
					continue;
				}
				
				Estudiantes e = new Estudiantes();
				e.setCodestudiante(rs.getInt(1));
				e.setNombre(rs.getString(2));
				e.setDireccion(rs.getString(3));
				e.setTlf(rs.getString(4));
				e.setFechaalta(rs.getDate(5));
				
				bd.store(e);
			}
			
			bd.commit();
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean comprobarPar(int cod) {
		try {
			IQuery consulta = new CriteriaQuery(Participa.class, Where.equal("codparticipacion", cod));
			bd.getObjects(consulta).getFirst();		
	  
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	private static boolean comprobarPro(int cod) {
		try {
			IQuery consulta = new CriteriaQuery(Proyectos.class, Where.equal("codigoproyecto", cod));
			bd.getObjects(consulta).getFirst();		
	  
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	private static boolean comprobarEst(int cod) {
		try {
			IQuery consulta = new CriteriaQuery(Estudiantes.class, Where.equal("codestudiante", cod));
			bd.getObjects(consulta).getFirst();		
	  
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	private static void menu() {
		System.out.println("OPERACIONES PROYECTOS\n");
		System.out.println("1. Crear BD");
		System.out.println("2. Listar un proyecto.");
		System.out.println("3. Insertar participación.");
		System.out.println("0. Salir");
	}

}
