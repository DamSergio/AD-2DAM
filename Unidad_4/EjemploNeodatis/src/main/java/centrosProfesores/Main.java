package centrosProfesores;

import java.sql.*;
import java.util.HashSet;

import org.neodatis.odb.*;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.core.query.IQuery;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ODBRuntimeException;
import org.neodatis.odb.Objects;

import org.neodatis.odb.OID;
import org.neodatis.odb.core.oid.OIDFactory;

import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Or;
import org.neodatis.odb.core.query.criteria.Not;

import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;

public class Main {

	private static ODB bd;

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/neodatis", "root", "1234");
	
			bd = ODBFactory.open("profesasig.neo");
			
			// Recorrer C1asignaturas y guardar en Neodatis
			InsertarAsignaturas(conexion);
			// Recorrer C1Centros y guardar en Neodatis
			InsertarCentros(conexion);
			// Recorrer C1Profesores y guardar en Neodatis
			InsertarProfesores(conexion);
			// Llenar el set de profesores de asignaturas, por cada objeto
			// asignatura hacemos la select
			llenarSetProfesAsignaturas(conexion);
			// Llenar el set de profesores de Centros y el director
			llenarSetProfesEnCentrosYDirector(conexion);
			conexion.close();
			bd.close();
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
}

	private static void llenarSetProfesEnCentrosYDirector(Connection conexion) throws SQLException {
		Objects<C1Centros> objectscen = bd.getObjects(C1Centros.class);
		while (objectscen.hasNext()) {
			C1Centros cee = objectscen.next();
	        HashSet<C1Profesores> setprofesores = new HashSet<C1Profesores>();
	        Statement sentencia = conexion.createStatement();
	        ResultSet resul = sentencia.executeQuery ("SELECT * FROM c1_profesores where cod_centro=" + cee.getCodCentro());
	        while (resul.next()) {
	        	IQuery consulta = new CriteriaQuery(C1Profesores.class, Where.equal("codProf", resul.getInt(1)));
	        	C1Profesores obj = (C1Profesores) bd.getObjects(consulta).getFirst();
	        	setprofesores.add(obj);
		    }
	        //Asigno el set al centro
	        cee.setSetprofesores(setprofesores);
	        // Localizo al director.
	        sentencia = conexion.createStatement();
	        resul = sentencia.executeQuery("SELECT director FROM c1_centros where cod_centro=" + cee.getCodCentro());
	        
	        if (resul.next()) {
	        	IQuery consulta = new CriteriaQuery(C1Profesores.class, Where.equal("codProf", resul.getInt(1)));
	        	try {
	        		C1Profesores obj = (C1Profesores) bd.getObjects(consulta).getFirst();
	        		cee.setDirector(obj);
	        	} catch (IndexOutOfBoundsException ee) {
	        		System.out.println("Centro  " + cee.getCodCentro() + ", Sin Director, es null.");
	        	}
	        }
	        bd.store(cee);
	        resul.close();sentencia.close();
		}
	   bd.commit();
	}

	private static void llenarSetProfesAsignaturas(Connection conexion) throws SQLException {
		Objects<C1Asignaturas> objects = bd.getObjects(C1Asignaturas.class);    
		while (objects.hasNext()) {
			C1Asignaturas asi = objects.next();
		    HashSet<C1Profesores> setprofesores=new HashSet<C1Profesores>();
		    Statement sentencia = conexion.createStatement();
		    ResultSet resul = sentencia.executeQuery ("SELECT * FROM c1_asigprof where cod_asig = '" + asi.getCodAsig() + "'");
		    while (resul.next()) {
		    	IQuery consulta = new CriteriaQuery(C1Profesores.class, Where.equal("codProf", resul.getInt(2)));
		    	//Cargo el objeto profesor
		    	C1Profesores obj = (C1Profesores) bd.getObjects(consulta).getFirst(); 
		    	//Lo añado al set de profesores
		    	setprofesores.add(obj);
		    }
		    //Asigno el set a la asignatura
		    asi.setSetprofesores(setprofesores);
		    bd.store(asi);
		    resul.close();sentencia.close();
		}
		bd.commit();
	}

	private static void InsertarProfesores(Connection conexion) {
		try {
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery ("SELECT * FROM c1_Profesores");
			while (resul.next()) {
				if (comprobarprofe(resul.getInt(1)) == false) {	
					IQuery consulta = new CriteriaQuery(C1Centros.class, Where.equal("codCentro", resul.getInt(6)));
					//Cargamos el centro del profesor
					C1Centros cen = (C1Centros) bd.getObjects(consulta).getFirst();
					C1Profesores nueprof = new C1Profesores(resul.getInt(1), resul.getString(2), resul.getString(3),resul.getDate(4), resul.getString(5), cen);
					bd.store(nueprof);
					System.out.println("Profe grabado " + resul.getInt(1));
				} else
					System.out.println("Profe: "+resul.getInt(1)+", EXISTE.");
		 	}
			bd.commit();
			resul.close();sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();	
		}
	}

	private static void InsertarCentros(Connection conexion) {
		try {
			Statement sentencia = (Statement) conexion.createStatement();
		    ResultSet resul = sentencia.executeQuery ("SELECT * FROM c1_centros");
		    while (resul.next()) {
		    	if (comprobarcentro(resul.getInt(1)) == false) {
		    		HashSet<C1Profesores> setprofesores = new HashSet<C1Profesores>();
		    		C1Centros cen = new C1Centros(resul.getInt(1), resul.getString(2), null, resul.getString(4), resul.getString(5), resul.getString(6), setprofesores);
		    		bd.store(cen);
		    		System.out.println("Centro grabado " + resul.getInt(1));
		    	} else
		    		System.out.println("Centro: " +resul.getInt(1)+ ", EXISTE.");
			}
			bd.commit();
			resul.close();sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void InsertarAsignaturas(Connection conexion) {
		try {
			Statement sentencia = (Statement) conexion.createStatement();
			ResultSet resul = sentencia.executeQuery("SELECT * FROM c1_asignaturas");
			while (resul.next()) {
				if (comprobarasig(resul.getString(1)) == false) {
					HashSet<C1Profesores> setprofesores = new HashSet<C1Profesores>();
					C1Asignaturas ass = new C1Asignaturas(resul.getString(1), resul.getString(2), setprofesores);
					bd.store(ass);
					System.out.println("Asignatura grabada " + resul.getString(1));
				} else
					System.out.println("Asig: "+ resul.getString(1)+ ", EXISTE.");
			}
			bd.commit();
			resul.close();sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static boolean comprobarasig(String cod) {
		try {
			IQuery consulta = new CriteriaQuery(C1Asignaturas.class, Where.equal("codAsig", cod));
			C1Asignaturas obj = (C1Asignaturas) bd.getObjects(consulta).getFirst();		
	  
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	private static boolean comprobarcentro(int cod) {
		try {
			IQuery consulta = new CriteriaQuery(C1Centros.class, Where.equal("codCentro", cod));
			C1Centros obj = (C1Centros) bd.getObjects(consulta).getFirst();
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	private static boolean comprobarprofe(int cod) {
		try {
			IQuery consulta = new CriteriaQuery(C1Profesores.class, Where.equal("codProf", cod));
			C1Profesores obj = (C1Profesores) bd.getObjects(consulta).getFirst();
		 	return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	} 
}
