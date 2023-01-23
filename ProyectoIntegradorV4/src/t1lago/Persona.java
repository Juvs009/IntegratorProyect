package t1lago;

//Clase persona con sus atributos, sus setters y getters y su toString modificado
public class Persona {

	private int id;
	private String nombre;
	private String apellido;
	private double nota;
	private String grado;
	
	public Persona(int id, String nombre, String apellido, double nota, String grado) {
		this.id=id;
		this.nombre=nombre;
		this.apellido=apellido;
		this.nota=nota;
		this.grado=grado;
		
	}
	public Persona() {
		
	}	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public double getNota() {
		return nota;
	}
	public void setNota(double nota) {
		this.nota = nota;
	}
	public String getGrado() {
		return grado;
	}
	public void setGrado(String grado) {
		this.grado = grado;
	}
	@Override
	public String toString() {
		return id+"*"+nombre+"*"+apellido+"*"+nota+"*"+grado;
	}
	
}
