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

import com.gabriel.trazability.command.SendMilkToFactoryTankCommand;
import com.gabriel.trazability.exceptions.FactoryTankNotFoundException;
import com.gabriel.trazability.exceptions.LiterOverflowFactoryTankException;
import com.gabriel.trazability.facade.FactoryTankFacade;
import com.gabriel.trazability.facade.SendMilkToFactoryTankFacade;
import com.gabriel.trazability.model.MilkSentToFactoryTank;

@Controller
public class SendMilkToFactoryTankController {
	
	@Autowired
	private FactoryTankFacade factoryTankFacade;
	
	@Autowired
	private SendMilkToFactoryTankFacade sendMilkToFactoryTankFacade;
	
	@RequestMapping(value = "/sendmilktofactorytank/loadpagesendmilktofactorytank", method = RequestMethod.GET)
	public String loadPageSendMilkToFactoryTank(HttpServletRequest request,	HttpServletResponse response) {
		return "sendmilktofactorytank/sendmilktofactorytank";
	}

	@RequestMapping(value = "/sendmilktofactorytank/sendlitersfromtrucktanktofactorytank/{numberMilkFilled}/{fromIdTruckTank}/{toIdFactoryTank}/{liters}", 
	method = RequestMethod.GET)
	public @ResponseBody void sendLitersFromTruckTankToFactoryTank(@PathVariable Long fromIdTruckTank, @PathVariable Long toIdFactoryTank,
    @PathVariable Long numberMilkFilled,@PathVariable Long liters,
    HttpServletRequest request,	HttpServletResponse response) {
		
		try{
			factoryTankFacade.addLitersFromTruckTankToFactoryTank(numberMilkFilled, fromIdTruckTank, toIdFactoryTank,liters);
		}
		catch(LiterOverflowFactoryTankException e){
			System.out.println("Litros sobrepasados controller");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		catch(FactoryTankNotFoundException e){
			System.out.println("Tanque no encontrado controller");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/sendmilktofactorytank/get/{id}",method = RequestMethod.GET)
	public @ResponseBody MilkSentToFactoryTank sendLitersFromTruckTankToFactoryTank(@PathVariable Long id, 
	HttpServletRequest request,	HttpServletResponse response){
		return sendMilkToFactoryTankFacade.get(id);
	}

	@RequestMapping(value = "/sendmilktofactorytank/update",method = RequestMethod.POST)
	public void updateMilkSentToFactoryTank(@ModelAttribute SendMilkToFactoryTankCommand command, 
	HttpServletRequest request,	HttpServletResponse response){
		sendMilkToFactoryTankFacade.update(command);
		
	}
	
	@RequestMapping(value = "/sendmilktofactorytank/delete/{id}",method = RequestMethod.GET)
	public void updateMilkSentToFactoryTank(@PathVariable Long id, 
	HttpServletRequest request,	HttpServletResponse response){
		sendMilkToFactoryTankFacade.delete(id);
		response.setStatus(response.SC_ACCEPTED);
	}
	
	@RequestMapping(value = "/sendmilktofactorytank/getsendmilktofactorytankbyday/{id}",method = RequestMethod.GET)
	public @ResponseBody List<MilkSentToFactoryTank> getDataSendMilkToFactoryTankByWorkingDay(@PathVariable Long id, 
	HttpServletRequest request,	HttpServletResponse response){
		return sendMilkToFactoryTankFacade.listAllDataMilkSentToFactoryTankByWorkingDay(id);
	}	
	
	@RequestMapping(value = "/sendmilktofactorytank/confirm/{id}",method = RequestMethod.GET)
	public void confirmMilkSentToFactoryTank(@PathVariable Long id, 
	HttpServletRequest request,	HttpServletResponse response){
		sendMilkToFactoryTankFacade.setLikeConfirmatedMilkSentToFactoryTankByWorkingDay(id);
	}
	

}