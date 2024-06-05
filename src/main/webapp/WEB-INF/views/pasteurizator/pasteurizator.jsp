<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap.min.css" type="text/css" media="all">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap.css" type="text/css" media="all">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap-image-gallery.min.css" type="text/css" media="all">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css" media="all">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap-responsive.css" type="text/css" media="all">
	
	<script src="<%= request.getContextPath()%>/resources/js/bootstrap-image-gallery.min.js" ></script>

	 <script src="<%= request.getContextPath()%>/resources/js/jquery.min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/json2.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/underscore-min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/backbone-min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/handlebars.js"></script>
	 
	 <script type="text/javascript">
	 
	 $(document).ready(function(){
		 
		 $.getJSON('http://localhost:8080/trazability/pasteurizator/getallpasteurizatornotdeleted', function(data) {

			  $.each(data, function(key, val)
			  {
				  var idField = "<td><input type='hidden' value='"+val.id+"' id='"+val.id+"'/>"+val.description+"</td>";
				  var capacityField = "<td>"+val.caudalSecond+"</td>";
				  var ipField = "<td>"+val.ip+"</td>";
				  var iconoDelete = "<%= request.getContextPath() %>/resources/img/eliminar.gif";
				  var iconoUpdate = "<%= request.getContextPath() %>/resources/img/editar.jpg";
				  var accionIconField = "<td><img  id='delete' name='"+val.id+"' class='icono' src='" + iconoDelete + "' /><img id='editar' name='"+val.id+"' class='icono' src='" + iconoUpdate + "'></td>";
				  
		  		  $("#tableTruckTank").append("<tr>" + idField + capacityField + ipField + accionIconField+"</tr>");
		  		  
			  });
			  
				 $('img#editar').click(function()
				 {
					 var idImagen = $(this).attr('name'); 
					 setDataProviderInModalWindow(idImagen);
					 $("#myModal").modal('show');
					 
				 });
				 
				 
				 $('img#delete').click(function()
				 {
					 var idImagen = $(this).attr('name'); 
					 setDataProviderInModalWindowDelete(idImagen);
					 $("#myModalDeleted").modal('show');
				 });
				 
		 });
		 

		$("#button-sendtank").click(function() 
		{
			sendData();
		});
		
		
		function setDataProviderInModalWindow(id)
		{
				$.getJSON('http://localhost:8080/trazability/pasteurizator/getpasteurizator/'+id, function(data) {

					  $.each(data, function(key, val)
					  {
						  var id = data.id;
						  var description = data.description;
						  var capacity = data.caudalSecond;
						  
						  $("#textIdTruckTank").val(id);
						  $("#textDescriptionTruckTank").val(description);
						  $("#textCapacityTruckTank").val(capacity);
					  });
				});
		};
			

			function setDataProviderInModalWindowDelete(id)
			{
					$.getJSON('http://localhost:8080/trazability/pasteurizator/getpasteurizator/'+id, function(data) {

						  $.each(data, function(key, val)
						  {
							  var id = data.id;
							  var description = data.description;
							  var capacity = data.caudalSecond;
							  
							  $("#deletedtextIdTruckTank").val(id);
							  $("#deletedtextDescriptionTruckTank").val(description);
							  $("#deletedtextCapacityTruckTank").val(capacity);
							  
						  });
						 });
				};

			 function openUpdateModal(id)
			 {
				 setDataProviderInModalWindow(id);
				 $("#myModal").modal('show');
				 
			 };

		
		
		
		//send data from provider form
		function sendData()
		{
			var descriptionTank = $("#descriptionTank").val();
			var capacityTank = $("#capacityTank").val();
			var ip = $("#ipTank").val();
			
			$.ajax({
					type: 'POST',
					url: 'create',
					data : 
					{ 
					    description : descriptionTank , 
					    caudalSecond    : capacityTank,
					    ip : ip, 
					},
					success: function(data) 
					{
						$("#resultado p").addClass("alert alert-success").attr("class","alert alert-success")
						.text("Datos de proveedor guardados con éxito");
							
					},
					error: function(xhr, text, error) 
					{
						var responseError = JSON.parse(xhr.responseText);
						
						if(xhr.status == 403)
						{

						}
						if(xhr.status == 400)
						{

						}
						if(xhr.status == 500)
						{

						}
					}
				});
		}
		
		

		function sendDataProviderForUpdated()
		{
			 var idTruckTank           = $("#textIdTruckTank").val();
			 var descriptionTruckTank  = $("#textDescriptionTruckTank").val();
			 var capacityTruckTank     = $("#textCapacityTruckTank").val();
			 var ipValue               = $("#textIpTruckTank").val();
			
			
			$.ajax({
					type: 'POST',
					url: 'update',
					data:
					{ 
						id            : idTruckTank,
					    description   : descriptionTruckTank , 
					    caudalSecond  : capacityTruckTank,
					    ip            : ipValue,
					},
					success: function(data) 
					{
						$("#resultado p").addClass("alert alert-success").attr("class","alert alert-success")
						.text("Datos de proveedor guardados con éxito");
							
					},
					error: function(xhr, text, error) 
					{
						var responseError = JSON.parse(xhr.responseText);
						
						if(xhr.status == 403)
						{

						}
						if(xhr.status == 400)
						{

						}
						if(xhr.status == 500)
						{

						}
					}
				});
		}


		function sendDataProviderForDeleted()
		{
			var idProvider = $("#deletedtextIdTruckTank").val();
			$.get('http://localhost:8080/trazability/pasteurizator/delete/'+idProvider, function(data)
			{

				
			});			
						
		}

		
		$("#buttonForUpdate").click(function()
		{
			if(confirm("¿Los datos son correctos?"))
				sendDataProviderForUpdated();
				
		});
		
		
		$("#buttonForDelete").click(function()
		{
			if(confirm("¿Va a eliminar esta cuba?"))
			sendDataProviderForDeleted();
		});
		
		
		
		
		
		}); //fin document ready
	 
	 </script>
	 
	
	<title>Cubas de Fábrica</title>
