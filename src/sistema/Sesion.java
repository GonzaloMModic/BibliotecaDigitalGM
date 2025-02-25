package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import excepciones.RegistroInvalidoException;
import personas.UsuarioDTO;

public class Sesion extends UsuarioDTO{
	private static Sesion instancia;
    private String tipoUsuario;

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public static Sesion getInstancia() {
	        if (instancia == null) {
	            instancia = new Sesion();
	        }
	        return instancia;
	}
    
	public Sesion iniciarSesion(Connection connection, String email, String pass) throws SQLException, RegistroInvalidoException {
	    tipoUsuario = null;
	    
	    if (email.isEmpty() || pass.isEmpty()) {
			throw new RegistroInvalidoException("Error: No puede haber casillas vacias");
		}

	    Sesion sesion = Sesion.getInstancia(); 

	    if (autenticarUsuario(connection, "cliente", email, pass, sesion)) {
	        sesion.setTipoUsuario("cliente");
	    } else if (autenticarUsuario(connection, "empleado", email, pass, sesion)) {
	        sesion.setTipoUsuario("empleado");
	    } else {
	        sesion.limpiarSesion();
	    }
	    return sesion;
	}

	private boolean autenticarUsuario(Connection connection, String tipoUsuario, String email, String pass, Sesion sesion) throws SQLException {
	    String sql = "SELECT * FROM " + tipoUsuario + " WHERE email = ? AND pass = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, email);
	        pstmt.setString(2, pass);

	        ResultSet resultSet = pstmt.executeQuery();
	        if (resultSet.next()) {
	            sesion.setNombre(resultSet.getString("nombre"));
	            sesion.setDNI(resultSet.getInt("dni"));
	            sesion.setEmail(resultSet.getString("email"));
	            sesion.setPass(resultSet.getString("pass"));
	            return true;
	        }
	    } 
	    return false;
	}
	
	public void limpiarSesion() {
	    this.nombre = null;
	    this.dni = 0;
	    this.email = null;
	    this.pass = null;
	    this.tipoUsuario = null;
	}
	
	public String nivelCuentaBD(Connection connection, int dni)throws SQLException{
		String nivelCuenta ;
		String sql = "SELECT nivelCuenta FROM cliente WHERE dni = ? ";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, dni);

	        ResultSet resultSet = pstmt.executeQuery();
	        if (resultSet.next()) {
	        	nivelCuenta = resultSet.getString("nivelCuenta");
	            return nivelCuenta;
	        }
	    } 
	    return null;
	}
	
	public int qPrestamosBD(Connection connection, int dni)throws SQLException{
		int qPrestamosBD ;
		String sql = "SELECT qPrestamos FROM cliente WHERE dni = ? ";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, dni);

	        ResultSet resultSet = pstmt.executeQuery();
	        if (resultSet.next()) {
	        	qPrestamosBD = resultSet.getInt("qPrestamos");
	            return qPrestamosBD;
	        }
	    } 
	    return (Integer) null;
	}
	
	public int traerNroLegajoBD(Connection connection, int dni)throws SQLException{
		int nroLegajo ;
		String sql = "SELECT nroLegajo FROM empleado WHERE dni = ? ";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, dni);

	        ResultSet resultSet = pstmt.executeQuery();
	        if (resultSet.next()) {
	        	nroLegajo = resultSet.getInt("nroLegajo");
	            return nroLegajo;
	        }
	    } 
	    return (Integer) null;
	}
	
	public String traerCargoBD(Connection connection, int dni)throws SQLException{
		String cargoBD ;
		String sql = "SELECT cargo FROM empleado WHERE dni = ? ";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, dni);

	        ResultSet resultSet = pstmt.executeQuery();
	        if (resultSet.next()) {
	        	cargoBD = resultSet.getString("cargo");
	            return cargoBD;
	        }
	    } 
	    return null;
	}	
}