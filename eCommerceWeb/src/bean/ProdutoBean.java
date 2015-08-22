package bean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import model.Categoria;
import model.Foto;
import model.Produto;
import model.TipoProduto;
import model.Usuario;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import dao.ConnectionFactory;
import dao.DAOFactory;
import dao.DaoGenerico;
import dao.FotoDAO;

@ManagedBean(name = "produtoBean")
@SessionScoped
public class ProdutoBean {
	private Map<String, String> opStatus;
	private Produto produto = new Produto();
	private TipoProduto tipoProduto;
	private Categoria categoria;
	private Usuario usuario;
	private Produto produtoSalvo;

	private List<Produto> listaProdutos = new ArrayList<Produto>();
	private List<Categoria> listaCategorias = new ArrayList<Categoria>();

	private List<TipoProduto> listaTipos = new ArrayList<TipoProduto>();
	private List<Foto> fotos = new ArrayList<Foto>();
	private Foto foto = new Foto();

	private UploadedFile fotografia;

	// Construtor
	public ProdutoBean() {
		opStatus = new HashMap<String, String>();
		opStatus.put("ATIVO", "ATIVO");
		opStatus.put("INATIVO", "INATIVO");
		popularListas();

	}

	public void novo() {
		produto = new Produto();
	}

	public void salvar() {
		EntityManager conexao = ConnectionFactory.getConnection();
		
		if (produto.equals(null)) {

			try {
				DaoGenerico<Produto, Integer> produtoDao = new DAOFactory()
						.getProdutoDAO(conexao);
				conexao.getTransaction().begin();

				produtoSalvo = produtoDao.salvar(produto);
				conexao.getTransaction().commit();
				conexao.clear();
				conexao.close();
				novo();
				popularListas();
			} catch (SQLException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Falha ao salvar " + e.getLocalizedMessage(),
								"Falha ao salvar " + e.getLocalizedMessage()));

			} finally {
				try {
					if(fotografia.getSize()>0){
					conexao = ConnectionFactory.getConnection();
					InputStream is = fotografia.getInputstream();
					// código usando Apache Commons IO
					byte[] bytes = IOUtils.toByteArray(is);
					foto.setDescricao(fotografia.getFileName().toString());
					foto.setFoto(bytes);
					foto.setProduto(produtoSalvo);
					// foto.setProduto(produtoSalvo);
					DaoGenerico<Foto, Integer> fotoDAO = new FotoDAO(conexao);
					conexao.getTransaction().begin();
					fotoDAO.salvar(foto);
					conexao.getTransaction().commit();
					conexao.close();
					}
				} catch (SQLException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Falha ao salvar "
											+ e.getLocalizedMessage(),
									"Falha ao salvar "
											+ e.getLocalizedMessage()));

				} catch (IOException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Falha ao salvar "
											+ e.getLocalizedMessage(),
									"Falha ao salvar "
											+ e.getLocalizedMessage()));

				}
			}

		} else {
			DaoGenerico<Produto, Integer> produtoDao;
			try {
				conexao = ConnectionFactory.getConnection();
				produtoDao = new DAOFactory().getProdutoDAO(conexao);
				conexao.getTransaction().begin();
				produtoSalvo = produtoDao.atualizar(produto);
				conexao.getTransaction().commit();
				conexao.clear();
				conexao.close();
				novo();
				popularListas();
			} catch (SQLException e) {
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Falha ao Atualizar "
												+ e.getLocalizedMessage(),
										"Falha ao Atualizar "
												+ e.getLocalizedMessage()));

			} finally {
				try {
					if(fotografia.getSize()>0){
					conexao = ConnectionFactory.getConnection();
					InputStream is = fotografia.getInputstream();
					// código usando Apache Commons IO
					byte[] bytes = IOUtils.toByteArray(is);
					foto.setDescricao(fotografia.getFileName().toString());
					foto.setFoto(bytes);
					foto.setProduto(produtoSalvo);
					DaoGenerico<Foto, Integer> fotoDAO = new FotoDAO(conexao);
					conexao.getTransaction().begin();
					fotoDAO.salvar(foto);
					conexao.getTransaction().commit();
					conexao.clear();
					conexao.close();
					}
				} catch (SQLException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Falha ao salvar "
											+ e.getLocalizedMessage(),
									"Falha ao salvar "
											+ e.getLocalizedMessage()));

				} catch (IOException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Falha ao salvar "
											+ e.getLocalizedMessage(),
									"Falha ao salvar "
											+ e.getLocalizedMessage()));

				} finally {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Sucesso",
									"Registro Atualizado com Sucesso"));
				}
			}

		}

	}

	public void filtraProduto(Integer id) {

		EntityManager conexao = ConnectionFactory.getConnection();
		DaoGenerico<Produto, Integer> produtoDao;
		Map<String, Object> filtro;
		filtro = new HashMap<String, Object>();
		filtro.put("param01", id);
		try {
			produtoDao = new DAOFactory().getProdutoDAO(conexao);
			listaProdutos = produtoDao
					.listPesqParam(
							"SELECT p FROM Produto p where p.tipoproduto.idTipoProduto = :param01",
							filtro);

		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Falha Listar produtos ", e.getLocalizedMessage()));

		}
		conexao.clear();
		conexao.close();

	}

	public void popularListas() {
		listaProdutos.clear();
		listaCategorias.clear();

		// busca dados de Produtos Cadastrados
		EntityManager conexao = ConnectionFactory.getConnection();
		DaoGenerico<Produto, Integer> produtoDao;
		try {
			produtoDao = new DAOFactory().getProdutoDAO(conexao);
			listaProdutos = produtoDao.todos();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Falha Listar produtos ", e.getLocalizedMessage()));
		}
		// busca dados da Categoria
		DaoGenerico<Categoria, Integer> categoriaDao;
		try {
			categoriaDao = new DAOFactory().getCategoriaDAO(conexao);
			listaCategorias = categoriaDao.todos();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Falha ao carregar dados de Categorias ", e
									.getLocalizedMessage()));

		}

		// busca dados de Tipo Produto
		DaoGenerico<TipoProduto, Integer> tipoDao;
		try {
			tipoDao = new DAOFactory().getTipoProdutoDAO(conexao);
			listaTipos = tipoDao.todos();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Falha ao carregar dados de Tipo de Produto ", e
									.getLocalizedMessage()));

		}

		conexao.clear();
		conexao.close();
	}

	public Map<String, String> getOpStatus() {
		return opStatus;
	}

	public void setOpStatus(Map<String, String> opStatus) {
		this.opStatus = opStatus;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
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

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public List<TipoProduto> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<TipoProduto> listaTipos) {
		this.listaTipos = listaTipos;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public UploadedFile getFotografia() {
		return fotografia;
	}

	public void setFotografia(UploadedFile fotografia) {
		this.fotografia = fotografia;
	}

	public Produto getProdutoSalvo() {
		return produtoSalvo;
	}

	public void setProdutoSalvo(Produto produtoSalvo) {
		this.produtoSalvo = produtoSalvo;
	}

	public void listarFotos() {
		try {
			System.out.println("TESTE LISTA " + produto.getDescricao());
			ServletContext sContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			EntityManager conexao = ConnectionFactory.getConnection();
			DaoGenerico<Foto, Integer> fotoDAO = new DAOFactory()
					.getFotoDAO(conexao);
			fotos = fotoDAO
					.listPesq("Select f from Foto f where f.produto.idProduto = "
							+ produto.getIdProduto());
			File folder = new File(sContext.getRealPath("/tmp"));
			if (!folder.exists()) {
				folder.mkdirs();
			}
			for (Foto f : fotos) {
				String nomeArquivo = f.getDescricao();
				String arquivo = sContext.getRealPath("/tmp")
						+ File.separator + nomeArquivo;
				criaArquivo(f.getFoto(), arquivo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void criaArquivo(byte[] bytes, String arquivo) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
