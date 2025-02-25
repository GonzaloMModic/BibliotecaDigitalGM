package personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import excepciones.EmailDuplicadoException;
import excepciones.DniDuplicadoException;
import excepciones.RegistroInvalidoException;

public class ClienteDAO extends UsuarioDAO {	
	
		public void registrarCliente(Connection connection, String nombre, int dni, String email, String pass)
			throws SQLException, EmailDuplicadoException, RegistroInvalidoException, DniDuplicadoException {
		String dniVerif = String.valueOf(dni);
		String nivelCuenta = "normal";
		int prestamosDefault = 0;
		if (nombre.isEmpty() || dniVerif.isEmpty()  || email.isEmpty() || pass.isEmpty()) {
			throw new RegistroInvalidoException("Error: No puede haber casillas vacias");
		}

		String sql1 = "SELECT dni FROM cliente WHERE dni = ?";
		try (PreparedStatement pstmt1 = connection.prepareStatement(sql1)) {
			pstmt1.setInt(1, dni);
			ResultSet resultSet = pstmt1.executeQuery();

			if (resultSet.next()) {
				throw new DniDuplicadoException("Error de sistema: Usuario ya registrado");
			}

			String sql2 = "SELECT email FROM cliente WHERE email = ?";
			try (PreparedStatement pstmt2 = connection.prepareStatement(sql2)) {
				pstmt2.setString(1, email);
				ResultSet resultSet2 = pstmt2.executeQuery();

				if (resultSet2.next()) {
					throw new EmailDuplicadoException(
							"Error de sistema: Email ya registrado");
				}

				String sql = "INSERT INTO cliente (nombre, dni, email, pass, nivelCuenta, qPrestamos) VALUES (?,?,?,?,?,?)";
					try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
						pstmt.setString(1, nombre);
						pstmt.setInt(2, dni);
						pstmt.setString(3, email);
						pstmt.setString(4, pass);
						pstmt.setString(5, nivelCuenta);
						pstmt.setInt(6, prestamosDefault);
	
						pstmt.executeUpdate();	

					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}
}
