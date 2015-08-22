package bean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.context.RequestContext;

import dao.ConnectionFactory;
import dao.DAOFactory;
import dao.DaoGenerico;
import model.Cliente;
import model.ItemPedido;
import model.Marca;
import model.Pedido;
import model.Produto;
import model.TipoProduto;
import model.Usuario;

@ManagedBean(name = "carrinhoBean")
@SessionScoped
public class CarrinhoBean {

	private List<ItemPedido> listaProdutos = new ArrayList<ItemPedido>();
	private Produto produto = new Produto();
	private ItemPedido itemPedido = new ItemPedido();
	private Pedido pedido = new Pedido();
	private Integer quantidadeItem = new Integer(1);

	public List<ItemPedido> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<ItemPedido> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public String finalizarPedido() {

		EntityManager conexao = ConnectionFactory.getConnection();

		if (FacesUtil.getSessionAtrib("usuarioLogado") == null) {

			FacesUtil.addSessionAtrib("finalizarPedido", "True");
			addMessage("Aviso!",
					"Favor logar no sistema para finalizar o pedido!");
			return "login.xhtml";

		}

		if (getListaProdutos().size() == 0) {
			addMessage(
					"Aviso!",
					"Não há produtos em seu carrinho, favor adicionar algum produto para finalizar o pedido!");
			return "index.xhtml";
		}

		try {

			DaoGenerico<Pedido, Integer> pedidoDao = new DAOFactory()
					.getPedidoDAO(conexao);
			DaoGenerico<ItemPedido, Integer> itemDao = new DAOFactory().getItePedidoDAO(conexao);			
			conexao.getTransaction().begin();
			fechaPedido();
			pedidoDao.salvar(pedido);
			
			for(ItemPedido item : pedido.getItempedidos()) {
				itemDao.salvar(item);
			}
			
			conexao.getTransaction().commit();
			conexao.clear();
			conexao.close();
			novo();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Falha ao salvar " + e.getLocalizedMessage(),
							"Falha ao salvar " + e.getLocalizedMessage()));

		}

		return null;

	}

	public void fechaPedido() {
		BigDecimal precoPedido = new BigDecimal(0);
		EntityManager conexao = ConnectionFactory.getConnection();
		DaoGenerico<Cliente, Integer> cliDao;
		Cliente cli = new Cliente();
		Map<String, Object> filtro = new HashMap<String, Object>();
		Usuario usu = (Usuario) FacesUtil.getSessionAtrib("usuarioLogado");
		
		for (ItemPedido prod : getListaProdutos()) {
			
			precoPedido = precoPedido.add(prod.getPrecoTotal());
		}

		try {
			filtro.put("param01", usu.getIdusuario());
			cliDao = new DAOFactory().getClienteDAO(conexao);
			cli = cliDao.pesqParam(
					"Select p from Cliente p where usuario.idusuario = :param01",
					filtro);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Falha Listar marcas ", e.getLocalizedMessage()));
		}

		getPedido().setPrecoTotal(precoPedido);
		getPedido().setCliente(cli);
		getPedido().setUsuario(usu);
		


	}

	public void novo() {
		pedido = new Pedido();
	}

	public void addProduto() {
		
		listaProdutos.add(new ItemPedido(getProduto().getPreco(), getProduto().getPreco(), getQuantidadeItem(),
				getPedido(), getProduto()));
		addMessage("Aviso!", "Produto " + getProduto().getDescricao()
				+ " adcionado ao carrinho.");
	}

	public void removeProduto(int id) {

		if (listaProdutos != null && listaProdutos.size() > 0) {
		
			listaProdutos.remove(id);
			addMessage("Aviso!", "Produto Removido");
		
		} else {

			addMessage("Aviso!", "Não há itens na lista.");

		}

	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	
	
	public Integer getQuantidadeItem() {
		return quantidadeItem;
	}

	public void setQuantidadeItem(Integer quantidade) {
		this.quantidadeItem = quantidade;
	}
	
	

	public ItemPedido getItemPedido() {
		return itemPedido;
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}


    public BigDecimal getTotal() {
            BigDecimal totalGeral = BigDecimal.ZERO;
            Iterator<?> itens = listaProdutos.iterator();
            while (itens.hasNext()) {
            	ItemPedido cartItem = (ItemPedido) itens.next();
                    totalGeral = totalGeral.add(cartItem.getPrecoUnitario());
            }
            return totalGeral;
    }


}
