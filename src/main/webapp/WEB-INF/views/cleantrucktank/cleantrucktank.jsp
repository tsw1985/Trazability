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
	 
	 <script src="<%= request.getContextPath()%>/resources/js/functions.js" ></script>
	 
	 <script type="text/javascript">
	 
	 
	 $(document).ready(function(){
		 
		 listFactoryTank();
			
			/*$("#cleantrucktank").click( function()
			{				
				var id = $("#idtrucktank").val();
				
				$.get("http://localhost:8080/trazability/truck/setlikeclean/"+id);
			});
			
			$("#dirtytrucktank").click( function()
			{
				var id = $("#idtrucktank").val();
				$.get("http://localhost:8080/trazability/truck/setlikedirty/"+id);
				
			});
			
			
			
			$("#dirtyfactorytank").click( function()
			{
				var id = $("#idtrucktank").val();
				$.get("http://localhost:8080/trazability/truck/setlikedirty/"+id);
				
			});
			
			$("#incrementidfilledtrucktank").click( function()
			{				
				var id = $("#idtrucktank").val();
					
				$.get("http://localhost:8080/trazability/truck/incrementnumberfilled/"+id);
				
			});*/
			
			
			$("#cleanFactoryTank").click( function(){
				
					var id = $("div.factoryTankSelected input[name=idFactoryTank]").val();
					$.get("http://localhost:8080/trazability/factorytank/incrementnumberfilled/"+id);
					
			});

			
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
						  
						  var tank = "<div id='factorytank" + i + "' class='factoryTankNoSelected'>" + val.description+"<br>LLENADO<input name='idfilled' type='text' class='inputwidth' value='" + val.idFilled + "'/><br><input name='idFactoryTank' type='text' class='inputwidth' value='" + val.id + "'/><br>ID DE LLENADO "+ val.idFilled+"</div>";                   
						  
				  		  $("#listfactoryTanks").append(tank);
				  		  
						  if( isActive == "ACTIVE" )
						  {
				  			  $("#factorytank" + i).removeClass("factoryTankNoSelected").addClass("factoryTankSelected");

				  			  
						  }
						  

						  //EVENTO CLICK EN FACTORYTANK
					  	  $("#factorytank" + i).click(function() {
					  		  
						   $.get('http://localhost:8080/trazability/factorytank/getactualcapacity/' + id , function(data){
							   actualLitersFactoryTank = parseInt(data);
						   });
					  		  
					  		var className = $(this).attr("class");

							  if(className == "factoryTankNoSelected")
							  {
								 $(this).removeClass("factoryTankNoSelected").addClass("factoryTankSelected");
							  }
							  
							  if(className == "factoryTankSelected")
							  {
								 $(this).removeClass("factoryTankSelected").addClass("factoryTankNoSelected");
							  }
					  	  });

				  		  
				  		  i++;
				  		  
					  });
				 });
			 }

			
			

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
			
			<div class="row">
		    	<div class="span10 offset1">
					<div class="redondoysombra" id="listfactoryTanks"></div><!-- listFactoryTanks -->
		    	</div>
		    </div><!-- row lista de tanques de fabrica -->
			
			
			
			
			<button id="cleanFactoryTank">MARCAR COMO LIMPIO EL TANQUE SELECCIONADO</button>

			
			
			
			
			<div class="separador"></div>
	 
 			<div class="separador"></div>
 			</div>
		</div>
</div>
</div>
</body>
</html>