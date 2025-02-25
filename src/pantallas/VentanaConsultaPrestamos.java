package pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;

import bd.Conexion;
import excepciones.RegistroInvalidoException;
import sistema.PrestamoDetalleDAO;
import sistema.PrestamoDetalleDTO;
import sistema.Sesion;

public class VentanaConsultaPrestamos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;

    public VentanaConsultaPrestamos() throws RegistroInvalidoException {
    	Sesion sesion = Sesion.getInstancia(); 
    	
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
        scrollPane.setBounds(20, 20, 550, 250);
        contentPane.add(scrollPane);

        JButton btnCerrar = new JButton("Volver");
        btnCerrar.setBounds(240, 300, 100, 30);
        contentPane.add(btnCerrar);
        btnCerrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
					VentanaMenuCliente ventanaMenuCliente = new VentanaMenuCliente();
					ventanaMenuCliente.setVisible(true);	
	                dispose();				
			}
        });

        try {
            Conexion conexionBD = Conexion.getInstancia();
            Connection connection = conexionBD.getConnection();
            PrestamoDetalleDAO dao = new PrestamoDetalleDAO();
            List<PrestamoDetalleDTO> prestamos = dao.consultarPrestamosClientes(connection, sesion.getDNI());

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
        }catch (RegistroInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
         } 
        catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "Algo salio mal...");
        }
    }
 
}