package excepciones;

public class EmailDuplicadoException extends Exception{
	
	public EmailDuplicadoException (String mensaje) {
		super(mensaje); 
	}
}
