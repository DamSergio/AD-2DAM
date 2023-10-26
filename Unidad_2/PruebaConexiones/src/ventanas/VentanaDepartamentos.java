package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;

import operaciones.OperacionesDepartamentos;

public class VentanaDepartamentos extends JFrame {
	
	private OperacionesDepartamentos ope = new OperacionesDepartamentos();

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCod;
	private JTextField txtName;
	private JTextField txtLoc;
	private JTextArea txtOutput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaDepartamentos frame = new VentanaDepartamentos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaDepartamentos() {
		setTitle("Operaciones Departamentos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 521);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Insertar Departamento");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 10, 203, 36);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Cod departamento:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(10, 56, 181, 36);
		contentPane.add(lblNewLabel_1);
		
		txtCod = new JTextField();
		txtCod.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCod.setBounds(201, 56, 414, 36);
		contentPane.add(txtCod);
		txtCod.setColumns(10);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtName.setColumns(10);
		txtName.setBounds(201, 102, 414, 36);
		contentPane.add(txtName);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nombre departamento:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(10, 102, 181, 36);
		contentPane.add(lblNewLabel_1_1);
		
		txtLoc = new JTextField();
		txtLoc.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtLoc.setColumns(10);
		txtLoc.setBounds(201, 148, 414, 36);
		contentPane.add(txtLoc);
		
		JLabel lblNewLabel_1_2 = new JLabel("Localidad departamento:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_2.setBounds(10, 148, 181, 36);
		contentPane.add(lblNewLabel_1_2);
		
		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtOutput.setText("");
				try {
					int cod = Integer.parseInt(txtCod.getText());
					String name = txtName.getText();
					String loc = txtLoc.getText();
					
					String output = ope.insertDep(cod, name, loc);
					txtOutput.append("\n--------------------------------------------------");
					txtOutput.append("\n" + output);
					txtOutput.append("\n--------------------------------------------------");
				} catch (NumberFormatException ex) {
					txtOutput.append("\n--------------------------------------------------");
					txtOutput.append("\nEl codigo debe ser numerico");
					txtOutput.append("\n--------------------------------------------------");
				}
			}
		});
		btnInsert.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnInsert.setBounds(10, 210, 137, 36);
		contentPane.add(btnInsert);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 256, 816, 2);
		contentPane.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 268, 816, 206);
		contentPane.add(scrollPane);
		
		txtOutput = new JTextArea();
		txtOutput.setFont(new Font("Consolas", Font.PLAIN, 13));
		txtOutput.setEditable(false);
		scrollPane.setViewportView(txtOutput);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtOutput.setText("");
				try {
					int cod = Integer.parseInt(txtCod.getText());
					
					String output = ope.consultDep(cod, txtName, txtLoc);
					txtOutput.append("\n--------------------------------------------------");
					txtOutput.append("\n" + output);
					txtOutput.append("\n--------------------------------------------------");
				} catch (NumberFormatException ex) {
					txtOutput.append("\n--------------------------------------------------");
					txtOutput.append("\nEl codigo debe ser numerico");
					txtOutput.append("\n--------------------------------------------------");
				}
			}
		});
		btnConsultar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnConsultar.setBounds(185, 210, 137, 36);
		contentPane.add(btnConsultar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtOutput.setText("");
				try {
					int cod = Integer.parseInt(txtCod.getText());
					
					String output = ope.DeleteDep(cod);
					txtOutput.append("\n--------------------------------------------------");
					txtOutput.append("\n" + output);
					txtOutput.append("\n--------------------------------------------------");
				} catch (NumberFormatException ex) {
					txtOutput.append("\n--------------------------------------------------");
					txtOutput.append("\nEl codigo debe ser numerico");
					txtOutput.append("\n--------------------------------------------------");
				}
			}
		});
		btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnBorrar.setBounds(353, 210, 137, 36);
		contentPane.add(btnBorrar);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtOutput.setText("");
				try {
					int cod = Integer.parseInt(txtCod.getText());
					String name = txtName.getText();
					String loc = txtLoc.getText();
					
					String output = ope.updateDep(cod, name, loc);
					txtOutput.append("\n--------------------------------------------------");
					txtOutput.append("\n" + output);
					txtOutput.append("\n--------------------------------------------------");
				} catch (NumberFormatException ex) {
					txtOutput.append("\n--------------------------------------------------");
					txtOutput.append("\nEl codigo debe ser numerico");
					txtOutput.append("\n--------------------------------------------------");
				}
			}
		});
		btnActualizar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnActualizar.setBounds(516, 210, 137, 36);
		contentPane.add(btnActualizar);
		
		JButton btnVerEmpleados = new JButton("Ver empleados");
		btnVerEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int cod = Integer.parseInt(txtCod.getText());
					
					ope.listarEmple(cod, txtOutput);
				} catch (NumberFormatException ex) {
					txtOutput.append("\n--------------------------------------------------");
					txtOutput.append("\nEl codigo debe ser numerico");
					txtOutput.append("\n--------------------------------------------------");
				}
			}
		});
		btnVerEmpleados.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnVerEmpleados.setBounds(689, 210, 137, 36);
		contentPane.add(btnVerEmpleados);
		
		JButton btnColumnas = new JButton("Columnas");
		btnColumnas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = ope.actualizarColumnas();
				
				txtOutput.append("\n--------------------------------------------------");
				txtOutput.append("\n" + msg);
				txtOutput.append("\n--------------------------------------------------");
			}
		});
		btnColumnas.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnColumnas.setBounds(689, 157, 137, 36);
		contentPane.add(btnColumnas);
		
		JButton btnBorrarColumnas = new JButton("Borrar Columnas");
		btnBorrarColumnas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = ope.borrarColumnas();
				
				txtOutput.append("\n--------------------------------------------------");
				txtOutput.append("\n" + msg);
				txtOutput.append("\n--------------------------------------------------");
			}
		});
		btnBorrarColumnas.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnBorrarColumnas.setBounds(663, 111, 163, 36);
		contentPane.add(btnBorrarColumnas);
		
		JButton btnNewButton = new JButton("Tablas");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal frame = new VentanaPrincipal();
				frame.setVisible(true);
			}
		});
		btnNewButton.setBounds(655, 65, 171, 21);
		contentPane.add(btnNewButton);
	}
}
