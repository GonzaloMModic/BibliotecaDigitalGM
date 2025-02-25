package pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import personas.UsuarioDAO;
import sistema.Sesion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaModificarDatos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtPass;
	private JTextField txtLvlCuenta;
	private JTextField txtEmail;
	private JTextField txtDNI;
	Sesion sesion = Sesion.getInstancia();
	private UsuarioDAO userModif = new UsuarioDAO();
	private JTextField txtCargo;	
	
	public VentanaModificarDatos() {
		String tipoUsuario = sesion.getTipoUsuario();		
		String nombreUser = sesion.getNombre();
		String EmailUser = sesion.getEmail();
		int DNIUser = sesion.getDNI();
		String passUser = sesion.getPass();			
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Modificar datos");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbluserPanel = new JLabel("Panel de usuario");
		lbluserPanel.setBounds(143, 10, 148, 25);
		lbluserPanel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lbluserPanel);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(30, 35, 150, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEmail.setBounds(225, 35, 150, 25);
		contentPane.add(lblEmail);
		
		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblContrasea.setBounds(30, 95, 150, 25);
		contentPane.add(lblContrasea);
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDni.setBounds(225, 95, 150, 25);
		contentPane.add(lblDni);		
		
		txtNombre = new JTextField(nombreUser);
		txtNombre.setBounds(30, 60, 148, 26);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtPass = new JTextField(passUser);
		txtPass.setColumns(10);
		txtPass.setBounds(30, 115, 148, 26);
		contentPane.add(txtPass);
		
		txtEmail = new JTextField(EmailUser);
		txtEmail.setColumns(10);
		txtEmail.setBounds(225, 60, 185, 26);
		contentPane.add(txtEmail);
		
		txtDNI = new JTextField(String.valueOf(DNIUser));
		txtDNI.setColumns(10);
		txtDNI.setBounds(227, 115, 185, 26);
		txtDNI.setEditable(false);
		contentPane.add(txtDNI);
		
		if(tipoUsuario.equals("cliente")) {
		JLabel lblNivelDeCuenta = new JLabel("Nivel de cuenta");
		lblNivelDeCuenta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNivelDeCuenta.setBounds(30, 145, 150, 25);
		contentPane.add(lblNivelDeCuenta);
		
		try {
			Conexion conexionBD = Conexion.getInstancia();
			Connection connection = conexionBD.getConnection();

			String lvlCuenta = sesion.nivelCuentaBD(connection, DNIUser);			
			
			txtLvlCuenta = new JTextField(lvlCuenta); 
			txtLvlCuenta.setColumns(10);
			txtLvlCuenta.setBounds(30, 170, 150, 25);
			txtLvlCuenta.setEditable(false);
			contentPane.add(txtLvlCuenta);				
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Algo salio mal...");
		}		
		} else {
		
		JLabel nroLegajo = new JLabel("N° Legajo");
		nroLegajo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nroLegajo.setBounds(30, 145, 150, 25);
		contentPane.add(nroLegajo);	
		
		try {
			Conexion conexionBD = Conexion.getInstancia();
			Connection connection = conexionBD.getConnection();
			
			int legajo = sesion.traerNroLegajoBD(connection, DNIUser);
			String cargo = sesion.traerCargoBD(connection, DNIUser);
			System.out.println(cargo);
		
		txtLvlCuenta = new JTextField(String.valueOf(legajo)); 
		txtLvlCuenta.setColumns(10);
		txtLvlCuenta.setBounds(30, 170, 150, 25);
		txtLvlCuenta.setEditable(false);
		contentPane.add(txtLvlCuenta);
		
		txtCargo = new JTextField(String.valueOf(cargo));
		txtCargo.setEditable(false);
		txtCargo.setColumns(10);
		txtCargo.setBounds(130, 200, 150, 25);
		contentPane.add(txtCargo);
		
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Algo salio mal...");
				}
		}
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(225, 170, 90, 25);
		contentPane.add(btnEditar);
		
		btnEditar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
					
					try{											
						Conexion conexionBD = Conexion.getInstancia();
						Connection connection = conexionBD.getConnection();
						
			            String nuevoNombre = txtNombre.getText().isEmpty() ? sesion.getNombre() : txtNombre.getText();
			            String nuevoEmail = txtEmail.getText().isEmpty() ? sesion.getEmail() : txtEmail.getText();
			            String nuevaPass = txtPass.getText().isEmpty() ? sesion.getPass() : txtPass.getText();	
			            sesion.setDNI(DNIUser);
			            sesion.setNombre(nuevoNombre);
			            sesion.setEmail(nuevoEmail);
			            sesion.setPass(nuevaPass);		         
	             
						userModif.actualizarDatosUsuario(connection, DNIUser, nombreUser, EmailUser, passUser,  nuevoNombre, nuevoEmail, nuevaPass);
						JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");
						
					} catch (SQLException e2) {
						JOptionPane.showMessageDialog(null, "Algo Salio mal.");
					};
				}
			}
		);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(320, 170, 90, 25);
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
		
		if(tipoUsuario.equals("cliente")) {
		JLabel lblNewLabel_1 = new JLabel("Aclaracion: El nivel de cuenta solo puede ser modificado por un empleado");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1.setToolTipText("");
		lblNewLabel_1.setBounds(31, 236, 393, 14);
		contentPane.add(lblNewLabel_1);
		} else {
			JLabel lblNewLabel_1 = new JLabel("Aclaracion: El nro de legajo y cargo no pueden ser modificados");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
			lblNewLabel_1.setToolTipText("");
			lblNewLabel_1.setBounds(31, 236, 393, 14);
			contentPane.add(lblNewLabel_1);
		}
	}
}