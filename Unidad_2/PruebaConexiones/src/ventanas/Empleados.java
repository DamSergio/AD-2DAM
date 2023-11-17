package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import operaciones.OpercionesEmpleados;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Empleados extends JFrame {
	
	private OpercionesEmpleados ope = new OpercionesEmpleados();

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCod;
	private JTextField txtApe;
	private JTextField txtOfi;
	private JTextField txtSal;
	private JTextField txtDir;
	private JTextField txtCodDep;
	private JTextField txtCom;
	private JTextField txtFech;
	private JTextArea txtOutput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Empleados frame = new Empleados();
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
	public Empleados() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 441);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("VENTANA DE EMPLEADOS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 554, 37);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("COD EMPLE");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(86, 73, 121, 23);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("APELLIDO");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(86, 106, 121, 23);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("OFICIO");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_2.setBounds(86, 139, 121, 23);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("SALARIO");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_3.setBounds(86, 172, 121, 23);
		contentPane.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("DIRECTOR");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_4.setBounds(86, 205, 121, 23);
		contentPane.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("COD DEP");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_5.setBounds(86, 238, 121, 23);
		contentPane.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("COMISION");
		lblNewLabel_1_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_6.setBounds(86, 271, 121, 23);
		contentPane.add(lblNewLabel_1_6);
		
		JLabel lblNewLabel_1_7 = new JLabel("FECHA ALTA");
		lblNewLabel_1_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_7.setBounds(86, 304, 121, 23);
		contentPane.add(lblNewLabel_1_7);
		
		txtCod = new JTextField();
		txtCod.setBounds(266, 75, 208, 23);
		contentPane.add(txtCod);
		txtCod.setColumns(10);
		
		txtApe = new JTextField();
		txtApe.setColumns(10);
		txtApe.setBounds(266, 106, 208, 23);
		contentPane.add(txtApe);
		
		txtOfi = new JTextField();
		txtOfi.setColumns(10);
		txtOfi.setBounds(266, 139, 208, 23);
		contentPane.add(txtOfi);
		
		txtSal = new JTextField();
		txtSal.setColumns(10);
		txtSal.setBounds(266, 172, 208, 23);
		contentPane.add(txtSal);
		
		txtDir = new JTextField();
		txtDir.setColumns(10);
		txtDir.setBounds(266, 205, 208, 23);
		contentPane.add(txtDir);
		
		txtCodDep = new JTextField();
		txtCodDep.setColumns(10);
		txtCodDep.setBounds(266, 238, 208, 23);
		contentPane.add(txtCodDep);
		
		txtCom = new JTextField();
		txtCom.setColumns(10);
		txtCom.setBounds(266, 271, 208, 23);
		contentPane.add(txtCom);
		
		txtFech = new JTextField();
		txtFech.setEditable(false);
		txtFech.setColumns(10);
		txtFech.setBounds(266, 304, 208, 23);
		
		Date fechaHoy = new Date();
		txtFech.setText(new SimpleDateFormat("dd/MM/yyyy").format(fechaHoy));
		
		contentPane.add(txtFech);
		
		JButton btnNewButton = new JButton("Insertar");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtOutput.setText("");
				comprobarInsertar();			
			}
		});
		btnNewButton.setBounds(86, 357, 121, 37);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(501, 10, 315, 384);
		contentPane.add(scrollPane);
		
		txtOutput = new JTextArea();
		scrollPane.setViewportView(txtOutput);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "";
				boolean insertar = true;
				
				int cod = 0;
				try {
					cod = Integer.parseInt(txtCod.getText());
				} catch (NumberFormatException ex) {
					msg += "Cod emple debe ser un numero\n";
					insertar = false;
				}
				
				String ape = txtApe.getText();
				if (ape.trim().length() <= 0) {
					msg += "Apellido no puede estar vacio\n";
					insertar = false;
				}
				
				String ofi = txtOfi.getText();
				if (ofi.trim().length() <= 0) {
					msg += "Oficio no puede estar vacio\n";
					insertar = false;
				}
				
				float sal = 0;
				try {
					sal = Float.parseFloat(txtSal.getText());
				} catch (NumberFormatException ex) {
					msg += "Salario debe ser un numero\n";
					insertar = false;
				}
				
				int dir = 0;
				try {
					dir = Integer.parseInt(txtDir.getText());
				} catch (NumberFormatException ex) {
					msg += "Director debe ser un numero\n";
					insertar = false;
				}
				
				int codDep = 0;
				try {
					codDep = Integer.parseInt(txtCodDep.getText());
				} catch (NumberFormatException ex) {
					msg += "Cod dep debe ser un numero\n";
					insertar = false;
				}
				
				float comision = 0;
				try {
					comision = Float.parseFloat(txtCom.getText());
				} catch (NumberFormatException ex) {
					//msg += "Comision debe ser un numero\n";
					comision = 0;
					txtCom.setText("0");
					insertar = false;
				}
				
				if (insertar) {
					msg = ope.actualizarEmple(cod, ape, ofi, sal, dir, codDep, comision, txtFech.getText());
					txtOutput.append("\n------------------------");
					txtOutput.append("\n" + msg);
					txtOutput.append("------------------------");
				} else {
					txtOutput.append("\n------------------------");
					txtOutput.append("\n" + msg);
					txtOutput.append("------------------------");
				}
			}
		});
		btnActualizar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnActualizar.setBounds(233, 357, 121, 37);
		contentPane.add(btnActualizar);
	}
	
	public String comprobarInsertar() {
		String msg = "";
		boolean insertar = true;
		
		int cod = 0;
		try {
			cod = Integer.parseInt(txtCod.getText());
		} catch (NumberFormatException ex) {
			msg += "Cod emple debe ser un numero\n";
			insertar = false;
		}
		
		String ape = txtApe.getText();
		if (ape.trim().length() <= 0) {
			msg += "Apellido no puede estar vacio\n";
			insertar = false;
		}
		
		String ofi = txtOfi.getText();
		if (ofi.trim().length() <= 0) {
			msg += "Oficio no puede estar vacio\n";
			insertar = false;
		}
		
		float sal = 0;
		try {
			sal = Float.parseFloat(txtSal.getText());
		} catch (NumberFormatException ex) {
			msg += "Salario debe ser un numero\n";
			insertar = false;
		}
		
		int dir = 0;
		try {
			dir = Integer.parseInt(txtDir.getText());
		} catch (NumberFormatException ex) {
			msg += "Director debe ser un numero\n";
			insertar = false;
		}
		
		int codDep = 0;
		try {
			codDep = Integer.parseInt(txtCodDep.getText());
		} catch (NumberFormatException ex) {
			msg += "Cod dep debe ser un numero\n";
			insertar = false;
		}
		
		float comision = 0;
		try {
			comision = Float.parseFloat(txtCom.getText());
		} catch (NumberFormatException ex) {
			//msg += "Comision debe ser un numero\n";
			comision = 0;
			txtCom.setText("0");
			insertar = false;
		}
		
		if (insertar) {
			msg = ope.insertarEmple(cod, ape, ofi, sal, dir, codDep, comision, txtFech.getText());
			txtOutput.append("\n------------------------");
			txtOutput.append("\n" + msg);
			txtOutput.append("------------------------");
		} else {
			txtOutput.append("\n------------------------");
			txtOutput.append("\n" + msg);
			txtOutput.append("------------------------");
		}

		return msg;
	}
}
