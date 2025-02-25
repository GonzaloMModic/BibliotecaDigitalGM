package bd;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Tablas {
	
	public void crearTablaCliente(Connection con) throws SQLException {
		String sql = """
				CREATE TABLE IF NOT EXISTS cliente (
				id_cliente INT AUTO_INCREMENT PRIMARY KEY,
				nombre VARCHAR(100) NOT NULL,
				dni INT UNIQUE NOT NULL,
				email VARCHAR(100) UNIQUE NOT NULL,
				pass VARCHAR(100) NOT NULL,				
				nivelCuenta VARCHAR(100) NOT NULL,
				qPrestamos INT NOT NULL
				);
				""";
		String alterTableSql = "ALTER TABLE cliente AUTO_INCREMENT = 100;";

		try (Statement stmt = (Statement) con.createStatement()) {
			stmt.execute(sql);
			stmt.executeUpdate(alterTableSql);
			//System.out.println("Se creo la tabla cliente");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void crearTablaEmpleado(Connection con) throws SQLException {
		String sql = """
				CREATE TABLE IF NOT EXISTS empleado (
				id_empleado INT AUTO_INCREMENT PRIMARY KEY,
				nombre VARCHAR(100) NOT NULL,
				dni INT UNIQUE NOT NULL,
				email VARCHAR(100) UNIQUE NOT NULL,
				pass VARCHAR(100) NOT NULL,				
				nroLegajo INT UNIQUE NOT NULL,
				cargo VARCHAR(100) NOT NULL
				);
				""";
		String alterTableSql = "ALTER TABLE empleado AUTO_INCREMENT = 100;";

		try (Statement stmt = (Statement) con.createStatement()) {
			stmt.execute(sql);
			stmt.executeUpdate(alterTableSql);
			//System.out.println("Se creo la tabla empleado");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void crearTablaLibro(Connection con) throws SQLException {
		String sql = """
				CREATE TABLE IF NOT EXISTS libro (
				id_libro INT AUTO_INCREMENT PRIMARY KEY,
				titulo VARCHAR(100) NOT NULL,
				nombreAutor VARCHAR(100) NOT NULL,
				fechaPublicacion INT  NOT NULL,
				editorial VARCHAR(100) NOT NULL,				
				ISBN VARCHAR(100) UNIQUE NOT NULL,
				prestado BOOLEAN DEFAULT TRUE
				);
				""";
		String alterTableSql = "ALTER TABLE libro AUTO_INCREMENT = 100;";

		try (Statement stmt = (Statement) con.createStatement()) {
			stmt.execute(sql);
			stmt.executeUpdate(alterTableSql);
			//System.out.println("Se creo la tabla libro");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void crearTablaPrestamo(Connection con) throws SQLException {
		String sql = """
				CREATE TABLE IF NOT EXISTS prestamo (
				id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
				dni_cliente INT NOT NULL,
				ISBN_libro VARCHAR(100) NOT NULL,
				fechaInicio DATE NOT NULL,
				fechaFin DATE,							
				estado VARCHAR(100) NOT NULL,				
				CONSTRAINT fk_cliente FOREIGN KEY (dni_cliente) REFERENCES cliente (dni) ON DELETE CASCADE,
				CONSTRAINT fk_libro FOREIGN KEY (ISBN_libro) REFERENCES libro (ISBN) ON DELETE CASCADE,
				CONSTRAINT chk_estado CHECK (estado IN ('prestado', 'devuelto', 'perdido'))		   
				);
				""";
		String alterTableSql = "ALTER TABLE prestamo AUTO_INCREMENT = 100;";

		try (Statement stmt = (Statement) con.createStatement()) {
			stmt.execute(sql);
			stmt.executeUpdate(alterTableSql);
			//System.out.println("Se creo la tabla prestamo");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
