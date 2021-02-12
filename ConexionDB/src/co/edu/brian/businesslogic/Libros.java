/**
 * 
 */
package co.edu.brian.businesslogic;

/**
 * @author brian-pc
 *
 */
public class Libros {
	
	private int id_libro;
	private String nombreLibro;
	private String editorial;
	private String autor;
	private double precio;


	public Libros() {
		id_libro     = 0;
		nombreLibro  = null;
		editorial    = null;
		autor        = null;
		precio       = 0.0;
	}	

	public int getId_libro() {
		return id_libro;
	}
	public void setId_libro(int id_libro) {
		this.id_libro = id_libro;
	}
	
	public String getNombreLibro() {
		return nombreLibro;
	}
	
	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
	}
	
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}


}
