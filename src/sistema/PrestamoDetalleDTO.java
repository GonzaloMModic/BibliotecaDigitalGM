package sistema;

public class PrestamoDetalleDTO extends PrestamoDTO {
	private String nombreCliente; 
    private String tituloLibro;   

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }
    
    public void setDniCliente(int dniCliente) {
		this.dniCliente = dniCliente;
	}

	public void setISBNLibro(String iSBNLibro) {
		ISBNLibro = iSBNLibro;
	}
	@Override
	public String toString() {
		return "Título libro = " + tituloLibro + "\n" +super.toString();
	}
}