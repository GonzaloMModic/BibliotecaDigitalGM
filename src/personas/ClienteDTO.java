package personas;

public abstract class ClienteDTO extends UsuarioDTO{
	private String nivelCuenta;	
	private int qPrestamos;

	public ClienteDTO(String nombre, int dni, String email, String contraseña, String nivelCuenta, int qPrestamos) {
		super(nombre, dni, email, contraseña);
		this.nivelCuenta = nivelCuenta;	
		this.qPrestamos = qPrestamos;
	}

	public ClienteDTO() {
	}

	public String getNivelCuenta() {
		return nivelCuenta;
	}

	public void setNivelCuenta(String nivelCuenta) {
		this.nivelCuenta = nivelCuenta;
	}

	public int getqPrestamos() {
		return qPrestamos;
	}

	public void setqPrestamos(int qPrestamos) {
		this.qPrestamos = qPrestamos;
	}
}