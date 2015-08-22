package dao;


import javax.persistence.EntityManager;

import model.TipoProduto;

public class TipoProdutoDAO extends DaoGenericoImpl<TipoProduto, Integer> {

	public TipoProdutoDAO(EntityManager conexao) {
		this.conexao = conexao;
		this.criteria = conexao.getCriteriaBuilder();
	}

	
}
