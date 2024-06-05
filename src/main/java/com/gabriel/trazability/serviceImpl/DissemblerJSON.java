package com.gabriel.trazability.serviceImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.gabriel.trazability.command.OrderMilkFilledCommand;
import com.gabriel.trazability.service.DisassemblerJSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DissemblerJSON implements DisassemblerJSON {

	@Override
	public List<Object> getListObjectsFromJSON(String json, Object tipoObjeto) {
		
		Object object = tipoObjeto;
		
	    Gson gson = new Gson();
	    Type tipoListaEmpleados = new TypeToken<List<Object>>(){}.getType();
	    
	    
	    List<OrderMilkFilledCommand> empleados = gson.fromJson(json, tipoListaEmpleados);
	    
	    
	    OrderMilkFilledCommand empleado1 = empleados.get(0);
	    OrderMilkFilledCommand empleado2 = empleados.get(1);
	    System.out.println("INCIDENCIA --->"+empleado1.getIncidence());

		
		

		return null;
	}

}
