package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Localidade;
import model.empresa;
import model.login;

public class horarios extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requisicao(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requisicao(request, response);
	}

	private void requisicao(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Localidade> listaOrigem = new ArrayList<>();

		Localidade l0 = new Localidade();
		l0.setId(0);
		l0.setNmLocalidade("");

		Localidade l1 = new Localidade();
		l1.setId(1);
		l1.setNmLocalidade("São Paulo");

		Localidade l2 = new Localidade();
		l2.setId(2);
		l2.setNmLocalidade("Rio de Janeiro");

		listaOrigem.add(l0);
		listaOrigem.add(l1);
		listaOrigem.add(l2);

		request.setAttribute("origem", listaOrigem);
		request.setAttribute("destino", listaOrigem);

		empresa dados = new empresa();
		dados.setEndereco("Rua Benjamin Constant, 270");
		dados.setNome("GM Soluções");
		request.setAttribute("dado", dados);
		if (request.getParameter("falha") != null) {
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		} else {
			request.getRequestDispatcher("horarios.jsp").forward(request,
					response);
		}

	}
}
