package converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import model.Categoria;
import model.Marca;
import dao.ConnectionFactory;
import dao.DAOFactory;
import dao.DaoGenerico;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter{


    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
    	EntityManager conexao = ConnectionFactory.getConnection();
		System.out.println("BUSCA A CATEGORIA COMO UM OBJETO");    	
    	Categoria categoria = null;
    	try{
    		DaoGenerico<Categoria, Integer> categoriaDao = new DAOFactory().getCategoriaDAO(conexao);
    		categoria = categoriaDao.pesquisarPorId((Integer.parseInt(string)));
    	}catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
        conexao.close();
        System.out.println("ID PESQUISADO: "    + string);
        System.out.println("Objeto Retornado "  + categoria.getDescricao());
        return categoria;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        System.out.println("Valor retornado" + ((Categoria) o).getIdCategoria());
    	return ((Categoria) o).getIdCategoria().toString();
    }

	
}
