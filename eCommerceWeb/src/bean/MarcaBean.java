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

import model.Marca;
import model.Usuario;
import dao.ConnectionFactory;
import dao.DAOFactory;
import dao.DaoGenerico;

@ManagedBean(name = "marcaBean")
@SessionScoped
public class MarcaBean {
	private Map<String, String> opStatus;
	private Marca marca = new Marca();
	private Usuario usuario;
	//private Marca marcaFiltrada;
	private List<Marca> listaMarcas = new ArrayList<Marca>();
	
	//Construtor
	public MarcaBean() {
		opStatus = new HashMap<String,String>();
		opStatus.put("ATIVO", "ATIVO");
		opStatus.put("INATIVO", "INATIVO");
		popularLista();
	}

	public void novo(){
		marca = new Marca();
	}
	public void salvar(){
		EntityManager conexao = ConnectionFactory.getConnection();
		if(marca.equals(null)){
		try {
			System.out.println(marca.getDescricao());
		DaoGenerico<Marca, Integer> marcaDao = new DAOFactory().getMarcaDAO(conexao);
		conexao.getTransaction().begin();
	
			marcaDao.salvar(marca);
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
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Sucesso",
						"Registro Salvo com Sucesso"));
			
		}else{
			try {
				
			DaoGenerico<Marca, Integer> marcaDao = new DAOFactory().getMarcaDAO(conexao);
			conexao.getTransaction().begin();
		
				marcaDao.atualizar(marca);
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
	public void popularLista(){
		listaMarcas.clear();
		EntityManager conexao = ConnectionFactory.getConnection();
		DaoGenerico<Marca, Integer> marcaDAO;
		try {
			marcaDAO = new DAOFactory().getMarcaDAO(conexao);
			listaMarcas = marcaDAO.todos();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Falha Listar marcas " ,
							e.getLocalizedMessage()));
		}
		conexao.clear();
		conexao.close();
	}
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
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

/*	public Marca getMarcaFiltrada() {
		return marcaFiltrada;
	}

	public void setMarcaFiltrada(Marca marcaFiltrada) {
		this.marcaFiltrada = marcaFiltrada;
	}
*/
	public List<Marca> getListaMarcas() {
		return listaMarcas;
	}

	public void setListaMarcas(List<Marca> listaMarcas) {
		this.listaMarcas = listaMarcas;
	}
	

	
	

}
