package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import excepciones.RegistroInvalidoException;

public class PrestamoDetalleDAO {
	
	public List<PrestamoDetalleDTO> consultarPrestamos(Connection connection) throws SQLException {
		
		String sql = """ 				
						SELECT p.*, 
						c.nombre AS nombre_cliente,
						l.titulo AS titulo_libro
						FROM 
						prestamo p
						INNER JOIN 
						cliente c ON p.dni_cliente = c.dni
						INNER JOIN 
						libro l ON p.ISBN_libro = l.ISBN
						WHERE estado = 'prestado'
						""";
		
		List <PrestamoDetalleDTO> prestamos = new ArrayList<>();
		
		try(Statement stmt = (Statement) connection.createStatement()){			
			ResultSet resultSet = stmt.executeQuery(sql);
			
			while (resultSet.next()) {
				PrestamoDetalleDTO detalle = new PrestamoDetalleDTO();
				detalle.setDniCliente(resultSet.getInt("dni_cliente"));
	            detalle.setISBNLibro(resultSet.getString("ISBN_libro"));
	            detalle.setFechaInicio(resultSet.getDate("fechaInicio"));
	            detalle.setFechaFin(resultSet.getDate("fechaFin"));
	            detalle.setEstado(resultSet.getString("estado"));

	            detalle.setNombreCliente(resultSet.getString("nombre_cliente"));
	            detalle.setTituloLibro(resultSet.getString("titulo_libro"));
				
				prestamos.add(detalle);
			}
		}	
		return prestamos;
	}
	
	public List<PrestamoDetalleDTO> consultarPrestamosClientes(Connection connection, int dni_cliente) throws SQLException, RegistroInvalidoException {
		
		String sql = """ 				
						SELECT p.*, 
						c.nombre AS nombre_cliente,
						l.titulo AS titulo_libro
						FROM 
						prestamo p
						INNER JOIN 
						cliente c ON p.dni_cliente = c.dni
						INNER JOIN 
						libro l ON p.ISBN_libro = l.ISBN
						WHERE dni_cliente = ? AND estado = 'prestado'
						""";
		
		List <PrestamoDetalleDTO> prestamos = new ArrayList<>();
		
		try(PreparedStatement pstmt = connection.prepareStatement(sql)){	
			pstmt.setInt(1, dni_cliente);
			
			ResultSet resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) {
				PrestamoDetalleDTO detalle = new PrestamoDetalleDTO();
				detalle.setDniCliente(resultSet.getInt("dni_cliente"));
	            detalle.setISBNLibro(resultSet.getString("ISBN_libro"));
	            detalle.setFechaInicio(resultSet.getDate("fechaInicio"));
	            detalle.setFechaFin(resultSet.getDate("fechaFin"));
	            detalle.setEstado(resultSet.getString("estado"));

	            detalle.setNombreCliente(resultSet.getString("nombre_cliente"));
	            detalle.setTituloLibro(resultSet.getString("titulo_libro"));
				
				prestamos.add(detalle);
			}
		}
		    return prestamos;
	}	
}