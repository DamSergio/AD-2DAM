
import java.sql.*;

public class pruebas {

	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println("-------sqlite----------------");
		sqlite();
		System.out.println("----------mysql-------------");
	 //	mysql();
		System.out.println("----------h2-------------");
		h2();
		System.out.println("----------derby-------------");
		derby();

		System.out.println("----------Access-------------");
		acces();

		System.out.println("----------hsqldb-------------");
		hsqldb();

		System.out.println("----------oracle-------------");
	//	oracle();

	}// main

	public static void oracle() throws ClassNotFoundException, SQLException {

		Connection conexion1 = DriverManager.getConnection("jdbc:oracle:thin:@cloud.riberadeltajo.es:8011:XE", "DAM","DAM");

		String sql = "SELECT * FROM departamentos";
		Statement sentencia = conexion1.createStatement();
		boolean valor = sentencia.execute(sql);

		if (valor) {
			ResultSet rs = sentencia.getResultSet();
			while (rs.next())
				System.out.printf("%s, %s, %s %n", rs.getString(1), rs.getString(2), rs.getString(3));
			rs.close();
		} else {
			int f = sentencia.getUpdateCount();
			System.out.printf("Filas afectadas:%d %n", f);
		}
		sentencia.close();
		conexion1.close();

	}

	public static void acces() throws ClassNotFoundException, SQLException {

		Connection conexion1 = DriverManager.getConnection("jdbc:ucanaccess://./Basesdatos/ejemplo.accdb");
		// si no se ponen los campos salen ordenados alfabéticamente (dnombre, dept_no,
		// loc)
		String sql = "SELECT dept_no, dnombre, loc FROM departamentos";
		Statement sentencia = conexion1.createStatement();
		boolean valor = sentencia.execute(sql);

		if (valor) {
			ResultSet rs = sentencia.getResultSet();
			while (rs.next())
				System.out.printf("%s, %s, %s %n", rs.getString(1), rs.getString(2), rs.getString(3));
			rs.close();
		} else {
			int f = sentencia.getUpdateCount();
			System.out.printf("Filas afectadas:%d %n", f);
		}
		sentencia.close();
		conexion1.close();

	}

	public static void hsqldb() throws ClassNotFoundException, SQLException {

		Connection conexion1 = DriverManager.getConnection("jdbc:hsqldb:file:./Basesdatos/hsqldb/ejemplo/ejemplo");
		String sql = "SELECT * FROM departamentos";
		Statement sentencia = conexion1.createStatement();
		boolean valor = sentencia.execute(sql);

		if (valor) {
			ResultSet rs = sentencia.getResultSet();
			while (rs.next())
				System.out.printf("%d, %s, %s %n", rs.getInt(1), rs.getString(2), rs.getString(3));
			rs.close();
		} else {
			int f = sentencia.getUpdateCount();
			System.out.printf("Filas afectadas:%d %n", f);
		}
		sentencia.close();
		conexion1.close();
	}// hsqldb

	public static void derby() throws ClassNotFoundException, SQLException {

		Connection conexion1 = DriverManager.getConnection("jdbc:derby:./Basesdatos/derby/ejemplo");

		String sql = "SELECT * FROM departamentos";
		Statement sentencia = conexion1.createStatement();
		boolean valor = sentencia.execute(sql);

		if (valor) {
			ResultSet rs = sentencia.getResultSet();
			while (rs.next())
				System.out.printf("%d, %s, %s %n", rs.getInt(1), rs.getString(2), rs.getString(3));
			rs.close();
		} else {
			int f = sentencia.getUpdateCount();
			System.out.printf("Filas afectadas:%d %n", f);
		}
		sentencia.close();
		conexion1.close();
	}// derby

	public static void h2() throws ClassNotFoundException, SQLException {

		Connection conexion1 = DriverManager.getConnection("jdbc:h2:./Basesdatos/h2/ejemplo", "sa", "");

		String sql = "SELECT * FROM departamentos";
		Statement sentencia = conexion1.createStatement();
		boolean valor = sentencia.execute(sql);

		if (valor) {
			ResultSet rs = sentencia.getResultSet();
			while (rs.next())
				System.out.printf("%d, %s, %s %n", rs.getInt(1), rs.getString(2), rs.getString(3));
			rs.close();
		} else {
			int f = sentencia.getUpdateCount();
			System.out.printf("Filas afectadas:%d %n", f);
		}
		sentencia.close();
		conexion1.close();
	}// h2

	public static void mysql() throws ClassNotFoundException, SQLException {

		Connection conexion1 = DriverManager.getConnection("jdbc:mysql://localhost:3307/ejemplo", "root", "");

		String sql = "SELECT * FROM departamentos";
		Statement sentencia = conexion1.createStatement();
		boolean valor = sentencia.execute(sql);

		if (valor) {
			ResultSet rs = sentencia.getResultSet();
			while (rs.next())
				System.out.printf("%d, %s, %s %n", rs.getInt(1), rs.getString(2), rs.getString(3));
			rs.close();
		} else {
			int f = sentencia.getUpdateCount();
			System.out.printf("Filas afectadas:%d %n", f);
		}
		sentencia.close();
		conexion1.close();
	}// mysql

	public static void sqlite() throws ClassNotFoundException, SQLException {

		// Connection conexion1 =
		// DriverManager.getConnection("jdbc:sqlite:./ejemplo2.db");
		Connection conexion1 = DriverManager.getConnection("jdbc:sqlite:./Basesdatos/sqlite/ejemplo.db");

		String sql = "SELECT * FROM departamentos";
		Statement sentencia = conexion1.createStatement();
		boolean valor = sentencia.execute(sql);

		if (valor) {
			ResultSet rs = sentencia.getResultSet();
			while (rs.next())
				System.out.printf("%d, %s, %s %n", rs.getInt(1), rs.getString(2), rs.getString(3));
			rs.close();
		} else {
			int f = sentencia.getUpdateCount();
			System.out.printf("Filas afectadas:%d %n", f);
		}
		sentencia.close();
		conexion1.close();
	}// sqlite

}
