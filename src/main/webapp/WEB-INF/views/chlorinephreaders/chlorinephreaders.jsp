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

		
		 $("#buttonsenddatastreetwater").click(function(){
			 
			 var chlorineStreetWater = $("#chlorinestreet").val();
			 var phStreetWater       = $("#phstreet").val();
			 
			 $.get('http://localhost:8080/trazability/chlorinestreetwater/setchlorineph/' + chlorineStreetWater + '/' + phStreetWater + '/' , function(data)
			 {
					alert("DATOS ENVIADOS "+ data);
			
			 }).fail(function(e)
			 {
				 alert(e.status);

				switch(e.status)
				{
					case 400:
					{
						alert("Error al enviar parámetros de agua de la calle");
						break;
					}
					case 404:
					{
						alert("Error 404 al enviar cloro y ph");
						break;
					}
				}
			 });
		 }); //End click enviar data agua calle
		
		 
		 $("#buttonsenddatasalmuerawater").click(function(){
			 
			 var chlorineSalmueraWater = $("#chlorinesalmuera").val();
			 var phSalmueraWater       = $("#phsalmuera").val();
			 
			 $.get('http://localhost:8080/trazability/chlorinesalmuera/setchlorine/'+ chlorineSalmueraWater + '/' + phSalmueraWater + '/' , function(data)
			 {
					alert("DATOS ENVIADOS "+ data);
			
			 }).fail(function(e)
			 {
				 alert(e.status);

				switch(e.status)
				{
					case 400:
					{
						alert("Error al enviar parámetros de agua de salmuera");
						break;
					}
					case 404:
					{
						alert("Error 404 al enviar cloro y ph");
						break;
					}
				}
			 });
		 });
		
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
			<h3 class="text-center titulo">LECTURAS DIARIAS</h3>
			<div class="separador"></div>
 			
 			<fieldset>
 				<legend>AGUA DE LA CALLE</legend>
 				<label><strong>CLORO</strong></label>
 				<input type="text" id="chlorinestreet" />
 				<label><strong>PH</strong></label>
 				<input type="text" id="phstreet" />
 				
 				<button id="buttonsenddatastreetwater">ENVIAR</button>
 				
 			</fieldset>
 			
 			 <fieldset>
 				<legend>AGUA DE SALMUERA</legend>
 				<label><strong>CLORO</strong></label>
 				<input type="text" id="chlorinesalmuera" />
 				<label><strong>PH</strong></label>
 				<input type="text" id="phsalmuera" />
				
 				<button id="buttonsenddatasalmuerawater">ENVIAR</button>
 				
 			</fieldset>
 			
 			<div class="separador"></div>
					
			</div>
		</div>
</div>
</div>
</body>
</html>