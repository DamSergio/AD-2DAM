package pruebaFicheros;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class OperarAleatorio {

	public static void main(String[] args) {
		File fichero = new File(".\\AleatorioEmple.dat");
		
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			
			consultar(file, 5);
			consultar(file, 90);
			insertar(file, 15, "Emple15", 10, 2000d);
			insertar(file, 3, "Emple3", 10, 2000d);
			insertar(file, 11, "Emple11", 10, 2000d);
			insertar(file, 15, "Emple15", 10, 2000d);
			
			actualizarNombreSalario(file, 1, "nuevo11", 1000d);
			
			actualizarSalarioDep(file, 10, 130d);
			
			borrarEmpleado(file, 8);
			
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void borrarEmpleado(RandomAccessFile file, int id) throws IOException {
		int pos = (id - 1) * 36;
		file.seek(pos);
		
		if (pos > file.length()) {
			System.out.println("No existe ese empleado");
		} else {
			int ident = file.readInt();
			file.seek(pos);
			
			if (ident == 0) { //no existe
				System.out.println("No existe ese empleado");
			} else {
				file.writeInt(0);
				StringBuffer buffer = new StringBuffer("");
				buffer.setLength(10);
				file.writeChars(buffer.toString());
				
				file.writeInt(0);
				
				file.writeDouble(0d);
				System.out.println("Borrado");
			}
		}
	}

	private static void actualizarSalarioDep(RandomAccessFile file, int dep, double sal) throws IOException {
		int pos = 0;
		int cont = 0;
		
		for (;;) {
			file.seek(pos);
			file.skipBytes(24);
			
			if (file.readInt() == dep) {
				file.seek(pos);
				file.skipBytes(28);
				
				double salario = file.readDouble() + sal;
				
				file.seek(pos);
				file.skipBytes(28);
				file.writeDouble(salario);
				
				cont++;
			} 
			
			pos += 36;
			
			if (pos >= file.length()) {
				break;
			}
		}
		
		System.out.println("Empleados actualizados: " + cont);
	}

	private static void actualizarNombreSalario(RandomAccessFile file, int id, String ape, double sal) throws IOException {
		long pos = (id - 1) * 36;
		file.seek(pos);
		
		if (pos > file.length()) {
			System.out.println("No existe ese empleado");
		} else {
			int ident = file.readInt();
			
			if (ident == 0) { //no existe
				System.out.println("No existe ese empleado");
			} else {
				//actualizo el nombre
				StringBuffer buffer = new StringBuffer(ape);
				buffer.setLength(10);
				file.writeChars(buffer.toString());
				
				file.skipBytes(4); // salto los 4 bytes del departamento, es lo mismo que file.readInt()
				
				file.writeDouble(sal);
				System.out.println("Actualizado");
			}
		}
	}

	private static void insertar(RandomAccessFile file, int id, String apellido, int dep, double sal) throws IOException {
		long pos = (id - 1) * 36;
		file.seek(pos);
		
		if (pos > file.length()) { 
			//nuevo reg al final
			file.writeInt(id); //escribo el id
			
			StringBuffer buffer = new StringBuffer(apellido);
			buffer.setLength(10);
			file.writeChars(buffer.toString()); //escribo el apellido por caracteres
			
			file.writeInt(dep); //escibo el departamento
			file.writeDouble(sal); //escribo el salario
		} else {
			//dentro del fichero, puede existir o ser nuevo
			int ident = file.readInt();
			
			if (ident == 0) { 
				//si al leer devuelve 0 es porque esta vacio (esta hueco)
				file.seek(pos);
				file.writeInt(id); //escribo el id
				
				StringBuffer buffer = new StringBuffer(apellido);
				buffer.setLength(10);
				file.writeChars(buffer.toString()); //escribo el apellido por caracteres
				
				file.writeInt(dep); //escibo el departamento
				file.writeDouble(sal); //escribo el salario
			} else {
				//esta ocupado, se borraria o se actualizaria, en este caso le vamos a enseñar los datos que hay y no se va a insertar
				System.out.println("Ya existe el empleado");
				
				consultar(file, ident);
			}
		}
	}

	public static void consultar(RandomAccessFile file, int id) throws IOException {
		int pos = (id - 1) * 36;
		char ape[] = new char[10];
		char aux;
		
		if (pos >= file.length()) {
			System.out.println("ID: "+ id + " no existe");
		} else {
			file.seek(pos);
			file.readInt();
			
			for (int i = 0; i < ape.length; i++) {
				aux = file.readChar();
				ape[i] = aux;
			}
			
			String apellido = new String(ape);
			int dep = file.readInt();
			double sal = file.readDouble();
			
			System.out.println(id + " " + apellido + " " + dep + " " + sal);
		}
	}

}
