package com.gabriel.trazability;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gabriel.trazability.command.OrderMilkFilledCommand;
import com.gabriel.trazability.exceptions.FactoryTankNotFoundException;
import com.gabriel.trazability.exceptions.LiterOverflowFactoryTankException;
import com.gabriel.trazability.facade.FactoryTankFacade;
import com.gabriel.trazability.facade.MilkFilledFacade;
import com.gabriel.trazability.model.MilkFilled;

@Controller
public class MilkFilledController {
	
	@Autowired
	private MilkFilledFacade milkFilledFacade;
	
	@Autowired
	private FactoryTankFacade factoryTankFacade;
	
	@RequestMapping(value = "/milkfilled/loadpage", method = RequestMethod.GET)
	public String loadPage(HttpServletRequest request,	HttpServletResponse response) {
		return "milkfilled/milkfilled";
	}
	
	@RequestMapping(value = "/milkfilled/create", method = RequestMethod.POST)
	public void create(@ModelAttribute OrderMilkFilledCommand order, HttpServletRequest request,	HttpServletResponse response) {
		milkFilledFacade.create(order);
	}
	
	@RequestMapping(value="/milkfilled/update", method = RequestMethod.POST)
	public @ResponseBody void update(@ModelAttribute OrderMilkFilledCommand order,HttpServletRequest request,	HttpServletResponse response){
		milkFilledFacade.update(order);
	}
	
	

	
	/*@RequestMapping(value = "/milkfilled/sendlitersfromtrucktanktofactorytank/{numberMilkFilled}/{fromIdTruckTank}/{toIdFactoryTank}/{liters}", 
	method = RequestMethod.GET)
	public @ResponseBody void sendLitersFromTruckTankToFactoryTank(@PathVariable Long fromIdTruckTank, @PathVariable Long toIdFactoryTank,
    @PathVariable Long numberMilkFilled,@PathVariable Long liters,
    HttpServletRequest request,	HttpServletResponse response) {
		
		
		try
		{
			factoryTankFacade.addLitersFromTruckTankToFactoryTank(numberMilkFilled, fromIdTruckTank, toIdFactoryTank,liters);
		}
		catch(LiterOverflowFactoryTankException e)
		{
			System.out.println("Litros sobrepasados controller");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		catch(FactoryTankNotFoundException e)
		{
			System.out.println("Tanque no encontrado controller");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}*/
	
	@RequestMapping(value="/milkfilled/get/{id}", method = RequestMethod.GET)
	public @ResponseBody MilkFilled get(@PathVariable Long id,HttpServletRequest request,	HttpServletResponse response){
		return milkFilledFacade.get(id);
	}
	

	
	@RequestMapping(value="/milkfilled/delete", method = RequestMethod.GET)
	public @ResponseBody void delete(String order,HttpServletRequest request,	HttpServletResponse response){
		milkFilledFacade.saveOrderMilkFilledByJSON(order);
	}

	
	@RequestMapping(value="/milkfilled/sendordermilkfilled", method = RequestMethod.POST)
	public @ResponseBody void orderJson(String order,HttpServletRequest request,	HttpServletResponse response){
		milkFilledFacade.saveOrderMilkFilledByJSON(order);
	}
	
	
	
	@RequestMapping(value="/milkfilled/getdatamilkfilledopen", method = RequestMethod.GET)
	public @ResponseBody List<MilkFilled> getDataMilkFilledOpen(HttpServletRequest request,	HttpServletResponse response){
		//return milkFilledFacade.getAllMilkFilledFromANumberWorkDayMilkFilled(number);
		return null;
	}
	
	
	@RequestMapping(value="/milkfilled/getallmilkfilledfromanumberworkdaymilkfilled/{number}", method = RequestMethod.GET)
	public @ResponseBody List<MilkFilled> getAllMilkFilledFromANumberWorkDayMilkFilled(@PathVariable Long number,HttpServletRequest request,	HttpServletResponse response){
		return milkFilledFacade.getAllMilkFilledFromANumberWorkDayMilkFilled(number);
	}
	
	
	@RequestMapping(value="/milkfilled/gettotallitersmilkfilledfromanumberworkdaymilkfilled/{number}", method = RequestMethod.GET)
	public @ResponseBody Long getTotalLitersMilkFilledFromANumberWorkDayMilkFilled(@PathVariable Long number,HttpServletRequest request,	HttpServletResponse response){
		return milkFilledFacade.getTotalLitersMilkFilledByNumberWorkDayMilkFilled(number);
	}

	//Actual metodo.
	//de momento lo hago con un comando
	@RequestMapping(value="/milkfilled/sendordermilkfilledbycommand",method = RequestMethod.POST)
	public @ResponseBody void orderCertificatesByCommand(@ModelAttribute OrderMilkFilledCommand milkFilledOrder,HttpServletRequest request,	HttpServletResponse response){
	    milkFilledFacade.create(milkFilledOrder);
	}
}