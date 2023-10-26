package operaciones;

import java.sql.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import pruebaConexiones.Conexiones;

public class OperacionesDepartamentos {
	private static Connection connection;
	private static Conexiones conn = new Conexiones();
	
	public OperacionesDepartamentos() {
		connection = conn.getOracle("ej", "1234");
	}
	
	public String borrarColumnas() {
		String msg = "";
		
		String col1 = "ALTER TABLE departamentos DROP COLUMN numples";
		String col2 = "ALTER TABLE departamentos DROP COLUMN media";
		
		PreparedStatement sentencia;
		try {
			sentencia = connection.prepareStatement(col1);
			int filas = sentencia.executeUpdate();
			sentencia = connection.prepareStatement(col2);
			filas = sentencia.executeUpdate();
			msg = "COLUMNAS BORRDAS.";
			sentencia.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		}
		
		
		return msg;
	}
	
	public String actualizarColumnas() {
		String msg = "";
		
		String col1 = "ALTER TABLE departamentos ADD numples INT DEFAULT 0";
		String col2 = "ALTER TABLE departamentos ADD media FLOAT DEFAULT 0";
		
		PreparedStatement sentencia;
		try {
			sentencia = connection.prepareStatement(col1);
			int filas = sentencia.executeUpdate();
			sentencia = connection.prepareStatement(col2);
			filas = sentencia.executeUpdate();
			msg += "COLUMNAS CREADAS.";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		}
		
		try {
			String update = "UPDATE departamentos d set numples = (SELECT COUNT(*) FROM empleados WHERE dept_no = d.dept_no), media = (SELECT coalesce(AVG(salario), 0) FROM empleados WHERE dept_no = d.dept_no)";
			sentencia = connection.prepareStatement(update);
			int filas = sentencia.executeUpdate();
			msg += "COLUMNAS ACTUALIZADAS";
			sentencia.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return msg;
	}
	
	public static String listarEmple(int cod, JTextArea t) {
		String msg = "";
		String sql= "SELECT * FROM empleados WHERE dept_no = ?";
		t.setText("");
		try {
			PreparedStatement sentencia =  connection.prepareStatement(sql);
			sentencia.setInt(1, cod);
			
			ResultSet rs = sentencia.executeQuery();
			String cadena = "";
			t.append("EMPLEADOS DEL DEPARTAMENTO: ");
			String patron = "%n%8s %-10s %10s %10s %10s %20s";
			
			int sw = 0;
			while(rs.next()) {
				if (sw == 0) {
					cadena = String.format(patron, "CODEMPLE", "APELLIDO", "OFICIO", "SALARIO", "DIRECTOR", "NOMBRE DIRECTOR");
					t.append(cadena);
					cadena = String.format(patron, "--------", "----------", "----------", "----------", "----------", "--------------------");
					t.append(cadena);
					sw = 1;
				}
				
				//buscamos al director de empleados rs.getInt(4)
				String consultaDir = "SELECT apellido FROM empleados WHERE emp_no = ?";
				PreparedStatement sentencia2 = connection.prepareStatement(consultaDir);
				sentencia2.setInt(1, rs.getInt(4));
				ResultSet rsDir = sentencia2.executeQuery();
				String apellido = "NO TIENE";
				if (rsDir.next()) {
					apellido = rsDir.getString(1);
				}
				rsDir.close();
				sentencia2.close();
				
				cadena = String.format(patron, rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(6), rs.getInt(4), apellido);
				t.append(cadena);
			}
			
			if (sw == 0) {
				t.append("\nSIN EMPLEADOS");
			}
			
			cadena = String.format(patron, "--------", "----------", "----------", "----------", "----------", "--------------------");
			t.append(cadena);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		}
		
		return msg;
	}
	
	public static String updateDep(int cod, String name, String loc) {
		String msg = "";
		String sql= "UPDATE departamentos SET dnombre = ?, loc = ? WHERE dept_no = ?";
		try {
			PreparedStatement sentencia =  connection.prepareStatement(sql);
			
			sentencia.setString(1, name);
			sentencia.setString(2, loc);
			sentencia.setInt(3, cod);
			
			int filas = sentencia.executeUpdate();
			if (filas > 0) {
				msg = "REGISTRO ACTUALIZADO CORRECTAMENTE: " + cod;
			} else {
				msg = "FILAS ACTUALIZADAS 0: " + cod;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		}
		
		return msg;
	}
	
	public static String DeleteDep(int cod) {
		String msg = "";
		String sql= "DELETE FROM departamentos WHERE dept_no = ?";
		try {
			PreparedStatement sentencia =  connection.prepareStatement(sql);
			
			sentencia.setInt(1, cod);
			
			int filas = sentencia.executeUpdate();
			if (filas > 0) {
				msg = "REGISTRO BORRADO CORRECTAMENTE: " + cod;
			} else {
				msg = "FILAS BORRADAS 0: " + cod;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		}
		
		return msg;
	}
	
	public static String consultDep(int cod, JTextField nombre, JTextField loc) {
		String msg = "";
		String sql= "SELECT * FROM departamentos WHERE dept_no = ?";
		try {
			PreparedStatement sentencia =  connection.prepareStatement(sql);
			sentencia.setInt(1, cod);
			ResultSet st = sentencia.executeQuery();
			
			if (st.next()) {
				msg = "codigo: " + st.getInt(1) + "\nnombre: " + st.getString(2) + "\nlocalidad: " + st.getString(3);
				
				nombre.setText(st.getString(2));
				loc.setText(st.getString(3));
			} else {
				msg = "DEPARTAMENTO NO EXISTE " + cod;
				
				nombre.setText("");
				loc.setText("");
			}
			
			sentencia.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		}
		
		return msg;
	}
	
	public static String insertDep(int cod, String name, String loc) {
		String msg = "";
		String sql= "INSERT INTO departamentos (dept_no, dnombre, loc) VALUES(?, ?, ?)";
		try {
			PreparedStatement sentencia =  connection.prepareStatement(sql);
			
			sentencia.setInt(1, cod);
			sentencia.setString(2, name);
			sentencia.setString(3, loc);
			
			int filas = sentencia.executeUpdate();
			if (filas > 0) {
				msg = "REGISTRO INSERTADO CORRECTAMENTE: " + cod;
			} else {
				msg = "FILAS INSERTADAS 0: " + cod;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		}
		
		return msg;
	}
}
