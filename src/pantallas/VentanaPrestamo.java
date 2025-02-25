package pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.RegistroInvalidoException;
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

public class VentanaPrestamo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtISBN;
	private PrestamoDAO prestamo = new PrestamoDAO();

	public VentanaPrestamo() {
		Sesion sesion = Sesion.getInstancia();
		String tipoUsuario = sesion.getTipoUsuario();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Prestamo");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ingrese el ISBN del libro que desea retirar");
		lblNewLabel.setBounds(82, 37, 272, 19);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel);
		
		txtISBN = new JTextField();
		txtISBN.setBounds(92, 67, 240, 19);
		contentPane.add(txtISBN);
		txtISBN.setColumns(10);
		
		JButton btnSolicitar = new JButton("Solicitar");
		btnSolicitar.setBounds(166, 97, 89, 23);
		contentPane.add(btnSolicitar);
		btnSolicitar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				try{		
					String ISBN = txtISBN.getText().trim();
					
					Conexion conexionBD = Conexion.getInstancia();
					Connection connection = conexionBD.getConnection();
					
					LocalDate fechaDev = LocalDate.now();						
					java.sql.Date sqlDateDev = java.sql.Date.valueOf(fechaDev);				
				
					prestamo.realizarPrestamo(connection, ISBN, sesion.getDNI(), sesion.qPrestamosBD(connection, sesion.getDNI()), sesion.nivelCuentaBD(connection, sesion.getDNI()), sqlDateDev);
					if(tipoUsuario.equals("cliente")) {
					VentanaMenuCliente ventanaMenuCliente = new VentanaMenuCliente();
					ventanaMenuCliente.setVisible(true);	
	                dispose();					
					} else {
					VentanaMenuEmpleado ventanaMenuEmpleado = new VentanaMenuEmpleado();
					ventanaMenuEmpleado.setVisible(true);	
	                dispose();
					}			
				} catch (RegistroInvalidoException e1) {
					JOptionPane.showMessageDialog(null, "El libro busacdo no existe");
				}catch	(SQLException e2) {
					JOptionPane.showMessageDialog(null, "Algo salio mal...");
				};
			}
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(166, 227, 89, 23);
		contentPane.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				if(tipoUsuario.equals("cliente")) {
					VentanaMenuCliente ventanaMenuCliente = new VentanaMenuCliente();
					ventanaMenuCliente.setVisible(true);	
	                dispose();					
				} else {
					VentanaMenuEmpleado ventanaMenuEmpleado = new VentanaMenuEmpleado();
					ventanaMenuEmpleado.setVisible(true);	
	                dispose();
				}   
			}
		});
	}
}