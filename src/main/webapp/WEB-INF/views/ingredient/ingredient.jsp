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
	 
	 
	 <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/jsKeyboard.css" type="text/css" media="screen"/>
	 <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jsKeyboard.js"></script>	
 	 
	 
	
	 
	 <script type="text/javascript">
	 
	 $(document).ready(function(){
		 
		 $(function () {
	         jsKeyboard.init("virtualKeyboard");
	         $("#txtContent").val(initText);
	     });
		 
		 
		 $.getJSON('http://localhost:8080/trazability/ingredient/getallingredientdetailorderbyiddesc', function(data) {

			  $.each(data, function(key, val){
				  
				  var idField = "<td><input type='hidden' value='"+val.id+"' id='"+val.id+"'/>"+val.note+"</td>";
				  var iconoDelete = "<%= request.getContextPath() %>/resources/img/eliminar.gif";
				  var iconoUpdate = "<%= request.getContextPath() %>/resources/img/editar.jpg";
				  var iconoAddDetail = "<%= request.getContextPath() %>/resources/img/add.jpg";
				  var iconoInfo = "<%= request.getContextPath() %>/resources/img/info.png";
				  
				  var accionIconField = "<td><img  id='delete' name='"+val.id+"' class='icono' src='" + iconoDelete + "' /><img id='editar' name='"+val.id+"' class='icono' src='" + iconoUpdate +"'/><img id='info' name='"+val.id+"' class='icono' src='" + iconoInfo +"'/></td>";
				  
		  		  $("#tableTruckTank").append("<tr>" + idField + accionIconField+"</tr>");
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
				 
				 
				 $('img#info').click(function()
				 {
					 var idImagen = $(this).attr('name'); 
					 showInformationIngredientDetail(idImagen);
					 $("#myModalshow").modal('show');
				 });
				 
				 
				 
				 
		 });
		 

		$("#button-sendtank").click(function() 
		{
			sendData();
		});
		
		
		function setDataProviderInModalWindow(id)
		{
			$("#infoIngredientIdUpdate").val(id);	
		};
			

			function setDataProviderInModalWindowDelete(idValue)
			{
					$.getJSON('http://localhost:8080/trazability/ingredient/getingredientdetail/'+idValue, function(data) {

						  $.each(data, function(key, val)
						  {
							  var id = data.id;
							  var description = data.note;
							  var capacity = data.loteNumber;
							  
							  $("#deletedtextIdTruckTank").val(id);
							  $("#deletedtextDescriptionTruckTank").val(description);
							  $("#deletedtextCapacityTruckTank").val(capacity);
							  
						  });
						 });
				};
				
				
				
				function showInformationIngredientDetail(idValue)
				{
						$.getJSON('http://localhost:8080/trazability/ingredient/getingredientdetail/'+idValue, function(data) {

							  $.each(data, function(key, val){
								  
								  var id = data.id;
								  var description = data.note;
								  var lote = data.lote;
								  var dateDeprecation = data.expirationDate;
								  var boughtDate = data.boughtDate;
								  
								  $("#addtextIdTruckTankshow").val(id);
								  $("#addtextDescriptionTruckTankshow").val(description);
								  $("#addtextBoughtTruckTankshow").val(boughtDate);
								  $("#addtextDeprecationTruckTankshow").val(dateDeprecation);
								  $("#addtextLoteTruckTankshow").val(lote);
								  
								  
							  });
							 });
					};

			 function openUpdateModal(id)
			 {
				 setDataProviderInModalWindow(id);
				 $("#myModal").modal('show');
				 
			 };

		
		
		
		//send data from provider form
		function sendDataAddNewIngredientDetail(){
			
			 var boughtDate = $("#addtextBoughtTruckTank").val();
			 var expirationDate =  $("#addtextDeprecationTruckTank").val();;
			 var loteNumber = $("#addtextLoteTruckTank").val();
			 var note = $("#addtextDescriptionTruckTank").val();
			
			$.ajax({
					type: 'POST',
					url: 'adddetail',
					data : 
					{ 
						boughtDate     : boughtDate ,
						expirationDate : expirationDate ,
						loteNumber     : loteNumber , 
						note           : note,
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
		
		

		function sendDataProviderForUpdated(){
			
			 var boughtDate = $("#infoIngredientDetailBoughtUpdate").val();
			 var expirationDate =  $("#infoIngredientDetailExpirationUpdate").val();;
			 var loteNumber = $("#infoIngredientDetailLoteUpdate").val();
			 var note = $("#infoIngredientDetailDescriptionUpdate").val();
			 var id = $("#infoIngredientIdUpdate").val();
			 
			$.ajax({
					type: 'POST',
					url: 'update',
					data : 
					{ 
						id             : id ,
						boughtDate     : boughtDate ,
						expirationDate : expirationDate ,
						loteNumber     : loteNumber , 
						note           : note ,
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
			$.get('http://localhost:8080/trazability/ingredient/delete/'+idProvider, function(data)
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
			if(confirm("¿Va a eliminar este tanque?"))
			sendDataProviderForDeleted();
		});
		
		
		$("#newingredient").click(function(){
			$("#myModalAdd").modal('show');
		});
		
		
		$("#buttonForAddDetail").click(function(){
			sendDataAddNewIngredientDetail();
		});
		
		
		
		
		}); //fin document ready
	 
	 </script>
	 
	
	<title>Tanques de camión</title>
</head>
<body>

<div class="container">
	<div class="row">
		<div class="span12">
		<div class="separador"></div>
		
			
			<%@include file="../includes/head.jsp" %>
			
			<div class="separador"></div>
			
			<div class="contenedor">
			<h3 class="text-center">INGREDIENTES ACTUALES</h3>
			
			<div id="newprovider">
				<button id="newingredient" class="btn btn-primary" data-toggle="collapse">Nuevo tanque</button>
			</div>  
			
			<div id="resultado">
				<p></p>
			</div>			

			
			<div class="row-fluid">
				<div class="span10 offset1">
					<table class="table provider" id="tableTruckTank">
					<tr class="success">
						<td><strong>Descripción</strong></td>
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
    <h3 id="myModalLabel">MODIFICAR DATOS DE INGREDIENTE</h3>
  </div>
  <div class="modal-body">
  <h4>DATOS ACTUALES DE INGREDIENTE</h4>

	<div class="form-horizontal">    
	
	<label>Id<label>
    <input type="text" id="infoIngredientIdUpdate" disabled="true" /><br>
	
    <label>Número de Lote</label>
    <input type="text" id="infoIngredientDetailLoteUpdate" /><br>
    
    <label>Fecha de caducidad</label>
    <input type="date" id="infoIngredientDetailExpirationUpdate" /><br>
    
    <label>Fecha de compra</label>
    <input type="date" id="infoIngredientDetailBoughtUpdate" /><br>
    
    <label>Descripción</label>
    <input type="text" id="infoIngredientDetailDescriptionUpdate" /><br>
    
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
    <h3 id="myModalLabel">ELIMINAR TANQUE</h3>
  </div>
  <div class="modal-body">
  <h4>DATOS ACTUALES DE TANQUE</h4>

	<div class="form-horizontal">    
    <label>Id<label>
    <input type="text" id="deletedtextIdTruckTank" disabled="true"/><br>
    <label>Descripción Tanque</label>
    <input type="text" id="deletedtextDescriptionTruckTank" disabled="true" /><br>
    <label>Capacidad de Tanque</label>
    <input type="text" id="deletedtextCapacityTruckTank" disabled="true"/><br>
    </div>
    
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
    <button id="buttonForDelete" class="btn btn-primary">Eliminar Proveedor</button>
  </div>
</div>



<!-- MODAL AÑADIR INGREDIENTE -->

<div id="myModalAdd" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h3 id="myModalLabel">DETALLES INGREDIENTE</h3>
  </div>
  <div class="modal-body">
  <h4>DETALLE INGREDIENTE</h4>

	<div class="form-horizontal">    
    <label>Id<label>
    <input type="text" id="addtextIdTruckTank" disabled="true"/><br>
    
    <label>Descripción</label>
    <input type="text" id="addtextDescriptionTruckTank" /><br>
    
    <label>Nº Lote</label>
    <input type="text" id="addtextLoteTruckTank" /><br>
    
    <label>Fecha de compra</label>
    <input type="date" id="addtextBoughtTruckTank" /><br>
    
    <label>Fecha de caducidad</label>
    <input type="date" id="addtextDeprecationTruckTank" /><br>
    
    
    
    
    </div>
    
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
    <button id="buttonForAddDetail" class="btn btn-primary">Enviar detalle</button>
  </div>
</div>



<div id="myModalshow" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h3 id="myModalLabel">DETALLES INGREDIENTE</h3>
  </div>
  <div class="modal-body">
  <h4>DETALLE INGREDIENTE</h4>

	<div class="form-horizontal">    
    <label>Id<label>
    <input type="text" id="addtextIdTruckTankshow" disabled="true"/><br>
    
    <label>Descripción</label>
    <input type="text" id="addtextDescriptionTruckTankshow" /><br>
    
    <label>Nº Lote</label>
    <input type="text" id="addtextLoteTruckTankshow" /><br>
    
    <label>Fecha de compra</label>
    <input type="date" id="addtextBoughtTruckTankshow" /><br>
    
    <label>Fecha de caducidad</label>
    <input type="date" id="addtextDeprecationTruckTankshow" /><br>
    
    
    
    
    </div>
    
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
    <button id="buttonForAddDetail" class="btn btn-primary">Enviar detalle</button>
  </div>
</div>










<script src="<%= request.getContextPath()%>/resources/js/bootstrap.js" ></script>
<script src="<%= request.getContextPath()%>/resources/js/bootstrap.min.js" ></script>
</body>
</html>