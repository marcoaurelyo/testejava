package dao;


import javax.persistence.EntityManager;

import model.Produto;

public class ProdutoDAO extends DaoGenericoImpl<Produto, Integer> {

	public ProdutoDAO(EntityManager conexao) {
		this.conexao = conexao;
		this.criteria = conexao.getCriteriaBuilder();
	}

	
}
