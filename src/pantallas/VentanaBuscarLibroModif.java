package pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.RegistroInvalidoException;
import sistema.LibroDAO;
import sistema.LibroDTO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaBuscarLibroModif extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtISBN;
	LibroDAO libro = new LibroDAO();

	public VentanaBuscarLibroModif() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Buscar libro a modificar");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ingrese el ISBN del libro que desea modificar");
		lblNewLabel.setBounds(71, 10, 292, 19);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel);
		
		txtISBN = new JTextField();
		txtISBN.setBounds(130, 40, 160, 20);
		contentPane.add(txtISBN);
		txtISBN.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(170, 98, 89, 23);
		contentPane.add(btnBuscar);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String ISBN = txtISBN.getText().trim();
				
				Conexion conexionBD = Conexion.getInstancia();
				Connection connection = conexionBD.getConnection();
				
				if(ISBN.equals("")) {
					JOptionPane.showMessageDialog(null, "No puede haber casillas vacias");					
				} else {
					LibroDTO libroModif = libro.obtenerDatosLibro(connection, ISBN);
					VentanaModificarLibro ventanaModificarLibro = new VentanaModificarLibro(libroModif);
					ventanaModificarLibro.setVisible(true);	
	                dispose();		
				}
				} catch(SQLException e1) {
					e1.getMessage();
				} catch (RegistroInvalidoException e1) {
					e1.getMessage();
				}
			}
				
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(170, 190, 89, 23);
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
