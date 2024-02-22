package depMySQL;

import java.sql.*;
import java.util.ArrayList;

public class DepartamentoImpl implements DepartamentoDAO {
	public static Connection conn;
	
	public DepartamentoImpl() {
		this.conn = this.getMysql("tema6_ad", "root", "1234");
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

	@Override
	public boolean insertarDep(Departamento dep) {
		try {
			PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO DEPARTAMENTOS (DEPT_NO, DNOMBRE, LOC) VALUES(?, ?, ?)");
			insertStatement.setInt(1, dep.getDeptNo());
			insertStatement.setString(2, dep.getdNombre());
			insertStatement.setString(3, dep.getLoc());
			
			insertStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean eliminarDep(int deptNo) {
		try {
			PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM DEPARTAMENTOS WHERE DEPT_NO = ?");
			deleteStatement.setInt(1, deptNo);
			
			int res = deleteStatement.executeUpdate();
			if (res == 0) return false;
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean modificarDep(int deptNo, Departamento dep) {
		try {
			PreparedStatement updateStatement = conn.prepareStatement("UPDATE DEPARTAMENTOS SET DNOMBRE = ?, LOC = ? WHERE DEPT_NO = ?");
			updateStatement.setString(1, dep.getdNombre());
			updateStatement.setString(2, dep.getLoc());
			updateStatement.setInt(3, deptNo);
			
			int res = updateStatement.executeUpdate();
			if (res == 0) return false;
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Departamento consultarDep(int deptNo) {
		try {
			PreparedStatement selectStatement = conn.prepareStatement("SELECT * FROM DEPARTAMENTOS WHERE DEPT_NO = ?");
			selectStatement.setInt(1, deptNo);
			
			ResultSet rsDep = selectStatement.executeQuery();
			if (rsDep.next()) {
				return new Departamento(rsDep.getInt(1), rsDep.getString(2), rsDep.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	@Override
	public ArrayList<Departamento> listaDeptartamentos() {
		ArrayList<Departamento> list = new ArrayList<>();
		try {
			PreparedStatement selectStatement = conn.prepareStatement("SELECT * FROM DEPARTAMENTOS");
			
			ResultSet rsDep = selectStatement.executeQuery();
			while (rsDep.next()) {
				list.add(new Departamento(rsDep.getInt(1), rsDep.getString(2), rsDep.getString(3)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		
		return list;
	}
}
