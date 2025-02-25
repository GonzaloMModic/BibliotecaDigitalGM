package personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import excepciones.EmailDuplicadoException;
import excepciones.NroLegajoDuplicadoException;
import excepciones.DniDuplicadoException;
import excepciones.RegistroInvalidoException;

public class EmpleadoDAO extends UsuarioDAO {
	
	public void registrarEmpleado(Connection connection, String nombre, int dni, String email, String pass, int nroLegajo, String cargo)
			throws SQLException, EmailDuplicadoException, RegistroInvalidoException, DniDuplicadoException, NroLegajoDuplicadoException {
		String dniVerif = String.valueOf(dni);
		String nroLegajoVerif = String.valueOf(nroLegajo);
		if (nombre.isEmpty() || dniVerif.isEmpty()  || email.isEmpty() || pass.isEmpty() || nroLegajoVerif.isEmpty() || cargo.isEmpty()) {
			throw new RegistroInvalidoException("Error: No puede haber casillas vacias");
		}

		String sql1 = "SELECT dni FROM empleado WHERE dni = ?";
		try (PreparedStatement pstmt1 = connection.prepareStatement(sql1)) {
			pstmt1.setInt(1, dni);
			ResultSet resultSet = pstmt1.executeQuery();

			if (resultSet.next()) {
				throw new DniDuplicadoException("Error de sistema: Dni ya registrado");
			}

			String sql2 = "SELECT email FROM empleado WHERE email = ?";
			try (PreparedStatement pstmt2 = connection.prepareStatement(sql2)) {
				pstmt2.setString(1, email);
				ResultSet resultSet2 = pstmt2.executeQuery();

				if (resultSet2.next()) {
					throw new EmailDuplicadoException(
							"Error de sistema: Email ya registrado");
				}
				
				String sql3 = "SELECT nroLegajo FROM empleado WHERE nroLegajo = ?";
				try (PreparedStatement pstmt3 = connection.prepareStatement(sql3)) {
					pstmt3.setInt(1, nroLegajo);
					ResultSet resultSet3 = pstmt3.executeQuery();

					if (resultSet3.next()) {
						throw new NroLegajoDuplicadoException(
								"Error de sistema: Nro Legajo ya registrado");
					}

				String sql = "INSERT INTO empleado (nombre, dni, email, pass, nroLegajo, cargo) VALUES (?,?,?,?,?,?)";
					try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
						pstmt.setString(1, nombre);
						pstmt.setInt(2, dni);
						pstmt.setString(3, email);
						pstmt.setString(4, pass);
						pstmt.setInt(5, nroLegajo);
						pstmt.setString(6, cargo);
	
						pstmt.executeUpdate();						
						
					} 
				}
			}
		}				
	}
}