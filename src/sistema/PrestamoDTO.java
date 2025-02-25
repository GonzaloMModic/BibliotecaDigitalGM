package sistema;
import java.util.Date;

public class PrestamoDTO {
	protected int dniCliente;
	protected String ISBNLibro;
	protected Date fechaInicio;
	protected Date fechaFin;
	protected String estado;

	public PrestamoDTO() {
	}

	public int getDniCliente() {
		return dniCliente;
	}

	public void setDniCliente(int dniCliente) {
		this.dniCliente = dniCliente;
	}

	public String getISBNLibro() {
		return ISBNLibro;
	}

	public void setISBNLibro(String iSBNLibro) {
		ISBNLibro = iSBNLibro;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String toString() {
		return "[DNI Cliente = " + dniCliente + ", ISBN Libro = " + ISBNLibro + ", fechaInicio = " + fechaInicio
				+ ", fechaFin = " + fechaFin + ", estado = " + estado + "]";
	}
}
