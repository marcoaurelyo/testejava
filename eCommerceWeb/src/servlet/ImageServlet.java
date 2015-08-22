package servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Foto;
import dao.ConnectionFactory;
import dao.DAOFactory;
import dao.DaoGenerico;

public class ImageServlet extends HttpServlet{

	private static final long serialVersionUID = 4878248359669413787L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
	
		try{
			byte[] imageBytes = null;
			EntityManager conexao = ConnectionFactory.getConnection();
			DaoGenerico<Foto, Integer> fotoDao = new DAOFactory().getFotoDAO(conexao);
			
			Foto f = fotoDao.PesqString("Select f from Foto f where f.idFoto = 4");
			imageBytes = f.getFoto();
			response.getOutputStream().write(imageBytes);
			response.getOutputStream().close();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	
	

}
