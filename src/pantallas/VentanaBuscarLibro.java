package pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.RegistroInvalidoException;
import sistema.LibroDAO;
import sistema.LibroDTO;
import sistema.Sesion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaBuscarLibro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtBuscar;
	private LibroDAO libroSeleccionado = new LibroDAO();
	private JTable tableResultados;
	private DefaultTableModel modeloTabla;
	Sesion sesion = Sesion.getInstancia();

	public VentanaBuscarLibro() {
		String tipoUsuario = sesion.getTipoUsuario();
		
		setTitle("Busqueda de libro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBusqueda = new JLabel("Ingrese una palabra clave para buscar un libro");
		lblBusqueda.setBounds(74, 10, 450, 25);
		lblBusqueda.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblBusqueda);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(74, 55, 180, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		 modeloTabla = new DefaultTableModel();
	     modeloTabla.addColumn("Título");
	     modeloTabla.addColumn("Autor");
	     modeloTabla.addColumn("Año publicación");
	     modeloTabla.addColumn("Editorial");
	     modeloTabla.addColumn("ISBN");
	     modeloTabla.addColumn("Stock");

	     tableResultados = new JTable(modeloTabla);
	     JScrollPane scrollPane = new JScrollPane(tableResultados);
	     scrollPane.setBounds(10, 98, 564, 200);
	     contentPane.add(scrollPane);
	     
	     JButton btnBuscar = new JButton("Buscar");
	     btnBuscar.setBounds(322, 53, 89, 25);
	     contentPane.add(btnBuscar);		
	     btnBuscar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
					
					try{
						String busqueda = txtBuscar.getText();					
						Conexion conexionBD = Conexion.getInstancia();
						Connection connection = conexionBD.getConnection();
						List<LibroDTO> libros;
	                    if (busqueda.equals("")) {
	                    	libros = libroSeleccionado.obtenerTodosLosLibros(connection);
	                    } else {
	                    	libros = libroSeleccionado.obtenerLibros(connection, busqueda);
	                    }
	                    modeloTabla.setRowCount(0);
	                    for (LibroDTO libro : libros) {
	                        modeloTabla.addRow(new Object[]{	                            
	                            libro.getTitulo(), 
	                            libro.getAutor(), 
	                            libro.getaPubli(),
	                            libro.getEditorial(),
	                            libro.getISBN(),
	                            libro.isPrestado()                         
	                        });
	                    }
					} catch (SQLException | RegistroInvalidoException e2) {
						JOptionPane.showMessageDialog(null, "Algo Salio mal.");
					};
				}
			}
		);	
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(464, 53, 89, 25);
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
	}
}
