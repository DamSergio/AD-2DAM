package departamentos;

import java.util.ArrayList;
import java.util.Date;

import factoriaDAO.DAOFactory;
import factoriaDAO.Departamento;
import factoriaDAO.DepartamentoDAO;
import factoriaDAO.Empleado;
import factoriaDAO.EmpleadoDAO;

public class Main {
	public static void main(String[] args) {
		probarNeodatis();
		probarOracle();		
		probarMySql();
		
		probarEmple();
	}

	private static void probarEmple() {
		System.out.println("------------------------------");
        System.out.println("PRUEBA ORACLE");
        
        DAOFactory db = DAOFactory.getDAOFactory(DAOFactory.ORACLE);
        EmpleadoDAO empDAO = db.getEmpleadoDAO();
        
        ArrayList<Empleado> lista = empDAO.Obtenerempleados();
        int i = 1;
		for (Empleado d : lista) {
			System.out.println(i + " ->  " + d);
			i++;
		}
		
		System.out.println("------------------- INSERTAR ---------------------");
		
		java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
		Empleado e =  new Empleado(2, "ape1", "ofi1", 7654, sqlDate, 1000f, 200f, 30);
		
		if (empDAO.InsertarEmp(e)) {
			System.out.println("INSERTADO CORRECTAMENTE");
		} else {
			System.out.println("ERROR AL INSERTAR");
		}
	}

	private static void probarMySql() {
		System.out.println("------------------------------");
        System.out.println("PRUEBA MYSQL");
        
		DAOFactory db = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		DepartamentoDAO depDAO = db.getDepartamentoDAO();
		
		ArrayList<Departamento> lista = depDAO.Obtenerdepartamentos();
		int i = 1;
		for (Departamento d : lista) {
			System.out.println(i + " ->  " + d);
			i++;
		}
	}

	private static void probarOracle() {
		System.out.println("------------------------------");
        System.out.println("PRUEBA ORACLE");
        
		DAOFactory db = DAOFactory.getDAOFactory(DAOFactory.ORACLE);
		DepartamentoDAO depDAO = db.getDepartamentoDAO();
		
		ArrayList<Departamento> lista = depDAO.Obtenerdepartamentos();
		int i = 1;
		for (Departamento d : lista) {
			System.out.println(i + " ->  " + d);
			i++;
		}
	}

	private static void probarNeodatis() {
		System.out.println("------------------------------");
        System.out.println("PRUEBA NEODATIS");
        
		DAOFactory db = DAOFactory.getDAOFactory(DAOFactory.NEODATIS);
		DepartamentoDAO depDAO = db.getDepartamentoDAO();
		
		ArrayList<Departamento> lista = depDAO.Obtenerdepartamentos();
		int i = 1;
		for (Departamento d : lista) {
			System.out.println(i + " ->  " + d);
			i++;
		}
	}
}
