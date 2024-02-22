package depOracle;

import java.util.ArrayList;

public interface DepartamentoDAO {
	public boolean insertarDep(Departamento dep);
    public boolean eliminarDep(int deptNo); 
    public boolean modificarDep(int deptNo, Departamento dep);
    public Departamento consultarDep(int deptNo);
    public ArrayList<Departamento> listaDeptartamentos();
}
