package factoriaDAO;

public abstract class DAOFactory {
	  // Bases de datos soportadas
	  public static final int MYSQL = 1;  
	  public static final int NEODATIS = 2;
	  public static final int ORACLE = 3;
	 
	  // Este método se reescribe en cada BD, SqlDbDAOFactory, NeodatisDAOFactory 
	  public abstract DepartamentoDAO getDepartamentoDAO();
	  public abstract EmpleadoDAO getEmpleadoDAO(); 
	  
	  public static DAOFactory getDAOFactory(int bd) {  
	    switch (bd) {
	      case MYSQL   :          
	           return  new SqlDbDAOFactory();     
	      case NEODATIS:       
	           return  new NeodatisDAOFactory();
	      case ORACLE:       
	           return  new OracleDAOFactory();
	      default      : 
	          return null;
	    }
	  }
	}
