package dao;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import model.Categoria;
import model.Cliente;
import model.Foto;
import model.ItemPedido;
import model.Marca;
import model.Pedido;
import model.Produto;
import model.TipoProduto;
import model.Usuario;

public class DAOFactory {
	public  DaoGenerico<Usuario, Integer> getUsuarioDAO(EntityManager conexao)
			throws SQLException {
		return new UsuarioDAO(conexao);
	}
	public  DaoGenerico<Categoria, Integer> getCategoriaDAO(EntityManager conexao)
			throws SQLException {
		return new CategoriaDAO(conexao);
	}
	public  DaoGenerico<Cliente, Integer> getClienteDAO(EntityManager conexao)
			throws SQLException {
		return new ClienteDAO(conexao);
	}
	public  DaoGenerico<ItemPedido, Integer> getItePedidoDAO(EntityManager conexao)
			throws SQLException {
		return new ItemPedidoDAO(conexao);
	}
	public DaoGenerico<Marca, Integer> getMarcaDAO(EntityManager conexao)
			throws SQLException {
		return new MarcaDAO(conexao);
	}
	public  DaoGenerico<Pedido, Integer> getPedidoDAO(EntityManager conexao)
			throws SQLException {
		return new PedidoDAO(conexao);
	}
	public  DaoGenerico<Produto, Integer> getProdutoDAO(EntityManager conexao)
			throws SQLException {
		return new ProdutoDAO(conexao);
	}
	public  DaoGenerico<TipoProduto, Integer> getTipoProdutoDAO(EntityManager conexao)
			throws SQLException {
		return new TipoProdutoDAO(conexao);
	}
	public  DaoGenerico<Foto, Integer> getFotoDAO(EntityManager conexao)
			throws SQLException {
		return new FotoDAO(conexao);
	}
	
}