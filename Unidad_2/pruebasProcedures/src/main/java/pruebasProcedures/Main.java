package pruebasProcedures;

import java.sql.*;

public class Main {
	static Conexiones conn = new Conexiones();
	
	public static void main(String[] args) throws SQLException {
		pruebaOracle(10);
		pruebaOracle(14);
		
		pruebaMySQL(10);
		pruebaMySQL(14);
	}

	private static void pruebaMySQL(int dep) throws SQLException {
		Connection conexion = conn.getMysql("ejemplo", "root", "1234");
		String sql = "{ call datos_dep (?, ?, ?) } ";
		CallableStatement llamada = conexion.prepareCall(sql);
		llamada.setInt(1, dep);//valor devuelto
		llamada.registerOutParameter(2, Types.VARCHAR);      //param de entrada
		llamada.registerOutParameter(3, Types.VARCHAR); 
		llamada.executeUpdate(); 
	       System.out.printf("Nombre Dep: %s, loc: %s %n",
	                        llamada.getString(2), llamada.getString(3));
	       llamada.close();
	       conexion.close();
	}

	private static void pruebaOracle(int dep) throws SQLException {
		Connection conexion = conn.getOracle("ej", "1234");
		
		//Construir orden de llamada
	       String sql = "{ ? = call nombre_dep (?, ?) } "; // ORACLE


	       //Preparar la llamada
	       CallableStatement llamada = conexion.prepareCall(sql);


	       //registrar parámetro de resultado
	       llamada.registerOutParameter(1, Types.VARCHAR);//valor devuelto


	       llamada.setInt(2,dep);      //param de entrada


	       //Registrar parámetro de salida
	       llamada.registerOutParameter(3, Types.VARCHAR);//parámetro OUT


	       //Ejecutar el procedimiento
	       llamada.executeUpdate(); 
	       System.out.printf("Nombre Dep: %s, Localidad: %s %n",
	                        llamada.getString(1), llamada.getString(3));
	       llamada.close();
	       conexion.close();

	}

}
