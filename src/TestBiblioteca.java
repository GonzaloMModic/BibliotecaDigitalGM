import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bd.Tablas;
import excepciones.ISBNDuplicadoException;
import excepciones.RegistroInvalidoException;
import sistema.LibroDAO;
import sistema.LibroDTO;
import sistema.Sesion;
import bd.Conexion;

class TestBiblioteca {
	
	Sesion sesion = new Sesion();
	
	@BeforeEach
	void setUpBD() throws SQLException {
		Conexion conexionBD = Conexion.getInstancia();
		Connection connection = conexionBD.getConnection();
		Tablas tablas = new Tablas();
		tablas.crearTablaCliente(connection);
		tablas.crearTablaEmpleado(connection);
		tablas.crearTablaLibro(connection);
		tablas.crearTablaPrestamo(connection);		
	};	
	
	void cargarDatos() throws SQLException {
		Conexion conexionBD = Conexion.getInstancia();
		Connection connection = conexionBD.getConnection();
		//Estos inserts no seran borrados de la bd de forma automatica luego de realizar las pruebas, 
		//se recomienda borrarlos manuelmente para volver a realizar las pruebas debido a que no pueden existir registros duplicados en Email, DNI, nroLegajo e ISBN
		connection.createStatement().execute("INSERT INTO cliente (nombre, dni, email, pass, nivelCuenta, qPrestamos) VALUES ('pepe', 12457889, 'pepe@pepe.com', '123456', 'normal', 0)");	
		connection.createStatement().execute("INSERT INTO empleado (nombre, dni, email, pass, nroLegajo, cargo) VALUES ('Peter', 56784512, 'Peter35@gmail.com','Spider35', 35 ,'Administrativo - Día')");
		connection.createStatement().execute("INSERT INTO libro (titulo, nombreAutor, fechaPublicacion, editorial, ISBN) VALUES ('La espada del destino','Andrzej Sapkowski', 1993, 'ARTIFEX', '98891058')");
		connection.createStatement().execute("INSERT INTO libro (titulo, nombreAutor, fechaPublicacion, editorial, ISBN) VALUES ('La sangre de los elfos','Andrzej Sapkowski', 1994, 'ARTIFEX', '98891065')");
		connection.createStatement().execute("INSERT INTO libro (titulo, nombreAutor, fechaPublicacion, editorial, ISBN) VALUES ('Tiempo de Odio','Andrzej Sapkowski', 1995, 'ARTIFEX', '98891072')");	
		connection.createStatement().execute("INSERT INTO libro (titulo, nombreAutor, fechaPublicacion, editorial, ISBN) VALUES ('Bautismo de fuego','Andrzej Sapkowski', 1996, 'ARTIFEX', '98891089')");	
		connection.createStatement().execute("INSERT INTO libro (titulo, nombreAutor, fechaPublicacion, editorial, ISBN) VALUES ('La torre de la golondrina','Andrzej Sapkowski', 1997, 'ARTIFEX', '98891096')");
		connection.createStatement().execute("INSERT INTO libro (titulo, nombreAutor, fechaPublicacion, editorial, ISBN) VALUES ('La dama del lago','Andrzej Sapkowski', 1999, 'ARTIFEX', '98891102')");
		connection.createStatement().execute("INSERT INTO libro (titulo, nombreAutor, fechaPublicacion, editorial, ISBN) VALUES ('Estacion de tormentas','Andrzej Sapkowski', 2013, 'ARTIFEX', '98891116')");
		connection.createStatement().execute("INSERT INTO libro (titulo, nombreAutor, fechaPublicacion, editorial, ISBN) VALUES ('Estudio en escarlata','Arthur Conan Doyle', 2007, 'Gradifco', '1093038')");
		connection.createStatement().execute("INSERT INTO libro (titulo, nombreAutor, fechaPublicacion, editorial, ISBN) VALUES ('El sabueso de Baskerville','Arthur Conan Doyle', 2007, 'Gradifco', '1093052')");
		connection.createStatement().execute("INSERT INTO libro (titulo, nombreAutor, fechaPublicacion, editorial, ISBN) VALUES ('El Signo de los Cuatro','Arthur Conan Doyle', 2010, 'Gargola', '09051188')");
		connection.createStatement().execute("INSERT INTO libro (titulo, nombreAutor, fechaPublicacion, editorial, ISBN) VALUES ('El calle del terror','Arthur Conan Doyle', 2015, 'Gargola', '76130929')");
	}	
	
	@AfterEach
	public void tearDown() throws SQLException {
		Conexion conexionBD = Conexion.getInstancia();
		Connection connection = conexionBD.getConnection();
		// Limpiar las tablas al finalizar cada prueba
		connection.createStatement().execute("DELETE FROM cliente WHERE email = 'juan@juan.com'");
		connection.createStatement().execute("DELETE FROM empleado WHERE email = 'Bruce45@gmail.com'");
	}

	@Test
	public void testInicioSesionValido() throws SQLException, RegistroInvalidoException {
		Sesion sesion = Sesion.getInstancia();
		Conexion conexionBD = Conexion.getInstancia();
		Connection connection = conexionBD.getConnection();	
		connection.createStatement().execute("INSERT INTO cliente (nombre, dni, email, pass, nivelCuenta, qPrestamos) VALUES ('Juan', 12457878, 'juan@juan.com', 'pass123', 'normal', 0)");
		sesion = sesion.iniciarSesion(connection, "juan@juan.com", "pass123");
		assertNotNull(sesion, "El cliente debería haberse encontrado.");
		assertEquals(12457878, sesion.getDNI());
		assertEquals("Juan", sesion.getNombre());
		assertEquals("cliente", sesion.getTipoUsuario());
		}
	
	@Test
	public void testAgregarLibro() throws SQLException, RegistroInvalidoException, ISBNDuplicadoException{
		LibroDAO agregarLibro = new LibroDAO();
		LibroDTO libro = new LibroDTO();
		Sesion sesion = Sesion.getInstancia();
		Conexion conexionBD = Conexion.getInstancia();
		Connection connection = conexionBD.getConnection();
		connection.createStatement().execute("INSERT INTO empleado (nombre, dni, email, pass, nroLegajo, cargo) VALUES ('Bruce', 45781256, 'Bruce45@gmail.com','Batman45', 45 ,'Administrativo - Noche')");
		sesion = sesion.iniciarSesion(connection, "Bruce45@gmail.com", "Batman45");
		agregarLibro.agregarLibro(connection, "El último deseo", "Andrzej Sapkowski", 1993, "ARTIFEX", "98891041"); //Este registro tampoco se borrara de la bd automaticamente
		libro = agregarLibro.obtenerDatosLibro(connection, "98891041" );
		assertEquals("El último deseo", libro.getTitulo());
		assertEquals("ARTIFEX", libro.getEditorial());
		assertEquals("98891041", libro.getISBN());
	}
}
