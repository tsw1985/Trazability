<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<title>Panel de Administración</title>
	$("#listfactoryTanks div").removeClass("factoryTankSelected").addClass("factoryTankNoSelected");
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap.min.css" type="text/css" media="all">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap.css" type="text/css" media="all">

	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css" media="all">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/bootstrap-responsive.css" type="text/css" media="all">
	
	<script src="<%= request.getContextPath()%>/resources/js/bootstrap-image-gallery.min.js" ></script>

	 <script src="<%= request.getContextPath()%>/resources/js/jquery.min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/json2.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/underscore-min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/backbone-min.js"></script>
	 <script src="<%= request.getContextPath()%>/resources/js/handlebars.js"></script>
	 
	 <script type="text/javascript" >
	 
	 var tabla = new Array();
	 var ingredientesSelectedTable = new Array();
	 
	 
	 var ingredientSelected = false;
	 var actualLitersFactoryTank = 0; 
	 var finishActualLiters = 0;
	 var litersConsumed = 0 ;
		 
	 var newLote = false;
	 
	 
	$(document).ready(function(){
		
		createIntervalGetTemperature();
		createIntervalGetStatePasteurizing();
		//listFactoryTank();
		loadLotesNotFinished();
		
		
		$("#newLote").click(function(){
			 
			 if(confirm("¿Nuevo Lote?")){
				     newLote = true;    
				 	 incrementLoteCounter();
				 	 setTimeout(function() { getLastLoteCounter(); },3000);
					 listIngredients();	 
					 listOperators();
					 listCubas();
					 listPasteurizator();
					 
					 listFactoryTank();
					 
					 $("#modalPendingLotes").modal('hide');
				 }
		 });
		
		
		$("#exitpage").click(function(){
			window.location.href = "exitpageproduction";
		});
		
		
		$("#endLote").click(function(){
			
			var ingredientsSelected = $("#tableIngredient tr.selected ").length;
			if(ingredientsSelected == 0){
				alert("NO PUEDE FINALIZAR UN LOTE SIN ELEGIR INGREDIENTES");
				return;
			}
			
			var cuajoTemperature = $("#cuajotemperature").val();
			var cuajoTime        = $("#cuajotime").val();
			var kgcheese         = $("#kgcheese").val();
			
			if(cuajoTemperature == "" || cuajoTime == "" || kgcheese == ""){
				alert("PARA PODER FINALIZAR EL LOTE TIENE QUE ESCRIBIR LOS DATOS DE: \nCUAJO TEMPERATURA\nTIEMPO DE CUAJADO\nKILOS DE QUESO");
			}else{
				
				var loteNumber = $("#countLoteCounter").val();
				var idPasteurizator = $("#pasteurizatorlist").val();
				var idCuba = $("#cubas").val();
				
				$.get('http://localhost:8080/trazability/service/setlotecounterlikefinished/' + loteNumber + '/' + idPasteurizator + '/' + idCuba , function(data){});
				 
				 setTimeout(function() { sendExtraInformationAboutLote(); },1000);
				 
				 setTimeout(function() { detachmentActualLoteCounterInPasteurizator(); },1000);
				
			}
			
		}); //fin end lote
		
		
		
		//Click en boton cargar LotePendiente
		$("#loadLotePending").click(function(){
			
			var loteSelected = $("#selectLotesNotFinished").val();
			$("#countLoteCounter").val(loteSelected);
			
			var loteCounterLoaded = $("#actualLotePasteurizing").text();
			if(loteCounterLoaded == loteSelected){
				
				//alert("ES EL QUE ESTA EN EL PASTE");
			}
			
			listOperators();
			listCubas();
			
			getDataLoteNotFinished(loteSelected);
			
			loadIngredientsSelectedInThisLote(loteSelected);
			
			startIntervalGetLitersFromPasteurizator();

			listPasteurizator();
			
			listFactoryTank();
			
			setTimeout(function(){ 
				checkIfPasteurizatorIsRunningInLoteLoaded(); 
			} , 1000);
			
			
			setTimeout(function(){ 
				listCubaLoteNotFinished(); 
			} , 1000);
			
			
			$("#modalPendingLotes").modal('hide');
			
			//listFactoryTank();
			
			//$("#cubas").attr("disabled","disabled");
		});
		
		
		 function getDataLoteNotFinished(loteCounter){
			 
			 $("#selectLotesNotFinished").val(loteCounter);
			 $("#tableMilkFilled").html("");
			 
			 $.getJSON('http://localhost:8080/trazability/lote/getdatalotenotfinished/'+loteCounter, function(data) {

				 $("#cubas").val(data[0].cuba.id);
				 $("#operator").val(data[0].operator.id);
				 
				  $.each(data, function(key, val){
					  
					  $("#tableMilkFilled").append("<tr><td>" + val.loteCounter.id + "</td>" +
						      "<td>" + val.factoryTank.id + "</td>" +
						      "<td>" + val.idFilledFactoryTank + "</td>" +
						      "<td>" + val.operator.id  + "</td>"+
						      "<td>"+ val.cuba.id + "</td>" +
						      "<td>" + val.pasteurizator.id  + "</td>"+
						      + "</tr>");
				  });
			});
		 }
		
		
		
		function loadIngredientsSelectedInThisLote(loteSelectedValue){

			var ingredientsList = new Array();
			$.get('http://localhost:8080/trazability/service/getingredientusedinlotecounter/' + loteSelectedValue, function(data) {
				ingredientsList = data.split("-");
			
			
			
			 var i = 0;
			 $.getJSON('http://localhost:8080/trazability/ingredient/getallingredientdetailorderbyiddesc', function(data) {
				 
				 $.each(data, function(key, val){
				 
					  var idField = "<tr id='tr"+i+"' class='noselected'   ><td>" + data[key].note +"</td><td>" +data[key].expirationDate +"-"+ data[key].lote+ "</td><td><label name='ingredientselected' >"+ data[key].id + "</label> </td></tr>";
					  $("#tableIngredient").append(idField);

					 for(var indice = 0 ; indice < ingredientsList.length ; indice++){
						 
						 if(ingredientsList[indice] == data[key].id ){
							 
							 $('#tr'+i).removeClass("noselected").addClass("selected");
							 
						 }//end if
						 
					 } //fin for
					 
						  $('#tr'+i).click(function() {
							  var className = $(this).attr("class");

							  if(className == "noselected")
							  {
								 $(this).removeClass("noselected").addClass("selected");
							  }

							  if(className == "selected")
						      {
								 $(this).removeClass("selected").addClass("noselected");
						      }
							  
							  
							  getIngredientsSelected();
							  sendIngredientSelected();
							  
							  
						  });
							  
							  i++;
				 }); //end each
					 
			 }); //fin getJsonGetALLIngredient

			}); // fin get ingrdientes used
				
		} //end function load IngredientSelectedIn this lote
		
		
		
		 //Encender Pasteurizador
		 $("#pasteurizatoron").click(function(){
			 
			 var factoryTanksSelected = $("div.factoryTankSelected").length;
			 if ( factoryTanksSelected == 0 || factoryTanksSelected >= 2){
				 alert("SELECCIONE UN TANQUE DE FABRICA PARA PODER PASTEURIZAR");
				 return;
			 }
			 
			 
			 
			 $("#pasteurizatorlist").attr("disabled","disabled");
			 
			 var pasteSelected = 0;
			 pasteSelected  = $("#pasteurizatorlist").val();
			 console.log("PASTEURIZADOR SELECCIONADO " + pasteSelected);
			 
			 $.get('http://localhost:8080/trazability/pasteurizator/getpasteurizator/' + pasteSelected , function(data){
				 
				 if(data.run == "RUNNING"){
					 alert("JUSTAMENTE AHORA , ALGUIEN HA EMPEZADO A UTILIZAR ESTE PASTEURIZADOR, ELIJA OTRO DE LOS DISPONIBLES");
					 $("#pasteurizatorlist option").remove();
					 
					 setTimeout(function(){
						 listPasteurizator();
					 },1500);
					 
					 return;
				 }
				 
			 });
			 

			 //Enciendo Pasteurizador
			 var valueLoteCounter = $("#countLoteCounter").val();
			 var cubaSelect = $("#cubas").val();
			 var idFactoryTankSelected = $("div.factoryTankSelected label[name='tankselected']").text();
			 
			 $.get('http://localhost:8080/trazability/pasteurizator/turnon/M=1/'+pasteSelected+'/' + valueLoteCounter + '/' + cubaSelect + '/' + idFactoryTankSelected ,function(data){

				$("#pasteurizatorstate").text(data);
				$("#pasteurizatorstatemodal").text(data);
				
				$("#pasteurizatoron").attr("disabled","disabled");
				$("#pasteurizatoroff").removeAttr("disabled");
				
				
				$("#listfactoryTanks").attr("disabled","disabled");
				
				
			 }); //fin pticion get encender pasteurizador
			 
			 setTimeout(function(){
				 sendDataToTableDescriptionLote();
				 startIntervalGetLitersFromPasteurizator();	 
			 },1500);
			 
			 $('#listfactoryTanks div').off('click');
			 
			 $("#cubas").attr("disabled","disabled");

		 }); //fin pasteurizatorON
		 
		 
		 /* VOY POR AQUI , controlar el tanque elegido en verde o rojo para ponerlo a no bloqued
		   controlar que el tanque que elija y se da a start , ver que no esta usandose en otro lote.*/
		$("#pasteurizatoroff").click(function(){

			$("#pasteurizatorlist").removeAttr("disabled");
			
			 var idFactoryTankSelectedGreen = $("div.factoryTankSelected label[name='tankselected']").text();
			 var loteCounter = $("#countLoteCounter").val();
			 
			 var pasteSelected = 0;
			 pasteSelected = $("#pasteurizatorlist").val();
			
			$.get('http://localhost:8080/trazability/pasteurizator/turnoff/M=0/'+pasteSelected +'/' + idFactoryTankSelectedGreen + '/' + loteCounter, function(data){
				$("#pasteurizatorstate").text(data);
				$("#pasteurizatorstatemodal").text(data);
				$("#pasteurizatoroff").attr("disabled","disabled");
				$("#pasteurizatoron").removeAttr("disabled");
			});
			
			
			/*setTimeout(function(){
				setFactoryTankLikeNotBloqued(idFactoryTankSelectedGreen);
			},1300);*/
			
			$("#pasteurizatorlist option").remove();
			
			setTimeout(function(){
				listPasteurizator();	
			},1500);
			
		
			$('#listfactoryTanks').html("");
			
			setTimeout(function(){
				listFactoryTank();	
			},1300);
			
			$("#cubas").removeAttr("disabled");
		});

		 
		 
	}); //final document ready
	
	
	
	
	
	function setFactoryTankLikeNotBloqued(idFactoryTank){
		
		$.get('http://localhost:8080/trazability/factorytank/setlikenotbloqued/' + idFactoryTank,function(data){
			
		});
	}
	
	
	
	function startIntervalGetLitersFromPasteurizator(pasteurizatorSelected){
		
		setInterval(function() {
			
			var pasteurizatorSelected = $("#pasteurizatorlist").val();
			
			
				var idActualLoteCounterForPasteurizator = 0;
				$.get('http://localhost:8080/trazability/pasteurizator/getpasteurizator/' + pasteurizatorSelected , function(data){
					
					idActualLoteCounterForPasteurizator = data.actualIdLoteCounterForWork;
					
					if($("#countLoteCounter").val() == idActualLoteCounterForPasteurizator){
						getLitersConsumedForPasteurizator(idActualLoteCounterForPasteurizator);	
					}
				});
		
		},3000);
	}
	
	
	
	function getActualLotePasteurizing(idPasteurizator){
		
		$.get('http://localhost:8080/trazability/pasteurizator/getpasteurizator/'+idPasteurizator,function(data){
			
			$("#actualLotePasteurizing").text(data.actualIdLoteCounterForWork);
			
		});
	}
	
	
	function checkIfPasteurizatorIsRunningInLoteLoaded(){
		
		var loteCounterLoaded = $("#countLoteCounter").val();
		
		$.get('http://localhost:8080/trazability/service/getlotecounter/' + loteCounterLoaded,function(data){
			
			var actualPasteurizator = data.actualPasteurizatorAsigned;
			console.log("VALOR DE PAST " + actualPasteurizator);

			setTimeout(function(){
				
				$.get('http://localhost:8080/trazability/pasteurizator/getpasteurizator/' + actualPasteurizator, function(dataPaste){
					
					if(dataPaste.actualIdLoteCounterForWork == loteCounterLoaded ){
						
						if(dataPaste.run == "RUNNING"){
							
							$("#pasteurizatoron").attr("disabled","disabled");
							$("#pasteurizatorlist option").remove();
							$("#pasteurizatorlist").append("<option value='"+dataPaste.id +"'>"+dataPaste.description +"</option>");
							$("#pasteurizatorlist").attr("disabled","disabled");
							$("#pasteurizatorlist").val(actualPasteurizator);
							
							$("#listfactoryTanks div").off("click");
							
							
						}	
						
					}
					else{
						$("#pasteurizatorlist option").remove();
						$("#pasteurizatorlist").removeAttr("disabled");
						listPasteurizator();
						
					}
						
					
					
					
					
				});
				
				
				
			},1000);
			
			
		} );
		
	}
	
	
	 function sendCuajoData()
	 {
		 var cuajoTemperature = $("#cuajotemperature").val();
		 var cuajoTime        = $("#cuajotime").val();
		 
		 $.get('http://localhost:8080/trazability/cuajodata/create/' + cuajoTemperature + '/' +cuajoTime + '/', function(data){
			 //alert("Data de cuajodata " + data);
		 });
	 }
	 
	 function sendExtraInformationAboutLote()
	 {
		 var loteCounter       = $("#countLoteCounter").val();
		 var kgcheese          = $("#kgcheese").val();
		 var cuajedTime        = $("#cuajotime").val();
		 var cuajedTemperature = $("#cuajotemperature").val();
		 
		 $.get('http://localhost:8080/trazability/service/create/'+loteCounter+'/' + kgcheese+'/' + cuajedTime +'/' + cuajedTemperature , function(data){
			 alert("Data de enviar extrainformation " + data);
		 });
	 }
	
	
	
	 function getLitersConsumedForPasteurizator(idActualLoteCounterForPasteurizatorValue){
		 
		 setInterval(function(){
				$.get('http://localhost:8080/trazability/service/getconsumedlitersbyidcounterlote/' + idActualLoteCounterForPasteurizatorValue, function(data){
					$("#litersConsumed").text(data);
				});
	     },2000);
	 }
	
	
	function setActualLoteToPasteurizator(idPasteurizator,valueLoteCounterf,idFactoryTankSelectedValue){
		
		 $.get("http://localhost:8080/trazability/pasteurizator/setactualidlotecounterforwork/"+idPasteurizator+ "/"+valueLoteCounterf +"/" + idFactoryTankSelectedValue  ,function(data){
		 });
	}
	
	
	
	//SEPARED FUNCTIONS
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
	
	 function listCubas(){
		 
		 $.getJSON('http://localhost:8080/trazability/cuba/listallcuba', function(data) {

			  $.each(data, function(key, val)
			  {
  
			      if(val.bloqued == "NOBLOQUED"){
					  var idField = "<option value='"+val.id+"'>" + val.description + "</option>";
			  		  $("#cubas").append(idField);			    	  				  
			      }

			  });
		});
	}
	 
	 
	function listCubaLoteNotFinished(){

		var loteCounter =  $("#countLoteCounter").val();
		$.getJSON('http://localhost:8080/trazability/cuba/getcubabylotecounter/' + loteCounter , function(data) {

			  $.each(data, function(key, val)
			  {
 				
			      if(data.idLoteCounter != ""){
					  $("#cubas option").remove();
					  $("#cubas").append("<option value='"+data.id +"'  >" + data.description + "</option>");
					  
			      }

			  });
		});
		
	}
	 
	 
	 
	function listPasteurizator(){
			
			 $.getJSON('http://localhost:8080/trazability/pasteurizator/getallpasteurizatornotdeleted', function(data) {

				  $.each(data, function(key, val){
					  
					  if(val.run == "NORUNNING"){
						  var idField = "<option value='"+val.id+"'>" + val.description + "</option>";
				  		  $("#pasteurizatorlist").append(idField);
					  }
				  });
			});
	}
	
	
	function setPasteurizatorInLoadLotePending(){
		
		var loteCounterLoaded = $("#countLoteCounter").val();
		
		$.get('http://localhost:8080/trazability/service/getlotecounter/' + loteCounterLoaded, function(data) {
			  	
			setTimeout(function(){
				
				///pasteurizator/getpasteurizator/{id}
				$.get('http://localhost:8080/trazability/pasteurizator/getpasteurizator/' + data.actualPasteurizatorAsigned , function(dataPaste){
					
					var idField = "<option value='"+dataPaste.id+"'>" + dataPaste.description + "</option>";
			  		  $("#pasteurizatorlist").append(idField);
					
				}); //end get pasteurizator
				
			},2000);
		
		}); //end get getLoteCounter
		
		
		
	}
	
	 
	
	function listIngredients(){
		 
		 var i = 0;
		 $.getJSON('http://localhost:8080/trazability/ingredient/getallingredientdetailorderbyiddesc', function(data) {
			 
			 $.each(data, function(key, val){
			 
						  var idField = "<tr id='tr"+i+"' class='noselected'   ><td>" + data[key].note +"</td><td>" +data[key].expirationDate +"-" +data[key].lote +"</td><td><label name='ingredientselected' >"+ data[key].id + "</label> </td></tr>";
						  $("#tableIngredient").append(idField);
						  
						  $('#tr'+i).click(function() {
							  var className = $(this).attr("class");

							  if(className == "noselected")
							  {
								 $(this).removeClass("noselected").addClass("selected");
							  }

							  if(className == "selected")
						      {
								 $(this).removeClass("selected").addClass("noselected");
						      }
							  
							  
							  getIngredientsSelected();
							  sendIngredientSelected();
							  
							  
						    });
						  
						  i++;
			 }); //end each
				 
		 }); //fin getJsonGetALLIngredient
	 }
	 
	 
	  function getIngredientsSelected(){
			
		  ingredientesSelectedTable = new Array();
		  
				 $("#tableIngredient tr.selected ").each(function(index){
					 var element = $("tr.selected td label[name=ingredientselected]").get(index);
					 var texto = $(element).text();
					 ingredientesSelectedTable.push(texto);
				 } );
	  }

	 
	 function sendIngredientSelected(){
	 
	   	 var loteNumber = $("#countLoteCounter").val();
	   	 var totalJson = "";
	   
		 for(var i = 0 ; i < ingredientesSelectedTable.length ; i++)
		 {
			 var row = '{"lote":"'+ $("#countLoteCounter").val() + '","idIngredientDetail":"'+ingredientesSelectedTable[i] + '"}';
			 totalJson = totalJson +row + ',';
		 }
		 
		 var finalJson = "["+ totalJson.substring(0,totalJson.length -1) + "]";

		 $.ajax({
				type: 'POST',
				url: 'sendorderingredientused/' + loteNumber,
				data:
				{ 
					order : finalJson,
				},
				success: function(data) 
				{
					alert("INGREDIENTES ENVIADOS CON EXITO");
						
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

	 
		function sendDataToTableDescriptionLote(){
			
			 var idOperator   			 = $("#operator").val();
			 var nameOperator            = $("#operator option:selected").text();
			 
			 var idCuba   			     = $("#cubas").val();
			 var nameCuba                = $("#cubas option:selected").text();
			 
			 //var litersFilled            = liters;
			 var loteNumber              = $("#countLoteCounter").val();
			 var idFilledFactoryTank     = $(".factoryTankSelected input[name=idfilled]").val();
			 var idFactoryTank           = $(".factoryTankSelected label[name=tankselected]").text();
			 var pasteurizator           = $("#pasteurizatorlist").val();
			 
			 //var cuajoTemperature        = $("#cuajotemperature").val();
			 //var cuajoTime               = $("#cuajotime").val();
			 
			 
			 $("#tableMilkFilled").append("<tr><td>" + loteNumber + "</td>" +
				      //"<td>" + litersFilled + "</td>" +
				      "<td>" + idFactoryTank + "</td>" +
				      "<td>" + idFilledFactoryTank + "</td>" +
				      "<td>" + idOperator   + "</td>"+
				      //"<td>"+cuajoTemperature+"</td>" + 
				      //"<td>"+cuajoTime + "</td>" +
				      "<td>"+ idCuba + "</td>" +
				      "<td>"+ pasteurizator + "</td>"
				      + "</tr>");
			 
			tabla = new Array(); 
			
			tabla.push( new Array(loteNumber , idFactoryTank , idFilledFactoryTank , idOperator , idCuba , pasteurizator));
			//tabla.push( new Array(loteNumber , litersFilled , idFactoryTank , idFilledFactoryTank , idOperator , idCuba , pasteurizator));
			
			sendLoteDataToBD(tabla);
			
			
			 
			 
		}
		
		
		 function sendLoteDataToBD(tablaSended)
		 {
			 $.ajax({
					type: 'POST',
					url: 'sendcommandlote',
					data:
					{ 
						idLoteCounter :tablaSended[0][0],
						//liters : tablaSended[0][1],
						idFactoryTank : tablaSended[0][1],
						idFilledFactoryTank : tablaSended[0][2],
						idOperator : tablaSended[0][3],
						cuba : tablaSended[0][4],
						pasteurizator : tablaSended[0][5],
					},
					success: function(data) 
					{
						alert("INFORME ENVIADO CON EXITO");
							
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


	 
	 
	 
	 function getLastLoteCounter()
	 {
		 $.getJSON('http://localhost:8080/trazability/service/getlastlotecounter', function(data) {
			$("#countLoteCounter").val(data);
		 });
	 }
	
	 function incrementLoteCounter()
	 {
		 $.get('http://localhost:8080/trazability/service/incrementlotecounter');
	 }
	 
	 function createIntervalGetTemperature(){

		 setInterval(function(){
			 $.get('http://localhost:8080/trazability/pasteurizator/gettemperature/1',function(data){
					 $("#pasteurizatortemperature").text(data+" Cº");
			});
	     },2000);

	}
	
	
	 function createIntervalGetStatePasteurizing(){

		 setInterval(function(){
			 $.get('http://localhost:8080/trazability/pasteurizator/ispasteurizing/1',function(data){
					 $("#ispasteurizing").text(data);
			});
			 
	     },2000);
		 
	 }
	 
	
	 function listFactoryTank()
	 {

		 $.getJSON('http://localhost:8080/trazability/factorytank/listallfactorytank', function(data) {

			 var i = 0;
			 
			  $.each(data, function(key, val)
			  {
				  var id = val.id;
				  var state = "";
				  var isActive = val.selectedActive;
				  
				  if(val.clean == "clean")
					  state = "LIMPIO";
				  if(val.clean == "dirty")
					  state = "SUCIO";
				  
				  var tank = "<div id='factorytank" + i + "' class='factoryTankNoSelected'>" + val.description+"<br>LLENADO<input name='idfilled' type='text' class='inputwidth' value='" + val.idFilled + "'/>  </div>";                   
				  
		  		  $("#listfactoryTanks").append(tank);
		  		  
				  /*if( isActive == "ACTIVE" )
				  {
		  			  $("#factorytank" + i).removeClass("factoryTankNoSelected").addClass("factoryTankSelected");
		  			  //actualLitersFactoryTank = parseInt(val.actualCapacity);
		  			  
				  }*/
				  
				  
				  
				  $("#factorytank" + i).append("<br><label id='actualLiters" + val.id + "'>0</label><br>ID TANK<label name='tankselected'>"+val.id+"</label>");
				
				  createIntervalListenerLitersFactoryTank(val.id);

				  //EVENTO CLICK EN FACTORYTANK
			  	  $("#factorytank" + i).click(function() {
			  		  
			  		  var className = $(this).attr("class");

					  if(className == "factoryTankNoSelected")
					  {
						  $(this).removeClass("factoryTankNoSelected").addClass("factoryTankSelected");
						 //setLikeSelectedActiveFactoryTank(id); 
					  }
					  
					  if(className == "factoryTankSelected")
					  {
						  $(this).removeClass("factoryTankSelected").addClass("factoryTankNoSelected");
						 //setLikeNotSelectedActiveFactoryTank(id);
					  }
			  	  });
				  
			  	if( val.bloqued == "BLOQUED" ){
			  		
			  		var loteCounter = $("#countLoteCounter").val();
			  		
			  		$("#factorytank" + i).off('click');
			  		
			  		console.log("ActualLoteCounter " + val.actualLoteCounterForWork + " LoteCounter " + loteCounter);
			  		
			  		if(val.actualLoteCounterForWork == loteCounter){
			  				
			  			$("#factorytank" + i).removeClass("factoryTankNoSelected").addClass("factoryTankSelected");
			  		}
			  		else{
			  			$("#factorytank" + i).removeClass("factoryTankNoSelected").addClass("factoryTankBloqued");
			  		}
			  		
			  	}
		  		  i++;
		  		  
			  });
		 });
		 
		 
		 $.get()
		 
		 
	 }
	 
	 
		function detachmentActualLoteCounterInPasteurizator(){
			var actualPasteurizator = $("#pasteurizatorlist").val();
			$.get('http://localhost:8080/trazability/pasteurizator/detachmentactualidlotecounterforworkinpasteurizator/' + actualPasteurizator , function(data){
				
			});
		}

	 
		function createIntervalListenerLitersFactoryTank(id)
		{
			  setInterval(function()
			  {
			      $.get('http://localhost:8080/trazability/factorytank/getactualcapacity/' + id , function(data){
				      $("#actualLiters" + id).text(data);
				      finishActualLiters = $(".factoryTankSelected label").text();

				      
			      });
			      
			  },3000);
		}
	 
	 
		function loadLotesNotFinished(){
			
			 $.getJSON('http://localhost:8080/trazability/service/getlotecounternotfinishedbylist', function(data) {

				  $.each(data, function(key, val){
					  
					  var idField = "<option value='" + val + "'>" + val + " - Lote: "+val  + "</option>";
			  		  $("#selectLotesNotFinished").append(idField);
				  });
			});

			 //var pasteurizator = $().val();
			 
			 //getActualLotePasteurizing(1);
			 
			 $("#modalPendingLotes").modal('show');
			 
		}
		
		
		 function setLikeSelectedActiveFactoryTank(id)
		 {
			 $.get('http://localhost:8080/trazability/factorytank/setlikeselectedactive/'+id,function(data){
				 
			 });
			 
		 }
		 
		 function setLikeNotSelectedActiveFactoryTank(id)
		 {
			 $.get('http://localhost:8080/trazability/factorytank/setlikenotselectedactive/' + id,function(data){
				 
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
		</div>
    </div><!-- row title -->
    
    <div class="separador"></div>
    
    <div class="contenedor">
    <div class="separador"></div>
    
		    <div class="row">
				<div class="span10 offset1">
				   <h3 class="text-center">P R O D U C C I Ó N  </h3>
				   <button id="exitpage" class="btn btn-warning"><strong>VOLVER A MENÚ PRINCIPAL</strong></button>
				   		
				   		<select id="pasteurizatorlist"></select>
						
						<button id="pasteurizatoron"   class="btn btn-warning">ENCENDER PASTEURIZADOR</button>
				  		<button id="pasteurizatoroff"  class="btn btn-warning">APAGAR PASTEURIZADOR</button>
						
						
						
						<label id="pasteurizatorstate"></label>
						<label id="pasteurizatortemperature">0</label>
						<label id="ispasteurizing"></label>
						<br>
						<label>LOTE Nº <input id="countLoteCounter" type="text" class="inputwidth" /></label>
						<label>Operadores:</label>
						<select id="operator"></select>
						<label>CUBAS</label><select id="cubas"></select>
						
						<button id="endLote" class="btn btn-primary">FINALIZAR LOTE</button>
				</div>
		    </div><!-- row buttons and operators -->
		    
		    <div class="separador"></div>
		    
		    <div class="row">
		    	<div class="span10 offset1">
					<div class="redondoysombra" id="listfactoryTanks"></div><!-- listFactoryTanks -->
		    	</div>
		    </div><!-- row lista de tanques de fabrica -->
		    
		    <div class="separador"></div>
		    
		    <div class="row">
		    	<div class="span10 offset1 redondoysombra">
					<label><strong>TEMPERATURA DE CUAJADO:</strong><input type="text" id="cuajotemperature" class="inputwidth" /></label> 
					<label><strong>TIEMPO DE CUAJADO:</strong><input type="text" id="cuajotime" class="inputwidth"/> </label>
					<label><strong>KG DE QUESO</strong></label><input type="text" id="kgcheese" class="inputwidth"/>
					<label>Litros Consumidos: </label><label id="litersConsumed"></label>
		    	</div>
		    </div><!-- row temperatura cuajado-->
		    
		    <div class="separador"></div>
		    
		    <div class="row">
		    	<div class="span4 offset1">
		    	<h4 class="text-center">SELECCIONE INGREDIENTES</h4>
					<table class="table redondoysombra" id="tableIngredient">
					  	 <tr class="info">
							<td><strong>INGREDIENTE</strong></td><td><strong>CADUCA</strong></td><td><strong>LOTE</strong></td>
						 </tr>
					</table>
		    	</div>
		    	
				<div class="row-fluid">		    	
			    	<div class="span7">
				    	<h4 class="text-center">DATOS DE LOTE ACTUAL</h4>
				    	<table class="table redondoysombra" id="tableHeader">
							<tr class="info">
								<td class="success"><strong>Lote</strong></td>
								
								<td class="success"><strong>Tanque</strong></td>
								<td class="success"><strong>Llenado</strong></td>
								<td class="success"><strong>Operador</strong></td>
								<td class="success"><strong>Cuba</strong></td>
								<td class="success"><strong>Pasteurizador</strong></td>
							</tr>
						</table>
		   				
		   				<table class="table redondoysombra" id="tableMilkFilled">
						</table>
										
			    	</div>
			    </div>
		    </div><!-- div ingredientes y tabla de datos -->
		    
		    

    </div><!-- contenedor redondeado -->
 </div><!-- container principal bootstrap -->



    
    <!-- Ventana Modal -->
    <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
				    <h3 id="myModalLabel">PASTEURIZADOR</h3>
				  </div>
				  <div class="modal-body">
				  <h4>ACCIONES</h4>
				  
				
				  
				  

				  
				      
				  </div>
				  <div class="modal-footer">
				    <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
				  </div>
	</div>
	<!--  fin ventana modal pasteurizador -->
	
	
    <!-- ventana modal que carga los lotes no finalizados -->
   <!-- Ventana Modal -->
    
    <div id="modalPendingLotes" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
				    <h3 id="myModalLabel">LOTES A MITAD DE FABRICACIÓN</h3>
	</div>
				  <div class="modal-body">
				  <select id="selectLotesNotFinished">
				  </select>
				  <br>
				  <label>LOTE ACTUAL EN PASTEURIZACIÓN:</label><label id="actualLotePasteurizing"></label>
				  <button id="loadLotePending">CARGAR LOTE</button>
				  <button id="newLote">NUEVO LOTE</button>
				  
				      
				  </div>
				  
				  <div class="modal-footer">
				    <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
				  </div>
	</div>






</body>

    <script src="<%= request.getContextPath()%>/resources/js/bootstrap.js" ></script>
    <script src="<%= request.getContextPath()%>/resources/js/bootstrap.min.js" ></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/RGraph.common.core.js" ></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/RGraph.common.dynamic.js" ></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/RGraph.bipolar.js" ></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/RGraph.gauge.js" ></script>


</html>