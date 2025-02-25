package pantallas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.RegistroInvalidoException;
import sistema.PrestamoDetalleDAO;
import sistema.PrestamoDetalleDTO;

public class VentanaConsultaPrestamoCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtDNICliente;

   	public VentanaConsultaPrestamoCliente() {
    	
        setTitle("Consulta de Préstamos");
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        model = new DefaultTableModel();
        model.addColumn("DNI Cliente");
        model.addColumn("ISBN Libro");
        model.addColumn("Fecha Inicio");
        model.addColumn("Fecha Fin");
        model.addColumn("Estado");
        model.addColumn("Nombre Cliente");
        model.addColumn("Título Libro");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 89, 550, 200);
        contentPane.add(scrollPane);

        JButton btnCerrar = new JButton("Volver");
        btnCerrar.setBounds(240, 300, 100, 30);
        contentPane.add(btnCerrar);
        btnCerrar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
             	VentanaMenuEmpleado ventanaMenuEmpleado = new VentanaMenuEmpleado();
             	ventanaMenuEmpleado.setVisible(true);            	
                 dispose();  
             }
        });
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(439, 45, 100, 30);
        contentPane.add(btnBuscar);
        
        txtDNICliente = new JTextField();
        txtDNICliente.setBounds(196, 45, 200, 30);
        contentPane.add(txtDNICliente);
        txtDNICliente.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("Consultar prestamos del cliente");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(196, 11, 270, 25);
        contentPane.add(lblNewLabel);
        
        JLabel lblIngreseElDni = new JLabel("Ingrese el DNI del cliente");
        lblIngreseElDni.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblIngreseElDni.setBounds(10, 45, 200, 30);
        contentPane.add(lblIngreseElDni);
        btnBuscar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {    	
        try {
        	validarCampos();        	
        	int DNI = Integer.parseInt(txtDNICliente.getText().trim());
        	
            Conexion conexionBD = Conexion.getInstancia();
            Connection connection = conexionBD.getConnection();         
            PrestamoDetalleDAO dao = new PrestamoDetalleDAO();       

            List<PrestamoDetalleDTO> prestamos = dao.consultarPrestamosClientes(connection, DNI);

            for (PrestamoDetalleDTO prestamo : prestamos) {
                Object[] row = {
                    prestamo.getDniCliente(),
                    prestamo.getISBNLibro(),
                    prestamo.getFechaInicio(),
                    prestamo.getFechaFin(),
                    prestamo.getEstado(),
                    prestamo.getNombreCliente(),
                    prestamo.getTituloLibro()
                };
                model.addRow(row);
            }
        } catch (RegistroInvalidoException e1) {
        	JOptionPane.showMessageDialog(null, e1.getMessage());
			}  catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
    		}catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, "Algo salio mal...");
        		}
        }
            
            private void validarCampos() throws RegistroInvalidoException{           	
            	if (txtDNICliente.getText().trim().isEmpty()) {
            		throw new RegistroInvalidoException("No puede haber campos vacíos.");
            	}        
            	 if (!txtDNICliente.getText().matches("\\d+")) {
 		            throw new NumberFormatException("DNI ingresado inválido. Por favor, ingrese solo números.");
 		        }	
            }
        });
    }
}
