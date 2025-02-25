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

public class VentanaMenuEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private UsuarioDAO user = new UsuarioDAO();

	public VentanaMenuEmpleado() {		
		Sesion sesion = Sesion.getInstancia(); 
        String tipoUsuario = sesion.getTipoUsuario(); 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Menu empleados");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Menu empleado");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(160, 11, 140, 25);
		contentPane.add(lblNewLabel);
		
		JButton btnBuscarLibro = new JButton("Buscar libro");
		btnBuscarLibro.setBounds(20, 60, 125, 30);
		contentPane.add(btnBuscarLibro);
		btnBuscarLibro.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	                VentanaBuscarLibro ventanaBuscarLibro = new VentanaBuscarLibro();
	                ventanaBuscarLibro.setVisible(true);
	                dispose();				
			}
		});
		
		JButton btnAgregarLibro = new JButton("Agregar libro");
		btnAgregarLibro.setBounds(160, 60, 125, 30);
		contentPane.add(btnAgregarLibro);
		btnAgregarLibro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		                VentanaAgregarLibro ventanaAgregarLibro = new VentanaAgregarLibro();
		                ventanaAgregarLibro.setVisible(true);
		                dispose();				
				}
			});	
		
		JButton btnModificarLibro = new JButton("Modificar libro");
		btnModificarLibro.setBounds(300, 60, 125, 30);
		contentPane.add(btnModificarLibro);
		btnModificarLibro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		                VentanaBuscarLibroModif ventandaModificarLibro = new VentanaBuscarLibroModif();
		                ventandaModificarLibro.setVisible(true);
		                dispose();				
				}
			});	
		
		JButton btnEliminarLibro = new JButton("Eliminar libro");
		btnEliminarLibro.setBounds(20, 110, 125, 30);
		contentPane.add(btnEliminarLibro);
		btnEliminarLibro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		                VentanaEliminarLibro ventanaEliminarLibro = new VentanaEliminarLibro();
		                ventanaEliminarLibro.setVisible(true);
		                dispose();				
				}
			});		
		
		JButton btnModificarUsuario = new JButton("Modificar datos");
		btnModificarUsuario.setBounds(230, 151, 195, 30);
		contentPane.add(btnModificarUsuario);
		btnModificarUsuario.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	                VentanaModificarDatos ventanaModificarDatos = new VentanaModificarDatos();
	                ventanaModificarDatos.setVisible(true);
	                dispose();				
			}
		});		
		
		JButton btnRegistrarDev = new JButton("Registrar devolución");
		btnRegistrarDev.setBounds(20, 151, 200, 30);
		contentPane.add(btnRegistrarDev);
		btnRegistrarDev.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            VentanaRegistrarDevolucion ventanaRegistrarDevolucion = new VentanaRegistrarDevolucion();
            ventanaRegistrarDevolucion.setVisible(true);
            dispose();				
		}
		});	
		
		JButton btnConsultarPrestamos = new JButton("Ver todos los prestamos");
		btnConsultarPrestamos.setBounds(20, 190, 200, 30);
		contentPane.add(btnConsultarPrestamos);
		btnConsultarPrestamos.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	                VerTodosLosPrestamos verTodosLosPrestamos = new VerTodosLosPrestamos();
	                verTodosLosPrestamos.setVisible(true);
	                dispose();				
			}
		});
		
		JButton btnVerPrestamosUsuario = new JButton("Ver prestamos de un cliente");
		btnVerPrestamosUsuario.setBounds(230, 190, 195, 30);
		contentPane.add(btnVerPrestamosUsuario);
		btnVerPrestamosUsuario.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            	VentanaConsultaPrestamoCliente ventanaConsultaPrestamosCliente = new VentanaConsultaPrestamoCliente();
            	ventanaConsultaPrestamosCliente.setVisible(true);
                dispose();	
        }});
		
		JButton btnEliminarUsuario = new JButton("Eliminar usuario");
		btnEliminarUsuario.setBounds(20, 230, 130, 20);
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
	                	JOptionPane.showMessageDialog(null,"No puede eliminar su usario, usted todavia tiene prestamos activos");
					} catch (RegistroInvalidoException e1) {
						e1.getMessage();
					}
			}
		});
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(333, 230, 75, 20);
		contentPane.add(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	                dispose();		
	                System.exit(0);
			}
		});
		
		JButton btnModificarNivelCuenta = new JButton("Modificar nivel de cuenta de cliente");
		btnModificarNivelCuenta.setBounds(160, 110, 265, 30);
		contentPane.add(btnModificarNivelCuenta);
		btnModificarNivelCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaModifNivelCuenta ventanaModifNivelCuenta = new VentanaModifNivelCuenta();
				ventanaModifNivelCuenta.setVisible(true);
                dispose();
				}
			});
		
	}
}
