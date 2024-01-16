package datos;

import java.math.BigDecimal;
import java.util.Date;

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

import ejemploPaises.Jugadores;

import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Or;
import org.neodatis.odb.core.query.criteria.Not;

import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;

public class Consultas {
	private static String articulos = "ARTICULOS.DAT";
	
	public static void main(String[] args) {
//		consultarVentas("Talavera", 100);
//		clientesCiudades("Madrid", "Oropesa");
//		añadirVenta(15, 23, 5, new Date().toString());
//		añadirVenta(159, 239, 0, new Date().toString());
//		pruebaValues();
		listadoResumenArtic();
	}

	private static void listadoResumenArtic() {
		ODB odb = ODBFactory.open(articulos);
		
		Values groupBy = odb.getValues(new ValuesCriteriaQuery(Ventas.class)
				.field("codarti.codarti")
				.count("codventa")
				.sum("univen")
				.groupBy("codarti.codarti")
		);
		
		System.out.printf("%7s %-20s %11s %10s %-7s %7s%n", "COD_ART", "NOMBRE", "SUMAUNI_VEN", "NUM_VENTAS", "PVP", "IMPORTE");
		System.out.printf("%7s %-20s %11s %10s %-7s %7s%n", "-------", "--------------------", "-----------", "----------", "-------", "-------");
		for (ObjectValues v : groupBy) {
			int codArti = (int) v.getByAlias("codarti.codarti");
			IQuery query = new CriteriaQuery(Articulos.class, Where.equal("codarti", codArti));
			Articulos art = (Articulos) odb.getObjects(query).getFirst();
			
			BigDecimal sum = (BigDecimal) v.getByAlias("univen");
			double importe = art.getPvp() * sum.doubleValue();
			
			System.out.printf("%7s %-20s %11s %10s %7s %7.2f%n", art.getCodarti(), art.getDenom(), v.getByAlias("univen"), v.getByAlias("codventa"), art.getPvp(), importe);
		}
		
		odb.close();
	}

	private static void pruebaValues() {
		ODB odb = ODBFactory.open(articulos);
		
		Values valores = odb.getValues(new ValuesCriteriaQuery(Clientes.class)
				.field("nombre","nom")
				.field("pobla")
		);
		while (valores.hasNext()) {
			ObjectValues objectValues=(ObjectValues)valores.next();
			System.out.println(objectValues.getByAlias("nom") + "-" + objectValues.getByAlias("pobla"));
		}
		
		odb.close();
	}

	private static void añadirVenta(int codCli, int codArt, int unidades, String fecha) {
		ODB odb = ODBFactory.open(articulos);
		boolean err = false;
		
		// COMPRUEBO QUE EXISTA EL CLIENTE
		IQuery queryCli = new CriteriaQuery(Clientes.class, Where.equal("numcli", codCli));
		Clientes cli = null;
		try {
			cli = (Clientes) odb.getObjects(queryCli).getFirst();
		} catch (IndexOutOfBoundsException e) {
			err = true;
			System.out.println("El cliente no existe");
		}
		
		// COMPRUEBO QUE EXISTA EL ARTICULO
		IQuery queryArt = new CriteriaQuery(Articulos.class, Where.equal("codarti", codArt));
		Articulos art = null;
		try {
			art = (Articulos) odb.getObjects(queryArt).getFirst();
		} catch (IndexOutOfBoundsException e) {
			err = true;
			System.out.println("El articulo no existe");
		}
		
		// COMPRUEBO QUE LAS UNIDADES SEAN MAYOR A 0
		if (unidades <= 0) {
			err = true;
			System.out.println("Las unidades deben de ser mayor a 0");
		}
		
		// INTERRUMPO SI HAY ALGUN ERROR
		if (err) {
			return;
		}
		
		// CALCULO EL COD MAX
		Values codVentaMax = odb.getValues(new ValuesCriteriaQuery(Ventas.class)
				.max("codventa")
		);
		ObjectValues codVenta = codVentaMax.nextValues();
		BigDecimal cod = (BigDecimal) codVenta.getByAlias("codventa");
		
		// CREO LA VENTA
		Ventas v = new Ventas();
		v.setCodventa(cod.intValue() + 1);
		v.setNumcli(cli);
		v.setCodarti(art);
		v.setUniven(unidades);
		v.setFecha(fecha);
		
		// INSERTO LA VENTA
		odb.store(v);
		
		System.out.println("Venta insertada");
		
		odb.close();
	}

	private static void clientesCiudades(String pobla1, String pobla2) {
		ODB odb = ODBFactory.open(articulos);
		
		ICriterion where = new Or().add(Where.equal("numcli.pobla", pobla1))
				.add(Where.equal("numcli.pobla", pobla2)
		);
		IQuery query = new CriteriaQuery(Ventas.class, where);
		
		System.out.printf("%-8s %-7s %20s %6s %6s %20s %8s %-10s %7s%n", "CodVenta", "CodArti", "NombreArticulo", "Precio", "NumCli", "NombreCli", "Unidades", "Fecha", "Importe");
		System.out.printf("%-8s %-7s %20s %6s %6s %20s %8s %-10s %7s%n", "--------", "-------", "--------------------", "------", "------", "--------------------", "--------", "----------", "-------");
		
		Objects<Ventas> ventas = odb.getObjects(query);
		for (Ventas v : ventas) {
			float importe = v.getUniven() * v.getCodarti().getPvp();
			System.out.printf("%-8s %-7s %20s %6s %6s %20s %8s %-10s %7s%n", v.getCodventa(), v.getCodarti().getCodarti(), v.getCodarti().getDenom(), v.getCodarti().getPvp(), v.getNumcli().getNumcli(), v.getNumcli().getNombre(), v.getUniven(), v.getFecha(), importe);
		}
		
		odb.close();
	}

	private static void consultarVentas(String ciudad, int precio) {
		ODB odb = ODBFactory.open(articulos);
		
		ICriterion where = new And().add(Where.equal("numcli.pobla", ciudad))
				.add(Where.le("codarti.pvp", precio)
		);
		IQuery query = new CriteriaQuery(Ventas.class, where);
		
		System.out.printf("%-8s %-7s %20s %6s %6s %20s %8s %-10s%n", "CodVenta", "CodArti", "NombreArticulo", "Precio", "NumCli", "NombreCli", "Unidades", "Fecha");
		System.out.printf("%-8s %-7s %20s %6s %6s %20s %8s %-10s%n", "--------", "-------", "--------------------", "------", "------", "--------------------", "--------", "----------");
		
		Objects<Ventas> ventas = odb.getObjects(query);
		for (Ventas v : ventas) {
			System.out.printf("%-8s %-7s %20s %6s %6s %20s %8s %-10s%n", v.getCodventa(), v.getCodarti().getCodarti(), v.getCodarti().getDenom(), v.getCodarti().getPvp(), v.getNumcli().getNumcli(), v.getNumcli().getNombre(), v.getUniven(), v.getFecha());
		}
		
		odb.close();
	}
}
