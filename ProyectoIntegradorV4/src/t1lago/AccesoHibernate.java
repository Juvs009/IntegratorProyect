package t1lago;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.*;

import utils.HibernateUtil;




public class AccesoHibernate implements Funcionalidad {

	Session session;
	
	public AccesoHibernate() {
		
		HibernateUtil util = new HibernateUtil(); 
		session = util.getSession();
		
	}
	
    
	
	public void cerrarSesion() {
		
		session.close();
		
	}

	//Crea una nueva Persona pasada por parámetro en la Base de Datos
	@Override
	public void NuevaPersona(Persona p) {
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
		
		
	}

	//Comprueba que el id no esté repetido
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

	//Lee una base de datos y devuelve un HashMap con sus datos
	@Override
	public HashMap<Integer, Persona> Leer() {
		HashMap<Integer, Persona> hm = new HashMap<>();
    	
    	TypedQuery<Persona> q= session.createQuery("select e from Persona e");
        List results = q.getResultList();
        
        Iterator personasIterator= results.iterator();
        
        
        while (personasIterator.hasNext()){
            Persona p = (Persona)personasIterator.next();
            hm.put(p.getId(), p);
        }
        return hm;
	}

	//Elimina una Persona de una Base de datos a partir de su id
	@Override
	public void EliminarPersona(int id) {
		
		session.beginTransaction();
		
		TypedQuery<Persona> q= session.createQuery("Delete from Persona where id="+id);
    	q.executeUpdate();
    	
    	session.getTransaction().commit();
    	session.clear();
		
	}

	//Modifica una Persona en la base de datos, busca la persona a modificar con el entero pasado por parámetro
	//y la reemplaza por la Persona pasada por parámetro
	@Override
	public void ModificarPersona(Persona p) {
		session.beginTransaction();
		session.merge(p);
		session.getTransaction().commit();
		
	}

	//Busca una Persona por su ID y la devuelve
	@Override
	public Persona BuscarPersonaID(int id) {
		try {
		TypedQuery<Persona> q= session.createQuery("select e from Persona e where id= "+id);
    	Persona p = q.getSingleResult();
    	if(p.getId()!=0) {
    		return p;
    		}else {
    			return null;
    		}
		}catch(NoResultException nr) {
			return null;
		}
	}

	//Busca Personas a partir de una cadena de caracteres pasada por parámetro y la devuelve en un HashMap
	@Override
	public HashMap<Integer, Persona> BuscarPersonaSt(HashMap<Integer, Persona> hm2, String s) {
		HashMap<Integer, Persona> hm = new HashMap<>();
		TypedQuery<Persona> q= session.createQuery("select e from Persona e where id LIKE '%"+s+"%' or nombre LIKE '%"+s+"%' "
				+ "or apellido LIKE '%"+s+"%' or Nota LIKE '%"+s+"%' or Curso LIKE '%"+s+"%'");
		List results = q.getResultList();
        
        Iterator personasIterator= results.iterator();
        
        while (personasIterator.hasNext()){
            Persona p = (Persona)personasIterator.next();
            hm.put(p.getId(), p);
        }
		
		return hm;
	}

	//Sobreescribe los datos de una base de datos por otros pasados en un HashMap
	@Override
	public void Escribir(HashMap<Integer, Persona> hm) {
		borrarDatos();
		
		session.beginTransaction();
		
		for (HashMap.Entry<Integer, Persona> entry : hm.entrySet()) {
			
			session.save(entry.getValue());
		}
		
		session.getTransaction().commit();
		
	}
	
	
	//Borra la base de datos
	public void borrarDatos() {
		session.beginTransaction();
    	
		TypedQuery q = session.createQuery("delete from Persona");
		q.executeUpdate();
		session.getTransaction().commit();
		
	}
	

}
