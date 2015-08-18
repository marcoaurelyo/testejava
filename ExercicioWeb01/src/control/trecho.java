package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Localidade;
import model.Trechos;

public class trecho extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String origem = request.getParameter("origem");
		String destino = request.getParameter("destino");
		
		if(origem.equals("0") || destino.equals("0"))
		{
			request.getRequestDispatcher("horarios.jsp").forward(request, response);
		}else
		{
			Trechos trecho1 = new Trechos();
			trecho1.setIdTrechos(1);
			trecho1.setNrPoltronas(40);
			trecho1.setOrigem("São Paulo - SP");
		    trecho1.setDestino("Rio de Janeiro - RJ");
			trecho1.setSaida("10:00:00");
			trecho1.setChegada("12:00:00");
			
		    Trechos trecho21 = new Trechos();
		    trecho21.setIdTrechos(2);
		    trecho21.setNrPoltronas(35);
		    trecho21.setOrigem("São Paulo - SP");
		    trecho21.setDestino("Rio de Janeiro - RJ");
		    trecho21.setSaida("13:00:00");
			trecho21.setChegada("15:00:00");
		    
			List<Trechos> lista = new ArrayList<Trechos>();
			lista.add(trecho1);
			lista.add(trecho21);
			request.setAttribute("lista", lista);
			request.getRequestDispatcher("trechos.jsp").forward(request, response);
		}
		

		
	}
}
