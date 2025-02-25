package pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.ISBNDuplicadoException;
import excepciones.RegistroInvalidoException;
import sistema.LibroDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTextField;

public class VentanaAgregarLibro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTitulo;
	private JTextField txtAutor;
	private JTextField txtAPubli;
	private JTextField txtEditorial;
	private JTextField txtISBN;
	LibroDAO libro = new LibroDAO();

	public VentanaAgregarLibro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Agregar libro");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ingrese los datos del nuevo libro");
		lblNewLabel.setBounds(111, 10, 211, 19);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Título");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(50, 40, 130, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Autor");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(250, 40, 150, 20);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Año de publicación");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1_1.setBounds(50, 100, 130, 20);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Editorial");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1_1_1.setBounds(250, 100, 130, 20);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("ISBN");
		lblNewLabel_1_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1_1_2.setBounds(50, 160, 130, 20);
		contentPane.add(lblNewLabel_1_1_1_2);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(280, 181, 89, 23);
		contentPane.add(btnAgregar);
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					validarCampos();
					String titulo = txtTitulo.getText().trim();
					String autor = txtAutor.getText().trim();
					int aPubli = Integer.parseInt(txtAPubli.getText().trim());
					String editorial = txtEditorial.getText().trim();
					String ISBN = txtISBN.getText().trim();
					
					Conexion conexionBD = Conexion.getInstancia();
		            Connection connection = conexionBD.getConnection();
		            
		            libro.agregarLibro(connection, titulo, autor, aPubli, editorial, ISBN);
		            VentanaMenuEmpleado ventanaMenuEmpleado = new VentanaMenuEmpleado();
					ventanaMenuEmpleado.setVisible(true);	
	                dispose();
		            
				}  catch (RegistroInvalidoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());					
				} catch (ISBNDuplicadoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, "Algo salio mal...");					
				}
			}
			
			private void validarCampos() throws RegistroInvalidoException {
		        if (txtTitulo.getText().trim().isEmpty() || txtAutor.getText().trim().isEmpty()
		                || txtAPubli.getText().trim().isEmpty() || txtEditorial.getText().trim().isEmpty()
		                || txtISBN.getText().trim().isEmpty()) {
		            throw new RegistroInvalidoException("No puede haber campos vacíos.");
		        }

		        if (!txtAPubli.getText().matches("\\d+")) {
		            throw new NumberFormatException("Año ingresado no válido.");
		        }		       
		    }
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(175, 227, 89, 23);
		contentPane.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				VentanaMenuEmpleado ventanaMenuEmpleado = new VentanaMenuEmpleado();
				ventanaMenuEmpleado.setVisible(true);	
                dispose();			    
		}
		});
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(50, 60, 150, 25);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtAutor = new JTextField();
		txtAutor.setColumns(10);
		txtAutor.setBounds(250, 60, 150, 25);
		contentPane.add(txtAutor);
		
		txtAPubli = new JTextField();
		txtAPubli.setColumns(10);
		txtAPubli.setBounds(50, 120, 150, 25);
		contentPane.add(txtAPubli);
		
		txtEditorial = new JTextField();
		txtEditorial.setColumns(10);
		txtEditorial.setBounds(250, 120, 150, 25);
		contentPane.add(txtEditorial);
		
		txtISBN = new JTextField();
		txtISBN.setColumns(10);
		txtISBN.setBounds(50, 180, 150, 25);
		contentPane.add(txtISBN);
	}
}
