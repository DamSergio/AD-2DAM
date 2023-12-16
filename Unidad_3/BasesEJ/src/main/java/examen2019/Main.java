package examen2019;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import datos.*;
import principal.Conexion;

public class Main {
	private static SessionFactory sesion;

	public static void main(String[] args) {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);
		sesion = Conexion.getSession(); //Creo la sessionFactory una única vez.
		
		ejercicio2("0101", BigInteger.valueOf(3), BigInteger.valueOf(2), "01/02/23", "28/02/23");
		System.out.println("------------------");
		ejercicio3();
		
		sesion.close();
	}

	private static void ejercicio3() {
		Session session = sesion.openSession();
		
		Query cons = session.createQuery("from T3clientes", T3clientes.class);
		
		List<T3clientes> clientes = cons.list();
		for (T3clientes cli : clientes) {
			System.out.println("Cod cliente: " + cli.getCodigocliente() + "\tNombre: " + cli.getNombrecliente() + "\tApellidos: " + cli.getApellido());
			
			if (cli.getT3reservases().size() == 0) {
				System.out.println("El cliente no tiene reservas");
				continue;
			}
			
			System.out.printf("%6s %8s %7s %12s %11s %10s %4s %6s %7s %9s %10s%n", "CODRES", "NUMHABIT", "CAMSUPL", "FECHAENTRADA", "FECHASALIDA", "TOTALSUPLE", "DIAS", "PVPDIA", "IMPORTE", "IMPORDESC", "IMPORTOTAL");
			System.out.printf("%6s %8s %7s %12s %11s %10s %4s %6s %7s %9s %10s%n", "------", "--------", "-------", "------------", "-----------", "----------", "----", "------", "-------", "---------", "----------");
			
			Set<T3reservas> reservas =  cli.getT3reservases();
			for (T3reservas res : reservas) {
				long difMS = res.getFechasalida().getTime() - res.getFechaentrada().getTime();
				long difDias = TimeUnit.DAYS.convert(difMS, TimeUnit.MILLISECONDS);
				
				long importe;
				
				System.out.printf("%6s %8s %7s %12s %11s %10s %4s %6s %7s %9s %10s%n", res.getCodreserva(), res.getT3habitaciones().getNumhabitacion(), res.getCamassupletorias(), res.getFechaentrada().toString(), res.getFechasalida().toString(),(res.getCamassupletorias().intValue() * 10), difDias, "PVPDIA", "IMPORTE", "IMPORDESC", "IMPORTOTAL");
			}
		}
	}

	private static void ejercicio2(String numHab, BigInteger codCli, BigInteger camas, String fechaE, String fechaS) {
		Session session = sesion.openSession();
		
		T3clientes cli = (T3clientes) session.get(T3clientes.class, codCli);
		if (cli == null) {
			System.out.println("No existe el cliente");
			session.close();
			return;
		}
		
		T3habitaciones hab = (T3habitaciones) session.get(T3habitaciones.class, numHab);
		if (hab == null) {
			System.out.println("No existe la habitacion");
			session.close();
			return;
		}
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		formato.setLenient(false);
		Date dateE = null;
		Date dateS = null;
		try {
			dateE = (Date) formato.parse(fechaE);
			dateS = (Date) formato.parse(fechaS);
			
		} catch (ParseException e) {
			System.out.println("Formato de fecha incorrecta");
			
			return;
		}
		
		Query cons=session.createQuery("from T3reservas r where r.t3habitaciones.numhabitacion=:numHab and (:fechaEntrada between r.fechaentrada and r.fechasalida ) or (:fechaSalida between r.fechaentrada and r.fechasalida)",T3reservas.class)
				.setParameter("numHab", numHab)
				.setParameter("fechaEntrada", dateE)
				.setParameter("fechaSalida", dateS);

		List<T3reservas> listaReservas = cons.list();
		if(!listaReservas.isEmpty()) {
			System.out.println("Ya existe una reserva para esta fecha");
			return;
		}
		
		if(fechaE.equals(fechaS) || !dateE.before(dateS) ) {
			System.out.println("Fechas incorrectas");
			return;
		}
		
		BigInteger descuento = BigInteger.valueOf(0);
		int mes = Integer.parseInt(fechaE.split("/")[1]);
		int trimestre = (mes - 1) / 3;
		
		if (trimestre == 0) {
			descuento = BigInteger.valueOf(10);
		} else if (trimestre == 1){
			descuento = BigInteger.valueOf(4);
		} else if (trimestre == 2) {
			descuento = BigInteger.valueOf(0);
		} else {
			descuento = BigInteger.valueOf(5);
		}
		
		Transaction tx = session.beginTransaction();
		
		BigInteger codRes = (BigInteger) session.createQuery("select max(r.codreserva) + 1 from T3reservas r", BigInteger.class).uniqueResult();
		
		T3reservas reserva = new T3reservas();
		
		reserva.setCodreserva(codRes);
		reserva.setT3clientes(cli);
		reserva.setT3habitaciones(hab);
		reserva.setCamassupletorias(camas);
		reserva.setFechaentrada(dateE);
		reserva.setFechasalida(dateS);
		reserva.setDescuento(descuento);
		
		session.persist(reserva);
		
		System.out.println("FUNCIONA");
		
		tx.commit();
		session.close();
	}
}
