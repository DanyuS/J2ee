package daoImpl;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class JDBConnect {
	
	private static SessionFactory factory;

	public static final ThreadLocal threadLocal = new ThreadLocal();
	static { 
		{
		Configuration  con = new Configuration().configure();
	
		factory  =  con.buildSessionFactory();
		} 
	}
	public static Session currentSession() {
		Session currentSession = (Session) threadLocal.get();
		//Session currentSession = (Session) threadLocal.get();
		if (currentSession == null) {
			currentSession = factory.openSession();
			//currentSession =  sessionFactory.openSession();
			threadLocal.set((currentSession));
		}
		return  currentSession;
	}
	
	public static void closeSession() {
		Session currentSession = (Session) threadLocal.get();
		if (currentSession == null) {
			currentSession.close();
		}
		threadLocal.set (null);
	}
	
}


