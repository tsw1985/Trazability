<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<title>Panel de Administración</title>
	
	 <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap.min.css" type="text/css" media="all">
	 <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap-image-gallery.min.css" type="text/css" media="all">
	 <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css" media="all">
	 <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap-responsive.css" type="text/css" media="all">
	
	 <script src="<%= request.getContextPath()%>/resources/js/jquery.min.js"></script>
	 

	 
	 <script type="text/javascript">
	 
	 $(document).ready(function(){
			
			$("#button-send").click(function() 
			{
				sendData();
			});
			
			
			
			$("#dataFactory").click( function()
			{
				window.location.href = "factory/create";
			});
			
			
			
			$("#newprovider").click( function()
			{
				window.location.href = "provider/create";
			});
			
			
			$("#truck").click( function()
			{
				window.location.href = "truck/create";
			});
			
			$("#personal").click( function()
			{
				window.location.href = "operator/create";
			});
			
			
			$("#milkfilled").click( function()
			{
				window.location.href = "milkfilled/loadpage";
			});
			
			
			$("#limpieza").click( function()
			{
				window.location.href = "truck/clean";
			});
			
			$("#factorytank").click( function()
			{
				window.location.href = "factorytank/loadpage";
			});
			
			
			$("#levelfactorytank").click( function()
			{
				window.location.href = "factorytank/loadpagelevelfactorytank";
			});
			
			
			$("#ingredient").click( function()
			{
				window.location.href = "ingredient/loadpageingredient";
			});
			
			$("#producction").click( function()
			{
				window.location.href = "production/loadpageproduction";
			});
			
			
			$("#cuba").click( function()
			{
				window.location.href = "cuba/loadpage";
			});
			
			
			$("#lectures").click( function()
			{
				window.location.href = "chlorinephreaders/loadpage";
			});
			
			
			$("#pasteurizator").click(function(){
				window.location.href = "pasteurizator/loadpage";
			});
			
			
			$("#dossier").click(function(){
				window.location.href = "dossier/loadpage";
			});
			
			$("#fillFactoryTank").click(function(){
				window.location.href = "sendmilktofactorytank/loadpagesendmilktofactorytank";
			});
			
			
			
		
		}); //fin document ready
	 
	 </script>
	 
	
	
</head>
<body>

<div class="container">
	<div class="row">
		<div class="span12">
			<div class="separador"></div>
		
			<%@include file="includes/head.jsp" %>
			
			<div class="separador"></div>
						
			<div class="contenedor">
			<div class="separador"></div>
			<h1 class="text-center titulo">PANEL DE ADMINISTRACIÓN --ARDUINO --</h1>
			<div class="separador"></div>
 			
 			<table class="table" id="tablehome">
 				<tr>
 					<td><button id="producction" class="btn-primary"><img class="panelAdmin" src="<%= request.getContextPath()%>/resources/img/produccion.jpg"/><br>PRODUCCIÓN</button></td>
 					<td><button class="btn-primary"><img class="panelAdmin" src="<%= request.getContextPath()%>/resources/img/humo.jpeg"/><br>AHUMADO</button></td>
 					<td><button id="limpieza" class="btn-primary"><img class="panelAdmin" src="<%= request.getContextPath()%>/resources/img/limpieza.jpg"/><br>LIMPIEZA TANQUES CAMIÓN</button></td>
 					<td><button id="dossier" class="btn-primary"><img class="panelAdmin" src="<%= request.getContextPath()%>/resources/img/informe.jpg"/><br>INFORMES</button></td>
 					<td><button id="cuba" class="btn-primary"><img class="panelAdmin" src="<%= request.getContextPath()%>/resources/img/tanque.gif"/><br>CUBAS<br>FÁBRICA</button></td>
 					<td><button id="ingredient" class="btn-primary"><img class="panelAdmin" src="<%= request.getContextPath()%>/resources/img/ingredientes.jpg"/><br>INGREDIENTES</button></td>
				</tr>
				<tr> 					
 					<td><button id="newprovider" class="btn-primary"><img class="panelAdmin" src="<%= request.getContextPath()%>/resources/img/proveedor.jpg"/><br>PROVEEDORES</button></td>
 					<td><button id="factorytank" class="btn-primary"><img class="panelAdmin" src="<%= request.getContextPath()%>/resources/img/proveedor.jpg"/><br>TANQUES DE FÁBRICA</button></td>
 					<td><button id="personal" class="btn-primary"><img class="panelAdmin" src="<%= request.getContextPath()%>/resources/img/personal.jpg"/><br>PERSONAL</button></td>
 					<td><button id="dataFactory" class="btn-primary"><img class="panelAdmin" src="<%= request.getContextPath()%>/resources/img/fabrica.jpg"/><br>DATOS DE FÁBRICA</button></td>
 					<td><button id="truck" class="btn-primary"><img class="panelAdmin" src="<%= request.getContextPath()%>/resources/img/camion.gif"/><br>TANQUES CAMIÓN</button></td>
 					<td><button id="milkfilled" class="btn-primary" >RECOGIDA LECHE</button></td>
 				</tr>
 				
 				<tr>
 					<td><button id="fillFactoryTank" class="btn-primary" >LLENAR TANQUES DE FÁBRICA</button></td>
 					<td><button id="levelfactorytank" class="btn btn-primary">NIVEL TANQUE FÁBRICA</button></td>
 					<td><button id="lectures" class="btn btn-primary">LECTURAS PH Y DEMÁS</button></td>
 					<td><button id="pasteurizator" class="btn btn-primary">PASTEURIZADORES</button></td>
 				</tr>
 				
 			</table>
 
 			<div class="separador"></div>
 
					
			</div>
		</div>
	
		
	
</div>
</div>

	
		 <script src="<%= request.getContextPath()%>/resources/js/bootstrap.min.js" ></script>
	


	 <script src="<%= request.getContextPath()%>/resources/js/json2.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/underscore-min.js"></script>
	



</body>
</html>