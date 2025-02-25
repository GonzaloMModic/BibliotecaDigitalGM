package personas;

public abstract class EmpleadoDTO extends UsuarioDTO {
	private int nroLegajo;
	private String cargo;

	public EmpleadoDTO(String nombre, int dni, String email, String contraseña, int nroLegajo, String cargo) {
		super(nombre, dni, email, contraseña);
		this.cargo = cargo;
		this.nroLegajo = nroLegajo;
	}

	public EmpleadoDTO() {
	}

	public int getNroLegajo() {
		return nroLegajo;
	}

	public void setNroLegajo(int nroLegajo) {
		this.nroLegajo = nroLegajo;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
}