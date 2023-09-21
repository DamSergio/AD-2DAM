package ejercicio1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {
	static int COD_LON = 4;
	static int NAME_LON = 30;
	static int STOCK_LON = 4;
	static int PVP_LON = 8;
	
	static int UNIT_SALES_LON = 4;
	static int DATE_LON = 20;
	
	static int PROD_LON = COD_LON + NAME_LON + STOCK_LON + PVP_LON;
	static int SALES_LON = COD_LON + UNIT_SALES_LON + DATE_LON;

	public static void main(String[] args) {
		Scanner t = new Scanner(System.in);
		
		try {
			File pro = new File(".\\Productos.dat");
			RandomAccessFile productos = new RandomAccessFile(pro, "rw");
			
			File ven = new File(".//DatosdeVentas.dat");
			RandomAccessFile ventas = new RandomAccessFile(ven, "rw");
			
			int op = 0;
			do {
				menu();
				op = t.nextInt();
				
				switch (op) {
					case 1:
						listarProductos(productos);
						break;
						
					case 2:
						listarVentas(ventas);
						break;
						
					case 3:
						actualizarExistencias(productos, ventas);
						break;
						
					case 4:
						System.out.println("Adios!");
						break;
						
					default:
						System.out.println("Opcion incorrecta");
				}
				
			} while (op != 4);
			
			productos.close();
			ventas.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t.close();
	}
	
	private static void actualizarExistencias(RandomAccessFile productos, RandomAccessFile ventas) throws IOException {
		int posProd = 0;
		int posSales = 0;
		
		while (posSales < ventas.length()) {
			ventas.seek(posSales);
			int codSales = ventas.readInt();
			int unitSold = ventas.readInt();
			
			posProd = (codSales - 1) * PROD_LON;
			if (posProd >= productos.length()) {
				posSales += SALES_LON;
				System.out.println("NO existe el producto: " + codSales);
				continue;
			}
			
			productos.seek(posProd);
			int codProd = productos.readInt();
			if (codProd != codSales) {
				posSales += SALES_LON;
				System.out.println("NO existe el producto: " + codSales);
				continue;
			}
			
			productos.skipBytes(NAME_LON);
			int stock = productos.readInt() - unitSold;
			productos.skipBytes(-STOCK_LON);
			productos.writeInt(stock);
			
			posSales += SALES_LON;
		}
		
	}

	private static void listarVentas(RandomAccessFile file) throws IOException {
		int pos = 0;
		System.out.printf("%6s %7s %10s %n", "CODIGO", "UNI VEN", "FECHA");
		System.out.printf("%6s %7s %10s %n", "------", "-------", "----------");
		
		while (pos < file.length()) {
			file.seek(pos);
			
			int cod = file.readInt();
			
			int stock = file.readInt();
			
			String date = "";
			for (int i = 0; i < DATE_LON / 2; i++) {
				date += file.readChar();
			}
			
			System.out.printf("%6s %7s %10s %n", cod, stock, date);
			
			pos += SALES_LON;
		}
		
	}

	private static void listarProductos(RandomAccessFile file) throws IOException {
		int pos = 0;
		System.out.printf("%6s %15s %11s %8s %n", "CODIGO", "NOMBRE", "EXISTENCIAS", "PVP");
		System.out.printf("%6s %15s %11s %8s %n", "------", "---------------", "-----------", "--------");
		
		while (pos < file.length()) {
			file.seek(pos);
			
			int cod = file.readInt();
			
			if (cod == 0) {
				pos += PROD_LON;
				continue;
			}
			
			String nom = "";
			for (int i = 0; i < NAME_LON / 2; i++) {
				nom += file.readChar();
			}
			
			int exis = file.readInt();
			
			double pvp = file.readDouble();
			
			System.out.printf("%6d %15s %11s %8s%n", cod, nom, exis, pvp);
			
			pos += PROD_LON;
		}
		
	}

	public static void menu() {
		System.out.println("1. Listar productos");
		System.out.println("2. Listar datos ventas");
		System.out.println("3. Actualizar existencias");
		System.out.println("4. Salir");
	}

}
