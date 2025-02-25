package bd;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class Conexion {

	private static final String URL = "jdbc:mysql://localhost:3306/test";
	private static final String USUARIO = "root";
	private static final String CONTRASEÑA = "";

	private static Conexion instancia;
	private Connection con;

	private Conexion() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
		} catch (ClassNotFoundException e) {
			System.err.println("No se pudo encontrar el controlador JDBC: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			throw new SQLException("Error al conectar con la base de datos", e);
		}
	}

	public static Conexion getInstancia() throws SQLException {
		if (instancia == null) {
			synchronized (Conexion.class) { // Para manejar concurrencia
				if (instancia == null) {
					instancia = new Conexion();
				}
			}
		}
		return instancia;
	}

	public Connection getConnection() {
		return con;
	}

}