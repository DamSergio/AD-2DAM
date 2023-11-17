package examen;

import java.util.Scanner;
import java.sql.*;

public class Main {
	static Conexiones con = new Conexiones();
	static Connection conn = con.getOracle("CENTROS", "centros");

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int op = 0;
		
		do {
			menu();
			op = sc.nextInt();
			
			switch (op) {
			case 1:
				ej1(1045);
				break;
			case 2:
				break;
			case 3:
				break;
			case 0:
				break;
			}
		} while (op != 0);
	}
	
	public static void ej1(int codCen) {
		try {
			PreparedStatement dc = conn.prepareStatement("SELECT cod_centro, nom_centro, COUNT(cod_prof) "
					+ "FROM c1_centros LEFT JOIN c1_profesores USING(cod_centro) "
					+ "WHERE cod_centro = ? "
					+ "GROUP BY cod_centro, nom_centro");
			dc.setInt(1, codCen);
			ResultSet datosCentro = dc.executeQuery();
			datosCentro.next();
			
			PreparedStatement nd = conn.prepareStatement("SELECT nombre_ape "
					+ "FROM c1_profesores "
					+ "WHERE cod_prof = (SELECT director FROM c1_centros WHERE cod_centro = ?)");
			nd.setInt(1, codCen);
			ResultSet nomDir = nd.executeQuery();
			nomDir.next();
			String nombre = "";
			try {
				nombre = nomDir.getString(1);
			} catch (Exception e) {
				nombre = "SIN DIRECTOR";
			}
			
			System.out.println("COD_CENTRO: " + datosCentro.getInt(1) + "\tNOMBRE CENTRO: " + datosCentro.getString(2));
			System.out.println("NOMBRE-DIRECTOR: " + nombre + "\tNUM-PROFESORES: " + datosCentro.getInt(3));
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void menu(){
		System.out.println("1. Ejercicio 1");
		System.out.println("2. Ejercicio 2");
		System.out.println("3. Ejercicio 3");
		System.out.println("0. Salir");
	}

}
