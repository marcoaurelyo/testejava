<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>.:: Login ::.</title>
<link href="http://gerenciador.netviagem.com.br/css/bootstrap.css"
	rel="stylesheet" type="text/css" />
<link href="http://gerenciador.netviagem.com.br/css/style.css"
	rel="stylesheet" type="text/css" />
</head>

<body>

	<div style="min-height: -221px;" class="container resize_page_content">
		<div id="MsgPadrao"></div>

		<div class="col-lg-3"></div>
		<div class="col-lg-6">
			<h2>Seja Bem-Vindo!</h2>
			<p><%= request.getAttribute("msg") %></p>
			<div class="boxForm">
				<form action="logar.do" method="post">
					<div class="row">
						<div class="col-lg-12">
							<label for="txtLogin"> Login:</label> <br class="clearfix">
								<input id="txtLogin" name="txtLogin" class="form-control" placeholder="Login"
								type="text">
						</div>
						<div class="col-lg-12">
							<label for="txtPassword"> Senha:</label> <br class="clearfix">
								<input id="txtPassword" name="txtPassword"class="form-control" placeholder="Senha"
								type="password">
						</div>

						<div class="col-lg-12">
							<br class="clearfix">
								<button type="submit" id="btnLogin"
									class="btn btn-default right">Entrar</button>
						</div>
				</form>
			</div>
		</div>
	</div>
	</div>
</body>
</html>