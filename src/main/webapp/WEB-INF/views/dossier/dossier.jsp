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
	
	 <script src="<%= request.getContextPath()%>/resources/js/bootstrap.min.js" ></script>
	 <script src="<%= request.getContextPath()%>/resources/js/bootstrap-image-gallery.min.js" ></script>
	

	 <script src="<%= request.getContextPath()%>/resources/js/jquery.min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/json2.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/underscore-min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/backbone-min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/handlebars.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/handlebars.js"></script>
	 

	 
	 <script type="text/javascript">
	 
	 $(document).ready(function(){
		
		}); //fin document ready
	 
	 </script>
	 
	
	
</head>
<body>

<div class="container">
	<div class="row">
		<div class="span12">
			<div class="separador"></div>
		
			<%@include file="../includes/head.jsp" %>
			
			<div class="separador"></div>
						
			<div class="contenedor">
			<div class="separador"></div>
			<h1 class="text-center titulo">INFORMES</h1>
			<div class="separador"></div>
 			
 			<input type="text" name="lotenumber" />
			<button class="btn btn-primary">DESCARGAR DOSSIER COMPLETO</button>
			<input type="radio" name="temperatures" />
			<input type="radio" name="proveedores" />
			 
			
					
			</div>
		</div>
	
		
	
</div>
</div>

	
	



</body>
</html>