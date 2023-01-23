package t1lago;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class FileManager  implements Funcionalidad {
	
	
	private Properties prop;
	Visor v = new Visor();
	
	FileManager(){
		try {
			prop = new Properties();
			InputStream input = new FileInputStream("src/Ficheros/config.properties");
			this.prop.load(input);
		} catch (FileNotFoundException e) {
			v.MensajeErrorRutaProp();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Método para leer un fichero de texto y devolver un hashmap con el contenido del fichero txt
	public HashMap<Integer, Persona> Leer() {
		HashMap<Integer, Persona> hm = new HashMap<Integer, Persona>();
		try {

			File f = new File(prop.getProperty("rutatxt"));
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			

			String linea;

			while ((linea = br.readLine()) != null) {
				Persona p = new Persona();
				String[] sp = linea.split("\\*");
				p.setId(Integer.valueOf(sp[0]));
				p.setNombre(sp[1]);
				p.setApellido(sp[2]);
				p.setNota(Double.valueOf(sp[3]));
				p.setGrado(sp[4]);

				hm.put(p.getId(), p);
				

			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}
	
	
	//Método para escribir en un fichero txt un hashmap pasado por parámetro
	public void EscribirF(HashMap<Integer, Persona> txt) {
		
		try {
		
			File f = new File(prop.getProperty("rutatxt"));
			FileWriter fw = new FileWriter(f,true);
			BufferedWriter bw = new BufferedWriter(fw);
		

			
			for (HashMap.Entry<Integer, Persona> entry : txt.entrySet()) {
				
			    bw.write(entry.getValue().toString());
			    bw.write("\n");
			}

			
		
		bw.close();
		fw.close();
		}catch(IOException e) {
			System.out.println("Error IO");
		}
		
	}
	
	//Método para añadir una nueva persona a un fichero txt
	public void NuevaPersona(Persona p){
		HashMap<Integer,Persona> pers = new HashMap<Integer,Persona>();
		
		
		pers.put(p.getId(), p);
		System.out.println(pers);
		
		EscribirF(pers);
		
	}
	
	//Método para comprobar si el id que se le está intentando añadir a una persona ya existe
	public boolean ComprobarID(int idn) {
		
		HashMap<Integer, Persona> hm = new HashMap<Integer,Persona>();
		hm = Leer();
		
		
		if(hm.containsKey(idn)) {
			return true;
		}else {
			return false;
		}
		
		
	}
	
	//Método para sobreescribir un fichero txt con el contenido de un hashmap pasado por parámetros
	public void Escribir(HashMap<Integer, Persona> txt) {
		
		try {
			File f = new File(prop.getProperty("rutatxt"));
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
		

			
			for (HashMap.Entry<Integer, Persona> entry : txt.entrySet()) {
				
			    bw.write(entry.getValue().toString());
			    bw.write("\n");
			}

			
		
		bw.close();
		fw.close();
		}catch(IOException e) {
			System.out.println("Error IO");
		}
		
	}

	//Método para eliminar una persona de un fichero txt a partir de un id pasado por parámetro
	public void EliminarPersona(int idb) {
		HashMap<Integer, Persona> hm = new HashMap<Integer,Persona>();
		hm = Leer();

		hm.remove(idb);
		Escribir(hm);
		
		
	}

	//Método para midificar una persona de un fichero txt a partir de un id pasado por parámetro
	public void ModificarPersona(Persona p) {
		HashMap<Integer, Persona> hm = new HashMap<Integer,Persona>();
		
		hm = Leer();
		hm.replace(p.getId(), p);
		Escribir(hm);

	}

	
	public Persona BuscarPersonaID(int id) {
		try {
		HashMap<Integer, Persona> hm = new HashMap<Integer, Persona>();
		Persona p = new Persona();
		hm = Leer();
		p = hm.get(id);
		if(p.getId()!=0) {
			return p;
			}else {
				return null;
			}
		}catch(NullPointerException np) {
			return null;
		}
		
	}
	//Método para buscar una persona en un fichero txt a partir de una cadena pasada por parámetro
	//Este método busca si esa persona contiene esa cadena de caracteres en cualquiera de sus componentes (id, nombre, apellido, nota, curso)
	public HashMap<Integer,Persona> BuscarPersonaSt(HashMap<Integer, Persona> hm, String s) {
		HashMap<Integer, Persona> map = new HashMap<>();
		
		for(Map.Entry<Integer, Persona> entry : hm.entrySet()) {
			Persona p = new Persona();
			if(entry.getValue().toString().toLowerCase().contains(s.toLowerCase())){
				p = entry.getValue();
				map.put(p.getId(), p);
			}
			
		}
		return map;
		
}



	
	
}
