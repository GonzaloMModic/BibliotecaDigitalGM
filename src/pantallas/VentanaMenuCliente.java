package pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.EliminacionInvalidaException;
import excepciones.RegistroInvalidoException;
import personas.UsuarioDAO;
import sistema.Sesion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;

public class VentanaMenuCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private UsuarioDAO user = new UsuarioDAO();

	public VentanaMenuCliente() {
		Sesion sesion = Sesion.getInstancia(); 
        String tipoUsuario = sesion.getTipoUsuario();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Menu clientes");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Opciones disponibles");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(125, 11, 220, 35);
		contentPane.add(lblNewLabel_1);
		
		JButton btnBuscarLibro = new JButton("Buscar libro");
		btnBuscarLibro.setBounds(55, 57, 150, 30);
		contentPane.add(btnBuscarLibro);
		btnBuscarLibro.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	                VentanaBuscarLibro ventanaBuscarLibro = new VentanaBuscarLibro();
	                ventanaBuscarLibro.setVisible(true);
	                dispose();				
			}
		});
													
        JButton btnConsultarPrestamos = new JButton("Prestamos actuales");
        btnConsultarPrestamos.setBounds(55, 98, 150, 30);
        contentPane.add(btnConsultarPrestamos);        
        btnConsultarPrestamos.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            	VentanaConsultaPrestamos ventanaConsultaPrestamos = null;
				try {
					ventanaConsultaPrestamos = new VentanaConsultaPrestamos();
				} catch (RegistroInvalidoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	ventanaConsultaPrestamos.setVisible(true);
                dispose();	
        }});

		JButton btnModificarDatos = new JButton("Modificar datos");
		btnModificarDatos.setBounds(215, 57, 150, 30);
		contentPane.add(btnModificarDatos);
		btnModificarDatos.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	                VentanaModificarDatos ventanaModificarDatos = new VentanaModificarDatos();
	                ventanaModificarDatos.setVisible(true);
	                dispose();				
			}
		});
		
		JButton btnPedirPrestamo = new JButton("Pedir prestamo");
		btnPedirPrestamo.setBounds(215, 98, 150, 30);
		contentPane.add(btnPedirPrestamo);
		btnPedirPrestamo.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	                VentanaPrestamo ventanaPrestamo = new VentanaPrestamo();
	                ventanaPrestamo.setVisible(true);
	                dispose();				
			}
		});
		
		JButton btnRealizarDevolucion = new JButton("Realizar devolución");
		btnRealizarDevolucion.setBounds(55, 139, 150, 30);
		contentPane.add(btnRealizarDevolucion);
		btnRealizarDevolucion.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	                VentanaRealizarDevolucion ventanarealizarDevolucion = new VentanaRealizarDevolucion();
	                ventanarealizarDevolucion.setVisible(true);
	                dispose();				
			}
		});
		
		JButton btnEliminarUsuario = new JButton("Eliminar usuario");
		btnEliminarUsuario.setBounds(215, 139, 150, 30);
		contentPane.add(btnEliminarUsuario);
		btnEliminarUsuario.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	                try {
	                	Conexion conexionBD = Conexion.getInstancia();
						Connection connection = conexionBD.getConnection();
						int respuesta = JOptionPane.showConfirmDialog(
					            null,                               
					            "¿Está seguro de que desea elimiar su usuario?", 
					            "Confirmar eliminación",                 
					            JOptionPane.YES_NO_OPTION,           
					            JOptionPane.QUESTION_MESSAGE       
					        );

						if(respuesta == JOptionPane.YES_OPTION) {
							user.eliminarRegistro(connection,sesion.getDNI(), tipoUsuario);
							JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente, saliendo del sistema...");
							dispose();
							System.exit(0);
						}						
	                } catch (SQLException e1) {
	                	JOptionPane.showMessageDialog(null, "Algo salio mal...");
	                } catch (EliminacionInvalidaException e1) {
	                	JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (RegistroInvalidoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(215, 180, 150, 30);
		contentPane.add(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	                dispose();		
	                System.exit(0);
			}
		});
		
		JButton btnNivelCuenta = new JButton("Nivel de cuenta");
		btnNivelCuenta.setBounds(55, 180, 150, 30);
		contentPane.add(btnNivelCuenta);
		btnNivelCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	                try {
	                	Conexion conexionBD = Conexion.getInstancia();
						Connection connection = conexionBD.getConnection();
						String lvlCuenta = sesion.nivelCuentaBD(connection, sesion.getDNI());
						JOptionPane.showMessageDialog(null, "Su nivel de cuenta es: " + lvlCuenta);
						
	                } catch (SQLException e1) {
	                	JOptionPane.showMessageDialog(null, "Algo salio mal...");
	                }
			}
		});
	}
}