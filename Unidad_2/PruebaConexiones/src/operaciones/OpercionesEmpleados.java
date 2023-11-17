package operaciones;

import java.sql.*;
import pruebaConexiones.Conexiones;

public class OpercionesEmpleados {
	private static Connection connection;
	private static Conexiones conn = new Conexiones();
	
	public OpercionesEmpleados() {
		connection = conn.getOracle("ej", "1234");
	}
	
	public String insertarEmple(int cod, String ape, String ofi, float sal, int dir, int codDep, float comision, String fecha) {
		String msg = "";
		
		if (sal <= 0) {
			msg += "\nSalario no valido";
		}
		
		String sqlDep = "SELECT COUNT(*) FROM departamentos WHERE dept_no = ?";
		PreparedStatement sentenciaDep;
		try {
			sentenciaDep = connection.prepareStatement(sqlDep);
			sentenciaDep.setInt(1, codDep);
			ResultSet rsDep = sentenciaDep.executeQuery();
			rsDep.next();
			int existeDep = rsDep.getInt(1);
			if (existeDep <= 0) {
				msg += "\nDepartamento no existe " + codDep;
			}
			rsDep.close();
			sentenciaDep.close();
			
			PreparedStatement sentenciaDir = connection.prepareStatement(
					"SELECT COUNT(*) FROM empleados WHERE emp_no = ?"
			);
			sentenciaDir.setInt(1, dir);
			ResultSet rsDir = sentenciaDir.executeQuery();
			rsDir.next();
			if (rsDir.getInt(1) <= 0) {
				msg += "\nDirector no existe " + dir;
			}
			rsDir.close();
			sentenciaDir.close();
			
			PreparedStatement sentenciaEmp = connection.prepareStatement(
					"SELECT COUNT(*) FROM empleados WHERE emp_no = ?"
			);
			sentenciaEmp.setInt(1, cod);
			ResultSet rsEmp = sentenciaEmp.executeQuery();
			rsEmp.next();
			if (rsEmp.getInt(1) != 0) {
				msg += "\nEmpleado ya existe " + cod;
			}
			rsEmp.close();
			sentenciaEmp.close();
			
			if (msg.equals("")) {
				//String sql= "INSERT INTO empleados (emp_no, apellido, oficio, dir, fecha_alt, salario, comision, dept_no) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement sentenciaSql = connection.prepareStatement(
						"INSERT INTO empleados (emp_no, apellido, oficio, dir, fecha_alt, salario, comision, dept_no) VALUES(?, ?, ?, ?, ?, ?, ?, ?)"
				);
				sentenciaSql.setInt(1, cod);
				sentenciaSql.setString(2, ape);
				sentenciaSql.setString(3, ofi);
				sentenciaSql.setInt(4, dir);
				sentenciaSql.setString(5, fecha);
				sentenciaSql.setFloat(6, sal);
				sentenciaSql.setFloat(7, comision);
				sentenciaSql.setInt(8, codDep);
				
				sentenciaSql.executeUpdate();
				msg = "\nEmpleado insertado correctamente: " + cod;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return msg;
	}

	public String actualizarEmple(int cod, String ape, String ofi, float sal, int dir, int codDep, float comision, String fecha) {
		String msg = "";
		
		if (sal <= 0) {
			msg += "\nSalario no valido";
		}
		
		String sqlDep = "SELECT COUNT(*) FROM departamentos WHERE dept_no = ?";
		
		PreparedStatement sentenciaDep;
		try {
			sentenciaDep = sentenciaDep = connection.prepareStatement(sqlDep);
			sentenciaDep.setInt(1, codDep);
			ResultSet rsDep = sentenciaDep.executeQuery();
			rsDep.next();
			int existeDep = rsDep.getInt(1);
			if (existeDep <= 0) {
				msg += "\nDepartamento no existe " + codDep;
			}
			
			rsDep.close();
			sentenciaDep.close();
			
			PreparedStatement sentenciaDir = connection.prepareStatement(
					"SELECT COUNT(*) FROM empleados WHERE emp_no = ?"
			);
			sentenciaDir.setInt(1, dir);
			ResultSet rsDir = sentenciaDir.executeQuery();
			rsDir.next();
			if (rsDir.getInt(1) <= 0) {
				msg += "\nDirector no existe " + dir;
			}
			rsDir.close();
			sentenciaDir.close();
			
			PreparedStatement sentenciaEmp = connection.prepareStatement(
					"SELECT COUNT(*) FROM empleados WHERE emp_no = ?"
			);
			sentenciaEmp.setInt(1, cod);
			ResultSet rsEmp = sentenciaEmp.executeQuery();
			rsEmp.next();
			if (rsEmp.getInt(1) == 0) {
				msg += "\nEmpleado no existe " + cod;
			}
			rsEmp.close();
			sentenciaEmp.close();
			
			if (msg.equals("")) {
				//String sql= "INSERT INTO empleados (emp_no, apellido, oficio, dir, fecha_alt, salario, comision, dept_no) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement sentenciaSql = connection.prepareStatement(
						"UPDATE empleados SET apellido = ?, oficio = ?, dir = ?, fecha_alt = ?, salario = ?, comision = ?, dept_no = ? WHERE emp_no = ?"
				);
				
				sentenciaSql.setString(1, ape);
				sentenciaSql.setString(2, ofi);
				sentenciaSql.setInt(3, dir);
				sentenciaSql.setString(4, fecha);
				sentenciaSql.setFloat(5, sal);
				sentenciaSql.setFloat(6, comision);
				sentenciaSql.setInt(7, codDep);
				sentenciaSql.setInt(8, cod);
				
				sentenciaSql.executeUpdate();
				msg = "\nEmpleado actualizado correctamente: " + cod;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return msg;
	}
}
