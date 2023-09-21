package departamentos;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio1 {
	static int LON = 72;
	static int DEP_LON = 4;
	static int NAME_LON = 30;
	static int LOC_LON = 30;
	static int EMP_LON = 4;
	static int SALARI_LON = 4;

	public static void main(String[] args) {
		File f = new File(".\\Ejercicio1.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(f, "rw");
			
			//72bytes por registro
			System.out.println(insertarRegistro(file, 1, "Dept1", "Talavera", 0, 0.0f));//dep int 4, nom str 30, loc str 30, num emple int 4, sal float 4
			System.out.println(insertarRegistro(file, 2, "Dept2", "Madrid", 4, 1000.0f));
			System.out.println(insertarRegistro(file, 3, "Dept3", "Toledo", 1, 1200.0f));
			System.out.println(insertarRegistro(file, 4, "Dept4", "Talavera", 3, 1300.0f));
			System.out.println(insertarRegistro(file, 10, "Dept4", "Talavera", 3, 1300.0f));
			System.out.println(insertarRegistro(file, 25, "Dept4", "Talavera", 3, 1300.0f));
			
			listarRegistros(file);
			
			visualizarRegistro(file, 25);
			visualizarRegistro(file, 15);
			
			System.out.println(modificarRegistro(file, 25, "Madrid", 1500.0f));
			System.out.println(modificarRegistro(file, 11, "Madrid", 1500.0f));
			visualizarRegistro(file, 25);
			
			System.out.println(borrarRegistro(file, 4));
			System.out.println(borrarRegistro(file, 5));
			visualizarRegistro(file, 4);
			
			listarRegistros(file);
			
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String borrarRegistro(RandomAccessFile file, int dep) throws IOException {
		if (!consultarRegistro(file, dep)) {
			return "Registro no borrado. No existe " + dep;
		}
		
		int pos = (dep - 1) * LON;
		file.seek(pos);
		file.writeInt(0);
		
		StringBuffer nom = new StringBuffer("");
		nom.setLength(NAME_LON / 2);
		file.writeChars(nom.toString());
		
		StringBuffer loc = new StringBuffer("");
		loc.setLength(LOC_LON / 2);
		file.writeChars(loc.toString());
		
		file.writeInt(0);
		file.writeFloat(0);
		
		return "Borrado";
	}

	private static String modificarRegistro(RandomAccessFile file, int dep, String localidad, float mediaSalario) throws IOException {
		if (!consultarRegistro(file, dep)) {
			return "Registro no modificado. No existe " + dep;
		}
		
		int pos = (dep - 1) * LON;
		file.seek(pos + DEP_LON + NAME_LON);
		
		StringBuffer loc = new StringBuffer(localidad);
		loc.setLength(LOC_LON / 2);
		file.writeChars(loc.toString());
		
		file.skipBytes(EMP_LON);
		file.writeFloat(mediaSalario);
		
		return "Registro actualizado: " + dep;
	}

	private static void visualizarRegistro(RandomAccessFile file, int dep) throws IOException {
		if (!consultarRegistro(file, dep)) {
			System.out.println("El departamento " + dep + " no existe");
			return;
		}
		
		int pos = (dep - 1) * LON;
		file.seek(pos);
		file.readInt();
		
		String nombre = "";
		for (int i = 0; i < 15; i++) {
			nombre += file.readChar();
		}
		
		String localidad = "";
		for (int i = 0; i < 15; i++) {
			localidad += file.readChar();
		}
		
		int numEmple = file.readInt();
		float mediaSalario = file.readFloat();
		
		System.out.println("Dep: " + dep + " nombre: " + nombre + " localidad: " + localidad + " numero empleados: " + numEmple + " media salario: " + mediaSalario);
	}

	private static void listarRegistros(RandomAccessFile file) throws IOException {
		int pos = 0;
		
		for (;;) {
			file.seek(pos);
			int dep = file.readInt();
			
			if (dep == 0) {
				pos += LON;
				continue;
			}
			
			String nombre = "";
			for (int i = 0; i < 15; i++) {
				nombre += file.readChar();
			}
			
			String localidad = "";
			for (int i = 0; i < 15; i++) {
				localidad += file.readChar();
			}
			
			int numEmple = file.readInt();
			float mediaSalario = file.readFloat();
			
			System.out.println("Dep: " + dep + " nombre: " + nombre + " localidad: " + localidad + " numero empleados: " + numEmple + " media salario: " + mediaSalario);
			
			pos += LON;
			
			if (pos >= file.length()) {
				break;
			}
		}
		
	}

	private static String insertarRegistro(RandomAccessFile file, int dep, String nombre, String localidad, int numEmp, float sal) throws IOException {
		if (dep < 1 || dep > 100) {
			return "El departamento es erroneo";
		}
		
		if (consultarRegistro(file, dep)) {
			return "El registro ya existe";
		}
		
		int pos = (dep - 1) * LON;
		file.seek(pos);
		file.writeInt(dep);
		
		StringBuffer nom = new StringBuffer(nombre);
		nom.setLength(NAME_LON / 2);
		file.writeChars(nom.toString());
		
		StringBuffer loc = new StringBuffer(localidad);
		loc.setLength(LOC_LON / 2);
		file.writeChars(loc.toString());
		
		file.writeInt(numEmp);
		file.writeFloat(sal);
		
		return "Registro insertado. Dep: " + dep + " nombre: " + nombre + " localidad: " + localidad + " numero empleados: " + numEmp + " media salario: " + sal;
	}
	
	private static boolean consultarRegistro(RandomAccessFile file, int dep) throws IOException {
		int pos = (dep - 1) * LON;
		if (pos >= file.length()) {
			return false;
		}
		
		file.seek(pos);
		int id = file.readInt();
		if (id == dep) {
			return true;
		}
	
		return false;
	}

}
