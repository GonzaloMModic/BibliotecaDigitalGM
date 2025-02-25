package pantallas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.DniDuplicadoException;
import excepciones.EmailDuplicadoException;
import excepciones.NroLegajoDuplicadoException;
import excepciones.RegistroInvalidoException;
import personas.EmpleadoDAO;

public class VentanaRegistroEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombreReg;
	private JPasswordField txtPassReg;
	private JTextField txtDNIReg;
	private JTextField txtEmailReg;
	private EmpleadoDAO empleadoReg = new EmpleadoDAO();
	private JTextField txtNroLegajo;
	private JTextField txtCargo;

	public VentanaRegistroEmpleado() { //Cargo lo trae null
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Registro empleados");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRClientes = new JLabel("Registro para empleados");
		lblRClientes.setBounds(90, 10, 270, 25);
		lblRClientes.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblRClientes);
		
		JLabel lblNewLabel_1 = new JLabel("Ingrese su nombre:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(20, 46, 150, 15);
		contentPane.add(lblNewLabel_1);
		
		txtNombreReg = new JTextField();
		txtNombreReg.setBounds(20, 72, 150, 25);
		contentPane.add(txtNombreReg);
		txtNombreReg.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Ingrese su DNI:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(240, 46, 150, 15);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Ingrese su email:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1_1.setBounds(20, 110, 150, 15);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Ingrese su contraseña:");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1_1_1.setBounds(240, 171, 150, 15);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		txtPassReg = new JPasswordField();
		txtPassReg.setBounds(240, 187, 150, 25);
		contentPane.add(txtPassReg);
		
		txtDNIReg = new JTextField();
		txtDNIReg.setColumns(10);
		txtDNIReg.setBounds(240, 70, 150, 25);
		contentPane.add(txtDNIReg);
		
		txtEmailReg = new JTextField();
		txtEmailReg.setColumns(10);
		txtEmailReg.setBounds(20, 136, 150, 25);
		contentPane.add(txtEmailReg);
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("Ingrese su N° de Legajo:");
		lblNewLabel_1_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1_1_2.setBounds(20, 170, 150, 15);
		contentPane.add(lblNewLabel_1_1_1_2);
		
		txtNroLegajo = new JTextField();
		txtNroLegajo.setColumns(10);
		txtNroLegajo.setBounds(20, 187, 150, 25);
		contentPane.add(txtNroLegajo);
		
		JLabel lblNewLabel_1_1_1_3 = new JLabel("Ingrese su cargo:");
		lblNewLabel_1_1_1_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1_1_3.setBounds(240, 110, 150, 15);
		contentPane.add(lblNewLabel_1_1_1_3);
		
		txtCargo = new JTextField();
		txtCargo.setColumns(10);
		txtCargo.setBounds(240, 135, 150, 25);
		contentPane.add(txtCargo);
		
		JButton btnRegistrarEmpleado = new JButton("Registrar");
		btnRegistrarEmpleado.setBounds(170, 227, 89, 23);
		contentPane.add(btnRegistrarEmpleado);
		btnRegistrarEmpleado.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		        try {
		            validarCamposFormulario();

		            String nombreReg = txtNombreReg.getText().trim();
		            String emailReg = txtEmailReg.getText().trim();
		            int dniReg = Integer.parseInt(txtDNIReg.getText().trim());
		            String passReg = txtPassReg.getText().trim();
		            int nroLegajoReg = Integer.parseInt(txtNroLegajo.getText().trim());
		            String cargoReg = txtCargo.getText().trim();

		            Conexion conexionBD = Conexion.getInstancia();
		            Connection connection = conexionBD.getConnection();

		            empleadoReg.registrarEmpleado(connection, nombreReg, dniReg, emailReg, passReg, nroLegajoReg, cargoReg);

		            JOptionPane.showMessageDialog(null, "Registro completado con éxito... Volviendo al menú inicial");
		            IniciarBibliotecaDigital ventanaMenuPrincipal = new IniciarBibliotecaDigital();
		            ventanaMenuPrincipal.setVisible(true);
		            dispose();

		        } catch (NumberFormatException e2) {
		            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para el DNI y el N° de Legajo.");
		        } catch (RegistroInvalidoException e2) {
		            JOptionPane.showMessageDialog(null, "No puede haber campos vacíos.");
		        } catch (DniDuplicadoException e2) {
		            JOptionPane.showMessageDialog(null, "El DNI ya está registrado.");
		        } catch (EmailDuplicadoException e2) {
		            JOptionPane.showMessageDialog(null, "El email ya está registrado.");
		        } catch (NroLegajoDuplicadoException e2) {
		            JOptionPane.showMessageDialog(null, "El número de legajo ya está registrado.");
		        } catch (SQLException e2) {
		            JOptionPane.showMessageDialog(null, "Error en la base de datos.");
		        }
		    }

		    private void validarCamposFormulario() throws RegistroInvalidoException {
		        if (txtNombreReg.getText().trim().isEmpty() || txtEmailReg.getText().trim().isEmpty()
		                || txtDNIReg.getText().trim().isEmpty() || txtPassReg.getText().trim().isEmpty()
		                || txtNroLegajo.getText().trim().isEmpty() || txtCargo.getText().trim().isEmpty()) {
		            throw new RegistroInvalidoException("No puede haber campos vacíos.");
		        }

		        if (!txtDNIReg.getText().matches("\\d+") || !txtNroLegajo.getText().matches("\\d+")) {
		            throw new NumberFormatException("DNI o N° de Legajo no válidos.");
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