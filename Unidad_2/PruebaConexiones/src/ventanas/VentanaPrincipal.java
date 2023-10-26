package ventanas;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pruebaConexiones.Conexiones;

public class VentanaPrincipal extends JFrame {
	
	private DefaultTableModel modelo;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	
	private Conexiones conn = new Conexiones();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VentanaPrincipal frame = new VentanaPrincipal();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 569, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnVerEmple = new JButton("Ver empleados");
		btnVerEmple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				llenarJtable("empleados");
			}
		});
		btnVerEmple.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnVerEmple.setBounds(10, 10, 116, 44);
		contentPane.add(btnVerEmple);
		
		JButton btnVerDepar = new JButton("Ver departamentos");
		btnVerDepar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				llenarJtable("departamentos");
			}
		});
		btnVerDepar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnVerDepar.setBounds(400, 10, 145, 44);
		contentPane.add(btnVerDepar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 97, 535, 272);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"New column1", "New column2", "New column3", "New column4"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnTableModel = new JButton("Ver modelo");
		btnTableModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnTableModel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnTableModel.setBounds(213, 10, 116, 44);
		contentPane.add(btnTableModel);
		
		
	}
	
	private void llenarJtable(String tabla) {
		
		try {
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conexion = conn.getOracle("ej", "1234");
			Statement sentencia = conexion.createStatement();
			String consulta = "SELECT *  FROM " + tabla;
			ResultSet resul = sentencia.executeQuery(consulta);

			ResultSetMetaData rsmd = resul.getMetaData();
			// Número de columnas
			int nColumnas = rsmd.getColumnCount();
			
			// Número de filas
			String consulta2 = "select count(*) from  " + tabla;
			
			Statement sentencia2 = conexion.createStatement();
			ResultSet resul2 = sentencia2.executeQuery(consulta2);
			resul2.next();
			int filas = resul2.getInt(1);
			resul2.close();
			sentencia2.close();
			
			// Se obtiene cada una de las etiquetas para cada columna
			String[] etiquetas = new String[nColumnas];
			for (int i = 1; i <= nColumnas; i++) {
				rsmd.getColumnName(i);
				System.out.println("Añado la columna " + rsmd.getColumnName(i).toUpperCase());
				etiquetas[i - 1] = rsmd.getColumnName(i).toUpperCase();
			}
			System.out.println("Filas: " + filas + ", columnas: " + nColumnas);

			// Recorremos el resul para cargar las filas de la consulta al array 
		        //bidimensional de objetos
			int numeroFila = 0;
			Object[][] datos = new Object[filas][nColumnas];
			resul = sentencia.executeQuery(consulta);
			while (resul.next()) {
			   //Bucle para cada fila, añadir las columnas 
		         for (int i = 0; i < nColumnas; i++) {
					datos[numeroFila][i] = resul.getObject(i + 1);
					System.out.println("Añado la columna " + i + ", datos " + 
		                      resul.getString(i + 1));
				}
			   numeroFila++;
			}

			// Asignamos los datos al modelo
			modelo = new DefaultTableModel(datos, etiquetas);
			modelo.setColumnIdentifiers(etiquetas); //esto puede sobrar
			modelo.setDataVector(datos, etiquetas); //esto puede sobrar
			
		       // Asignamos el modelo a la tabla
			table.setModel(modelo);
			Color fg = Color.PINK;
			table.setBackground(fg);
			table.setForeground(Color.BLUE);

			resul.close();
			conexion.close();
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	

}