</head>
<body>

<div class="container">
	<div class="row">
		<div class="span12">
		<div class="separador"></div>
		
			
			<%@include file="../includes/head.jsp" %>
			
			<div class="separador"></div>
			
			<div class="contenedor">
			<h3 class="text-center">Pasteurizadores de fábrica</h3>
			
			<div id="newprovider">
				<a href="#data" class="btn btn-primary" data-toggle="collapse">Nuevo pasteurizador</a></div>  
			
			<div id="data" class="collapse">  
					
						<div class="row-fluid">
						<div class="span6 offset3">
					
					<div class="form-horizontal">
						  <label>Litros por segundo</label><input type="text" id="capacityTank" width="45px"><br>
						  <label>Descripción</label><input type="text" id="descriptionTank" size="100"><br>
						  <label>IP</label><input type="text" id="ipTank" size="100"><br>
						  
						  <button id="button-sendtank" class="btn">Guardar cuba</button>
					</div>
					
						</div>
						</div>
 			</div>  

			<div id="resultado">
				<p></p>
			</div>			

			<h3 class="text-center titulo">Listado de pasteurizadores actuales</h3>
			
			<div class="row-fluid">
				<div class="span10 offset1">
					<table class="table provider" id="tableTruckTank">
					<tr class="success">
						<td><strong>Descripción</strong></td>
						<td><strong>Litros por segundo</strong></td>
						<td><strong>IP Asignada</strong></td>
						<td><strong>Acción</strong></td>
					</tr>


					</table>
					</div>
			
				</div>
			</div>
		</div>
</div>
</div>

<!-- Modal Update Provider -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h3 id="myModalLabel">MODIFICAR DATOS DE PASTEURIZADOR</h3>
  </div>
  <div class="modal-body">
  <h4>DATOS ACTUALES DE PASTEURIZADOR</h4>

	<div class="form-horizontal">    
    <label>Id<label>
    <input type="text" id="textIdTruckTank" disabled="true"/><br>
    <label>Descripción</label>
    <input type="text" id="textDescriptionTruckTank" /><br>
    <label>Capacidad</label>
    <input type="text" id="textCapacityTruckTank" /><br>
    <label>IP</label>
    <input type="text" id="textIpTruckTank" /><br>
    </div>
    
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
    <button id="buttonForUpdate" class="btn btn-primary">Guardar cambios</button>
  </div>
</div>


<!-- Modal Delete -->
<div id="myModalDeleted" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h3 id="myModalLabel">ELIMINAR PASTEURIZADOR</h3>
  </div>
  <div class="modal-body">
  <h4>DATOS ACTUALES DE PASTEURIZADOR</h4>

	<div class="form-horizontal">    
    <label>Id<label>
    <input type="text" id="deletedtextIdTruckTank" disabled="true"/><br>
    <label>Descripción Pasteurizador</label>
    <input type="text" id="deletedtextDescriptionTruckTank" disabled="true" /><br>
    <label>Litros por segundo</label>
    <input type="text" id="deletedtextCapacityTruckTank" disabled="true"/><br>
    <label>IP</label>
    <input type="text" id="deletedtextIpTruckTank" disabled="true"/><br>
    </div>
    
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
    <button id="buttonForDelete" class="btn btn-primary">Eliminar Pasteurizador</button>
  </div>
</div>


<script src="<%= request.getContextPath()%>/resources/js/bootstrap.js" ></script>
<script src="<%= request.getContextPath()%>/resources/js/bootstrap.min.js" ></script>
</body>
</html>