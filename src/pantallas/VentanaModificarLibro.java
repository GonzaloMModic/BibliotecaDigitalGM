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

public class VentanaModificarLibro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTitulo;
	private JTextField txtAutor;
	private JTextField txtAPubli;
	private JTextField txtEditorial;
	private JTextField txtPrestamo;
	private LibroDAO nuevoLibro = new LibroDAO();

	public VentanaModificarLibro(LibroDTO libroModif) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Modificar Libro");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ingrese los nuevos datos");
		lblNewLabel.setBounds(134, 10, 165, 19);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel);
		
		JLabel lblTitulo = new JLabel("Título");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTitulo.setBounds(50, 50, 150, 20);
		contentPane.add(lblTitulo);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAutor.setBounds(240, 50, 150, 20);
		contentPane.add(lblAutor);
		
		JLabel lblAoDePublicacin = new JLabel("Año de publicación");
		lblAoDePublicacin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAoDePublicacin.setBounds(50, 110, 150, 20);
		contentPane.add(lblAoDePublicacin);
		
		JLabel lblEditorial = new JLabel("Editorial");
		lblEditorial.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEditorial.setBounds(240, 110, 150, 20);
		contentPane.add(lblEditorial);
		
		JLabel lblEstadoPrestamo = new JLabel("Stock");
		lblEstadoPrestamo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEstadoPrestamo.setBounds(50, 170, 150, 20);
		contentPane.add(lblEstadoPrestamo);
		
		txtTitulo = new JTextField(libroModif.getTitulo());
		txtTitulo.setBounds(50, 70, 150, 25);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtAutor = new JTextField(libroModif.getAutor());
		txtAutor.setColumns(10);
		txtAutor.setBounds(240, 70, 150, 25);
		contentPane.add(txtAutor);
		
		txtAPubli = new JTextField(String.valueOf(libroModif.getaPubli()));
		txtAPubli.setColumns(10);
		txtAPubli.setBounds(50, 130, 150, 25);
		contentPane.add(txtAPubli);
		
		txtEditorial = new JTextField(libroModif.getEditorial());
		txtEditorial.setColumns(10);
		txtEditorial.setBounds(240, 130, 150, 25);
		contentPane.add(txtEditorial);
		
		txtPrestamo = new JTextField(String.valueOf(libroModif.isPrestado()));
		txtPrestamo.setColumns(10);
		txtPrestamo.setBounds(50, 190, 150, 25);
		contentPane.add(txtPrestamo);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(270, 190, 90, 25);
		contentPane.add(btnEditar);
		btnEditar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				validarCampos();
				String titulo = txtTitulo.getText().trim();
				String autor = txtAutor.getText().trim();
				int aPubli =  Integer.parseInt(txtAPubli.getText().trim());
				String editorial = txtEditorial.getText().trim();
				String estadoStock = txtPrestamo.getText().trim().toLowerCase();
				
				if (!estadoStock.equals("true") && !estadoStock.equals("false")) {
			        throw new IllegalArgumentException("Debe ingresar 'true' o 'false'.");
			    }
				boolean estado = Boolean.parseBoolean(estadoStock);
				
				Conexion conexionBD = Conexion.getInstancia();
				Connection connection = conexionBD.getConnection();
				
			nuevoLibro.actualizarLibro(connection, libroModif, titulo, autor, aPubli, editorial, estado);
			JOptionPane.showMessageDialog(null, "Libro actualizado correctamente");
			} 	catch (RegistroInvalidoException e1) {				
				JOptionPane.showMessageDialog(null, e1.getMessage());
			} 	catch(IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}	catch(SQLException e1){
				JOptionPane.showMessageDialog(null, "Algo salio mal...");
			}
		}
		 private void validarCampos() throws RegistroInvalidoException {
		        if (txtTitulo.getText().trim().isEmpty() || txtAutor.getText().trim().isEmpty()
		                || txtAPubli.getText().trim().isEmpty() || txtEditorial.getText().trim().isEmpty()) {
		            throw new RegistroInvalidoException("No puede haber campos vacíos.");
		        }
		        if (!txtAPubli.getText().matches("\\d+")) {
		            throw new NumberFormatException("DNI o N° de Legajo no válidos.");
		        }		       
		    }
		});
		
		JButton btnVolverAlMenu = new JButton("Volver al menu");
		btnVolverAlMenu.setBounds(160, 226, 120, 25);
		contentPane.add(btnVolverAlMenu);
		btnVolverAlMenu.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {		
		VentanaMenuEmpleado ventanaMenuEmpleado = new VentanaMenuEmpleado();
		ventanaMenuEmpleado.setVisible(true);	
        dispose();
		}
		});
	}

}
