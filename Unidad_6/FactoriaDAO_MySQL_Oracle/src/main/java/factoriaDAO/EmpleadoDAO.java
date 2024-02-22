package factoriaDAO;

import java.util.ArrayList;

public interface EmpleadoDAO {
	public boolean InsertarEmp(Empleado emple);
	public boolean EliminarEmp(int id); 
	public boolean ModificarEmp(int id, Empleado emple);
	public Empleado ConsultarEmp(int id);    
	public ArrayList<Empleado> Obtenerempleados();
	public ArrayList<Empleado> ObtenerEmpleadosDep(int deptno);
}
