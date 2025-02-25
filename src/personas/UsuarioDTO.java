package personas;

public abstract class UsuarioDTO {
	protected String nombre;
	protected int dni;
	protected String email;
	protected String pass;
	
	public UsuarioDTO() {
		
	}
	
	public UsuarioDTO(String nombre, int dni, String email, String pass) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.pass = pass;
    }
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getDNI() {
		return dni;
	}
	public void setDNI(int dni) {
		this.dni = dni;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "UsuarioDTO nombre=" + nombre + ", dni=" + dni + ", email=" + email + ", pass=" + pass;
	}
	
}
