package com.gabriel.trazability;
import java.io.IOException;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gabriel.trazability.command.LoteCommand;
import com.gabriel.trazability.exceptions.NotDataChlorinePhException;
import com.gabriel.trazability.facade.IngredientDetailUsedInLoteFacade;
import com.gabriel.trazability.facade.LoteFacade;
import com.gabriel.trazability.quartzscheduler.RunMeTask;

@Controller
public class ProductionController {
	
	@Autowired
	private LoteFacade loteFacade;
	
	@Autowired
	private IngredientDetailUsedInLoteFacade ingredientDetailUsedInLoteFacade;
	
	//public static Timer timer;
	

	@RequestMapping(value = "/production/loadpageproduction", method = RequestMethod.GET)
	public String getPage(HttpServletRequest request,	HttpServletResponse response) {
		
		
		/*timer = new Timer();
		RunMeTask run = new RunMeTask(timer);
		timer.schedule( run , 1000 , 3000);*/
		return "production/productiondos";
	}
	
	/*
	 * ESTE METODO QUEDA COMENTADO PORQUE ESTA PREPARADO PARA CUANDO ENTRE EN MARCHA
	 * EL ARDUINO. PERO DESDE EL HOMECONTROLLER SE PUEDE PARAR EL TIMER DE AQUI , YA QUE
	 * ES ESTATICO. TAMBIEN PODIA HABER FUNCIONADO CON GETTER Y SETTER.
	 * 
	@RequestMapping(value = "/production/exitpageproduction", method = RequestMethod.GET)
	public String exitPage(HttpServletRequest request,	HttpServletResponse response) {
		
		if( timer == null ){
			return "home";
		}
		else{
			timer.cancel();
			return "home";
		}
	}
	*/
	
	@RequestMapping(value="/production/sendorderlote", method = RequestMethod.POST)
	public @ResponseBody void saveLoteOrderByJson(String order,HttpServletRequest request,	HttpServletResponse response){
		loteFacade.saveOrderLoteByJson(order);
	}
	
	@RequestMapping(value="/production/sendcommandlote", method = RequestMethod.POST)
	public @ResponseBody void saveLoteOrderByCommand(LoteCommand loteCommand,HttpServletRequest request,	HttpServletResponse response){
		
		try{
			loteFacade.saveLoteByCommand(loteCommand);
		}
		catch(NotDataChlorinePhException e){
			response.setStatus(HttpServletResponse.SC_CONFLICT);
		}
	}
	
	@RequestMapping(value="/production/sendorderingredientused/{idLoteCounter}", method = RequestMethod.POST)
	public @ResponseBody void saveIngredientDetailOrderByJson(@PathVariable Long idLoteCounter,  String order,
			HttpServletRequest request,	HttpServletResponse response){
		ingredientDetailUsedInLoteFacade.saveOrderIngredientDetailByJson(order,idLoteCounter);
	}
	
	
	
	
	
	
	
	
}