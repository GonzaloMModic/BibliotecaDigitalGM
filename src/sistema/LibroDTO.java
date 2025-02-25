package sistema;

public class LibroDTO {
	private String 	titulo; 
    private String 	autor; 
    private int aPubli;
    private String 	editorial; 
    private boolean 	prestado;
    private String 	ISBN;
    
    public LibroDTO() {
    	
    }

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getaPubli() {
		return aPubli;
	}

	public void setaPubli(int aPubli) {
		this.aPubli = aPubli;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public boolean isPrestado() {
		return prestado;
	}

	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	@Override
	public String toString() {
		return "Título: " + titulo + ", Autor: " + autor + ", Año de publicación: " + aPubli + ", Editorial: " + editorial + ", Stock: " + prestado
				+ ", ISBN: " + ISBN + "\n";
	} 	
}