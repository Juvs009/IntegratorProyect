package t1lago;

import java.util.HashMap;

//Interfaz con los diferentes métodos principales de las clases
public interface Funcionalidad {

	public void NuevaPersona(Persona p);
	public boolean ComprobarID(int id);
	public HashMap<Integer, Persona> Leer();
	public void EliminarPersona(int id);
	public void ModificarPersona(Persona p);
	public Persona BuscarPersonaID(int id);
	public HashMap<Integer,Persona> BuscarPersonaSt(HashMap<Integer, Persona> hm, String s);
	public void Escribir(HashMap<Integer, Persona> hm);
	
	
}
