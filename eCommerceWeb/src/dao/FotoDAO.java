package dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.hibernate.Query;

import model.Foto;

public class FotoDAO extends DaoGenericoImpl<Foto, Integer> {

	public FotoDAO(EntityManager conexao) {
		this.conexao = conexao;
		this.criteria = conexao.getCriteriaBuilder();
	}

}
