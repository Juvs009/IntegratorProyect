package t1lago;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

public class DBManager implements Funcionalidad {

	Connection conn;
	private Properties prop;
	Visor v = new Visor();

	public DBManager() {
		try {
			prop = new Properties();
			InputStream input = new FileInputStream("src/Ficheros/jdbc.properties");
			this.prop.load(input);
		} catch (FileNotFoundException e) {
			v.MensajeErrorRutaProp();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Librer�a de MySQL
		String driver = prop.getProperty("driver");

		// Nombre de la base de datos
		String database = prop.getProperty("database");

		// Host
		String hostname = prop.getProperty("hostname");

		// Puerto
		String port = prop.getProperty("port");

		// Ruta de nuestra base de datos (desactivamos el uso de SSL con
		// "?useSSL=false")
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";

		// Nombre de usuario
		String username = prop.getProperty("username");

		// Clave de usuario
		String password = prop.getProperty("password");
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	
}
	
	//Crea una nueva Persona pasada por parámetros en una base de datos 
	public void NuevaPersona(Persona p) {
		try {

			String query = "insert into alumnos (ID, Nombre, Apellido, Nota, Curso) values (?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setInt(1, p.getId());
			ps.setString(2, p.getNombre());
			ps.setString(3, p.getApellido());
			ps.setDouble(4, p.getNota());
			ps.setString(5, p.getGrado());

			ps.execute();
			
		} catch (SQLIntegrityConstraintViolationException e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
			v.MensajeIDDuplicado();
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Comprueba que el id no esté repetido
	public boolean ComprobarID(int id) {
		HashMap<Integer, Persona> hm = new HashMap<Integer,Persona>();
		hm = Leer();
		
		
		if(hm.containsKey(id)) {
			return true;
		}else {
			return false;
		}
	}
	
	//Modifica una Persona en la base de datos, busca la persona a modificar con el entero pasado por parámetro
	//y la reemplaza por la Persona pasada por parámetro
	public void ModificarPersona(Persona p) {
		try {

			String query = "update alumnos set nombre =?, apellido=?, nota=?, curso=? where id ="+p.getId();
			PreparedStatement ps = conn.prepareStatement(query);

			
			ps.setString(1, p.getNombre());
			ps.setString(2, p.getApellido());
			ps.setDouble(3, p.getNota());
			ps.setString(4, p.getGrado());

			ps.execute();
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
			
	}
	
	//Lee una base de datos y devuelve un HashMap con sus datos
	public HashMap<Integer, Persona> Leer(){
		HashMap<Integer, Persona> hm = new HashMap<>();
		try {
			
			
			String query = "Select * from alumnos";
			
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);

			// iterate through the java resultset
			while (rs.next()) {
				Persona p = new Persona();
				int id = rs.getInt("id");
				String name = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				double nota = rs.getDouble("nota");
				String curso = rs.getString("curso");
				
				p.setId(id);
				p.setNombre(name);
				p.setApellido(apellido);
				p.setNota(nota);
				p.setGrado(curso);
				
				hm.put(id, p);
			}
			
			st.close();
			
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return hm;
	}
	
	//Elimina una Persona de una Base de datos a partir de su id
	public void EliminarPersona(int id) {
		try {
			
			String query = "Delete from alumnos where id="+id;
			PreparedStatement ps = conn.prepareStatement(query);
			ps.execute();

		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Sobreescribe los datos de una base de datos por otros pasados en un HashMap
	public void Escribir(HashMap<Integer, Persona> hm) {
		
		try {
			BorrarBase();
			
			String query = "insert into alumnos (ID, Nombre, Apellido, Nota, Curso) values (?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(query);
			
			for (HashMap.Entry<Integer, Persona> entry : hm.entrySet()) {
				ps.setInt(1, entry.getValue().getId());
				ps.setString(2, entry.getValue().getNombre());
				ps.setString(3, entry.getValue().getApellido());
				ps.setDouble(4, entry.getValue().getNota());
				ps.setString(5, entry.getValue().getGrado());
			   
				ps.execute();
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Borra la base de datos
	public void BorrarBase() {
		try {
		String query = "Delete from alumnos";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.execute();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Busca una Persona por su ID y la devuelve
	public Persona BuscarPersonaID(int id) {
		Persona p = new Persona();
		try {
		String query = "Select * FROM alumnos Where id = "+id;
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			p.setId(rs.getInt("ID"));
			p.setNombre(rs.getString("nombre"));
			p.setApellido(rs.getString("apellido"));
			p.setNota(rs.getDouble("nota"));
			p.setGrado(rs.getString("curso"));
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(p.getId()!=0) {
		return p;
		}else {
			return null;
		}
	}
	
	//Busca Personas a partir de una cadena de caracteres pasada por parámetro y la devuelve en un HashMap
	public HashMap<Integer,Persona> BuscarPersonaSt(HashMap<Integer, Persona>hm2, String s) {
		HashMap<Integer, Persona> hm = new HashMap<>();
		try{
		String query = "SELECT * FROM alumnos WHERE id LIKE '%"+s+"%' or nombre LIKE '%"+s+"%' "
				+ "or apellido LIKE '%"+s+"%' or Nota LIKE '%"+s+"%' or Curso LIKE '%"+s+"%'";
		
		Statement st = conn.createStatement();
		
		
		ResultSet rs = st.executeQuery(query);
		
		while (rs.next()) {
			Persona p = new Persona();
			p.setId(rs.getInt("ID"));
			p.setNombre(rs.getString("nombre"));
			p.setApellido(rs.getString("apellido"));
			p.setNota(rs.getDouble("nota"));
			p.setGrado(rs.getString("curso"));
			hm.put(p.getId(), p);
		}
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return hm;
		
		
	}
	
	
	
	
}
