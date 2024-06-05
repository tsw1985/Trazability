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
	 
	 <script src="<%= request.getContextPath()%>/resources/js/functions.js" ></script>
	 
	 <script type="text/javascript">
	 
	 $(document).ready(function(){
		 
		 
		 
		 $.getJSON('http://localhost:8080/trazability/operator/getalloperator', function(data) {

			  $.each(data, function(key, val)
			  {
				  var idField = "<td><input type='hidden' value='"+val.id+"'  id='"+val.id+"'/>"+val.nameOperator+"</td>";
				  var addressField = "<td>"+val.address+"</td>";
				  var cifField = "<td>"+ val.cif +"</td>";
				  var phoneField = "<td>"+ val.phone +"</td>";
				  var iconoDelete = "<%= request.getContextPath() %>/resources/img/eliminar.gif";
				  var iconoUpdate = "<%= request.getContextPath() %>/resources/img/editar.jpg";
				  var accionIconField = "<td><img  id='delete' name='"+val.id+"' class='icono' src='" + iconoDelete + "' /><img id='editar' name='"+val.id+"' class='icono' src='" + iconoUpdate + "'></td>";
				  
		  		  $("#tableproviders").append("<tr>" + idField + addressField + cifField + phoneField + accionIconField+"</tr>");
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
		 

		$("#button-sendprovider").click(function() 
		{
			sendData();
		});
		
		
		function setDataProviderInModalWindow(id)
		{
				$.getJSON('http://localhost:8080/trazability/operator/getoperator/'+id, function(data) {

					  $.each(data, function(key, val)
					  {
						  var id = data.id;
						  var name = data.name;
						  var address = data.address;
						  var cif = data.cif;
						  var phone = data.phone;
						  
						  $("#textIdProvider").val(id);
						  $("#textNameProvider").val(name);
						  $("#textAddressProvider").val(address);
						  $("#textCifProvider").val(cif);
						  $("#textPhoneProvider").val(phone);
					  });
					 });
			};
			

			function setDataProviderInModalWindowDelete(id)
			{
					$.getJSON('http://localhost:8080/trazability/operator/getoperator/'+id, function(data) {

						  $.each(data, function(key, val)
						  {
							  var id = data.id;
							  var name = data.name;
							  var address = data.address;
							  var cif = data.cif;
							  var phone = data.phone;
							  
							  $("#deletedtextIdProvider").val(id);
							  $("#deletedtextNameProvider").val(name);
							  $("#deletedtextAddressProvider").val(address);
							  $("#deletedtextCifProvider").val(cif);
							  $("#deletedtextPhoneProvider").val(phone);
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
			var nameProvider = $("#nameProvider").val();
			var addressProvider = $("#addressProvider").val();
			var cifProvider = $("#cifProvider").val();
			var phoneProvider= $("#phoneProvider").val();
			
			
			$.ajax({
					type: 'POST',
					url: 'create',
					data : 
					{ 
					    name    : nameProvider , 
					    address : addressProvider,
					    cif     : cifProvider, 
					    phone   : phoneProvider 
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
			 var idProvider      = $("#textIdProvider").val();
			 var nameProvider    = $("#textNameProvider").val();
			 var addressProvider = $("#textAddressProvider").val();
			 var cifProvider     = $("#textCifProvider").val();
			 var phoneProvider   = $("#textPhoneProvider").val();
			
			
			$.ajax({
					type: 'POST',
					url: 'update',
					data : 
					{ 
						id      : idProvider,
					    name    : nameProvider , 
					    address : addressProvider,
					    cif     : cifProvider, 
					    phone   : phoneProvider 
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
			var idProvider = $("#deletedtextIdProvider").val();
			$.get('http://localhost:8080/trazability/operator/delete/'+idProvider, function(data)
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
			if(confirm("¿Va a eliminar este proveedor?"))
			sendDataProviderForDeleted();
		});
		
		
		
		
		
		}); //fin document ready
	 
	 </script>
	 
	
	<title>Datos de la fábrica</title>
</head>
<body>

<div class="container">
	<div class="row">
		<div class="span12">
		<div class="separador"></div>
		
			
			<%@include file="../includes/head.jsp" %>
			
			<div class="separador"></div>
			
			<div class="contenedor">

			
			<div id="newprovider">
				<a href="#data" class="btn btn-primary" data-toggle="collapse">Nuevo Operario</a></div>  
			
			<div id="data" class="collapse">  
					
						<div class="row-fluid">
						<div class="span6 offset3">
					
					<div class="form-horizontal">
						  <label>Nombre</label><input type="text" id="nameProvider" width="45px">
						  <label>Dirección</label><input type="text" id="addressProvider" size="100">
						  <label>Teléfono</label><input type="text" id="phoneProvider"  size="20">
						  <label>CIF</label><input type="text" id="cifProvider" size="25">
						  
						  <button id="button-sendprovider" class="btn">Guardar proveedor</button>
					</div>
					
						</div>
						</div>
 			</div>  

			<div id="resultado">
				<p></p>
			</div>			
			

			<h3 class="text-center titulo">Listado de personal</h3>
			
			<div class="row-fluid">
				<div class="span10 offset1">
					<table class="table provider" id="tableproviders">
					<tr class="success">
						<td><strong>Nombre</strong></td>
						<td><strong>Dirección</strong></td>
						<td><strong>CIF</strong></td>
						<td><strong>Teléfono</strong></td>
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
    <h3 id="myModalLabel">MODIFICAR DATOS DE OPERARIO</h3>
  </div>
  <div class="modal-body">
  <h4>DATOS ACTUALES DE OPERARIO</h4>

	<div class="form-horizontal">    
    <label>Id<label>
    <input type="text" id="textIdProvider" disabled="true"/><br>
    <label>Nombre de operario</label>
    <input type="text" id="textNameProvider" /><br>
    <label>Dirección</label>
    <input type="text" id="textAddressProvider" /><br>
    <label>Teléfono</label>
    <input type="text" id="textPhoneProvider" /><br>
    <label>Cif</label>
    <input type="text" id="textCifProvider" /><br>
    </div>
    
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
    <button id="buttonForUpdate" class="btn btn-primary">Guardar cambios</button>
  </div>
</div>


<!-- Modal Update Provider -->
<div id="myModalDeleted" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h3 id="myModalLabel">ELIMINAR OPERARIO</h3>
  </div>
  <div class="modal-body">
  <h4>DATOS ACTUALES DE OPERARIO</h4>

	<div class="form-horizontal">    
    <label>Id<label>
    <input type="text" id="deletedtextIdProvider" disabled="true"/><br>
    <label>Nombre de operario</label>
    <input type="text" id="deletedtextNameProvider" disabled="true" /><br>
    <label>Dirección</label>
    <input type="text" id="deletedtextAddressProvider" disabled="true"/><br>
    <label>Teléfono</label>
    <input type="text" id="deletedtextPhoneProvider" disabled="true"/><br>
    <label>Cif</label>
    <input type="text" id="deletedtextCifProvider" disabled="true"/><br>
    </div>
    
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
    <button id="buttonForDelete" class="btn btn-primary">Eliminar Proveedor</button>
  </div>
</div>


<script src="<%= request.getContextPath()%>/resources/js/bootstrap.js" ></script>
<script src="<%= request.getContextPath()%>/resources/js/bootstrap.min.js" ></script>
</body>
</html>