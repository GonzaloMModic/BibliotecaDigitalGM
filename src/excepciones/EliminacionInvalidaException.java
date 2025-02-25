package excepciones;

public class EliminacionInvalidaException extends Exception{
	
	public EliminacionInvalidaException (String mensaje) {
		super(mensaje); 
	}
}