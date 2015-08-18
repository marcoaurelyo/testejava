<%@page import="model.login"%>

<%
	if (session.getAttribute("login") != null) {
		login lo = (login) session.getAttribute("login");

		if (!lo.getNome().isEmpty()) {

			Cookie[] cookies = request.getCookies();

			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];

				if (cookie.getName().equals("hora")) {
					String hora = cookie.getValue();

					out.println("Usuario  Logado desde:" + hora);
					break;
				}
			}
		}
	}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>.:: Sistema de Passagens ::.</title>
<link href="http://gerenciador.netviagem.com.br/css/bootstrap.css"
	rel="stylesheet" type="text/css" />
<link href="http://gerenciador.netviagem.com.br/css/style.css"
	rel="stylesheet" type="text/css" />

</head>
<body>
<jsp:include page="validasessao.jsp" />
	<div class="boxForm">
		<div class="row">
			<div class="col-lg-10">
				<h3 class="center">Sistema de Buscar Passagens</h3>
			</div>

			<div class="col-lg-2">
				<jsp:include page="headlogin.jsp" />
			</div>
		</div>
		<div class="row"></div>
		<br />
		<form action="buscarTrecho.do" method="get">
			<div class="row">
				<div class="col-lg-4"></div>
				<div class="col-lg-4">
					<label>Origem:</label> <select name="origem"
						class="form-control">
						<c:forEach items="${origem}" var="ori">
							<option value="${ori.id}">${ori.nmLocalidade}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-lg-4"></div>
			</div>
			<br />
			<div class="row">
				<div class="col-lg-4"></div>
				<div class="col-lg-4">
					<label>Destino:</label> <select name="destino"
						class="form-control">
						<c:forEach items="${destino}" var="ori">
							<option value="${ori.id}">${ori.nmLocalidade}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-lg-4"></div>
			</div>
			<br />
			<div class="row">

				<div class="col-lg-4"></div>
				<div class="col-lg-4">
					<input type="submit" id="btnPesquisar"
						class="btn btn-default right" value="Pesquisar" />
				</div>
				<div class="col-lg-4"></div>
			</div>
			<div>
			<jsp:useBean id="dado" class="model.empresa" scope="request" />
				Nome: <jsp:getProperty name="dado" property="nome" />
				Endereço: <jsp:getProperty name="dado" property="endereco" />
			</div>
		</form>
	</div>
	
</body>
</html>