package converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import model.Marca;
import dao.ConnectionFactory;
import dao.DAOFactory;
import dao.DaoGenerico;

@FacesConverter(forClass = Marca.class)
public class MarcaConverter implements Converter{


    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
    	EntityManager conexao = ConnectionFactory.getConnection();
		    	
    	Marca marca = null;
    	try{
    		DaoGenerico<Marca, Integer> marcaDao = new DAOFactory().getMarcaDAO(conexao);
    		marca = marcaDao.pesquisarPorId((Integer.parseInt(string)));
    	}catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
        conexao.close();
        return marca;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

    	return ((Marca) o).getIdMarca().toString();
    }

	
}
