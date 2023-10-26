package pruebaConexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class EjemploResultMetadata {

	public static void main(String[] args) {
		resultsetMetadata();
	}

	private static void resultsetMetadata() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // Cargar el driver
			Connection conexion = DriverManager.getConnection(
				"jdbc:mysql://localhost/ejemplo", "ejemplo", "ejemplo");
		         
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
			conexion.close();


		     } catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		     } catch (SQLException e) {
			e.printStackTrace();
				}

	}

}
