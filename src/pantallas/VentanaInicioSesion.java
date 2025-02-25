package pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.RegistroInvalidoException;
import sistema.Sesion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class VentanaInicioSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmailInicio;
	private JPasswordField passwordField;
	private JPasswordField txtPassInicio;	

	public VentanaInicioSesion() {		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Inicio sesion");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Inicio de sesion");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel.setBounds(142, 11, 145, 52);
		contentPane.add(lblNewLabel);
		
		JLabel lbEmailInicio = new JLabel("Ingrese su email:");
		lbEmailInicio.setVerticalAlignment(SwingConstants.BOTTOM);
		lbEmailInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbEmailInicio.setBounds(87, 59, 250, 25);
		contentPane.add(lbEmailInicio);
		
		txtEmailInicio = new JTextField();
		txtEmailInicio.setBounds(87, 89, 250, 25);
		contentPane.add(txtEmailInicio);
		txtEmailInicio.setColumns(10);
		
		JLabel lbPassInicio = new JLabel("Ingrese su contraseña:");
		lbPassInicio.setVerticalAlignment(SwingConstants.BOTTOM);
		lbPassInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbPassInicio.setBounds(87, 125, 250, 25);
		contentPane.add(lbPassInicio);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(280, 197, -161, 20);
		contentPane.add(passwordField);
		
		txtPassInicio = new JPasswordField();
		txtPassInicio.setBounds(87, 161, 250, 25);
		contentPane.add(txtPassInicio);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtEmailInicio.getText().equals("") || txtPassInicio.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "No puede haber campos vacios");					
				}else {
					try{
						Conexion conexionBD = Conexion.getInstancia();
						Connection connection = conexionBD.getConnection();					
					try {
						Sesion sesion = Sesion.getInstancia();
						sesion = sesion.iniciarSesion(connection, txtEmailInicio.getText(), txtPassInicio.getText());
						String tipoUsuario = sesion.getTipoUsuario();						
						
						if ("cliente".equals(tipoUsuario)) {   							
							JOptionPane.showMessageDialog(null, "Ingreso como cliente correctamente");
							VentanaMenuCliente ventanaMenuCLiente = new VentanaMenuCliente();
							ventanaMenuCLiente.setVisible(true);	
							dispose();
					    } 	
					    	else if ("empleado".equals(tipoUsuario)) {  
						    JOptionPane.showMessageDialog(null, "Ingreso como empleado correctamente");
							VentanaMenuEmpleado ventanaMenuEmpleado = new VentanaMenuEmpleado();
							ventanaMenuEmpleado.setVisible(true);
							dispose();						    
					}	else {
						JOptionPane.showMessageDialog(null, "No se encontró un usuario que posea ese email y esa contraseña, vuelva a intentarlo");
						}
					} catch (SQLException | RegistroInvalidoException e1) {
						JOptionPane.showMessageDialog(null, "Registro Inválido");
					}						
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, "Algo salio mal...");
				};
			}
		}});
		btnIngresar.setBounds(142, 197, 128, 23);
		contentPane.add(btnIngresar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(335, 227, 89, 23);
		contentPane.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	                IniciarBibliotecaDigital pantallaInicial = new IniciarBibliotecaDigital();
	                pantallaInicial.setVisible(true);	
	                dispose();
			}
		});
	} 
}