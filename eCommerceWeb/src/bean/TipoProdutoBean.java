package bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import model.TipoProduto;
import model.Usuario;
import dao.ConnectionFactory;
import dao.DAOFactory;
import dao.DaoGenerico;

@ManagedBean(name = "tipoProdutoBean")
@SessionScoped
public class TipoProdutoBean {
	private Map<String, String> opStatus;
	private TipoProduto tipoProduto = new TipoProduto();
	private Usuario usuario;
	// private Marca marcaFiltrada;
	private List<TipoProduto> listaTipos = new ArrayList<TipoProduto>();
	private MenuModel menuButton = new DefaultMenuModel();
	private String menuDesc = new String();
	private MenuModel menuButton1 = new DefaultMenuModel();
	private String menuDesc1 = new String();
	private MenuModel menuButton2 = new DefaultMenuModel();
	private String menuDesc2 = new String();
	private MenuModel menuButton3 = new DefaultMenuModel();
	private String menuDesc3 = new String();
	private MenuModel menuButton4 = new DefaultMenuModel();
	private String menuDesc4 = new String();	
	private MenuModel menuButton5 = new DefaultMenuModel();
	private String menuDesc5 = new String();
	// Construtor
	
	
	public TipoProdutoBean() {
		opStatus = new HashMap<String, String>();
		opStatus.put("ATIVO", "ATIVO");
		opStatus.put("INATIVO", "INATIVO");
		popularLista();
	}

	public void novo() {
		tipoProduto = new TipoProduto();
	}

	public void salvar() {
		EntityManager conexao = ConnectionFactory.getConnection();
		if (tipoProduto.equals(null)) {

			try {

				DaoGenerico<TipoProduto, Integer> tipoProdutoDao = new DAOFactory()
						.getTipoProdutoDAO(conexao);
				conexao.getTransaction().begin();

				tipoProdutoDao.salvar(tipoProduto);
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
			DaoGenerico<TipoProduto, Integer> tipoProdutoDao;
			try {
				tipoProdutoDao = new DAOFactory().getTipoProdutoDAO(conexao);
				conexao.getTransaction().begin();
				tipoProdutoDao.atualizar(tipoProduto);
				conexao.getTransaction().commit();
				conexao.clear();
				conexao.close();
				novo();
				popularLista();
			} catch (SQLException e) {
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Falha ao Atualizar "
												+ e.getLocalizedMessage(),
										"Falha ao Atualizar "
												+ e.getLocalizedMessage()));

			}
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
							"Registro Atualizado com Sucesso"));

		}

	}

	@PostConstruct
	public void populaTipoProduto() {
		setMenuDesc(this.buscaTipoProduto(1,this.getMenuButton()));
		setMenuDesc1(this.buscaTipoProduto(2,this.getMenuButton1()));
		setMenuDesc2(this.buscaTipoProduto(3,this.getMenuButton2()));
		setMenuDesc3(this.buscaTipoProduto(4,this.getMenuButton3()));
		setMenuDesc4(this.buscaTipoProduto(5,this.getMenuButton4()));
		setMenuDesc5(this.buscaTipoProduto(6,this.getMenuButton5()));		
	}
	
	public String buscaTipoProduto(Integer id,MenuModel menu) {
		List<TipoProduto> listaTipoProduto = new ArrayList<TipoProduto>();
		EntityManager conexao = ConnectionFactory.getConnection();
		DaoGenerico<TipoProduto, Integer> tipoProdutoDAO;
		Map<String, Object> filtro;
		filtro = new HashMap<String, Object>();
		String descMenu = null;
		
		try {			
			filtro.put("param01", id);			
			tipoProdutoDAO = new DAOFactory().getTipoProdutoDAO(conexao);
			listaTipoProduto = tipoProdutoDAO
					.listPesqParam(
							"SELECT t FROM TipoProduto t where t.subTipoProduto.idTipoProduto = :param01",
							filtro);
			for (TipoProduto tipo : listaTipoProduto) {
				DefaultMenuItem itemMenu = new DefaultMenuItem(
						tipo.getDescricao());
				itemMenu.setAjax(false);
				itemMenu.setCommand("#{produtoBean.filtraProduto("
						+ String.valueOf(tipo.getIdTipoProduto()) + ")}");
				menu.addElement(itemMenu);
				if (descMenu == null) {
					descMenu = tipo.getSubTipoProduto().getDescricao();
				}
			}
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Falha Listar marcas ", e.getLocalizedMessage()));
		}

		conexao.clear();
		conexao.close();
		return descMenu;

	}

	public void popularLista() {
		listaTipos.clear();
		EntityManager conexao = ConnectionFactory.getConnection();
		DaoGenerico<TipoProduto, Integer> tipoProdutoDAO;
		try {
			tipoProdutoDAO = new DAOFactory().getTipoProdutoDAO(conexao);
			listaTipos = tipoProdutoDAO.todos();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Falha Listar marcas ", e.getLocalizedMessage()));
		}
		conexao.clear();
		conexao.close();
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
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
	public List<TipoProduto> getListaTipoProduto() {
		return listaTipos;
	}

	public void setListaTipoProduto(List<TipoProduto> listaTipos) {
		this.listaTipos = listaTipos;
	}

	public MenuModel getMenuButton() {
		return menuButton;
	}

	public void setMenuButton(MenuModel menuButton) {
		this.menuButton = menuButton;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public MenuModel getMenuButton1() {
		return menuButton1;
	}

	public void setMenuButton1(MenuModel menuButton1) {
		this.menuButton1 = menuButton1;
	}

	public String getMenuDesc1() {
		return menuDesc1;
	}

	public void setMenuDesc1(String menuDesc1) {
		this.menuDesc1 = menuDesc1;
	}

	public MenuModel getMenuButton2() {
		return menuButton2;
	}

	public void setMenuButton2(MenuModel menuButton2) {
		this.menuButton2 = menuButton2;
	}

	public String getMenuDesc2() {
		return menuDesc2;
	}

	public void setMenuDesc2(String menuDesc2) {
		this.menuDesc2 = menuDesc2;
	}

	public MenuModel getMenuButton3() {
		return menuButton3;
	}

	public void setMenuButton3(MenuModel menuButton3) {
		this.menuButton3 = menuButton3;
	}

	public String getMenuDesc3() {
		return menuDesc3;
	}

	public void setMenuDesc3(String menuDesc3) {
		this.menuDesc3 = menuDesc3;
	}

	public MenuModel getMenuButton4() {
		return menuButton4;
	}

	public void setMenuButton4(MenuModel menuButton4) {
		this.menuButton4 = menuButton4;
	}

	public String getMenuDesc4() {
		return menuDesc4;
	}

	public void setMenuDesc4(String menuDesc4) {
		this.menuDesc4 = menuDesc4;
	}

	public MenuModel getMenuButton5() {
		return menuButton5;
	}

	public void setMenuButton5(MenuModel menuButton5) {
		this.menuButton5 = menuButton5;
	}

	public String getMenuDesc5() {
		return menuDesc5;
	}

	public void setMenuDesc5(String menuDesc5) {
		this.menuDesc5 = menuDesc5;
	}
	
	

}
