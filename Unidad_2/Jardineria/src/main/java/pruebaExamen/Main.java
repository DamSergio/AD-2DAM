package pruebaExamen;

import java.sql.*;
import java.util.Scanner;

public class Main {
	static Conexiones con = new Conexiones();
	static Connection conn = con.getOracle("jardineria", "jardineria");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int op = 0;
		
		do {
			menu();
			op = sc.nextInt();
			
			switch (op) {
			case 1:
				insertarEmple("ficticio", "ape1", "ape2", "3897", "jhsdbf@mail.com", "TAL-ES", 1, "Director Oficina");
				break;
			case 2:
				pedidosCliente(1);
				break;
			case 3:
				crearClienteSinPedido();
				break;
			case 4:
				actualizarClienteEmpleado();
				break;
			case 5:
				updateStock();
				break;
			case 6:
				oficinasFuncion("BCN-ES");
				break;
			case 7:
				pedidosClientes();
				break;
			case 8:
				nuevosEmples();
				break;
			case 0:
				System.out.println("Bye bye 🖐🖐🖐");
				break;
			}
		} while (op != 0);
		
	}
	
	private static void nuevosEmples() {
		try {
			Statement emp = conn.createStatement();
			ResultSet empleadosNuevos = emp.executeQuery("SELECT * FROM nuevosempleados");
			while (empleadosNuevos.next()) {
				boolean err = false;
				
				PreparedStatement ofi = conn.prepareStatement("SELECT COUNT(*) FROM empleados WHERE codigooficina = ?");
				ofi.setString(1, empleadosNuevos.getString(7));
				ResultSet existeOfi = ofi.executeQuery();
				existeOfi.next();
				if (existeOfi.getInt(1) == 0) {
					System.out.println("Oficina erronea");
					err = true;
				}
				existeOfi.close();
				ofi.close();
				
				PreparedStatement jefe = conn.prepareStatement("SELECT COUNT(codigojefe) FROM empleados WHERE codigojefe = ?");
				jefe.setInt(1, empleadosNuevos.getInt(8));
				ResultSet existeJefe = jefe.executeQuery();
				existeJefe.next();
				if (existeJefe.getInt(1) == 0) {
					System.out.println("Jefe erroneo");
					err = true;
				}
				existeJefe.close();
				jefe.close();
				
				if (err) {
					System.out.println("Empleado: " + empleadosNuevos.getInt(1) + " " + empleadosNuevos.getString(2) + " erroneo, no se ha podido insertar/modificar");
					continue;
				}
				
				PreparedStatement emple = conn.prepareStatement("SELECT * FROM empleados WHERE codigoempleado = ?");
				emple.setInt(1, empleadosNuevos.getInt(1));
				ResultSet empleado = emple.executeQuery();
				if (empleado.next()) {
					PreparedStatement upd = conn.prepareStatement(
							"UPDATE empleados SET nombre = ?, apellido1 = ?, apellido2 = ?, extension = ?, email = ?, codigooficina = ?, codigojefe = ?, puesto = ? WHERE codigoempleado = ?"
					);
					
					upd.setString(1, empleadosNuevos.getString(2));
					upd.setString(2, empleadosNuevos.getString(3));
					upd.setString(3, empleadosNuevos.getString(4));
					upd.setString(4, empleadosNuevos.getString(5));
					upd.setString(5, empleadosNuevos.getString(6));
					upd.setString(6, empleadosNuevos.getString(7));
					upd.setInt(7, empleadosNuevos.getInt(8));
					upd.setString(8, empleadosNuevos.getString(9));
					upd.setInt(9, empleadosNuevos.getInt(1));
					
					upd.executeUpdate();
					upd.close();
				} else {
					PreparedStatement ins = conn.prepareStatement(
							"INSERT INTO empleados (codigoempleado, nombre, apellido1, apellido2, extension, email, codigooficina, codigojefe, puesto) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)"
					);
					
					ins.setInt(1, empleadosNuevos.getInt(1));
					ins.setString(2, empleadosNuevos.getString(2));
					ins.setString(3, empleadosNuevos.getString(3));
					ins.setString(4, empleadosNuevos.getString(4));
					ins.setString(5, empleadosNuevos.getString(5));
					ins.setString(6, empleadosNuevos.getString(6));
					ins.setString(7, empleadosNuevos.getString(7));
					ins.setInt(8, empleadosNuevos.getInt(8));
					ins.setString(9, empleadosNuevos.getString(9));
					
					ins.executeUpdate();
					ins.close();
				}
				empleado.close();
				emple.close();
			}
			empleadosNuevos.close();
			emp.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void pedidosClientes() {
		try {
			Statement cli = conn.createStatement();
			ResultSet clientes = cli.executeQuery("SELECT codigocliente FROM clientes");
			while (clientes.next()) {
				pedidosCliente(clientes.getInt(1));
				System.out.println("\n\n");
			}
			cli.close();
			clientes.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void oficinasFuncion(String ofi) {
		 try {
			CallableStatement func = conn.prepareCall("{ ? = call veroficina (?, ?, ?, ?, ?) }");
			
			func.registerOutParameter(1, Types.INTEGER);
			func.setString(2, ofi);
			func.registerOutParameter(3, Types.VARCHAR);
			func.registerOutParameter(4, Types.VARCHAR);
			func.registerOutParameter(5, Types.VARCHAR);
			func.registerOutParameter(6, Types.VARCHAR);
			
			func.executeUpdate();
			
			System.out.printf("%11s %-20s %-20s %-20s %-50s %10s%n", "COD_OFICINA", "CIUDAD", "PAIS", "REGION", "DIRECCION", "NUM EMPLES");
			System.out.printf("%11s %-20s %-20s %-20s %-50s %10s%n", "-----------", "--------------------", "--------------------", "--------------------", "--------------------------------------------------", "----------");
			System.out.printf("%11s %-20s %-20s %-20s %-50s %10s%n", ofi, func.getString(3), func.getString(4), func.getString(5), func.getString(6), func.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void updateStock() {
		try {
			Statement colum = conn.createStatement();
			colum.executeUpdate("ALTER TABLE productos ADD stockactualizado INT DEFAULT 0");
			System.out.println("la columna stockactualizado se ha creado");
			colum.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("La columna stockactualizado ya existe");
		}
		
		try {
			Statement update = conn.createStatement();
			update.executeUpdate("UPDATE productos p SET stockactualizado = NVL(p.cantidadenstock - (SELECT SUM(CANTIDAD) FROM detallepedidos WHERE codigoproducto = p.codigoproducto), p.cantidadenstock)");
			
			update.close();
			Statement prod = conn.createStatement();
			ResultSet productos = prod.executeQuery("SELECT codigoproducto, cantidadenstock, stockactualizado FROM productos WHERE stockactualizado < 5 ORDER BY codigoproducto");
			
			System.out.println("PRODUCTOS A REPONER");
			while (productos.next()) {
				System.out.println("codigo: " + productos.getString(1) + ", stock: " + productos.getInt(2) + ", stock actualizado: " + productos.getInt(3));
			}
			
			prod.close();
			productos.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void actualizarClienteEmpleado() {
		try {
			Statement colum = conn.createStatement();
			colum.executeUpdate("ALTER TABLE empleados ADD numclientes INT DEFAULT 0");
			System.out.println("la columna NUMCLIENTES se ha creado");
			colum.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("La columna NUMCLIENTES ya existe");
		}
		
		try {
			Statement update = conn.createStatement();
			int emplesUpdated = update.executeUpdate("UPDATE empleados e SET numclientes = (SELECT COUNT(*) FROM clientes WHERE CODIGOEMPLEADOREPVENTAS = e.codigoempleado)");
			System.out.println("La clumna NUMCLIENTES Se ha actualizado");
			
			System.out.println(emplesUpdated + " actualizados");
			
			update.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void crearClienteSinPedido() {
		try {
			Statement tabla = conn.createStatement();
			tabla.executeUpdate("CREATE TABLE clientessinpedido AS SELECT * FROM clientes WHERE codigocliente NOT IN (SELECT codigocliente FROM pedidos) ORDER BY codigocliente");
			tabla.executeUpdate("ALTER TABLE clientessinpedido ADD CONSTRAINT pk PRIMARY KEY codigocliente");
			tabla.executeUpdate("ALTER TABLE clientessinpedido ADD CONSTRAINT fk FOREIGN KEY (codigoempleadorepventas) REFERENCES empleados (codigoempleado)");
			
			//System.out.println("Tabla creada con datos y restricciones añadidas");
		} catch (SQLException e) {
			//System.out.println("La tabla ya esta creada");
		}
		
		try {
			Statement tabla = conn.createStatement();
			int del = tabla.executeUpdate("DELETE FROM clientes WHERE codigocliente NOT IN (SELECT codigocliente FROM pedidos)");
			
			System.out.println("Se han borrado " + del + " registros de la tabla clientes");
			tabla.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Statement cli = conn.createStatement();
			ResultSet clientes = cli.executeQuery("SELECT * FROM clientessinpedidos");
			while (clientes.next()) {
				System.out.println("nombre: " + clientes.getString(2));
			}
		} catch (SQLException e) {
			
		}
	}

	private static void pedidosCliente(int codCli) {
		try {
			PreparedStatement cli = conn.prepareStatement("SELECT codigocliente, nombrecliente, lineadireccion1, COUNT(codigopedido) FROM clientes JOIN pedidos USING(codigocliente) WHERE codigocliente = ? GROUP BY codigocliente, nombrecliente, lineadireccion1");
			cli.setInt(1, codCli);
			ResultSet cliente = cli.executeQuery();
			if (cliente.next()) {
				System.out.println("COD-CLIENTE: " + cliente.getString(1) + "\tNOMBRE: " + cliente.getString(2));
				System.out.println("DIRECCION1: " + cliente.getString(3) + "\tNUM PEDIDOS: " + cliente.getInt(4));
			}
			cli.close();
			cliente.close();
			
			System.out.println("-----------------------------------------------------------------------");
			
			PreparedStatement ped = conn.prepareStatement("SELECT codigopedido, fechapedido, estado FROM pedidos WHERE codigocliente = ?");
			ped.setInt(1, codCli);
			ResultSet pedidos = ped.executeQuery();
			while (pedidos.next()) {
				
				PreparedStatement prod = conn.prepareStatement("SELECT numerolinea, codigoproducto, nombre, cantidad, preciounidad, (cantidad * preciounidad)importe FROM detallepedidos JOIN productos USING(codigoproducto) WHERE codigopedido = ? ORDER BY numerolinea");
				prod.setInt(1, pedidos.getInt(1));
				ResultSet producto = prod.executeQuery();
				
				if (producto.next()) {
					int tCant = 0;
					float tPrec = 0;
					float tImpo = 0;
					
					System.out.println("\tCOD-PEDIDO: " + pedidos.getInt(1) + "\tFECHA PEDIDO: " + pedidos.getString(2) + "\tESTADO: " + pedidos.getString(3));
					System.out.printf("        %9s %8s %30s %8s %9s %9s%n", "NUM-LINEA", "COD-PROD", "NOMBRE PRODUCTO", "CANTIDAD", "PRECIO", "IMPORTE");
					System.out.printf("        %9s %8s %30s %8s %9s %9s%n", "---------", "--------", "---------------", "--------", "---------", "---------");
					do {
						String nombre = producto.getString(3);
						if (nombre.length() > 30) {
							nombre = nombre.substring(0, 30);
						}
						
						System.out.printf("        %9s %8s %30s %8s %9s %9s%n", producto.getString(1), producto.getString(2), nombre, producto.getInt(4), producto.getFloat(5), producto.getFloat(6));
						
						tCant += producto.getInt(4);
						tPrec += producto.getInt(5);
						tImpo += producto.getInt(6);
					} while (producto.next());
					prod.close();
					producto.close();
					
					System.out.printf("        %9s %8s %30s %8s %9s %9s%n", "---------", "--------", "---------------", "--------", "---------", "---------");
					System.out.printf("        %-49s %8s %9s %9s%n","TOTALES POR PEDIDO:", tCant, tPrec, tImpo);
					System.out.println("\n");
				} else {
					System.out.println("El pedido " + pedidos.getInt(1) + " no tiene detalle");
				}
				
			}
			ped.close();
			pedidos.close();
			
			System.out.println("-----------------------------------------------------------------------");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void insertarEmple(String nombre, String ape1, String ape2, String exte,
			String email, String codOfi, int codJefe, String puesto) {
		boolean err = false;
		String errMsg = "";
		try {
			Statement getCod = conn.createStatement();
			ResultSet cod = getCod.executeQuery("SELECT MAX(codigoempleado) FROM empleados");
			cod.next();
			int codEmp = cod.getInt(1) + 1;
			cod.close();
			getCod.close();
			
			PreparedStatement ofi = conn.prepareStatement("SELECT COUNT(codigooficina) FROM oficinas WHERE codigooficina = ?");
			ofi.setString(1, codOfi);
			ResultSet oficina = ofi.executeQuery();
			oficina.next();
			if (oficina.getInt(1) == 0) {
				err = true;
				errMsg += "Oficina no existe: " + codOfi + ". ";
			}
			oficina.close();
			ofi.close();
			
			PreparedStatement jefe = conn.prepareStatement("SELECT COUNT(codigojefe) FROM empleados WHERE codigojefe = ?");
			jefe.setInt(1, codJefe);
			ResultSet existeJefe = jefe.executeQuery();
			existeJefe.next();
			if (existeJefe.getInt(1) == 0) {
				err = true;
				errMsg += "\nEl jefe no existe: " + codJefe + ". ";
			}
			existeJefe.close();
			jefe.close();
			
			if (!err) {
				PreparedStatement insert = conn.prepareStatement("INSERT INTO empleados (codigoempleado, nombre, apellido1, apellido2, extension, email, codigooficina, codigojefe, puesto) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
				
				insert.setInt(1, codEmp);
				insert.setString(2, nombre);
				insert.setString(3, ape1);
				insert.setString(4, ape2);
				insert.setString(5, exte);
				insert.setString(6, email);
				insert.setString(7, codOfi);
				insert.setInt(8, codJefe);
				insert.setString(9, puesto);
				
				insert.executeUpdate();
				System.out.println("Insertado correctamente");
			} else {
				System.out.println(errMsg);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void menu() {
		System.out.println("1 Insertar Empleado");
		System.out.println("2 Visualizar pedidos de un cliente");
		System.out.println("3 Crear clientes sin pedido.");
		System.out.println("4 Actualizar Clientes por empleado.");
		System.out.println("5 Crear STOCKACTUALIZADO.");
		System.out.println("6 Oficinas con función almacenada.");
		System.out.println("7 Ver los pedidos de todos los clientes.");
		System.out.println("8 Tratar nuevos empleados.");
		System.out.println("0 SALIR.");
		 
	}

}
