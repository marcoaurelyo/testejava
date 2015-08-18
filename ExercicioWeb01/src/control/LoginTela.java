package control;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.login;

public class LoginTela extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nome = request.getParameter("txtLogin");
		String senha = request.getParameter("txtPassword");

		login l = new login();
		l.setNome(nome);
		l.setSenha(senha);

		boolean logado = false;
		HttpSession sessao = request.getSession();
		if (validarLogin(l)) {
			logado = true;

			sessao.setAttribute("nome", l.getNome());
			sessao.setAttribute("login", l);
		} else {
			sessao.invalidate();
		}

		response.setContentType("text/html");
		String strCokkie = ((logado) ? "SUCESSO" : "NEGADO") + l.getNome()
				+ " - " + new Date().toString();

		Cookie cookie = new Cookie("login", strCokkie);
		cookie.setMaxAge(30 * 60);
		response.addCookie(cookie);
		Cookie cookieh = new Cookie("hora", new Date().toLocaleString());
		cookieh.setMaxAge(30 * 60);
		response.addCookie(cookieh);
		request.setAttribute("msg", getInitParameter("mensagemBoasVindas"));
		if (!logado) {
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		} else {
	
			request.getRequestDispatcher("carregarLocalidade.do").forward(
					request, response);
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("src") != null) {
			request.setAttribute("msg", getInitParameter("mensagemBoasVindas"));
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		} else {
			request.getSession().invalidate();
			request.getRequestDispatcher("carregarLocalidade.do").forward(
					request, response);
		}
	}

	private boolean validarLogin(login Lo) {

		if (Lo.getNome().equals(getServletContext().getInitParameter("nome"))
				&& Lo.getSenha().equals(
						getServletContext().getInitParameter("senha")))
			return true;

		return false;
	}
}
