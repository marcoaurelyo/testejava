package dao;


import javax.persistence.EntityManager;

import model.Usuario;

public class UsuarioDAO extends DaoGenericoImpl<Usuario, Integer> {

	public UsuarioDAO(EntityManager conexao) {
		this.conexao = conexao;
		this.criteria = conexao.getCriteriaBuilder();
	}

	
}
