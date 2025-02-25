package pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.DevolucionInvalidaException;
import sistema.PrestamoDAO;
import sistema.Sesion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaRegistrarDevolucion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtISBN;
	private PrestamoDAO prestamo = new PrestamoDAO();
	private JTextField txtDNI;

	public VentanaRegistrarDevolucion() {
		Sesion sesion = Sesion.getInstancia(); 
		
		setTitle("Registrar devolución");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Realizar devolución");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Para registrar una devolución");
		lblNewLabel.setBounds(123, 10, 188, 20);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ingrese el ISBN del libro que desea devolver");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(70, 41, 300, 20);
		contentPane.add(lblNewLabel_1);
		
		txtISBN = new JTextField();
		txtISBN.setBounds(123, 72, 188, 20);
		contentPane.add(txtISBN);
		txtISBN.setColumns(10);
		
		JButton btnDevolver = new JButton("Devolver");
		btnDevolver.setBounds(170, 172, 89, 23);
		contentPane.add(btnDevolver);
		btnDevolver.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				if(txtISBN.getText().trim().isEmpty() || txtDNI.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Las casillsas no pueden estar vacías");						
						} else {
							try{					
								Conexion conexionBD = Conexion.getInstancia();
								Connection connection = conexionBD.getConnection();
								int DNI = Integer.parseInt(txtDNI.getText().trim());
								int qPrestamos = sesion.qPrestamosBD(connection, DNI);
								String nivelCuenta = sesion.nivelCuentaBD(connection, DNI);
								
								LocalDate fechaDev = LocalDate.now();						
								java.sql.Date sqlDateDev = java.sql.Date.valueOf(fechaDev);
								
								prestamo.realizarDevolucion(connection, txtISBN.getText() , DNI , qPrestamos , nivelCuenta , sqlDateDev );
								JOptionPane.showMessageDialog(null, "Devolución registrada con éxito");		
								dispose();	
							} catch (DevolucionInvalidaException e1) {
								e1.getMessage();
							} catch (SQLException e3) {
								JOptionPane.showMessageDialog(null, "Algo salio mal");								
							} 
						}
				}});
		
		JButton btnVovler = new JButton("Volver");
		btnVovler.setBounds(170, 206, 89, 23);
		contentPane.add(btnVovler);
		
		JLabel lblNewLabel_1_1 = new JLabel("ingrese el DNI del cliente");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(133, 103, 210, 20);
		contentPane.add(lblNewLabel_1_1);
		
		txtDNI = new JTextField();
		txtDNI.setColumns(10);
		txtDNI.setBounds(123, 134, 188, 20);
		contentPane.add(txtDNI);
		btnVovler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					VentanaMenuEmpleado ventanaMenuEmpleado = new VentanaMenuEmpleado();
					ventanaMenuEmpleado.setVisible(true);	
	                dispose();					
				    
			}
        });
	}
}