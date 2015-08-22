package dao
;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {
	/*	
	private static final String driver = "org.postgresql.Driver";
	private static final String url = "jdbc:postgresql://localhost/postgres";
	private static final String user = "postgres";
	private static final String password = "amatsu22";
	

public static Connection getConnection() {
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);
			return con;
		} catch (ClassNotFoundException | SQLException ex) {
			throw new RuntimeException("Erro: banco " + url + ". " + ex);
		}
	}*/
	private static final Logger logger = Logger.getLogger(ConnectionFactory.class.getName());
	private static EntityManagerFactory emf = null;
	private static EntityManager manager;
	
	public static EntityManager getConnection() {
		
		if (emf==null) {
			emf = Persistence.createEntityManagerFactory("eCommerceWeb");
			manager = emf.createEntityManager();
		}
		manager = emf.createEntityManager();
		return manager;
	}
	
	public void closeConnection() {
		try { 
			emf.close();
			manager.close();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Erro: banco " +  ". {0}", ex);
		}
	}
}