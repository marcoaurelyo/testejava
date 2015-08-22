package converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import model.TipoProduto;
import dao.ConnectionFactory;
import dao.DAOFactory;
import dao.DaoGenerico;

@FacesConverter(forClass = TipoProduto.class)
public class TipoProdutoConverter implements Converter{


    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
    	EntityManager conexao = ConnectionFactory.getConnection();
    	TipoProduto tp = null;
    	try{
    		DaoGenerico<TipoProduto, Integer> tpDao = new DAOFactory().getTipoProdutoDAO(conexao);
    		tp = tpDao.pesquisarPorId(Integer.parseInt(string));
    	}catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
        conexao.close();
        return tp;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
    	return ((TipoProduto) o).getIdTipoProduto().toString();
    }

	
}
