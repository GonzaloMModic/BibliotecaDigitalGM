package sistema;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import excepciones.DevolucionInvalidaException;
import excepciones.RegistroInvalidoException;

public class PrestamoDAO {
	
	//Metodos para registrar un prestamo	
	public void realizarPrestamo(Connection connection, String ISBNLibro, int dniCliente, int qPrestamos, String nivelCuenta, Date fechaInicio) throws SQLException, RegistroInvalidoException {	
		int accion = 1;
		
		LibroDAO libroS = new LibroDAO();
		LibroDTO libroSeleccionado = new LibroDTO();		
		
		libroSeleccionado = libroS.obtenerDatosLibro(connection, ISBNLibro);
		
		if (libroSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "El libro seleccionado no es válido.");
            return;
        }
		
		boolean estadoLibro = libroSeleccionado.isPrestado();
		
		if("premium".equals(nivelCuenta) && estadoLibro) {
			actualizarPLibro(connection, ISBNLibro, accion);
			actualizarPCliente(connection, dniCliente, accion);
			registrarPrestamo(connection, ISBNLibro, dniCliente, fechaInicio);
			
			JOptionPane.showMessageDialog(null, "Préstamo registrado con éxito.");
		}
		else if ("normal".equals(nivelCuenta) && qPrestamos < 5 && estadoLibro) {
			actualizarPLibro(connection, ISBNLibro, accion);
			actualizarPCliente(connection, dniCliente, accion);
			registrarPrestamo(connection, ISBNLibro, dniCliente, fechaInicio);	
			
			JOptionPane.showMessageDialog(null, "Préstamo registrado con éxito.");
		}
		else if (nivelCuenta == "normal" && qPrestamos >= 5) {			
			JOptionPane.showMessageDialog(null, "Límite de préstamos excedido. No se puede registrar el préstamo.");
		}	
		else if (!estadoLibro){
			JOptionPane.showMessageDialog(null, "El libro no está disponible para préstamo.");
		}
		else {
			JOptionPane.showMessageDialog(null, "Error en el sistema. Por favor, contacte al administrador.");
		}
	}
	
	
	public void registrarPrestamo(Connection connection, String ISBNLibro, int dniCliente, Date fechaInicio) throws SQLException {
		
		String sql = "INSERT INTO prestamo (dni_cliente, ISBN_libro, fechaInicio, estado) VALUES (?,?,?,?)";
		
		try(PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setInt(1, dniCliente);
			pstmt.setString(2, ISBNLibro);
			pstmt.setDate(3, fechaInicio);
			pstmt.setString(4, "prestado");
			
			int filasAfectadas = pstmt.executeUpdate();
			
			if (filasAfectadas > 0) {
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "El libro seleccionado no es válido.");
		}
		
	}
	
	public void actualizarPCliente(Connection connection, int dniCliente, int accion) throws SQLException {
		
		int qPrestamosCliente = 0;
		String sql1 = "SELECT qPrestamos FROM cliente WHERE dni = ?";
		
		try(PreparedStatement pstmt1 = connection.prepareStatement(sql1)){
			pstmt1.setInt(1, dniCliente);	
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next()) {
	            qPrestamosCliente = rs.getInt("qPrestamos");
	        } else {
	            throw new SQLException("Cliente no encontrado.");
	        }
		}
		
		int prestamosTotales = qPrestamosCliente;
		
		if(accion == 1) {
		 prestamosTotales = qPrestamosCliente + 1;
		}
		if(accion == 2){ 
		 prestamosTotales = qPrestamosCliente - 1;
		}
		
		String sql = "UPDATE cliente SET qPrestamos = ? WHERE dni = ?";
		
		try(PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setInt(1, prestamosTotales);
			pstmt.setInt(2, dniCliente);	
			
			pstmt.executeUpdate();
		
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new SQLException("Error al actualizar cliente.", e);
		    }
		}
	public void actualizarPLibro(Connection connection, String ISBNLibro, int accion) throws SQLException {
		boolean estadoLibro;		
		String sql = "UPDATE libro SET prestado = ? WHERE ISBN = ?";
		
		
		if(accion == 1) {
			estadoLibro = false;
			}
		else if(accion == 2){ 
				estadoLibro = true;
			}
		else {
		    throw new IllegalArgumentException("Acción no válida.");		    
		}
		
		 String sqlVerificarLibro = "SELECT * FROM libro WHERE ISBN = ?";
		    try (PreparedStatement pstmtVerificar = connection.prepareStatement(sqlVerificarLibro)) {
		        pstmtVerificar.setString(1, ISBNLibro);
		        ResultSet rs = pstmtVerificar.executeQuery();
		        if (!rs.next()) {
		            throw new SQLException("El libro con ISBN " + ISBNLibro + " no existe.");
		        }
		    }	
		
		try(PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setBoolean(1, estadoLibro);	
			pstmt.setString(2, ISBNLibro);
			
		    pstmt.executeUpdate();
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new SQLException("Error al actualizar libro.", e);
		    }
		}

	//Fin de metodos para registrar un prestamo
	
	//Metodos para realizar una Devolución (Cliente)	
	
	public void realizarDevolucion(Connection connection, String ISBNLibro, int dniCliente, int qPrestamos, String nivelCuenta, Date fechaFin) throws SQLException, DevolucionInvalidaException {		
		int accion = 2;	
	
		String sqlVerif = "SELECT estado FROM prestamo WHERE dni_cliente = ? AND ISBN_libro = ? AND estado = 'prestado'";
		try (PreparedStatement pstmtVerif = connection.prepareStatement(sqlVerif)) {
	        pstmtVerif.setInt(1, dniCliente);
	        pstmtVerif.setString(2, ISBNLibro);

	        ResultSet rs = pstmtVerif.executeQuery();

	        if (!rs.next()) {
	        	throw new DevolucionInvalidaException("Usted no posee préstamos activos actualmente");
	        }
	    } 
		
		  try {
		        actualizarPLibro(connection, ISBNLibro, accion);
		        actualizarPCliente(connection, dniCliente, accion);
		        registrarDevolucion(connection, ISBNLibro, dniCliente, fechaFin);
		        JOptionPane.showMessageDialog(null, "Devolución registrada con éxito");
		    } catch (SQLException e) {
		        JOptionPane.showMessageDialog(null, "Error al registrar la devolución en la base de datos, puede ser que no tenga prestamos activos.");
		        throw e;
		    }
		}
	
	public void registrarDevolucion(Connection connection, String ISBNLibro, int dniCliente, Date fechaFin) throws SQLException {
		
		String sql = "UPDATE prestamo SET fechaFin = ?, estado = ? WHERE (dni_cliente = ? AND ISBN_libro = ?) ";
		
		try(PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setDate(1, fechaFin);
			pstmt.setString(2, "devuelto");
			pstmt.setInt(3, dniCliente);
			pstmt.setString(4, ISBNLibro);
			
			int filasAfectadas = pstmt.executeUpdate();
			
			if (filasAfectadas > 0) {
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "El libro seleccionado no es válido.");
		}
		
	}	
	//Fin de metodos para realizar una Devolición	

}
