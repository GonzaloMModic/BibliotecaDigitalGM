package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import excepciones.EliminacionInvalidaException;
import excepciones.ISBNDuplicadoException;
import excepciones.RegistroInvalidoException;

public class LibroDAO {	
	
	public void agregarLibro(Connection connection, String titulo, String nombreAutor, int fechaPublicacion, String editorial, String ISBN ) throws SQLException, RegistroInvalidoException, ISBNDuplicadoException {
		String fechaVerif = String.valueOf(fechaPublicacion);
		
		if(titulo.isEmpty() || nombreAutor.isEmpty() || fechaVerif.isEmpty() || editorial.isEmpty() || ISBN.isEmpty()) {
			throw new RegistroInvalidoException("Error: No puede haber casillas vacias");
		}		
		
		String sqlVerif = "SELECT ISBN FROM libro WHERE ISBN = ?";
		try (PreparedStatement pstmt1 = connection.prepareStatement(sqlVerif)) {
            pstmt1.setString(1, ISBN);
            try (ResultSet resultSet = pstmt1.executeQuery()) {
                if (resultSet.next()) {
                    throw new ISBNDuplicadoException("Error de sistema: ISBN ya registrado");
                }
            }			
		
			String sql = "INSERT INTO libro (titulo, nombreAutor, fechaPublicacion, editorial, ISBN) VALUES (?,?,?,?,?)";
			try(PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setString(1, titulo);
				pstmt.setString(2, nombreAutor);
				pstmt.setInt(3, fechaPublicacion);
				pstmt.setString(4, editorial);
				pstmt.setString(5, ISBN);
				
				int filasAfectadas = pstmt.executeUpdate();
				
				if (filasAfectadas > 0) {
					JOptionPane.showMessageDialog(null, "Libro agregado correctamente");
				}
			} 	catch (SQLException e) {
				e.printStackTrace();
				}	
		} 
	}
	//Metodos para actualizar libros
	public LibroDTO obtenerDatosLibro(Connection connection, String ISBN) throws SQLException, RegistroInvalidoException {
		if(ISBN.isEmpty()) {
			throw new RegistroInvalidoException("Error: No puede haber casillas vacias");
		}
	    String sql = "SELECT * FROM libro WHERE ISBN = ?";
	    LibroDTO libro = null;

	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, ISBN);
	        try (ResultSet resultSet = pstmt.executeQuery()) {
	            if (resultSet.next()) {
	            	libro = new LibroDTO();
	            	libro.setTitulo(resultSet.getString("titulo"));
	            	libro.setAutor(resultSet.getString("nombreAutor"));
	            	libro.setEditorial(resultSet.getString("editorial"));
	            	libro.setaPubli(resultSet.getInt("fechaPublicacion"));
	            	libro.setISBN(resultSet.getString("ISBN"));
	            	libro.setPrestado(resultSet.getBoolean("prestado"));	            	
	            } 
	            else {
	            	JOptionPane.showMessageDialog(null, "El libro buscado no existe");
	            	throw new RegistroInvalidoException("El libro buscado no existe.");
	            }
	        }
	    }
	    return libro;
	}
	
	public void actualizarLibro(Connection connection, LibroDTO libro, String nuevoTitulo, String nuevoAutor, int nuevoAPubli, String nuevoEditorial, Boolean nuevoPrestado) throws SQLException {
	    String sql = "UPDATE libro SET titulo = ?, nombreAutor = ?, fechaPublicacion = ?, editorial = ?,prestado = ? WHERE ISBN = ?";
	    
	    String nuevoPubliVerif =  String.valueOf(nuevoAPubli);
	    
	    if (nuevoTitulo == null || nuevoTitulo.isEmpty()) {
	        nuevoTitulo = libro.getTitulo();
	    }
	    if (nuevoAutor == null || nuevoAutor.isEmpty()) {
	        nuevoAutor = libro.getAutor();
	    }
	    if (nuevoEditorial == null || nuevoEditorial.isEmpty()) {
	        nuevoEditorial = libro.getEditorial();
	    }
	    if (nuevoPubliVerif == null || nuevoPubliVerif.isEmpty()) {
	    	nuevoAPubli = libro.getaPubli();
	    }

	    if (nuevoPrestado == null) {
	        nuevoPrestado = libro.isPrestado();
	    }

	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, nuevoTitulo);
	        pstmt.setString(2, nuevoAutor);
	        pstmt.setInt(3, nuevoAPubli);
	        pstmt.setString(4, nuevoEditorial);	       
	        pstmt.setBoolean(5, nuevoPrestado); 
	        pstmt.setString(6, libro.getISBN()); 

	        pstmt.executeUpdate();
	    }
	}
	//Fin de metodos para actualizar libros
	
	//Metodo general para buscar libros
	
	public List<LibroDTO> obtenerLibros(Connection connection, String busqueda) throws SQLException, RegistroInvalidoException {	
		if(busqueda.isEmpty()) {
			throw new RegistroInvalidoException("Error: No puede haber casillas vacias");
		}
	    String sql = "SELECT * FROM libro WHERE titulo LIKE ? OR nombreAutor LIKE ? OR editorial LIKE ? OR ISBN LIKE ?";

	    List <LibroDTO> libros = new ArrayList<>();

	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	    	String busquedaP = "%" + busqueda + "%"; //El % es para que busque coincidencias en cualquier parte del texto
	        pstmt.setString(1, busquedaP);
	        pstmt.setString(2, busquedaP);
	        pstmt.setString(3, busquedaP);
	        pstmt.setString(4, busquedaP);	        
	        
	        try (ResultSet resultSet = pstmt.executeQuery()) {
	            while (resultSet.next()) {
	            	LibroDTO Libro = new LibroDTO();
	            	Libro.setTitulo(resultSet.getString("titulo"));
	            	Libro.setAutor(resultSet.getString("nombreAutor"));
	            	Libro.setEditorial(resultSet.getString("editorial"));
	            	Libro.setPrestado(resultSet.getBoolean("prestado"));
	            	Libro.setISBN(resultSet.getString("ISBN"));
	            	Libro.setaPubli(resultSet.getInt("fechaPublicacion"));
	            	libros.add(Libro);
	            }
	        }
	    }
	    return libros;
	}
	
	public List<LibroDTO> obtenerTodosLosLibros(Connection connection) throws SQLException{
		
		String sql = "SELECT * FROM libro";
		List <LibroDTO> libros = new ArrayList<>();
		
		try(Statement stmt = (Statement) connection.createStatement()){
			ResultSet resultSet = stmt.executeQuery(sql);			
			
			while (resultSet.next()) {
				LibroDTO libro = new LibroDTO();
				
				libro.setTitulo(resultSet.getString("titulo"));
				libro.setAutor(resultSet.getString("nombreAutor"));
				libro.setEditorial(resultSet.getString("editorial"));
				libro.setISBN(resultSet.getString("ISBN"));
				libro.setPrestado(resultSet.getBoolean("prestado"));
				libro.setaPubli(resultSet.getInt("fechaPublicacion"));
				
				libros.add(libro);
			}	
		}
		return libros;
	}
	//Metodo para eliminar Libros
	public void eliminarLibro(Connection connection, String ISBN) throws SQLException, RegistroInvalidoException, EliminacionInvalidaException {
		
		String sqlVerif= "SELECT prestado FROM libro WHERE ISBN = ?";
		
		try (PreparedStatement pstmtVerif = connection.prepareStatement(sqlVerif)){
			pstmtVerif.setString(1, ISBN);
			try (ResultSet resultSet = pstmtVerif.executeQuery()){
				if (resultSet.next()) {
	                boolean estaPrestado = resultSet.getBoolean("prestado");
	                if (!estaPrestado) {
	                    throw new EliminacionInvalidaException("No se puede eliminar el libro ya que actualmente está prestado.");
	                }
	            }  else {
	                throw new SQLException("El ISBN proporcionado no existe en la base de datos.");
	            }
			}
		}			
		
		String sql = "DELETE FROM libro WHERE ISBN = ?";		
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, ISBN);	        
	        int filasActualizadas = pstmt.executeUpdate();   
	        if (filasActualizadas > 0) {
		        JOptionPane.showMessageDialog(null, "Libro eliminado correctamente");
	        } else {
	        	JOptionPane.showMessageDialog(null, "ISBN no identificado, por favor revise los datos ingresados");
	        }
		}  	
	}
}