package utils;

import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateUtil {

    private SessionFactory sessionFactory;
    
    private Session session;
    
    public HibernateUtil(){

        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) config file.
        	sessionFactory = new Configuration().configure().buildSessionFactory();
        	session = sessionFactory.openSession();
                     
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    	
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public Session getSession() {
    	return session;
    }
    
}
