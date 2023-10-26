package pruebaConexiones;

import java.sql.*;

public class PruebadatabaseMetadata {
	static Conexiones conec = new Conexiones();

	public static void main(String[] args) {

		metadatSQLite();
		metadatOracle();
		//metadatMariadb();
		resultsetMetadata();


	}

	private static void metadatMariadb() {
		Connection conexion = conec.getMariadb("ejemplo","root","");
		informacion("MARIADB", conexion,"EJEMPLO",null);

	}
	
	private static void metadatSQLite() {
		Connection conexion = conec.getSqlite("../misDB/sqlite/ejemplo.db");
		informacion("SQLITE", conexion,null, null);

	}
	private static void metadatOracle() {
		Connection conexion = conec.getOracle("DAM","DAM");
		informacion("ORACLE", conexion, null, "DAM");

	}

	// //getTables(catalogo, esquema, patronDeTabla, tipos[])	
	public static void informacion(String nombreconexion, Connection conexion,
			String cat, String esqu) {
		DatabaseMetaData dbmd;
		try {
			dbmd = conexion.getMetaData();
			ResultSet resul = null;
			String nombre = dbmd.getDatabaseProductName();
			String driver = dbmd.getDriverName();
			String url = dbmd.getURL();
			String usuario = dbmd.getUserName();

			System.out.println("INFORMACIÓN SOBRE LA BASE DE DATOS:" + nombreconexion);
		  	System.out.println("==========================================");
		  	System.out.printf("Nombre : %s %n", nombre );
		  	System.out.printf("Driver : %s %n", driver );
		  	System.out.printf("URL    : %s %n", url );
		  	System.out.printf("Usuario: %s %n", usuario );

			
		      //Obtener información de las tablas y vistas que hay 		       
		      resul = dbmd.getTables(cat, esqu, null, null);
			//getTables(catalogo, esquema, patronDeTabla, tipos[])	 
		  	
	    	while (resul.next()) {			   
			     String catalogo = resul.getString(1);//columna 1 
			     String esquema = resul.getString(2); //columna 2
			     String tabla = resul.getString(3);   //columna 3
			     String tipo = resul.getString(4);	//columna 4
		  	     System.out.printf("%s - Catalogo: %s, Esquema: %s, Nombre: %s %n", tipo, catalogo, esquema, tabla);
		      }   

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void resultsetMetadata() {
		try {
			//Class.forName("com.mysql.jdbc.Driver"); // Cargar el driver
			Connection conexion = conec.getOracle("DAM","DAM");
		         
			Statement sentencia = conexion.createStatement();
			ResultSet rs = sentencia
					.executeQuery("SELECT * FROM departamentos");
			
		      ResultSetMetaData rsmd = rs.getMetaData();
					
			int nColumnas = rsmd.getColumnCount();
			String nula;
			System.out.printf("Número de columnas recuperadas: %d%n",
			                   nColumnas);
			for (int i = 1; i <= nColumnas; i++) {
			  System.out.printf("Columna %d: %n ", i);
			  System.out.printf("  Nombre: %s %n   Tipo: %s %n ",
				 rsmd.getColumnName(i),  rsmd.getColumnTypeName(i));


			  if (rsmd.isNullable(i) == 0)
				nula = "NO";
			  else
				nula = "SI";
						
			  System.out.printf("  Puede ser nula?: %s %n ", nula);			
			  System.out.printf("  Máximo ancho de la columna: %d %n",
						     rsmd.getColumnDisplaySize(i));
			}// for
			
		      sentencia.close();
			rs.close();


		     
		     } catch (SQLException e) {
			e.printStackTrace();
				}

	}

}
