package t1lago;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;


import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLManager implements Funcionalidad{

	private Properties prop;
	Visor v = new Visor();
	XMLManager(){
		try {
			
			prop = new Properties();
			InputStream input = new FileInputStream("src/Ficheros/config.properties");
			prop.load(input);
		} catch (FileNotFoundException e) {
			v.MensajeErrorRutaProp();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//Método para leer un fichero xml y devolver un hashmap con el contenido
	public HashMap<Integer,Persona> Leer() {
		
		HashMap<Integer,Persona> miHM = new HashMap<>();

		

		try {
			File inputFile = new File(prop.getProperty("rutaxml"));
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(inputFile);
			//System.out.println("Root element :" + document.getRootElement().getName());
			Element classElement = document.getRootElement();

			List<Element> studentList = classElement.getChildren();
			//System.out.println("----------------------------");

			
			for (int temp = 0; temp < studentList.size(); temp++) {
				Element elemento = studentList.get(temp);
				
				int id = Integer.parseInt(elemento.getAttributeValue("id"));
				String nombre = elemento.getChild("nombre").getText();
				String apellido= elemento.getChild("apellido").getText();
				double nota = Double.parseDouble(elemento.getChild("nota").getText());
				String grado= elemento.getChild("curso").getText();
				
				
				Persona pers = new Persona(id, nombre, apellido, nota, grado);
				
				miHM.put(pers.getId(), pers);
				
				
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		return miHM;
	}
	
	//Método para comprobar si el id pasado por parámetro ya existe en otra persona
	public boolean ComprobarID(int id) {
		boolean b = false;
		try {
			File inputFile = new File(prop.getProperty("rutaxml"));
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(inputFile);
			System.out.println("Root element : " + document.getRootElement().getName());
			Element classElement = document.getRootElement();
			
			List<Element> studentList = classElement.getChildren();
			ArrayList<Integer> al = new ArrayList<Integer>();
			for (int temp = 0; temp < studentList.size(); temp++) {
				Element student = studentList.get(temp);
				al.add(Integer.parseInt(student.getAttributeValue("id")));
				
			}
			if(al.contains(id)) {
				b = true;
			}else {
				b = false;
			}
			
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return b;
	}
	
	//Método para añadir una nueva persona a un fichero xml
	public void NuevaPersona(Persona p)  {
		
		try {
			File outputFile = new File(prop.getProperty("rutaxml"));
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(outputFile);
			System.out.println("Root element :" + document.getRootElement().getName());
			Element raiz = document.getRootElement();
			
			/*
			 * Creamos nodo empleado y sus hijos (apellido, nombre y salario)
			 * Como son elementos simples a�adimos nodo de texto con valor
			 */
			
			Element persona = new Element("Persona");
			/*
			 * Creamos y a�adimos atributo
			 */
			Attribute attr = new Attribute("id",String.valueOf(p.getId()));
			persona.setAttribute(attr);
			
			raiz.addContent(persona);
			Element nombre = new Element("nombre");
			nombre.setText(p.getNombre());
			
			Element apellido = new Element("apellido");
			apellido.setText(p.getApellido());
			
			Element nota = new Element("nota");
			nota.setText(Double.toString(p.getNota()));
			
			Element curso = new Element("curso");
			curso.setText(p.getGrado());
					
			
			/*
			 * A�adimos hijos al elemento empleado
			 */
			
			persona.addContent(nombre);
			persona.addContent(apellido);
			persona.addContent(nota);
			persona.addContent(curso);	

			
			/*
			 * Volvemos a escribir el fichero
			 */
			
			
			Format f = Format.getPrettyFormat (); // Formato de visualizaci�n perfecto de xml
			// Format f = Format.getCompactFormat (); // El formato de visualizaci�n compacto de xml
			f.setEncoding("gbk");
			f.setOmitDeclaration(false);

			// Genera un archivo xml mediante transmisi�n; escribe un �rbol DOM desde la memoria al disco duro.
			XMLOutputter xmlOut = new XMLOutputter(f);
			xmlOut.output(raiz, new FileOutputStream(outputFile));		
			
			System.out.println("Añadido un nuevo elemento");
			
		}catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}

	//Método para pedir una persona por pantalla
	public Persona PedirPersona(Scanner sc) {
	
		Persona p = new Persona();
		boolean b = true;
		
		p = v.MenuPedirPersona();
		while(b) {
			b = ComprobarID(p.getId());
			if(b) {
				v.MensajeErrorID();
				p.setId(v.MenuPedirID());
			}
		}
	return p;
}
	//Método para escribir un hashmap pasado por parámetro a un fichero xml
	public void Escribir(HashMap<Integer, Persona> hm) {
		
		try {
			File fi = new File(prop.getProperty("rutaxml"));
			Element raiz = new Element("Personas");			
			
			for (HashMap.Entry<Integer, Persona> entry : hm.entrySet()) {
				Element persona = new Element("Persona");
				raiz.addContent(persona);
				
				Attribute attr = new Attribute("id",String.valueOf(entry.getValue().getId()));
				persona.setAttribute(attr);
				
				Element nombre = new Element("nombre");
				nombre.setText(entry.getValue().getNombre());
				
				Element apellido = new Element("apellido");
				apellido.setText(entry.getValue().getApellido());
				
				Element nota = new Element("nota");
				nota.setText(Double.toString(entry.getValue().getNota()));
				
				Element curso = new Element("curso");
				curso.setText(entry.getValue().getGrado());
				
				
				persona.addContent(nombre);
				persona.addContent(apellido);
				persona.addContent(nota);
				persona.addContent(curso);	

			}
			
			
			Format f = Format.getPrettyFormat (); // Formato de visualizaci�n perfecto de xml
			// Format f = Format.getCompactFormat (); // El formato de visualizaci�n compacto de xml
			f.setEncoding("gbk");
			f.setOmitDeclaration(false);

			// Genera un archivo xml mediante transmisi�n; escribe un �rbol DOM desde la memoria al disco duro.
			XMLOutputter xmlOut = new XMLOutputter(f);
			xmlOut.output(raiz, new FileOutputStream(fi));
	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//Método para eliminar una persona de un fichero xml a partir del id parasdo por parámetro
	public void EliminarPersona(int id) {
		HashMap<Integer, Persona> hm = new HashMap<>();
		
		hm = Leer();
		hm.remove(id);
		Escribir(hm);
		
	}
	
	//Método para modificar una persona de un fichero xml a partir del id pasado por parámetro
	public void ModificarPersona(Persona p) {
		HashMap<Integer, Persona> hm = new HashMap<>();
		
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

	//Método para buscar una persona en un fichero xml a partir de una cadena pasada por parámetro
	//Este método busca si esa persona contiene esa cadena de caracteres en cualquiera de sus componentes (id, nombre, apellido, nota, curso)
	public HashMap<Integer, Persona> BuscarPersonaSt(HashMap<Integer, Persona> hm, String s) {
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
