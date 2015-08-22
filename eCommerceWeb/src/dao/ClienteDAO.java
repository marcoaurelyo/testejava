package dao;

import javax.persistence.EntityManager;

import model.Cliente;


public class ClienteDAO extends DaoGenericoImpl<Cliente, Integer> {

	public ClienteDAO(EntityManager conexao) {
		this.conexao = conexao;
		this.criteria = conexao.getCriteriaBuilder();
	}

	
}
