package dao;

import javax.persistence.EntityManager;

import model.ItemPedido;

public class ItemPedidoDAO extends DaoGenericoImpl<ItemPedido, Integer> {

	public ItemPedidoDAO(EntityManager conexao) {
		this.conexao = conexao;
		this.criteria = conexao.getCriteriaBuilder();
	}

}
