package dao;


import javax.persistence.EntityManager;

import model.Pedido;

public class PedidoDAO extends DaoGenericoImpl<Pedido, Integer> {

	public PedidoDAO(EntityManager conexao) {
		this.conexao = conexao;
		this.criteria = conexao.getCriteriaBuilder();
	}

	
}
