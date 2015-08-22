package junit;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import model.Categoria;
import model.Cliente;
import model.Marca;
import model.Pedido;
import model.Usuario;

import org.junit.Test;

import dao.ConnectionFactory;
import dao.DAOFactory;
import dao.DaoGenerico;

public class Teste {

	@Test
	public void test() throws SQLException {
		EntityManager con = ConnectionFactory.getConnection();
		
		DaoGenerico<Usuario, Integer> usuarioDao = new DAOFactory().getUsuarioDAO(con);
		Usuario usu = new Usuario();
		usu.setLogin("admin");
		usu.setPerfil("admin");
		usu.setSenha("admin");
		con.getTransaction().begin();
		usuarioDao.salvar(usu);
		con.getTransaction().commit();
		con.clear();
		con.close();
	}

}
