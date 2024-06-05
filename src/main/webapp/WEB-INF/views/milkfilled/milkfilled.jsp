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
	

	  <script src="<%= request.getContextPath()%>/resources/js/bootstrap-image-gallery.min.js" ></script>

	 <script src="<%= request.getContextPath()%>/resources/js/jquery.min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/json2.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/underscore-min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/backbone-min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/handlebars.js"></script>

	
	 <script type="text/javascript" >
	 var tabla = new Array();
	 
	 $(document).ready(function(){
		 

		 $(this).delay(5000);
  	     getLastCounterNumberMilkFilled();
		 listProviders();	 
		 listOperators();
		 listTruckTanks();
		 listFactoryTank();
		 selectProvider();
		 selectOperator();
		 selectTruckTank();
		 
		 //incrementCounterNumberMilkFilled();
		 
		 setTimeout(function(){
			 listDataMilkFilled();			 
		 },1600);

	 
		$("#buttonForUpdate").click(function(){
			sendMilkFilledDataUpdated();
			window.location.href = "loadpage";
		});

		 
		 
		 
		 $("#save").click(function(){
			 
			 sendMilkFilled();
			 
			 window.location.href = "loadpage";
			 
			 	  //muestra colummna fila del td de la tabla actual
			      //$("#tableMilkFilled td").click(function(){
  				      
			    	  //for(var i = 0 ; i < $("#tableMilkFilled tr").length())
			    	  
			    	  	//var col = $(this).parent().children().index($(this));
				      	//var row = $(this).parent().parent().children().index($(this).parent());
				      	
				        //alert("ROW: " + row + "COL: " + col);
				        //tabla[row - 1][col] = "Cambiado";
				      
				     // alert('Row: ' + row + ', Column: ' + col);
		 		  //});
			 
		 }); //button save
		 
		 
		 
		 
		 $("#sendlitersfromtrucktanktofactorytank").click(function(){
			 
			 sendLitersFromTruckTankToFactoryTank();
			 
		 });

	 }); //fin document ready
	 
	 
	 function sendMilkFilled(){
		 /*
		    ESTA FUNCION CONVIERNE A JSON EL ARRAY. LO QUE LO ANULO DE MOMENTO PARA USAR LA MANERA TRADICIONAL
		    PARA ENVIAR LOS DATOS.
		    
		 var totalJson = "";
		 for(var i = 0 ; i < tabla.length ; i++)
		 {
			 var row = '{"numberWorkDayMilkFilled":"'+tabla[i][0]+'","displayChecked":"'+tabla[i][1]+'","incidence":"'+tabla[i][2]+'","idProvider":"'+tabla[i][3]+'","tankProvider":"'+tabla[i][4]+'","idTruckTank":"'+tabla[i][5]+'","litersFilled":"'+tabla[i][6]+'","correction":"'+tabla[i][7]+'","idOperator":"'+tabla[i][8]+'"}';
			 totalJson = totalJson +row + ',';
		 }
		 
		 var finalJson = "["+ totalJson.substring(0,totalJson.length -1) + "]";
		 */
		 
		 
		 
		 var idProvideR              = $("#provider").val();
		 var nameProvideR            = $("#provider option:selected").text();
		 var idOperatoR   			 = $("#operator").val();
		 var nameOperatoR            = $("#operator option:selected").text();
		 var idTruckTanK             = $("#truck").val();
		 var nameTruckTanK           = $("#truck option:selected").text();
		 var litersFilleD            = $("#litersfilled").val(); 
		 var incidencE               = $("#incidence").val();
		 var numberWorkDayMilkFilleD = $("#countNumberMilkFilled").val();
		 var tankProvideR            = $("#tankprovider").val();
		 var correctioN              = $("#correctionproblem").val();
		 
		 var displayChecked = "";
		 if($("#display").is(':checked'))
			displayChecked = true;
		 else
			 displayChecked = false;

		 
		 if(idProvideR == "" || idOperatoR==""   || 
		    idTruckTanK == "" || litersFilleD == "" || 
		    incidencE == "" || numberWorkDayMilkFilleD == "" || 
		    tankProvideR == "" || correctioN == ""){
			 
			 alert("FALTAN DATOS POR RELLENAR!!!");
			 return;
		 }
			 
		 
		 $.ajax({
				type: 'POST',
				url: 'create',
				data:
				{ 
					idProvider   : idProvideR,
					idOperator   : idOperatoR ,
					idTruckTank  : idTruckTanK,
					litersFilled : litersFilleD,
					incidence    : incidencE,
					tankProvider : tankProvideR ,
					correction   : correctioN ,
					display      : displayChecked,
					numberWorkDayMilkFilled : numberWorkDayMilkFilleD ,
					
					
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
	 
	 
	 
	 function incrementCounterNumberMilkFilled()
	 {
		 $.get('http://localhost:8080/trazability/service/incrementcounterworkingdayformilkfilled');
	 }
	 
	 
	 function getLastCounterNumberMilkFilled()
	 {
		 $.getJSON('http://localhost:8080/trazability/service/getlastcounterworkingdayformilkfilled', function(data) {
			$("#countNumberMilkFilled").val(data);
		 });
	 }
	 
	 function listProviders()
	 {
		 $.getJSON('http://localhost:8080/trazability/provider/getallprovider', function(data) {
	
			  $.each(data, function(key, val)
			  {
				  var idField = "<option value='"+val.id+"'>"+val.name+"</option>";
		  		  $("#provider").append(idField);
			  });
				 
		 });
	 }
	 
	 function listOperators()
	 {
		 $.getJSON('http://localhost:8080/trazability/operator/getalloperator', function(data) {

			  $.each(data, function(key, val)
			  {
				  var idField = "<option value='"+val.id+"'>"+val.nameOperator+"</option>";
				  
		  		  $("#operator").append(idField);
			  });
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
				  
				  
				  var idField = "<option value='"+val.id+"'>"+val.description+"</option>";
				  
		  		  $("#truck").append(idField);
			  });
		 });

	 }
	 
	 
	 function listDataMilkFilled(){
		 
		 var numberDay = $("#countNumberMilkFilled").val();
		 
		 $.getJSON('http://localhost:8080/trazability/milkfilled/getallmilkfilledfromanumberworkdaymilkfilled/' + numberDay, function(data) {

			  $.each(data, function(key, val)
			  {

				  var idField = "<td><input type='hidden' value='"+val.id+"' id='"+val.id+"'/>"+val.id+"</td>";
				  var numberWorkDayMilkFilled = "<td>"+val.numberWorkDayMilkFilled+"</td>";
				  var display = "<td>"+val.display+"</td>";
				  var incidence = "<td>"+val.incidence+"</td>";
				  var provider = "<td>" + val.provider.name + "</td>"; 
				  var providerTank = "<td>" + val.providerTank + "</td>";
				  var truckTank = "<td>" + val.truckTank.description + "</td>";
				  var liters  = "<td>" + val.totalFilled + "</td>";
				  var correction = "<td>" + val.correction + "</td>";
				  var operator = "<td>" + val.operator.nameOperator +  "</td>";
				  
				  var iconoDelete = "<%= request.getContextPath() %>/resources/img/eliminar.gif";
				  var iconoUpdate = "<%= request.getContextPath() %>/resources/img/editar.jpg";
				  var accionIconField = "<td><img  id='delete' name='"+val.id+"' class='icono' src='" + iconoDelete + "' /><img id='editar' name='"+val.id+"' class='icono' src='" + iconoUpdate + "'></td>";
				  
			      $("#tableMilkFilled").append("<tr>" + numberWorkDayMilkFilled + display + incidence + provider + providerTank + truckTank + liters + correction + operator+ accionIconField + "</tr>");
				  
			  });
			  
			  
				 $('img#editar').click(function()
				 {
					 var idImagen = $(this).attr('name'); 
					 setDataMilkFilledInWindowUpdate(idImagen);
					 $("#myModalUpdate").modal('show');
				 });
			  
		 });
	 }//end listDataMilkFilled
	 
	 
	 function selectProvider(){
		 $.getJSON('http://localhost:8080/trazability/provider/getallprovider', function(data) {
				
			  $.each(data, function(key, val)
			  {
				  var idField = "<option value='"+val.id+"'>"+val.name+"</option>";
		  		  $("#providerUpdate").append(idField);
			  });
		 });
	 } //end createSelectProvider
	 
	 
	 
	 function selectOperator(){
		 $.getJSON('http://localhost:8080/trazability/operator/getalloperator', function(data) {

			  $.each(data, function(key, val)
			  {
				  var idField = "<option value='"+val.id+"'>"+val.nameOperator+"</option>";
				  
		  		  $("#operatorUpdate").append(idField);
			  });
		 });
	 } //end select operator
	 
	 
	 function selectTruckTank(){

		 $.getJSON('http://localhost:8080/trazability/truck/listalltrucktank', function(data) {

			  $.each(data, function(key, val)
			  {
				  var idField = "<option value='"+val.id+"'>"+val.description +"</option>";
				  
		  		  $("#trucktankUpdate").append(idField);
			  });
		 });
	 }
	 
	 
	 
	 function listFactoryTank(){
		 
		 ///factorytank/listallfactorytank
		 
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
	 
	 
	 function sendRowOrder(numberWorkDayMilkFilled,displayChecked,incidence,idProvider,tankProvider,idTruckTank,litersFilled,correction,idOperator)
	 {
			$.ajax({
				type: 'POST',
				url: 'sendordermilkfilledbycommand',
				data:
				{ 
					idProvider    : idProvider,
				    idOperator    : idOperator,
				    idTruckTank   : idTruckTank,
				    litersFilled  : litersFilled,
				    incidence     : incidence,
				    correction    : correction,
				    numberWorkDayMilkFilled : numberWorkDayMilkFilled,
				    display       : displayChecked.toString(),
				    tankProvider  : tankProvider,
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
	 
	 
	 function sendMilkFilledDataUpdated(){
		 
		  	var id               = $("#textIdMilkFilled").val();
		    var displayChecked   = $("#textDisplayMilkFilled").val();
		    var incidence        = $("#textIncidenceMilkFilled").val();
		    var idProvider       = $("#providerUpdate").val();
		    var tankProvider     = $("#textProviderTankMilkFilled").val();
		    var idTruckTank      = $("#trucktankUpdate").val();
		    var litersFilled     = $("#textLitersMilkFilled").val();
		    var correction       = $("#textCorrectionMilkFilled").val();
		    var idOperator       = $("#operatorUpdate").val();
		 
		 $.ajax({
				type: 'POST',
				url: 'update',
				data:
				{
					id           : id,
					idProvider   : idProvider,
					idOperator   : idOperator ,
					idTruckTank  : idTruckTank,
					litersFilled : litersFilled,
					incidence    : incidence,
					tankProvider : tankProvider ,
					correction   : correction ,
					display      : displayChecked,
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
	 
	 
	 

	 
	 
	 
	 function setDataMilkFilledInWindowUpdate(id){
		 
				$.getJSON('http://localhost:8080/trazability/milkfilled/get/'+id, function(data) {

					  //$.each(data, function(key, val)
					  //{
						  console.log(data);
						  
						     var idMilkFilled            = data.id;
							 var idProvider              = data.provider.id;
							 var idOperator   			 = data.operator.id;
							 var idTruckTank             = data.truckTank.id;
							 var display                 = data.display;
							 var litersFilled            = data.totalFilled; 
							 var incidence               = data.incidence;
							 //var numberWorkDayMilkFilled = data.numberWorkDayMilkFilled;
							 var tankProvider            = data.providerTank;
							 var correction              = data.correction;
						  
						  
						    $("#textIdMilkFilled").val(idMilkFilled);
						    $("#textDisplayMilkFilled").val(display);
						    $("#textIncidenceMilkFilled").val(incidence);
						    $("#textProviderMilkFilled").val(idProvider);
						    $("#textProviderTankMilkFilled").val(tankProvider);
						    $("#trucktankUpdate").val(idTruckTank);
						    $("#textLitersMilkFilled").val(litersFilled);
						    $("#textCorrectionMilkFilled").val(correction);
						    $("#textOperatorMilkFilled").val(idOperator);
						  
					  //});
				});
		};
		
		


	 

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
			<h4 class="text-center">PARTE DE RECOGIDA DE LECHE Nº:<input type="text" class="inputwidth" id="countNumberMilkFilled" disabled="disabled" /></h4>
			
				<label><strong>-OPERARIO QUE RECOGE LA LECHE:</strong></label>
				<select id="operator"></select>
				<br>
				
 				<label><strong>-LECHE DEL PROVEEDOR:</strong></label>
				<select id="provider"></select> 
				<br>

					
				<label><strong>-ESTA LECHE VA AL TANQUE DE CAMIÓN:</strong></label>
				<select id="truck"></select>	
				<br>
				
				<label><strong>-LITROS RECOGIDOS:</strong></label>
				<input type="text" id="litersfilled"/>
				<br>
				
				<label><strong>-PROVIENE DEL TANQUE DE PROVEEDOR CON NOMBRE:</strong></label>
				<input id="tankprovider" type="text" />
				<br>
				

				
				
				<label><strong>-¿HAY MUESTRA DE LECHE?</strong></label>
				<input type="checkbox" id="display"/>
				<br>
						
				<label><strong>-¿HAY ALGUNA INCIDENCIA?¿CUÁL?</strong></label>
				<input id="incidence" type="text" />
				<br>


				<label><strong>-¿CUÁL FUE LA CORRECIÓN?</strong></label>
				<input id="correctionproblem" type="text" />
				<br>
				
				<!-- 
				<label>Destino Tanques de fábrica</label><select id="factoryTank"></select><br> -->
				
				
				
				<button id="save" class="btn btn-primary">GUARDAR DATOS</button>

				<button id="sendlitersfromtrucktanktofactorytank" class="btn btn-primary">Enviar litros</button>
				
				
				<table class="table" id="tableMilkFilled">
					<tr class ="success">
						<!-- <td class="success"><strong>Id</strong></td> -->
						<td class="success"><strong>Nº Recogida</strong></td>
						<td class="success"><strong>Muestra</strong></td>
						<td class="success"><strong>Incidencia</strong></td>
						<td class="success"><strong>Proveedor</strong></td>
						<td class="success"><strong>Tanque origen proveedor</strong></td>
						<td class="success"><strong>Tanque camión destino</strong></td>
						<td class="success"><strong>Litros recogidos</strong></td>
						<td class="success"><strong>Corrección</strong></td>
						<td class="success"><strong>Operador</strong></td>
						<td class="success"><strong>EDICIÓN</strong></td>
					</tr>
				
				</table>				
				
				
			</div><!-- contenedor -->
		</div>
	
<div id="myModalUpdate" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h3 id="myModalLabel">MODIFICAR DATOS DE RECODIGA</h3>
  </div>
  <div class="modal-body">
  <h4>DATOS ACTUALES DE RECOGIDA</h4>

	<div class="form-horizontal">    
    <input type="hidden" id="textIdMilkFilled" disabled="true"/><br>
    <label>Muestra</label>
    <input type="text" id="textDisplayMilkFilled" /><br>
    <label>Incidencia</label>
    <input type="text" id="textIncidenceMilkFilled" /><br>
    <label>Proveedor</label>
    <select id="providerUpdate"></select><br>
    <label>Tanque de origen del proveedor</label>
    <input type="text" id="textProviderTankMilkFilled" /><br>
    <label>Tanque de camión de destino</label>
    <select id="trucktankUpdate"></select><br><br>
    <label>Litros recogidos</label>
    <input type="text" id="textLitersMilkFilled" /><br>
    <label>Corrección</label>
    <input type="text" id="textCorrectionMilkFilled" /><br>
    <label>Operador</label>
    <select id="operatorUpdate"></select><br>
    
    </div>
    
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
    <button id="buttonForUpdate" class="btn btn-primary">Guardar cambios</button>
  </div>
</div>
	
		
	
</div>
</div>
<script src="<%= request.getContextPath()%>/resources/js/bootstrap.js" ></script>
<script src="<%= request.getContextPath()%>/resources/js/bootstrap.min.js" ></script>
</body>
</html>