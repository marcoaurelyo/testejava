<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>.:: Consulta de Trecho ::.</title>
<link href="http://gerenciador.netviagem.com.br/css/bootstrap.css"
	rel="stylesheet" type="text/css" />
<link href="http://gerenciador.netviagem.com.br/css/style.css"
	rel="stylesheet" type="text/css" />
</head>


<body>
<jsp:include page="validasessao.jsp" />
	<table class="table table-striped">
		<tr>
			<td>ORIGEM</td>
			<td>DESTINO</td>
			<td>HR SAIDA</td>
			<td>HR CHEGADA</td>
			<td>P LIVRES</td>
		</tr>
		<c:forEach items="${lista}" var="tre">
			<tr>
				<td>${tre.origem}</td>
				<td>${tre.destino}</td>
				<td>${tre.saida}</td>
				<td>${tre.chegada}</td>
				<td>${tre.nrPoltronas}</td>
			</tr>
		</c:forEach>

	</table>

</body>
</html>