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

public class VentanaRealizarDevolucion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtISBN;
	private PrestamoDAO prestamo = new PrestamoDAO();

	public VentanaRealizarDevolucion() {
		Sesion sesion = Sesion.getInstancia(); ; 
		
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
		txtISBN.setBounds(123, 83, 188, 20);
		contentPane.add(txtISBN);
		txtISBN.setColumns(10);
		
		JButton btnDevolver = new JButton("Devolver");
		btnDevolver.setBounds(170, 128, 89, 23);
		contentPane.add(btnDevolver);
		btnDevolver.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				if(txtISBN.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "La casilla no puede estar vacía");						
						} else {
							try{					
								Conexion conexionBD = Conexion.getInstancia();
								Connection connection = conexionBD.getConnection();
								
								int qPrestamos = sesion.qPrestamosBD(connection, sesion.getDNI());
								String nivelCuenta = sesion.nivelCuentaBD(connection, sesion.getDNI());
								
								LocalDate fechaDev = LocalDate.now();						
								java.sql.Date sqlDateDev = java.sql.Date.valueOf(fechaDev);
								
								prestamo.realizarDevolucion(connection, txtISBN.getText() , sesion.getDNI() , qPrestamos , nivelCuenta , sqlDateDev );							
							} 	catch (DevolucionInvalidaException e1) {
								JOptionPane.showMessageDialog(null, "Usted no posee un prestamo sobre este libro actualmente, revise el ISBN");	
							} 	catch (SQLException e3) {
								JOptionPane.showMessageDialog(null, "Algo salio mal...");		
							} 
						}
				}});
		
		JButton btnVovler = new JButton("Volver");
		btnVovler.setBounds(170, 206, 89, 23);
		contentPane.add(btnVovler);
		btnVovler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	VentanaMenuCliente ventanaMenuCliente = new VentanaMenuCliente();
            	ventanaMenuCliente.setVisible(true);            	
                dispose();  
            }
        });
	}
}
