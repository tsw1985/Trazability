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
	 <script src="<%= request.getContextPath()%>/resources/js/json2.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/underscore-min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/backbone-min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/handlebars.js"></script>
	 
	 <script type="text/javascript" >
	 
	 $(document).ready(function(){

		 $(this).delay(5000);
		 listTruckTanks();
		 listFactoryTank();
		 selectTruckTank();
		 selectFactoryTank();
		 
		 getLastCounterNumberMilkFilled();
		 
		 setTimeout(function(){
			 listDataMilkSendToFactoryTank();	 
		 },1800);
		 
		 
		 $("#sendmilkfilled").click(function(){

			 var msg = "ATENCIÓN:\n-------------------\nASEGURESE DE QUE TODOS LOS DATOS SON CORRECTOS.\nUNA VEZ CERRADO EL PARTE DE RECOGIDA NO PODRÁ MODIFICAR LOS DATOS!!!!!";

			 if(confirm(msg)){

				 var dayCounter = $("#countNumberMilkFilled").val();
				 
				 $.get("http://localhost:8080/trazability/service/setlikeclosed/" + dayCounter , function(data)
				 {
					if(data=="0")
					 	alert("ERROR !!! . NO SE HA CERRADO EL PARTE. ERROR EN SERVIDOR. ");
					else
						alert("PARTE CERRADO");
					
				 }).fail(function(e)
				 {
					 alert(e.status);

					switch(e.status)
					{
						case 400:
						{
							alert("Error al enviar parámetros");
							break;
						}
						case 404:
						{
							alert("Error al enviar parámetros. NO SE HA CERRADO EL PARTE ACTUAL");
							break;
						}
					}
						
				 });
			 }
		 }); //end click milk filled
		 
		 
		 $("#buttonForUpdate").click(function(){
			 sendMilkSentToFactoryTankDataUpdated();	 
		 });
		 
		 
		 function selectTruckTank(){

			 $.getJSON('http://localhost:8080/trazability/truck/listalltrucktank', function(data) {

				  $.each(data, function(key, val){
					  
					  var idField = "<option value='"+val.id+"'>"+val.description +"</option>";
					  
			  		  $("#trucktankUpdate").append(idField);
				  });
			 });
		 }

		 
		 function selectFactoryTank(){
			 
			 $.getJSON('http://localhost:8080/trazability/factorytank/listallfactorytank', function(data) {

				  $.each(data, function(key, val)
				  {
					  var idField = "<option value='"+val.id+"'>"+val.description +"</option>";
			  		  $("#factoryTankUpdate").append(idField);
				  });
			 });

		 }

		 
		 function listDataMilkSendToFactoryTank(){
			 
			 var numberDay = $("#countNumberMilkFilled").val();
			 
			 $.getJSON('http://localhost:8080/trazability/sendmilktofactorytank/getsendmilktofactorytankbyday/' + numberDay, function(data) {

				  $.each(data, function(key, val){

					  var idField = "<td><input type='hidden' value='"+val.id+"' id='"+val.id+"'/>"+val.id+"</td>";
					  var numberWorkDayMilkFilled = "<td>"+val.counterWorkingDayForMilkFilled.id+"</td>";
					  var dateSystemEntered= "<td>"+val.dateTimeMilkFilled+"</td>";
					  var truckTank = "<td>" + val.truckTank.description + "</td>";
					  var factoryTank = "<td>" + val.factoryTank.description + "</td>";
					  var liters = "<td>" + val.liters + "</td>";
					  var iconoDelete = "<%= request.getContextPath() %>/resources/img/eliminar.gif";
					  var iconoUpdate = "<%= request.getContextPath() %>/resources/img/editar.jpg";
					  var accionIconField = "<td><img  id='delete' name='"+val.id+"' class='icono' src='" + iconoDelete + "' /><img id='editar' name='"+val.id+"' class='icono' src='" + iconoUpdate + "'></td>";
					  
				      $("#tableMilkFilled").append("<tr>" + numberWorkDayMilkFilled + dateSystemEntered + truckTank + factoryTank + liters + accionIconField + "</tr>");
					  
				  });
				  
					 $('img#editar').click(function()
					 {
						 var idImagen = $(this).attr('name'); 
						 setMilkSentToFactoryTankUpdate(idImagen);
						 $("#myModalUpdate").modal('show');
					 });
					 
					 
					 $('img#delete').click(function(){
						 
						 var idImagen = $(this).attr('name');
						 dialogDeleteItem(idImagen);
					 });
					 
					 
				  
			 });
		 }//end listDataMilkFilled
		 
		 
		 function dialogDeleteItem(id){
			 
			 $.getJSON('http://localhost:8080/trazability/sendmilktofactorytank/get/' +id, function(data) {
				 	
				 var msg = "¿QUIERE ELIMINAR ESTE REGISTRO?\n" + "\nID-> " + data.id + "\nFECHA DE ENTRADA AL SISTEMA: " + data.dateTimeMilkSentToFactoryTank + "\nTANQUE ORIGEN: "+data.truckTank.description+
						 "\nTANQUE DE DESTINO: " + data.factoryTank.description + "\nLITROS: " + data.liters;
				 
				 if( confirm(msg) )
				 	 setLikeDeletedSentMilkFilledById(id);
				 
			 });
		 }

		 
		 
		 
		 $("#save").click(function(){
			 
			 var idProvider              = $("#provider").val();
			 var nameProvider            = $("#provider option:selected").text();
			 var idOperator   			 = $("#operator").val();
			 var nameOperator            = $("#operator option:selected").text();
			 var idTruckTank             = $("#truck").val();
			 var nameTruckTank           = $("#truck option:selected").text();
			 var litersFilled            = $("#litersfilled").val(); 
			 var incidence               = $("#incidence").val();
			 var numberWorkDayMilkFilled = $("#countNumberMilkFilled").val();
			 var tankProvider            = $("#tankprovider").val();
			 var correction              = $("#correctionproblem").val();
			 
			 var displayChecked = "";
			 if($("#display").is(':checked'))
				displayChecked = true;
			 else
				 displayChecked = false;
			 
			 
			 $("#tableMilkFilled").append("<tr><td>" + numberWorkDayMilkFilled + "</td>" +
					 				      "<td>" + displayChecked + "</td>" +
					 				      "<td>" + incidence + "</td>" +
					 				      "<td>" + "<input type='text' value='" + idProvider + "' class='inputwidth' /> " + nameProvider + "</td>" +
					 				      "<td>" + tankProvider + "</td>"+
					 				      "<td>" + "<input type='text' value='" + idTruckTank + "' class='inputwidth' />" + nameTruckTank + "</td>" +
					 				      "<td>" + litersFilled + "</td>" +
					 				      "<td>" + correction   + "</td>" +
					 				      "<td>" + "<input type='text' value='" + idOperator + "' class='inputwidth' />" + nameOperator + "</td></tr>");
		 }); //button save
		 
		 $("#sendlitersfromtrucktanktofactorytank").click(function(){
			 
			 sendLitersFromTruckTankToFactoryTank();
			 
		 });
		 
		 
		 
		 $("#confirmsent").click(function(){
			 var numberWorkDayMilkFilled = $("#countNumberMilkFilled").val();
			 setLikeConfirm(numberWorkDayMilkFilled);
		 });

	 }); //fin document ready
	 
	 
	 function setLikeConfirm(id){
		 $.get('http://localhost:8080/trazability/sendmilktofactorytank/confirm/'+id,function(data){
				 
	 	 });
	 }
	 
	 
	 function listTruckTanks()
	 {
		 $.getJSON('http://localhost:8080/trazability/truck/listalltrucktank', function(data) {

			  $.each(data, function(key, val)
			  {
				  var state = "";
				  if(val.clean == "clean")
					  state = "LIMPIO";
				  if(val.clean == "dirty")
					  state = "SUCIO";
				  
				  
				  var idField = "<option value='"+val.id+"'>"+val.description+" ESTADO: "+state +"</option>";
				  
		  		  $("#truck").append(idField);
			  });
		 });

	 }
	 
	 
	 function listFactoryTank(){
		 
		 $.getJSON('http://localhost:8080/trazability/factorytank/listallfactorytank', function(data) {

			  $.each(data, function(key, val)
			  {
				  var state = "";
				  if(val.clean == "clean")
					  state = "LIMPIO";
				  if(val.clean == "dirty")
					  state = "SUCIO";
				  
				  
				  var idField = "<option value='"+val.id+"'>"+val.description+" ESTADO: "+state +"</option>";
				  
		  		  $("#factoryTank").append(idField);
			  });
		 });
	 }
	 
	 
	 function sendLitersFromTruckTankToFactoryTank()
	 {
		 var idTruckTank             = $("#truck").val();
		 var litersFilled            = $("#litersfilled").val(); 
		 var numberWorkDayMilkFilled = $("#countNumberMilkFilled").val();
		 var factoryTank             = $("#factoryTank").val();
		 
		
		 
		 $.get('http://localhost:8080/trazability/sendmilktofactorytank/sendlitersfromtrucktanktofactorytank/'+numberWorkDayMilkFilled+ '/' + idTruckTank+ '/' + factoryTank+ '/' + litersFilled , function(data)
		 {
				alert("ENVIADO "+ data);
		
		 }).fail(function(e)
		 {
			 alert(e.status);

			switch(e.status)
			{
				case 400:
				{
					alert("Error al enviar parámetros");
					break;
				}
				case 404:
				{
					alert("Error al enviar parámetros, no existe tanque fábrica o de camión ");
					break;
				}
			}
				
		 });
	 }
	 
	 
	 function sendMilkSentToFactoryTankDataUpdated(){
		 
		 var idMilkFilled 			 = $("#textIdMilkFilled").val();
		 var dateEnterSystem         = $("#textDateEntryMilkFilled").val();
		 var idTruckTank             = $("#trucktankUpdate").val();
		 var factoryTank             = $("#factoryTankUpdate").val();
		 var litersFilled            = $("#textLitersMilkFilled").val(); 
		 
		 
		 $.ajax({
				type: 'POST',
				url: 'update',
				data:
				{
					idMilkFilled         : idMilkFilled,
					dateEnterSystem   : dateEnterSystem,
					idTruckTank   : idTruckTank ,
					factoryTank  : factoryTank,
					litersFilled : litersFilled,
				},
				success: function(data) 
				{
					alert("DATOS ENVIADOS CON ÉXITO");
						
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
	 
	 function setLikeDeletedSentMilkFilledById(id){
		 alert("A BORRAR " + id);
		 
		 $.get('http://localhost:8080/trazability/sendmilktofactorytank/delete/'+id,function(data){
			 
		 });
	 }
	 
	 
	 function getLastCounterNumberMilkFilled()
	 {
		 $.getJSON('http://localhost:8080/trazability/service/getlastcounterworkingdayformilkfilled', function(data) {
			$("#countNumberMilkFilled").val(data);
		 });
	 }
	 
	 
	 
	 function setMilkSentToFactoryTankUpdate(id){
		 
		 $.getJSON('http://localhost:8080/trazability/sendmilktofactorytank/get/' +id, function(data) {
			 
			 	$("#textIdMilkFilled").val(data.id);
			    $("#textDateEntryMilkFilled").val(data.dateTimeMilkSentToFactoryTank);
			    $("#trucktankUpdate").val(data.truckTank.id);
			    $("#factoryTankUpdate").val(data.factoryTank.id);
			    $("#textLitersMilkFilled").val(data.liters);
		 });
		 
		 
	 }

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
			
			<h1 class="text-center">ENVIAR LITROS DE CAMIÓN A FÁBRICA</h1>
			<h4 class="text-center">PARTE DE RECOGIDA DE LECHE Nº:<input type="text" class="inputwidth" id="countNumberMilkFilled" disabled="disabled" /></h4>
			
			
				<label>Fecha de recogida</label>
				<input type="date" id="dateMilkFilled"/>	
				<br>
			
				<label>Tanque de camión:</label>
				<select id="truck"></select>	
				<br>
				
				<label>Destino Tanques de fábrica</label>
				<select id="factoryTank"></select>
				<br>
				<label>Litros a enviar:</label>
				<input type="text" id="litersfilled"/>
				<br>
				
				<button id="confirmsent" class="btn btn-warning">CONFIRMAR ENVIO DE LITROS</button>
				<br>
				<button id="sendmilkfilled" class="btn btn-primary">CERRAR PARTE DE RECOGIDA</button>
				<br>
				<button id="sendlitersfromtrucktanktofactorytank" class="btn btn-primary">Enviar litros</button>
				<br>
				
				<table class="table" id="tableMilkFilled">
					<tr class="success">
						
						<td class="success"><strong>Nº Parte</strong></td>
						<td class="success"><strong>Fecha Entrada</strong></td>
						<td class="success"><strong>Origen Tanque Camión</strong></td>
						<td class="success"><strong>Destino Tanque Fábrica</strong></td>
						<td class="success"><strong>Litros Enviados</strong></td>
						<td class="success"><strong>EDICIÓN</strong></td>

					</tr>
				</table>				
				
			</div><!-- contenedor -->
		</div>
		
		
		<div id="myModalUpdate" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h3 id="myModalLabel">MODIFICAR DATOS DE ENVIO DE LECHE</h3>
  </div>
  <div class="modal-body">
  <h4>DATOS ACTUALES DE ENVIO</h4>

	<div class="form-horizontal">    
    <input type="text" id="textIdMilkFilled" disabled="disabled"/><br>
    <label>Fecha de entrada</label>
    <input type="date" id="textDateEntryMilkFilled" /><br>
    <label>Tanque de camión de origen</label>
    <select id="trucktankUpdate"></select><br><br>
    <label>Tanque de fábrica de destino</label>
    <select id="factoryTankUpdate"></select><br>
    <label>Litros enviados</label>
    <input type="text" id="textLitersMilkFilled" /><br>
    
    </div>
    
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
    <button id="buttonForUpdate" class="btn btn-primary">Guardar cambios</button>
  </div>
</div>
		
	

	
	
</div>
</div>
<script src="<%= request.getContextPath()%>/resources/js/bootstrap.min.js" ></script>
</body>
</html>