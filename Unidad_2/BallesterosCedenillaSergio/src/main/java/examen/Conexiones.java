package examen;

import java.sql.*;

public class Conexiones {

	public Connection getOracle(String usuario, String clave) {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", usuario,
					clave);
 
			return conexion;
			
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Connection getMysql(String bd, String usuario, String clave) {

		try {
			Class.forName ("com.mysql.cj.jdbc.Driver");	
			Connection conexion = DriverManager.getConnection  
			        ("jdbc:mysql://localhost:3306/"+bd, usuario, clave); 
 
			return conexion;
			
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
