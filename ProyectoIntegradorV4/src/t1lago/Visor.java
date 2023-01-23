package t1lago;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;


//Clase visor para imprimir por pantalla la mayoría de elementos
public class Visor {
	
	Scanner s = new Scanner(System.in);

	public int MenuFunc() {
		int f = 0;
		while(f<1 || f>5) {
			System.out.println("Selecciona con que tipo de fichero quieres trabajar");
			System.out.println("1. Fichero txt");
			System.out.println("2. Fichero xml");
			System.out.println("3. Base de datos sql");
			System.out.println("4. Hibernate");
			System.out.println("5. JSON");
			f = s.nextInt();
			if(f<1 || f>5)System.out.println("Opción no válida \n");
		}
		return f;
	}
	
	public int MenuOperacion() {
		int o = 0;
		while(o<1 || o>9) {
			System.out.println("Selecciona una opción:");
			System.out.println("1. Mostrar lista");
			System.out.println("2. Borrar persona");
			System.out.println("3. Añadir persona");
			System.out.println("4. Modificar persona");
			System.out.println("5. Buscar persona por id");
			System.out.println("6. Buscar persona por texto");
			System.out.println("7. Exportar");
			System.out.println("8. Volver a elegir el tipo de fichero");
			System.out.println("9. Salir");
			o = s.nextInt();
			if(o<1 || o>9) System.out.println("Opción inválida");
		}
		return o;
	}
	
	public Persona MenuPedirPersona() throws InputMismatchException {
		Persona p = new Persona();
		System.out.println("Introduce el ID: ");
		int id = s.nextInt();
		p.setId(id);
		System.out.println("Introduce el nombre: ");
		p.setNombre(s.next());
		System.out.println("Introduce el apellido: ");
		p.setApellido(s.next());
		System.out.println("Introduce la nota: ");
		p.setNota(s.nextDouble());
		System.out.println("Introduce el curso");
		p.setGrado(s.next());
		return p;
	}
	
	public int MenuPedirID() {
		System.out.println("Introduce el ID: ");
		int id = s.nextInt();
		return id;
	}
	
	public String MenuPedirString() {
		System.out.println("Introduce los caracteres por los que quieres filtrar");
		String st = s.next();
		return st;
	}
	
	public Funcionalidad MenuExportar() {
		Funcionalidad fc = null;
		int i = 0;
		while(fc == null) {
			System.out.println("Exportar");
			System.out.println("1. Exportar a txt");
			System.out.println("2. Exportar a xml");
			System.out.println("3. Exportar a Base de datos sql");
			System.out.println("4. Exportar a hibernate");
			System.out.println("5. Exportar a JSON");
			i = s.nextInt();
			switch(i) {
			case 1:
				fc = new FileManager();
				break;
			case 2:
				fc = new XMLManager();
				break;
			case 3:
				fc = new DBManager();
				break;
			case 4:
				fc = new AccesoHibernate();
				break;
			case 5:
				fc = new JSONManager();
				break;
				default: System.out.println("Número incorrecto");
			}

		}
		return fc;
	}
	
	public void MostrarPersonas(HashMap<Integer, Persona> hm) {
		  for (Integer key: hm.keySet()){ 
		      System.out.println("ID: "+
			  hm.get(key).getId()+"\nNombre: "+hm.get(key).getNombre()+"\nApellido: "+hm.
			  get(key).getApellido()
			  +"\nNota: "+hm.get(key).getNota()+"\nGrado: "+hm.get(key).getGrado()+"\n");
			  }
	}
	

	
	//Diferentes mensajes para escribir por pantalla ante errores o ejecuciones correctas
	public void MensajeErrorPersona() {
		System.err.println("Esa persona no existe\n");
	}
	public void MensajeErrorID() {
		System.err.println("Ese id ya existe, prueba otro\n");
	}
	public void MensajeErrorRutaProp() {
		System.err.println("La ruta del fichero properties no es correcta\n");
	}
	public void MensajeErrorExportarAFichero() {
		System.out.println("¡No puedes exportar de un fichero txt al mismo fichero txt!\n");
	}
	public void MensajeErrorExportar() {
		System.err.println("Ha habido un error al exportar el archivo\n");
	}
	public void MensajeExportacionCorrecta() {
		System.out.println("Se ha exportado correctamente\n");
	}
	public void MensajeErrorExportarAXml() {
		System.out.println("¡No puedes exportar de un archivo xml al mismo archivo xml!\n");
	}
	public void MensajeErrorInput() {
		System.err.println("En caso de decimales se tendrá que escribir una ,\n");
	}
	public void MensajeIDDuplicado() {
		System.err.println("El id está duplicado, ¡tienes que elegir uno nuevo!");
	}
	public void MensajeErrorModificar() {
		System.out.println("Ese id no existe");
	}
	public void MensajePersonaModificada() {
		System.out.println("¡Persona modificada!");
	}
	public void MensajePersonaBorrada() {
		System.out.println("¡Persona borrada!");
	}
	public void MensajeAdios() {
		System.out.println("Adiós");
	}
	public void MensajeTipo() {
		System.out.println("Volverás a elegir el tipo de fichero");
	}
}
