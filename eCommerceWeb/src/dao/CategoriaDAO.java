package dao;

import javax.persistence.EntityManager;

import model.Categoria;

public class CategoriaDAO extends DaoGenericoImpl<Categoria, Integer> {

	public CategoriaDAO(EntityManager conexao) {
		this.conexao = conexao;
		this.criteria = conexao.getCriteriaBuilder();
	}

}
