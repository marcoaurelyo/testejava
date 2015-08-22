package bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtil {
	public static void addMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(msg));
	}

	public static void addMessage(String clienteId, String msg) {
		FacesContext.getCurrentInstance().addMessage(clienteId,
				new FacesMessage(msg));
	}

	public static void addSessionAtrib(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(key, value);
	}

	public static Object getSessionAtrib(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(key);
	}
}