package dao;


import javax.persistence.EntityManager;

import model.Marca;

public class MarcaDAO extends DaoGenericoImpl<Marca, Integer> {

	public MarcaDAO(EntityManager conexao) {
		this.conexao = conexao;
		this.criteria = conexao.getCriteriaBuilder();
	}

	
}
