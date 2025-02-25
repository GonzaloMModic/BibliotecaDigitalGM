package pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.DniDuplicadoException;
import excepciones.EmailDuplicadoException;
import excepciones.RegistroInvalidoException;
import personas.ClienteDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class VentanaRegistroCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombreReg;
	private JPasswordField txtPassReg;
	private JTextField txtDNIReg;
	private JTextField txtEmailReg;
	private ClienteDAO clienteReg = new ClienteDAO();

	public VentanaRegistroCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Registro clientes");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRClientes = new JLabel("Registro para clientes");
		lblRClientes.setBounds(117, 10, 199, 25);
		lblRClientes.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblRClientes);
		
		JLabel lblNewLabel_1 = new JLabel("Ingrese su nombre:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(20, 46, 150, 15);
		contentPane.add(lblNewLabel_1);
		
		txtNombreReg = new JTextField();
		txtNombreReg.setBounds(20, 72, 150, 20);
		contentPane.add(txtNombreReg);
		txtNombreReg.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Ingrese su DNI:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(240, 46, 150, 15);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Ingrese su email:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1_1.setBounds(20, 109, 150, 15);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Ingrese su contraseña:");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1_1_1.setBounds(240, 109, 150, 15);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		txtPassReg = new JPasswordField();
		txtPassReg.setBounds(240, 135, 150, 20);
		contentPane.add(txtPassReg);
		
		txtDNIReg = new JTextField();
		txtDNIReg.setColumns(10);
		txtDNIReg.setBounds(240, 70, 150, 20);
		contentPane.add(txtDNIReg);
		
		txtEmailReg = new JTextField();
		txtEmailReg.setColumns(10);
		txtEmailReg.setBounds(20, 135, 150, 20);
		contentPane.add(txtEmailReg);
		
		JButton btnRegistrarCliente = new JButton("Registrar");
		btnRegistrarCliente.setBounds(170, 184, 89, 23);
		contentPane.add(btnRegistrarCliente);
		btnRegistrarCliente.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(txtNombreReg.getText().equals("")|| txtEmailReg.getText().equals("") || txtDNIReg.getText().equals("")|| txtPassReg.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "No puede haber campos vacios");
			}
			else {
				String nombreReg = txtNombreReg.getText();
				String DNICheck = txtDNIReg.getText();
				int DNIReg;
				String emailReg = txtEmailReg.getText();
				String passReg = txtPassReg.getText();
				if(DNICheck.matches("\\d+")) {					
					try{					
						Conexion conexionBD = Conexion.getInstancia();
						Connection connection = conexionBD.getConnection();
						DNIReg = Integer.parseInt(txtDNIReg.getText());
					clienteReg.registrarCliente(connection, nombreReg, DNIReg, emailReg, passReg);
					}  catch (NumberFormatException e2) {
						e2.printStackTrace();
					}catch (EmailDuplicadoException | DniDuplicadoException e2) {
					    JOptionPane.showMessageDialog(null, "Email o DNI ya registrado, no se puede registrar el usuario.");
					    return;
					} catch (SQLException | RegistroInvalidoException e2) {
						JOptionPane.showMessageDialog(null, "Algo Salio mal.");
						return;
					} ;
					JOptionPane.showMessageDialog(null, "Registro completado con éxito... Volviendo al menu inicial");
					IniciarBibliotecaDigital ventanaMenuPrincipal = new IniciarBibliotecaDigital();
					ventanaMenuPrincipal.setVisible(true);
					dispose();
				} else {					
					    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para el DNI.");
					}
				}
			}
		});
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