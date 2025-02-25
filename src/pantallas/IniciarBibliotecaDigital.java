package pantallas;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

import bd.Conexion;
import bd.Tablas;

//Esta clase contiene el punto de entrada (main) de la aplicación.
public class IniciarBibliotecaDigital extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    public IniciarBibliotecaDigital() {
        setTitle("Menu de Inicio");
        setAutoRequestFocus(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 480, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("¡Bienvenido a nuestra biblioteca digital!");
        lblNewLabel.setBounds(100, 29, 250, 19);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));

        JButton btnRegistrarseComoEmpleado = new JButton("Registrarse como EMPLEADO");
        btnRegistrarseComoEmpleado.setBounds(120, 154, 210, 23);
        btnRegistrarseComoEmpleado.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                VentanaRegistroEmpleado ventanaRegistroEmpleado = new VentanaRegistroEmpleado();
                ventanaRegistroEmpleado.setVisible(true);
                dispose(); 
        }
        });

        JButton btnMenuInicio1 = new JButton("Iniciar Sesión");
        btnMenuInicio1.setBounds(120, 86, 210, 23);
        btnMenuInicio1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                VentanaInicioSesion ventanaInicioSesion = new VentanaInicioSesion();
                ventanaInicioSesion.setVisible(true);
                dispose();             
        }
        });

        JButton btnRegistrarseComoCliente = new JButton("Registrarse como CLIENTE");
        btnRegistrarseComoCliente.setBounds(120, 120, 210, 23);
        btnRegistrarseComoCliente.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                VentanaRegistroCliente ventanaRegistroCliente = new VentanaRegistroCliente();
                ventanaRegistroCliente.setVisible(true);
                dispose(); 
        }
        });

        contentPane.setLayout(null);
        contentPane.add(lblNewLabel);
        contentPane.add(btnRegistrarseComoEmpleado);
        contentPane.add(btnRegistrarseComoCliente);
        contentPane.add(btnMenuInicio1);
        
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(170, 227, 105, 23);
        contentPane.add(btnSalir);
        btnSalir.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	                dispose();		
	                System.exit(0);
		}
		}); 
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {                	
                    IniciarBibliotecaDigital frame = new IniciarBibliotecaDigital();
                    frame.setVisible(true);

                    Conexion conexionBD = Conexion.getInstancia();
                    Connection connection = conexionBD.getConnection();
                    Tablas tablas = new Tablas();                    

                    try {                    	
                        tablas.crearTablaCliente(connection);
                        tablas.crearTablaEmpleado(connection);
                        tablas.crearTablaLibro(connection);
                        tablas.crearTablaPrestamo(connection);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(frame, "Error al crear las tablas");                        
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al iniciar la aplicación");                    
                }
            }
        });
    }
}
