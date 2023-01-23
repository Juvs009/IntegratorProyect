package t1lago;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import utils.ApiRequests;

public class JSONManager implements Funcionalidad {

	ApiRequests encargadoPeticiones;
	private Properties prop;
	private String SERVER_PATH, GET_PER, UPDATE_PER, NEW_PER, DEL_PER, BUS_PER, BUSST_PER, ESC_PER; // Datos de la conexion
	
	public JSONManager() {
		
		try {
			prop = new Properties();
			InputStream input = new FileInputStream("src/Ficheros/config.properties");
			this.prop.load(input);
		} catch (FileNotFoundException e) {
			System.err.println("La ruta del fichero properties no es correcta\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		encargadoPeticiones = new ApiRequests();
	
		SERVER_PATH = prop.getProperty("rutajson");
		GET_PER = "leePersonas.php";
		UPDATE_PER = "modificaPersona.php";
		NEW_PER = "nuevaPersona.php";
		DEL_PER = "eliminarPersona.php";
		BUS_PER = "buscarPersonaID.php";
		BUSST_PER = "buscarPersonaST.php";
		ESC_PER = "escribirPersonas.php";
	}
	
	
	
	@Override
	public void NuevaPersona(Persona p) {
		try {
			
				
				JSONObject objDeposito = new JSONObject();
				
				objDeposito.put("peticion", "add");
				objDeposito.put("idPersona", p.getId());
				objDeposito.put("nombrePersona", p.getNombre());
				objDeposito.put("apellidoPersona", p.getApellido());
				objDeposito.put("notaPersona", p.getNota());
				objDeposito.put("cursoPersona", p.getGrado());
				
				String json = objDeposito.toJSONString();
				
				String url = SERVER_PATH + NEW_PER;

				System.out.println("La url a la que lanzamos la petici�n es " + url);
				System.out.println("El json que enviamos es: ");
				System.out.println(json);
				//System.exit(-1);

				String response = encargadoPeticiones.postRequest(url, json);
				
				System.out.println("El json que recibimos es: ");
				
				System.out.println(response); // Traza para pruebas
				//System.exit(-1);
				
				// Parseamos la respuesta y la convertimos en un JSONObject
				

				JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

				if (respuesta == null) { // Si hay alg�n error de parseo (json
											// incorrecto porque hay alg�n caracter
											// raro, etc.) la respuesta ser� null
					System.out.println("El json recibido no es correcto. Finaliza la ejecuci�n");
					System.exit(-1);
				} else { // El JSON recibido es correcto
					
					// Sera "ok" si todo ha ido bien o "error" si hay alg�n problema
					String estado = (String) respuesta.get("estado"); 
					if (estado.equals("ok")) {

						System.out.println("Nueva Persona añadida");

					} else { // Hemos recibido el json pero en el estado se nos
								// indica que ha habido alg�n error

						System.out.println("Acceso JSON REMOTO - Error al almacenar los datos");
						System.out.println("Error: " + (String) respuesta.get("error"));
						System.out.println("Consulta: " + (String) respuesta.get("query"));

						System.exit(-1);

					}
			}
			
			} catch (Exception e) {
				System.out.println(
						"Excepcion desconocida. Traza de error comentada en el m�todo 'annadirJugador' de la clase JSON REMOTO");
				// e.printStackTrace();
				System.out.println("Fin ejecuci�n");
				System.exit(-1);
			}
		
	}
	@Override
	public boolean ComprobarID(int id) {
		HashMap<Integer, Persona> hm = new HashMap<Integer,Persona>();
		hm = Leer();
		
		
		if(hm.containsKey(id)) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public HashMap<Integer, Persona> Leer() {
		HashMap<Integer, Persona> aux = new HashMap<>();
		
		try {

			System.out.println("---------- Leemos datos de JSON --------------------");

			System.out.println("Lanzamos peticion JSON para depositos");

			String url = SERVER_PATH + GET_PER; // Sacadas de configuracion

			System.out.println("La url a la que lanzamos la petici�n es " +  url); // Traza para pruebas

			String response = encargadoPeticiones.getRequest(url);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay alg�n error de parseo (json
										// incorrecto porque hay alg�n caracter
										// raro, etc.) la respuesta ser� null
				System.out.println("El json recibido no es correcto. Finaliza la ejecuci�n");
				System.exit(-1);
			} else { // El JSON recibido es correcto
				// Sera "ok" si todo ha ido bien o "error" si hay alg�n problema
				String estado = (String) respuesta.get("estado"); 
				// Si ok, obtenemos array de jugadores para recorrer y generar hashmap
				if (estado.equals("ok")) { 
					JSONArray array = (JSONArray) respuesta.get("Personas");

					if (array.size() > 0) {

						// Declaramos variables
						Persona nuevaPer;
						int id;
						String nombre;
						String apellido;
						double nota;
						String curso;
						

						for (int i = 0; i < array.size(); i++) {
							JSONObject row = (JSONObject) array.get(i);
							
							id = Integer.parseInt(row.get("idPersona").toString());
							nombre = row.get("nombrePersona").toString();
							apellido = row.get("apellidoPersona").toString();
							nota = Double.parseDouble(row.get("notaPersona").toString());
							curso = row.get("cursoPersona").toString();
							

							nuevaPer = new Persona(id, nombre, apellido, nota, curso);

							aux.put(id, nuevaPer);
						}

						System.out.println("Acceso JSON Remoto - Leidos datos correctamente y generado hashmap");
						System.out.println();

					} else { // El array de jugadores est� vac�o
						System.out.println("Acceso JSON Remoto - No hay datos que tratar");
						System.out.println();
					}

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido alg�n error

					System.out.println("Ha ocurrido un error en la busqueda de datos");
					System.out.println("Error: " + (String) respuesta.get("error"));
					System.out.println("Consulta: " + (String) respuesta.get("query"));

					System.exit(-1);

				}
			}

		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en la busqueda de datos");

			e.printStackTrace();

			System.exit(-1);
		}

		return aux;
	}
	@Override
	public void EliminarPersona(int id) {
		try {
			
			
			JSONObject objPersona = new JSONObject();
			
			objPersona.put("peticion", "delete");
			objPersona.put("idPersona", id);
			
			
			String json = objPersona.toJSONString();
			
			String url = SERVER_PATH + DEL_PER;

			System.out.println("La url a la que lanzamos la petici�n es " + url);
			System.out.println("El json que enviamos es: ");
			System.out.println(json);

			String response = encargadoPeticiones.postRequest(url, json);
			
			System.out.println("El json que recibimos es: ");
			
			System.out.println(response); // Traza para pruebas
			
			// Parseamos la respuesta y la convertimos en un JSONObject
			

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay alg�n error de parseo (json
										// incorrecto porque hay alg�n caracter
										// raro, etc.) la respuesta ser� null
				System.out.println("El json recibido no es correcto. Finaliza la ejecuci�n");
				System.exit(-1);
			} else { // El JSON recibido es correcto
				
				// Sera "ok" si todo ha ido bien o "error" si hay alg�n problema
				String estado = (String) respuesta.get("estado"); 
				if (estado.equals("ok")) {

					System.out.println("Persona eliminada");

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido alg�n error

					System.out.println("Acceso JSON REMOTO - Error al almacenar los datos");
					System.out.println("Error: " + (String) respuesta.get("error"));
					System.out.println("Consulta: " + (String) respuesta.get("query"));

					System.exit(-1);

				}
		}
		
		} catch (Exception e) {
			System.out.println(
					"Excepcion desconocida. Traza de error comentada en el m�todo 'annadirJugador' de la clase JSON REMOTO");
			// e.printStackTrace();
			System.out.println("Fin ejecuci�n");
			System.exit(-1);
		}
		
	}
	@Override
	public void ModificarPersona(Persona p) {
		try {
			
			
			JSONObject objPersona = new JSONObject();
			
			objPersona.put("peticion", "update");
			objPersona.put("idPersona", p.getId());
			objPersona.put("nombrePersona", p.getNombre());
			objPersona.put("apellidoPersona", p.getApellido());
			objPersona.put("notaPersona", p.getNota());
			objPersona.put("cursoPersona", p.getGrado());
			
			String json = objPersona.toJSONString();
			
			String url = SERVER_PATH + UPDATE_PER;

			System.out.println("La url a la que lanzamos la petici�n es " + url);
			System.out.println("El json que enviamos es: ");
			System.out.println(json);
			//System.exit(-1);

			String response = encargadoPeticiones.postRequest(url, json);
			
			System.out.println("El json que recibimos es: ");
			
			System.out.println(response); // Traza para pruebas
			
			// Parseamos la respuesta y la convertimos en un JSONObject
			

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay alg�n error de parseo (json
										// incorrecto porque hay alg�n caracter
										// raro, etc.) la respuesta ser� null
				System.out.println("El json recibido no es correcto. Finaliza la ejecuci�n");
				System.exit(-1);
			} else { // El JSON recibido es correcto
				
				// Sera "ok" si todo ha ido bien o "error" si hay alg�n problema
				String estado = (String) respuesta.get("estado"); 
				if (estado.equals("ok")) {

					System.out.println("Persona actualizada");

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido alg�n error

					System.out.println("Acceso JSON REMOTO - Error al almacenar los datos");
					System.out.println("Error: " + (String) respuesta.get("error"));
					System.out.println("Consulta: " + (String) respuesta.get("query"));

					System.exit(-1);

				}
		}
		
		} catch (Exception e) {
			System.out.println(
					"Excepcion desconocida. Traza de error comentada en el m�todo 'annadirJugador' de la clase JSON REMOTO");
			// e.printStackTrace();
			System.out.println("Fin ejecuci�n");
			System.exit(-1);
		}
		
	}
	@Override
	public Persona BuscarPersonaID(int id) {
		try {
			
			Persona per;
			JSONObject objPersona = new JSONObject();
			
			
			objPersona.put("peticion", "buscarID");
			objPersona.put("idPersona", id);
			
			
			String json = objPersona.toJSONString();
			
			String url = SERVER_PATH + BUS_PER;

			System.out.println("La url a la que lanzamos la petici�n es " + url);
			System.out.println("El json que enviamos es: ");
			System.out.println(json);

			String response = encargadoPeticiones.postRequest(url, json);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay alg�n error de parseo (json
										// incorrecto porque hay alg�n caracter
										// raro, etc.) la respuesta ser� null
				System.out.println("El json recibido no es correcto. Finaliza la ejecuci�n");
				return null;
			} else { // El JSON recibido es correcto
				
				// Sera "ok" si todo ha ido bien o "error" si hay alg�n problema
				String estado = (String) respuesta.get("estado"); 
				if (estado.equals("ok")) {
					JSONObject array = (JSONObject) respuesta.get("persona");
					
					int id2 = Integer.parseInt(array.get("idPersona").toString());
					String nombre = array.get("nombrePersona").toString();
					String apellido = array.get("apellidoPersona").toString();
					double nota = Double.parseDouble(array.get("notaPersona").toString());
					String curso = array.get("cursoPersona").toString();
					
					per = new Persona(id2, nombre, apellido, nota, curso);
					
					return per;

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido alg�n error
					return null;

				}
		}
		
		} catch (Exception e) {
			System.out.println(
					"Excepcion desconocida. Traza de error comentada en el m�todo 'annadirJugador' de la clase JSON REMOTO");
			 e.printStackTrace();
			System.out.println("Fin ejecuci�n");
			return null;
		}

	}
	@Override
	public HashMap<Integer, Persona> BuscarPersonaSt(HashMap<Integer, Persona> hm, String s) {
try {
		HashMap<Integer, Persona> aux = new HashMap<>();
			JSONObject objPersona = new JSONObject();
			
			objPersona.put("Mensaje", s);
			objPersona.put("peticion", "buscarST");
			
			String json = objPersona.toJSONString();
			
			String url = SERVER_PATH + BUSST_PER;

			System.out.println("La url a la que lanzamos la petici�n es " + url);
			System.out.println("El json que enviamos es: ");
			System.out.println(json);

			String response = encargadoPeticiones.postRequest(url, json);
			
			

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay alg�n error de parseo (json
										// incorrecto porque hay alg�n caracter
										// raro, etc.) la respuesta ser� null
				System.out.println("El json recibido no es correcto. Finaliza la ejecuci�n");
				return null;
			} else { // El JSON recibido es correcto
				
				// Sera "ok" si todo ha ido bien o "error" si hay alg�n problema
				String estado = (String) respuesta.get("estado"); 
				if (estado.equals("ok")) { 
					JSONArray array = (JSONArray) respuesta.get("Personas");

					if (array.size() > 0) {
						Persona nuevaPer;
						int id;
						String nombre;
						String apellido;
						double nota;
						String curso;
						

						for (int i = 0; i < array.size(); i++) {
							JSONObject row = (JSONObject) array.get(i);
							
							id = Integer.parseInt(row.get("idPersona").toString());
							nombre = row.get("nombrePersona").toString();
							apellido = row.get("apellidoPersona").toString();
							nota = Double.parseDouble(row.get("notaPersona").toString());
							curso = row.get("cursoPersona").toString();
							

							nuevaPer = new Persona(id, nombre, apellido, nota, curso);

							aux.put(id, nuevaPer);
						}

						System.out.println("Acceso JSON Remoto - Leidos datos correctamente y generado hashmap");
						System.out.println();
						return aux;

					} else { // El array de jugadores est� vac�o
						System.out.println("Acceso JSON Remoto - No hay datos que tratar");
						System.out.println();
						return aux;
					}

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido alg�n error

					System.out.println("Ha ocurrido un error en la busqueda de datos");
					System.out.println("Error: " + (String) respuesta.get("error"));
					System.out.println("Consulta: " + (String) respuesta.get("query"));

					return aux;

				}
		}
		
		} catch (Exception e) {
			System.out.println(
					"Excepcion desconocida. Traza de error comentada en el m�todo 'annadirJugador' de la clase JSON REMOTO");
			 e.printStackTrace();
			System.out.println("Fin ejecuci�n");
			return null;
		}
		
		
	}
	@Override
	public void Escribir(HashMap<Integer, Persona> hm) {
		
		try {
			
				JSONObject objPeticion = new JSONObject();
				
				JSONArray arr = new JSONArray();
				for(Map.Entry<Integer, Persona> aux : hm.entrySet()) {
					JSONObject objPersona = new JSONObject();
					objPersona.put("idPersona", aux.getValue().getId());
					objPersona.put("nombrePersona", aux.getValue().getNombre());
					objPersona.put("apellidoPersona", aux.getValue().getApellido());
					objPersona.put("notaPersona", aux.getValue().getNota());
					objPersona.put("cursoPersona", aux.getValue().getGrado());
					
					
					arr.add(objPersona);
					
				}
				
				objPeticion.put("peticion", "addAll");
				objPeticion.put("Personas", arr);
				
				String json = objPeticion.toJSONString();
				
				String url = SERVER_PATH + ESC_PER;

				System.out.println("La url a la que lanzamos la petici�n es " + url);
				System.out.println("El json que enviamos es: ");
				System.out.println(json);
				//System.exit(-1);

				String response = encargadoPeticiones.postRequest(url, json);
				
				System.out.println("El json que recibimos es: ");
				
				System.out.println(response); // Traza para pruebas
				

				JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

				if (respuesta == null) { // Si hay alg�n error de parseo (json
											// incorrecto porque hay alg�n caracter
											// raro, etc.) la respuesta ser� null
					System.out.println("El json recibido no es correcto. Finaliza la ejecuci�n");
					
				} else { // El JSON recibido es correcto
					
					// Sera "ok" si todo ha ido bien o "error" si hay alg�n problema
					String estado = (String) respuesta.get("estado"); 
					if (estado.equals("ok")) { 
						JSONArray array = (JSONArray) respuesta.get("Personas");

							System.out.println("Acceso JSON Remoto - Escritos los datos correctamente");
							System.out.println();

					} else { // Hemos recibido el json pero en el estado se nos
								// indica que ha habido alg�n error

						System.out.println("Ha ocurrido un error en la busqueda de datos");
						System.out.println("Error: " + (String) respuesta.get("error"));
						System.out.println("Consulta: " + (String) respuesta.get("query"));


					}
			}
			
			} catch (Exception e) {
				System.out.println(
						"Excepcion desconocida. Traza de error comentada en el m�todo 'annadirJugador' de la clase JSON REMOTO");
				 e.printStackTrace();
				System.out.println("Fin ejecuci�n");
			}
		
	}
	
}
