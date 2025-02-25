package personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import excepciones.EliminacionInvalidaException;
import excepciones.RegistroInvalidoException;
import sistema.PrestamoDetalleDAO;
import sistema.PrestamoDetalleDTO;

public class UsuarioDAO {	
	
	//Metodos para eliminar usuarios
	
	public void identificarUsuario(Connection connection, int dni, String email, String pass) throws SQLException, RegistroInvalidoException, EliminacionInvalidaException {
	    String dniVerif = String.valueOf(dni);
	    
	    if (dniVerif.isEmpty() || email.isEmpty() || pass.isEmpty()) {
	        throw new RegistroInvalidoException("Error: No puede haber casillas vacías");
	    }
	    
	    int filasAfectadas = eliminarRegistro(connection, dni, "cliente");
	    
	    if (filasAfectadas == 0) {
	        filasAfectadas = eliminarRegistro(connection, dni, "empleado");
	    } else {
	        JOptionPane.showMessageDialog(null, "Registro Inválido, Email o contraseña incorrecto");
	    } 
	}

	public int eliminarRegistro(Connection connection, int dni, String tipoUsuario) throws SQLException, EliminacionInvalidaException, RegistroInvalidoException {
		//Este metodo va a devolver un int en donde si es cero significa que NO encontro el registro en la tabla y si es 1, que si lo encontro y lo elimino
		
		PrestamoDetalleDAO prestamo = new PrestamoDetalleDAO();
		
		List<PrestamoDetalleDTO> prestamos = prestamo.consultarPrestamosClientes(connection, dni );
		
		if(!prestamos.isEmpty()) {
			throw new EliminacionInvalidaException("No puede eliminar su usario, usted todavia tiene prestamos activos");			
		}		
		
	    String sql = "DELETE FROM " + tipoUsuario + " WHERE dni = ?";
	    
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, dni);
	        
	        return pstmt.executeUpdate();	        
		}  
	}	
	//Fin metodos eliminar usuarios
	
	//--Metodos para actualizar informacion de usuarios	
	public boolean actualizarDatosUsuario(Connection connection, int DNI, String nombre, String email, String pass, String nuevoNombre, String nuevoEmail, String nuevoPass) throws SQLException {
	    String sql = "UPDATE cliente SET nombre = ?, email = ?, pass = ? WHERE dni = ?";
	    
	    if (nuevoNombre == null || nuevoNombre.isEmpty()) {
	        nuevoNombre = nombre;
	    }
	    if (nuevoEmail == null || nuevoEmail.isEmpty()) {
	        nuevoEmail = email;
	    }
	    if (nuevoPass == null || nuevoPass.isEmpty()) {
	        nuevoPass = pass;
	    }

	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, nuevoNombre);
	        pstmt.setString(2, nuevoEmail);
	        pstmt.setString(3, nuevoPass);
	        pstmt.setInt(4, DNI);

	        int filasAfectadas = pstmt.executeUpdate();
	        return filasAfectadas > 0;
	    }
	}
	
	public void actualizarNivelCuentaCliente(Connection connection, int dniCliente) throws SQLException, RegistroInvalidoException {
		String sqlVerif = "SELECT * FROM CLIENTE WHERE dni = ?";
		String nivel = "error";
		String sql = "error";
		try (PreparedStatement pstmtVerif = connection.prepareStatement(sqlVerif)){
			pstmtVerif.setInt(1, dniCliente);
			
			ResultSet rs = pstmtVerif.executeQuery();
			if(rs.next()) {
				nivel = rs.getString("nivelCuenta");
			}
		}
		
		if(nivel.equals("normal")) {
		sql = "UPDATE cliente SET nivelCuenta = 'premium' WHERE dni = ?";
		} else if(nivel.equals("premium")) {
		sql = "UPDATE cliente SET nivelCuenta = 'normal' WHERE dni = ?";
		} else {
			throw new RegistroInvalidoException("Usuario no encontrado");
		}
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setInt(1, dniCliente);
			
			pstmt.executeUpdate();		
		}
	}
	//--Fin, Metodos para actualizar info de usuarios
}
