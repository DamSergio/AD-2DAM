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
				ej1(1000);
				break;
			case 2:
				ej2(true);
				break;
			case 3:
				ej3(2002, "IF0001");
				break;
			case 0:
				System.out.println("BYE BYE 🖐🖐🖐");
				break;
			}
		} while (op != 0);
	}
	
	private static void ej3(int codProf, String codAsig) {
		boolean err = false;
		try {
			PreparedStatement p = conn.prepareStatement("SELECT COUNT(*) FROM c1_profesores WHERE cod_prof = ?");
			p.setInt(1, codProf);
			ResultSet profesor = p.executeQuery();
			profesor.next();
			if (profesor.getInt(1) == 0) {
				System.out.println("NO EXISTE EL PROFESOR: " + codProf);
				err = true;
			}
			profesor.close();
			
			PreparedStatement a = conn.prepareStatement("SELECT COUNT(*) FROM c1_asignaturas WHERE cod_asig = ?");
			a.setString(1, codAsig);
			ResultSet asig = a.executeQuery();
			asig.next();
			if (asig.getInt(1) == 0) {
				System.out.println("NO EXISTE LA ASIGNATURA: " + codAsig);
				err = true;
			}
			asig.close();
			
			PreparedStatement pa = conn.prepareStatement("SELECT COUNT(*) FROM c1_asigprof WHERE C1_ASIGNATURAS_COD_ASIG = ? AND C1_PROFESORES_COD_PROF = ?");
			pa.setString(1, codAsig);
			pa.setInt(2, codProf);
			ResultSet profAsig = pa.executeQuery();
			profAsig.next();
			if (profAsig.getInt(1) > 0) {
				System.out.println("EL PROFESOR: " + codProf +" YA IMPARTE LA ASIGNATURA: " + codAsig);
				err = true;
			}
			profAsig.close();
			
			if (!err) {
				PreparedStatement i = conn.prepareStatement("INSERT INTO c1_asigprof (C1_ASIGNATURAS_COD_ASIG, C1_PROFESORES_COD_PROF) VALUES(?, ?)");
				i.setString(1, codAsig);
				i.setInt(2, codProf);
				i.executeUpdate();
				
				System.out.println("REGISTRO INSERTADO CORRECTAMENTE");
				ej2(false);
			} else {
				System.out.println("REGISTRO NO INSERTADO");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void ej2(boolean mostrar) {
		try {
			PreparedStatement atAsig = conn.prepareStatement("ALTER TABLE c1_asignaturas ADD num_prof INT DEFAULT 0");
			atAsig.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if (mostrar)
				System.out.println("LA COLUMNA num_prof YA EXISTE");
		}
		
		try {
			PreparedStatement atProf = conn.prepareStatement("ALTER TABLE c1_profesores ADD num_asig INT DEFAULT 0");
			atProf.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if (mostrar)
				System.out.println("LA COLUMNA num_asig YA EXISTE");
		}
		
		try {
			PreparedStatement upAsig = conn.prepareStatement("UPDATE c1_asignaturas a SET num_prof = (SELECT COUNT(*) FROM c1_asigprof WHERE C1_ASIGNATURAS_COD_ASIG = a.cod_asig)");
			upAsig.executeUpdate();
			PreparedStatement upProf = conn.prepareStatement("UPDATE c1_profesores p SET num_asig = (SELECT COUNT(*) FROM c1_asigprof WHERE C1_PROFESORES_COD_PROF = p.cod_prof)");
			upProf.executeUpdate();
			
			if (mostrar) {
				PreparedStatement ps = conn.prepareStatement("SELECT cod_asig, nombre_asi, cod_prof, nombre_ape FROM c1_profesores JOIN c1_asigprof ON cod_prof = c1_PROFESORES_COD_PROF JOIN c1_asignaturas ON cod_asig = C1_ASIGNATURAS_COD_ASIG ");
				ResultSet profAsig = ps.executeQuery();
				
				System.out.printf("%8s %-30s %8s %-30s%n", "COD ASIG", "NOMBRE ASIG", "COD PROF", "NOMBRE PROF");
				System.out.printf("%8s %-30s %8s %-30s%n", "--------", "------------------------------", "--------", "------------------------------");
				while (profAsig.next()) {
					System.out.printf("%8s %-30s %8s %-30s%n", profAsig.getString(1), profAsig.getString(2), profAsig.getInt(3), profAsig.getString(4));
				}
				System.out.printf("%8s %-30s %8s %-30s%n", "--------", "------------------------------", "--------", "------------------------------");
				profAsig.close();
			}
			
			System.out.println("COLUMNAS ACTUALIZADAS CORRECTAMENTE");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("COLUMNAS NO ACTUALIZADAS");
		}
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
			
			if (datosCentro.getInt(3) > 0) {
				String nomMax = "";
				int asigMax = 0;
				
				System.out.println("-------------------------------------------------------------------------------------------------------");
				System.out.println("PROFESORES DEL CENTRO");
				System.out.printf("%8s %-30s %12s %-30s %8s%n", "COD-PROF", "NOMBRE", "ESPECIALIDAD", "NOMBRE JEFE", "NUM-ASIG");
				System.out.printf("%8s %-30s %12s %-30s %8s%n", "--------", "------------------------------", "------------", "------------------------------", "--------");
				
				PreparedStatement dp = conn.prepareStatement("SELECT cod_prof, nombre_ape, especialidad, COUNT(c1_ASIGNATURAS_COD_ASIG), cod_prof1 FROM c1_profesores JOIN c1_asigprof ON cod_prof = c1_PROFESORES_COD_PROF WHERE cod_centro = ? GROUP BY cod_prof, nombre_ape, especialidad, cod_prof1");
				dp.setInt(1, codCen);
				ResultSet datosProf = dp.executeQuery();
				while (datosProf.next()) {
					PreparedStatement nj = conn.prepareStatement("SELECT nombre_ape FROM c1_profesores WHERE cod_prof = ?");
					nj.setInt(1, datosProf.getInt(5));
					ResultSet nomJefe = nj.executeQuery();
					nomJefe.next();
					String noJ = "";
					try {
						noJ = nomJefe.getString(1);
					} catch (Exception e) {
						noJ = "NO TIENE JEFE";
					}
					
					System.out.printf("%8s %-30s %12s %-30s %8s%n", datosProf.getInt(1), datosProf.getString(2), datosProf.getString(3), noJ, datosProf.getInt(4));
				
					if (datosProf.getInt(4) > asigMax) {
						asigMax = datosProf.getInt(4);
						nomMax = datosProf.getString(2);
					}
				}
				System.out.printf("%8s %-30s %12s %-30s %8s%n", "--------", "------------------------------", "------------", "------------------------------", "--------");
				System.out.println("NOMBRE DEL PROFESOR QUE IMPARTE MAS ASIFNATURAS: " + nomMax);
				
			}
			
			datosCentro.close();
			nomDir.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("NO EXISTE ESE CENTRO");
		}
	}
	
	public static void menu(){
		System.out.println("1. Ejercicio 1");
		System.out.println("2. Ejercicio 2");
		System.out.println("3. Ejercicio 3");
		System.out.println("0. Salir");
	}

}
