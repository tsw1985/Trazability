<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap.min.css" type="text/css" media="all">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap-image-gallery.min.css" type="text/css" media="all">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css" media="all">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap-responsive.css" type="text/css" media="all">
	
	 <script src="<%= request.getContextPath()%>/resources/js/jquery.min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/json2.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/underscore-min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/backbone-min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/handlebars.js"></script>
	 
	 <script src="<%= request.getContextPath()%>/resources/js/functions.js" ></script>
	 
	 <script type="text/javascript">
	 
	 $(document).ready(function(){
		 
		 
			$("#button-send").click(function() 
			{
				sendData();
			});

			$("a[name=showdata]").click(function()
			{
				$.getJSON("http://localhost:8080/trazability/factory/getfactorydata",function(data)
			    {
					 $("#shownameFactory").val(data.name);
					 $("#showcifFactory").val(data.cif);
					 $("#showaddressFactory").val(data.address);
					 $("#showphoneFactory").val(data.phone);
				 });
			});
			
			
			
		//send data from factory
		function sendData()
		{
			var nameFactory = $("#nameFactory").val();
			var addressFactory = $("#addressFactory").val();
			var cifFactory = $("#cifFactory").val();
			var phoneFactory = $("#phoneFactory").val();
			
			
			$.ajax({
					type: 'POST',
					url: 'create',
					data : 
					{ 
					    name    : nameFactory , 
					    address : addressFactory,
					    cif     : cifFactory, 
					    phone   : phoneFactory 
					},
					success: function(data) 
					{
							$("#resultado p").addClass("alert alert-success").attr("class","alert alert-success")
							.text("Datos de fábrica guardados con éxito");
							$('#button-send').attr("disabled", true);
					},
					error: function(xhr, text, error) 
					{
						var responseError = JSON.parse(xhr.responseText);
						
						if(xhr.status == 403)
						{
						    $('#barra').hide();
							$("#resultadoNuevoRegistro").addClass("alert alert-danger").text("ACCESO PROHIBIDO");
							$('#botonRegistroFichajeGratis').attr("disabled", false);
						}
						if(xhr.status == 400)
						{
							$('#barra').hide();
							$("#resultadoNuevoRegistro").addClass("alert alert-danger").text(responseError.error);
							$('#botonRegistroFichajeGratis').attr("disabled", false);
						}
						if(xhr.status == 500)
						{
							$('#barra').hide();
							$("#resultadoNuevoRegistro").addClass("alert alert-danger").text("ERROR INTERNO EN EL SERVIDOR:" + responseError.error);
							$('#botonRegistroFichajeGratis').attr("disabled", false);
						}
					}
				});
		}
		}); //fin document ready
	 
	 </script>
	 
	
	<title>Datos de la fábrica</title>
</head>
<body>

<div class="container">
	<div class="row">
		<div class="span10 offset1">
		<div class="separador"></div>
		<div class="separador"></div>
		
			
			<%@include file="../includes/head.jsp" %>
			
			<div class="separador"></div>
						
			<div class="contenedor">
			<div class="separador"></div>
			
			<ul class="nav nav-tabs">
				  <li class="active"><a href="#newfactory" data-toggle="tab">Nueva Fábrica</a></li>
				  <li><a name="showdata" href="#showdatafactory" data-toggle="tab">Ver datos de fábrica actual</a></li>
			</ul>
			
			
			<div class="tab-content">
			  <div class="tab-pane active" id="newfactory">
			  <div class="row-fluid">
							<div class="span6 offset3">
							<h3 class="text-center">Nueva Fábrica</h3>
					
								<div class="form-horizontal">
									<div class="separador"></div>
								<div class="control-group">
								    <label class="control-label" for="inputEmail">Nombre de fábrica</label>
								    <div class="controls">
									      <input type="text" id="nameFactory"/>
									</div>
								</div>
								
								<div class="control-group">
								   <label class="control-label" for="inputPassword">CIF</label>
								   <div class="controls">
								      <input type="text" id="cifFactory"/>
								   </div>
								</div>
								
								<div class="control-group">
								   <label class="control-label" for="inputPassword">Dirección</label>
								   <div class="controls">
								      <input type="text" id="addressFactory" />
								   </div>
								</div>
								
								
								<div class="control-group">
								   <label class="control-label" for="inputPassword">Teléfono</label>
								   <div class="controls">
								      <input type="text" id="phoneFactory" />
								   </div>
								</div>
								
								<div class="control-group">
								   <div class="controls">
								    <button id="button-send" class="btn btn-primary">GUARDAR DATOS</button>
								    
								   </div>
								</div>
							
								</div><!-- horizontal form -->
							<div id="resultado"><p class="text-center">${resultado}</p></div>
						<div class="separador"></div>
					</div>
				</div><!-- row fluid -->
			  </div><!-- tab newfactory -->


  			  <div class="tab-pane" id="showdatafactory">
  			  
  							<div class="row-fluid">
							<div class="span6 offset3">
							<h3 class="text-center">Datos Actuales de Fábrica</h3>
					
							${datosFabrica}
					
								<div class="form-horizontal">
									<div class="separador"></div>
								<div class="control-group">
								    <label class="control-label" for="inputEmail">Nombre de fábrica</label>
								    <div class="controls">
									      <input type="text" id="shownameFactory"/>
									</div>
								</div>
								
								<div class="control-group">
								   <label class="control-label" for="inputPassword">CIF</label>
								   <div class="controls">
								      <input type="text" id="showcifFactory"/>
								   </div>
								</div>
								
								<div class="control-group">
								   <label class="control-label" for="inputPassword">Dirección</label>
								   <div class="controls">
								      <input type="text" id="showaddressFactory" />
								   </div>
								</div>
								
								
								<div class="control-group">
								   <label class="control-label" for="inputPassword">Teléfono</label>
								   <div class="controls">
								      <input type="text" id="showphoneFactory" />
								   </div>
								</div>
								
								</div><!-- horizontal form -->

						<div class="separador"></div>
					</div>
				</div><!-- row fluid -->
	  
  			  
  			  
  			  
  			  </div><!-- tab showdatafactory -->
		</div><!-- tab content -->
			
			
			
			
				</div><!-- contenedor -->
 	</div><!-- span10 offset1 -->  
			
			<h1 class="text-center titulo">Datos de fábrica</h1>
	
		
	
</div><!-- row -->
</div><!-- container -->

	
	

<script src="<%= request.getContextPath()%>/resources/js/bootstrap.min.js" ></script>
<script src="<%= request.getContextPath()%>/resources/js/bootstrap-image-gallery.min.js" ></script>

</body>
</html>