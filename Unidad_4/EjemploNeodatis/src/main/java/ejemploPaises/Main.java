package ejemploPaises;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;

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
	private static String equipos = "equipos.db";
	
	public static void main(String[] args) {
//		crearBD();
//		System.out.println("\n");
//		consultarOID(4); // Jugador
//		consultarOID(9); // Pais
//		System.out.println("\n");
//		consultarDeporte("Tenis");
//		System.out.println("\n");
//		consultarPais("ESPAÑA");
//		System.out.println("\n");
//		consultarJugador("Mara");
//		System.out.println("\n");
//		sumarEdad("ESPAÑA");
//		System.out.println("\n");
//		actualizarPais("ESPAÑA", "ARGENTINA"); // Si el pais no existe se crea añadir id = 100;
//		System.out.println("\n");
//		jugadores14irlandafranciaitalia();
//		System.out.println("\n");
//		paisDeporte("ESPAÑA", "Voleibol");
//		System.out.println("\n");
//		borrarPais("ITALIA");
//		sumaEdad("ITALIA");
//		sumaEdad("ESPAÑA");
//		sumaEdad("CACA");
//		contarJugadores("ESPAÑA");
//		mediaEdad("ESPAÑA");
		edadMinMax("ESPAÑA");
	}
	
	private static void edadMinMax(String pais) {
		ODB odb = ODBFactory.open(equipos);
		
		Values val4 = odb.getValues(new ValuesCriteriaQuery(Jugadores.class, Where.equal("pais.nombrepais", pais))
				.min("edad","edad_min")
                .max("edad","edad_max")
        );
		
		ObjectValues ov4 = val4.nextValues();
		
		BigDecimal maxima = (BigDecimal) ov4.getByAlias("edad_max");
		BigDecimal minima = (BigDecimal) ov4.getByAlias("edad_min");
		
		if (minima.intValue() < 0) {
			System.out.println("El pais no existe");
			return;
		}
		
		System.out.println("Edad maxima: " + maxima.intValue() + "  Edad minima: " + minima.intValue());
		
		odb.close();
	}

	private static void mediaEdad(String pais) {
		ODB odb = ODBFactory.open(equipos);
		
		
		Values count = odb.getValues(new ValuesCriteriaQuery(Jugadores.class, Where.equal("pais.nombrepais", pais)).count("edad"));
		Values sum = odb.getValues(new ValuesCriteriaQuery(Jugadores.class, Where.equal("pais.nombrepais", pais)).sum("edad"));
		ObjectValues ovCount = count.nextValues();
		ObjectValues ovSum = sum.nextValues();
		
		BigInteger cont = (BigInteger) ovCount.getByAlias("edad");
		BigDecimal su = (BigDecimal) ovSum.getByAlias("edad");
		
		if (cont.intValue() <= 0) {
			System.out.println("El pais no existe");
			return;
		}
		
		double media = (double) su.doubleValue() / cont.doubleValue();
		System.out.println("Media de edad de jugadores de: " + pais + " => " +media);
		
		
		odb.close();
	}

	private static void contarJugadores(String pais) {
		ODB odb = ODBFactory.open(equipos);
		
		Values val = odb.getValues(new ValuesCriteriaQuery(Jugadores.class, Where.equal("pais.nombrepais", pais)).count("nombre"));
		ObjectValues ov = val.nextValues();
		
		BigInteger value = (BigInteger) ov.getByAlias("nombre");
		System.out.println("Número de jugadores de: " + pais + " => " + value.longValue());
		
		odb.close();
	}

	private static void sumaEdad(String pais) {
		ODB odb = ODBFactory.open(equipos);
		
		Values val = odb.getValues(new ValuesCriteriaQuery(Jugadores.class, Where.equal("pais.nombrepais", pais)).sum("edad"));
		ObjectValues ov = val.nextValues();
		
		BigDecimal value = (BigDecimal) ov.getByAlias("edad");
		System.out.println("Suma de edad de: "+ pais + " => "+ value.longValue());
		
		odb.close();
	}

	private static void borrarPais(String pais) {
		ODB odb = ODBFactory.open(equipos);
		
		try {
			IQuery query = new CriteriaQuery(Paises.class, Where.equal("nombrepais", pais));
			Paises p = (Paises) odb.getObjects(query).getFirst();
			
			IQuery query2 = new CriteriaQuery(Jugadores.class, Where.equal("pais.nombrepais", pais));
			Objects<Jugadores> jugadores = odb.getObjects(query2);
			for (Jugadores jug : jugadores) {
				jug.setPais(null);
				System.out.println("Jugador " + jug.getNombre() + ", pais borrado");
				odb.store(jug);
			}
			
			odb.delete(p);
			System.out.println("Pais " + pais + " borrado.");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("El pais: " + pais + " no existe.");
		}
		
		odb.close();
	}

	private static void paisDeporte(String pais, String deporte) {
		ODB odb = ODBFactory.open(equipos);
		
		ICriterion where = new And().add(Where.equal("pais.nombrepais", pais))
				.add(Where.equal("deporte", deporte)
		);
		
		IQuery query = new CriteriaQuery(Jugadores.class, where);
		Objects<Jugadores> jugadores = odb.getObjects(query);
		if (jugadores.size() == 0) {
			System.out.println("No hay jugadores de ese pais/deporte");
			return;
		}
		
		System.out.println("jugadores de " + pais + " que juegan " + deporte + ": " + jugadores.size() + "\n");
		for (Jugadores jug : jugadores) {
			System.out.println(jug);
		}
		
		odb.close();
	}

	private static void jugadores14irlandafranciaitalia() {
		ODB odb = ODBFactory.open(equipos);
		ICriterion criterio = new And().add(Where.equal("edad", 14))
				.add(new Or().add(Where.equal("pais.nombrepais","IRLANDA"))
				.add(Where.equal("pais.nombrepais","ITALIA"))
				.add(Where.equal("pais.nombrepais","FRANCIA"))
		);
		
		IQuery query = new CriteriaQuery(Jugadores.class, criterio);
		Objects jugadores = odb.getObjects(query);

		if (jugadores.size() == 0) {
			System.out.println(" No existen jugadores de 14 años de " + " IRLANDA, ITALIA, FRANCIA.");
		} else {
			Jugadores jug;
			System.out.println("Jugadores de 14 años de IRLANDA, ITALIA,FRANCIA.");
			
			while (jugadores.hasNext()) {
				jug = (Jugadores) jugadores.next();
				System.out.println(jug.getNombre() + " - Edad: " + jug.getEdad() + ", CIUDAD : " + jug.getCiudad() + ", PAIS: " + jug.getPais().getNombrepais());
			}
		}
		
		odb.close();
	}

	
	private static void actualizarPais(String pais, String nuevoPais) {
		ODB odb = ODBFactory.open(equipos); // Abrir BD
		
		IQuery query = new CriteriaQuery(Jugadores.class, Where.equal("pais.nombrepais", pais));
		
		Objects<Jugadores> objects = odb.getObjects(query);
		if (objects.size() <= 0) {
			System.out.println("No hay jugadores de: " + pais);
			return;
		}
		
		Paises p = null;
		try {
			IQuery qPais = new CriteriaQuery(Paises.class, Where.equal("nombrepais", nuevoPais));
			p = (Paises) odb.getObjects(qPais).getFirst();
		} catch (IndexOutOfBoundsException e) {
			p = new Paises(100, nuevoPais);
		}
		
		for (Jugadores jug : objects) {
			System.out.printf("Jugador %s pais anterior %s", jug.getNombre(), jug.getPais().getNombrepais());
			jug.setPais(p);
			odb.store(jug);
			System.out.printf(". Nuevo pais: %s%n", jug.getPais().getNombrepais());
		}
		
		odb.close();
	}

	private static void sumarEdad(String pais) {
		ODB odb = ODBFactory.open(equipos); // Abrir BD
		
		try {
			IQuery query = new CriteriaQuery(Jugadores.class, Where.equal("pais.nombrepais", pais));
			
			Objects<Jugadores> objects = odb.getObjects(query);
			for (Jugadores jug : objects) {
				System.out.printf("Jugador %s edad anterior %s", jug.getNombre(), jug.getEdad());
				jug.setEdad(jug.getEdad() + 1);
				odb.store(jug);
				System.out.printf(". Nueva edad: %s%n", jug.getEdad());
			}
		} catch (ODBRuntimeException e) {
			// TODO: handle exception
		}
		
		odb.close();
	}

	private static void consultarJugador(String nombre) {
		ODB odb = ODBFactory.open(equipos); // Abrir BD
		try {
			IQuery query = new CriteriaQuery(Jugadores.class, Where.equal("nombre", nombre));
			
			Jugadores j = (Jugadores) odb.getObjects(query).getFirst();
			System.out.println(j);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("OBJETO NO LOCALIZADO");
		}
		
		odb.close();
	}
	
	private static void consultarPais(String pais) {
		ODB odb = ODBFactory.open(equipos); // Abrir BD
		
		IQuery query = new CriteriaQuery(Jugadores.class, Where.equal("pais.nombrepais", pais));
		query.orderByAsc("nombre"); // Ordena por nombre
		
		//Obtiene todos los jugadores DE LA CONSULTA
		int i = 1;
		
		Objects<Jugadores> objects2 = odb.getObjects(query); 
		System.out.println("Jugadores del pais: " + pais + " => " + objects2.size());
		
		for (Jugadores jug : objects2) {
				System.out.println((i++) + " - " + jug.toString());
		}
		
		odb.close();
	}

	private static void consultarDeporte(String deporte) {
		ODB odb = ODBFactory.open(equipos); // Abrir BD
		
		IQuery query = new CriteriaQuery(Jugadores.class, Where.equal("deporte", deporte));
		query.orderByAsc("nombre"); // Ordena por nombre
		
		//Obtiene todos los jugadores DE LA CONSULTA
		int i = 1;
		
		Objects<Jugadores> objects2 = odb.getObjects(query); 
		System.out.println("Jugadores del deporte: " + deporte + " => " + objects2.size());
		
		for (Jugadores jug : objects2) {
				System.out.println((i++) + " - " + jug.toString());
		}
		
		odb.close();
	}

	public static void consultarOID(int num) {
		ODB odb = ODBFactory.open(equipos); // Abrir BD
		
		try {
			// Obtener objeto con OID
			OID oid = OIDFactory.buildObjectOID(num);
			
			Object jug  = (Object) odb.getObjectFromId(oid);
			System.out.println("Tipo: " + jug.getClass().getSimpleName() + " => " + jug.toString());
		} catch (ODBRuntimeException er1) {
			System.out.println("OID = " + num + ". ERROR BD ODBRuntimeException");
		}
	    
		odb.close(); // Cerrar BD	
	}

	public static void crearBD() {
		File file = new File(equipos);
		if (file.exists()) {
			file.delete();
		}
		
		ODB odb = ODBFactory.open(equipos); // Abrir BD
		
		//Paises
		Paises españa = new Paises(1, "ESPAÑA");
		Paises italia = new Paises(2, "ITALIA");
		Paises alemania = new Paises(3, "ALEMANIA");
		
		//Jugadores de ESPAÑA
		Jugadores j1 = new Jugadores("Maria", "Voleibol", "Madrid", 14, españa);
		Jugadores j2 = new Jugadores("Pedro", "Voleibol", "Talavera", 15, españa);
		Jugadores j3 = new Jugadores("Juan", "Tenis", "Talavera", 16, españa);
		
		//Jugadores de ITALIA
		Jugadores j4 = new Jugadores("Giovani", "Voleibol", "Roma", 14, italia);
		Jugadores j5 = new Jugadores("Luigi", "Tenis", "Roma", 15, italia);
		
		//Jugadores de ALEMANIA
		Jugadores j10 = new Jugadores("Jurgen", "Tenis", "Berlin", 16, alemania);
		Jugadores j11 = new Jugadores("Frank", "Voleibol", "Berlin", 15, alemania);
		Jugadores j12 = new Jugadores("Rose", "Padel", "Munich", 14, alemania);
		
		//Almacenar datos
		odb.store(j1);
		odb.store(j2);
		odb.store(j3);
		odb.store(j4);
		odb.store(j5);
		odb.store(j10);
		odb.store(j11);
		odb.store(j12);
		
		System.out.println("BASE DE DATOS CREADA");
		System.out.println("LISTADO DE DATOS");
		
		Objects<Jugadores> objects = odb.getObjects(Jugadores.class);
		System.out.println(objects.size() + " Jugadores:");
		
		int i = 1;
		for (Jugadores jug : objects) {
	    	System.out.println((i++) + jug.toString());
		}
		
		odb.close();
	}

}
