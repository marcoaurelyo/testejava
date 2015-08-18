package control;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TestarListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		System.out.println("Conexto Ligado");
		
	}

	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("Contexto desligado...");
	}

}
