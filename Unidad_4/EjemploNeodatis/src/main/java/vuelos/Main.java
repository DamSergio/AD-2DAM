package vuelos;

import java.sql.*;
import java.util.HashSet;

import org.neodatis.odb.*;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.core.query.IQuery;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ODBRuntimeException;
import org.neodatis.odb.Objects;

import org.neodatis.odb.OID;
import org.neodatis.odb.core.oid.OIDFactory;

import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Or;
import org.neodatis.odb.core.query.criteria.Not;

import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;

public class Main {
	private static ODB bd;
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/vuelos", "root", "1234");
	
			bd = ODBFactory.open("vuelos.neo");
			
			insertarPasajeros(conexion);
			insertarTripulaciones(conexion);
			insertarVuelos(conexion);
			insertarPasajerosVuelo(conexion);
			
			conexion.close();
			bd.close();
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void insertarPasajerosVuelo(Connection conexion) throws SQLException {
		Objects<Pasajeros> pasajeros = bd.getObjects(Pasajeros.class);
		while (pasajeros.hasNext()) {
			Pasajeros p = pasajeros.next();
			PreparedStatement ps = conexion.prepareStatement("SELECT * FROM pasajeros");
		}
	}

	private static void insertarVuelos(Connection conexion) {
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM vuelo");
			while (rs.next()) {
				if (comprobarVuelo(rs.getString(1))) {
					System.out.println("Vuelo: " + rs.getString(2) + ", EXISTE");
					continue;
				}
				
				Vuelo v = new Vuelo();
				v.setIdentificador(rs.getString(1));
				v.setAeropuertoorigen(rs.getString(2));
				v.setAeropuertodestino(rs.getString(3));
				
				bd.store(v);
				System.out.println("Vuelo grabado: " + v.getIdentificador());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void insertarTripulaciones(Connection conexion) {
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM personal");
			while (rs.next()) {
				if (comprobarPersonal(rs.getShort(1))) {
					System.out.println("Tripulacion: " + rs.getString(2) + ", EXISTE");
					continue;
				}
				
				Tripulacion t = new Tripulacion();
				t.setCodigo(rs.getShort(1));
				t.setNombre(rs.getString(2));
				t.setCategoria(rs.getString(3));
				
				bd.store(t);
				System.out.println("Tripulacion grabada: " + t.getCodigo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void insertarPasajeros(Connection conexion) {
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM pasajero");
			while (rs.next()) {
				if (comprobarPasajero(rs.getShort(1))) {
					System.out.println("Pasajero: " + rs.getString(2) + ", EXISTE");
					continue;
				}
				
				Pasajeros p = new Pasajeros();
				p.setCodigo(rs.getShort(1));
				p.setNombre(rs.getString(2));
				p.setTlf(rs.getString(3));
				p.setDireccion(rs.getString(4));
				
				bd.store(p);
				System.out.println("Pasajero grabado: " + p.getCodigo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean comprobarPasajero(short cod) {
		try {
			IQuery consulta = new CriteriaQuery(Pasajeros.class, Where.equal("codigo", cod));
			Pasajeros obj = (Pasajeros) bd.getObjects(consulta).getFirst();
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	private static boolean comprobarPersonal(short cod) {
		try {
			IQuery consulta = new CriteriaQuery(Tripulacion.class, Where.equal("codigo", cod));
			Tripulacion obj = (Tripulacion) bd.getObjects(consulta).getFirst();
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	private static boolean comprobarVuelo(String id) {
		try {
			IQuery consulta = new CriteriaQuery(Vuelo.class, Where.equal("identificador", id));
			Vuelo obj = (Vuelo) bd.getObjects(consulta).getFirst();
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
}
