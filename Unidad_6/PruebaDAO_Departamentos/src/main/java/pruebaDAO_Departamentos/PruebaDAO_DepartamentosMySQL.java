package pruebaDAO_Departamentos;

import java.util.ArrayList;

import depMySQL.Departamento;
import depMySQL.DepartamentoDAO;
import depMySQL.DepartamentoImpl;

public class PruebaDAO_DepartamentosMySQL {

	public static void main(String[] args) {
		DepartamentoDAO depDAO = new DepartamentoImpl();
		
		// INSERTAR
	    Departamento dep1 = new Departamento(17, "NÓMINAS", "SEVILLA");
	    depDAO.insertarDep(dep1);
	       
	    // CONSULTAR
	    Departamento dep2 = depDAO.consultarDep(17);
	    System.out.printf("Dep: %d, Nombre: %s, Loc: %s %n",dep2.getDeptNo(),dep2.getdNombre(), dep2.getLoc());
	        
	    // MODIFICAR 
	    dep2.setdNombre("nuevonom");
	    dep2.setLoc("nuevaloc");
	    depDAO.modificarDep(17, dep2);
	                    
	    //ELIMINAR
	    depDAO.eliminarDep(17);
	    
	    // LISTA
	    ArrayList<Departamento> lista = depDAO.listaDeptartamentos();
	    for (Departamento d : lista) {
	    	System.out.println(d);
	    }
	}

}
