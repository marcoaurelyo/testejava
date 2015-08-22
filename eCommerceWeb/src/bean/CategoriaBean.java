package bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import model.Categoria;
import model.TipoProduto;
import model.Usuario;
import dao.ConnectionFactory;
import dao.DAOFactory;
import dao.DaoGenerico;

@ManagedBean(name = "categoriaBean")
@SessionScoped
public class CategoriaBean {
	private Map<String, String> opStatus;
	private Categoria categoria = new Categoria();
	private Usuario usuario;
	// private Marca marcaFiltrada;
	private List<Categoria> listaCategorias = new ArrayList<Categoria>();

	// Construtor
	public CategoriaBean() {
		opStatus = new HashMap<String, String>();
		opStatus.put("ATIVO", "ATIVO");
		opStatus.put("INATIVO", "INATIVO");
		popularLista();
	}

	public void novo() {
		categoria = new Categoria();
	}

	public void salvar() {
		EntityManager conexao = ConnectionFactory.getConnection();
		if (categoria.equals(null)) {

			try {

				DaoGenerico<Categoria, Integer> categoriaDao = new DAOFactory()
						.getCategoriaDAO(conexao);
				conexao.getTransaction().begin();

				categoriaDao.salvar(categoria);
				conexao.getTransaction().commit();
				conexao.clear();
				conexao.close();
				novo();
				popularLista();
			} catch (SQLException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Falha ao salvar " + e.getLocalizedMessage(),
								"Falha ao salvar " + e.getLocalizedMessage()));

			}
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
							"Registro Salvo com Sucesso"));

		} else {
			DaoGenerico<Categoria, Integer> categoriaDao;
			try {
				categoriaDao = new DAOFactory().getCategoriaDAO(conexao);
				conexao.getTransaction().begin();
				categoriaDao.atualizar(categoria);
				conexao.getTransaction().commit();
				conexao.clear();
				conexao.close();
				novo();
				popularLista();
			} catch (SQLException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Falha ao Atualizar " + e.getLocalizedMessage(),
								"Falha ao Atualizar " + e.getLocalizedMessage()));

			}
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Sucesso",
							"Registro Atualizado com Sucesso"));
			

		}

	}

	public void popularLista() {
		listaCategorias.clear();
		EntityManager conexao = ConnectionFactory.getConnection();
		DaoGenerico<Categoria, Integer> categoriaDAO;
		try {
			categoriaDAO = new DAOFactory().getCategoriaDAO(conexao);
			listaCategorias = categoriaDAO.todos();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Falha Listar marcas ", e.getLocalizedMessage()));
		}
		conexao.clear();
		conexao.close();
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Map<String, String> getOpStatus() {
		return opStatus;
	}

	public void setOpStatus(Map<String, String> opStatus) {
		this.opStatus = opStatus;
	}

	/*
	 * public Marca getMarcaFiltrada() { return marcaFiltrada; }
	 * 
	 * public void setMarcaFiltrada(Marca marcaFiltrada) { this.marcaFiltrada =
	 * marcaFiltrada; }
	 */
	public List<Categoria> getListaCategoria() {
		return listaCategorias;
	}

	public void setListaCategoria(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

}
