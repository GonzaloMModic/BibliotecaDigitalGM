package excepciones;

public class DniDuplicadoException extends Exception{
	
	public DniDuplicadoException (String mensaje) {
		super(mensaje); 
	}
}