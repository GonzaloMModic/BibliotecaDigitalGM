package pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.RegistroInvalidoException;
import personas.UsuarioDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaModifNivelCuenta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDNICliente;
	private UsuarioDAO user = new UsuarioDAO();

	public VentanaModifNivelCuenta() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Modificar Nivel de cuenta de Cliente");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Para modificar el nivel de cuenta de un usuario");
		lblNewLabel.setBounds(65, 10, 310, 20);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ingrese el DNI del CLIENTE");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(119, 41, 220, 20);
		contentPane.add(lblNewLabel_1);
		
		txtDNICliente = new JTextField();
		txtDNICliente.setBounds(130, 72, 170, 20);
		contentPane.add(txtDNICliente);
		txtDNICliente.setColumns(10);
		
		JButton btnMejorar = new JButton("Modificar");
		btnMejorar.setBounds(173, 103, 90, 25);
		contentPane.add(btnMejorar);
		btnMejorar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		                try {
		                	Conexion conexionBD = Conexion.getInstancia();
							Connection connection = conexionBD.getConnection();
							int DNI = Integer.parseInt(txtDNICliente.getText().trim());
							int respuesta = JOptionPane.showConfirmDialog(
						            null,                               
						            "¿Está seguro de que desea modificar el nivel de cuenta del usuario " + DNI + " ?", 
						            "Confirmar modificación",                 
						            JOptionPane.YES_NO_OPTION,           
						            JOptionPane.QUESTION_MESSAGE       
						        );

							if(respuesta == JOptionPane.YES_OPTION) {
								user.actualizarNivelCuentaCliente(connection, DNI);
								JOptionPane.showMessageDialog(null, "Usuario modificado correctamente");
								VentanaMenuEmpleado ventanaMenuEmpleado = new VentanaMenuEmpleado();
								ventanaMenuEmpleado.setVisible(true);								
								dispose();
							}						
		                } catch (SQLException e1) {
		                	JOptionPane.showMessageDialog(null, "Algo salio mal...");
		                } catch (RegistroInvalidoException e1) {
							e1.getMessage();
						}
				}
			});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(173, 195, 90, 25);
		contentPane.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			VentanaMenuEmpleado ventanaMenuEmpleado = new VentanaMenuEmpleado();
			ventanaMenuEmpleado.setVisible(true);
		    dispose();				
		}
		});
	}
}
