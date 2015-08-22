package bean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import model.Usuario;
import dao.ConnectionFactory;
import dao.DAOFactory;
import dao.DaoGenerico;
import dao.UsuarioDAO;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

	private String usuario;
	private String senha;
	private Usuario usu;

	public LoginBean() {
	}

	public String validaLogin() throws SQLException {
		EntityManager conexao = new ConnectionFactory().getConnection();

		// DaoGenerico<Usuario, Integer> usuarioDAO =
		// DAOFactory.getUsuarioDAO(conexao);

		CriteriaBuilder cb = conexao.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
		Root<Usuario> root = cq.from(Usuario.class);
		Predicate condicao = cb.equal(root.<String> get("login"), usuario);
		cq.where(condicao);

		usu = null;
		try {
			usu = conexao.createQuery(cq).getSingleResult();
			System.out.println(usuario);
		} catch (NoResultException e) {
			FacesUtil.addMessage("Usuario inexistente");
			return null;
		}

		if (usu.getSenha().equals(senha)) {
			FacesUtil.addSessionAtrib("usuarioLogado", usu);

			if (FacesUtil.getSessionAtrib("finalizarPedido").equals("True")) {
				FacesUtil.addSessionAtrib("finalizarPedido", "False");
				return "carrinho.xhtml";
			}
			if (usu.getPerfil().equals("admin")) {
				return "admin.xhtml";
			}
			return "index.xhtml";
		} else {
			FacesUtil.addMessage("Senha invalida");
			return null;
		}

	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUsu() {
		return usu;
	}

	public void setUsu(Usuario usu) {
		this.usu = usu;
	}

}
