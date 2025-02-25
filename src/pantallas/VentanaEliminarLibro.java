package pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.EliminacionInvalidaException;
import excepciones.RegistroInvalidoException;
import sistema.LibroDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaEliminarLibro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtISBN;
	private LibroDAO libro = new LibroDAO();

	public VentanaEliminarLibro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Eliminar libro");
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblISBN = new JLabel("Ingrese el ISBN del libro que desea eliminar");
		lblISBN.setBounds(76, 10, 282, 19);
		lblISBN.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblISBN);
		
		txtISBN = new JTextField();
		txtISBN.setBounds(125, 40, 180, 25);
		contentPane.add(txtISBN);
		txtISBN.setColumns(10);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(169, 76, 89, 23);
		contentPane.add(btnEliminar);
		btnEliminar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		                try {
		                	Conexion conexionBD = Conexion.getInstancia();
							Connection connection = conexionBD.getConnection();
							if (txtISBN.getText().trim().isEmpty()) {
			            		throw new RegistroInvalidoException("No puede haber campos vacíos.");
			            	}   else {
							String ISBN = txtISBN.getText();
							int respuesta = JOptionPane.showConfirmDialog(
						            null,                               
						            "¿Está seguro de que desea elimiar el libro?", 
						            "Confirmar eliminación",                 
						            JOptionPane.YES_NO_OPTION,           
						            JOptionPane.QUESTION_MESSAGE        
						        );
							if(respuesta == JOptionPane.YES_OPTION) {
								libro.eliminarLibro(connection, ISBN);								
							}	
		                }
							} 	catch (EliminacionInvalidaException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage());
							}	catch (SQLException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage());
							} 	catch (RegistroInvalidoException e1) {
								JOptionPane.showMessageDialog(null, "La casilla no puede estar vacía");
							} 
		}
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(169, 193, 89, 23);
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