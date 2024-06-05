package com.gabriel.trazability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gabriel.trazability.command.FactoryTankCommand;
import com.gabriel.trazability.facade.FactoryTankFacade;
import com.gabriel.trazability.model.FactoryTank;

@Controller
public class FactoryTankController {
	
	@Autowired
	private FactoryTankFacade factoryTankFacade;
	
	@RequestMapping(value = "/factorytank/loadpage", method = RequestMethod.GET)
	public String loadPage(HttpServletRequest request,	HttpServletResponse response) {
		return "factorytank/factorytank";
	}
	
	@RequestMapping(value = "/factorytank/loadpagelevelfactorytank", method = RequestMethod.GET)
	public String loadPageShowLevelFactoryTank(HttpServletRequest request,	HttpServletResponse response) {
		return "levelfactorytank/levelfactorytank";
	}

	@RequestMapping(value = "/factorytank/create", method = RequestMethod.POST)
	public ModelAndView createFactoryTank(@ModelAttribute FactoryTankCommand factoryTankCommand,
									      HttpServletRequest request,	HttpServletResponse response) {

		factoryTankFacade.create(factoryTankCommand);
		Map<String,Object> model = new HashMap<String,Object>();
		return new ModelAndView("factorytank/factorytank",model);
	}
	
	@RequestMapping(value="/factorytank/update",method = RequestMethod.POST)
	public ModelAndView updateFactoryTank(@ModelAttribute FactoryTankCommand factoryTankCommand ,
			   HttpServletRequest request,	HttpServletResponse response)
	{
		factoryTankFacade.update(factoryTankCommand.getId(), factoryTankCommand);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("resultado", "Datos cambiados con éxito");
		return new ModelAndView("factorytank/factorytank",model);
	}
	
	@RequestMapping(value="/factorytank/listallfactorytank",method = RequestMethod.GET)
	public @ResponseBody List<FactoryTank> getAllfactoryTank()	{
		return factoryTankFacade.getAllfactoryTank();
	}
	
	@RequestMapping(value="/factorytank/getactualcapacity/{id}",method = RequestMethod.GET)
	public @ResponseBody Long getActualCapacityFactoryTank(@PathVariable Long id)	{
		return factoryTankFacade.getActualCapacity(id);
	}
	
	@RequestMapping(value = "/factorytank/getfactorytank/{id}", method = RequestMethod.GET)
	public @ResponseBody FactoryTank getfactoryTank(@PathVariable Long id) {
		return factoryTankFacade.get(id);
	}
	
	@RequestMapping(value="/factorytank/delete/{id}",method= RequestMethod.GET)
	public @ResponseBody void deleteFactoryTank(@PathVariable Long id){
		factoryTankFacade.delete(id);
	}
	
	@RequestMapping(value="/factorytank/setlikedirty/{id}",method= RequestMethod.GET)
	public @ResponseBody void setLikeDirty(@PathVariable Long id){
		factoryTankFacade.setLikeDirty(id);
	}

	@RequestMapping(value="/factorytank/setlikeclean/{id}",method= RequestMethod.GET)
	public @ResponseBody void setLikeClean(@PathVariable Long id){
		factoryTankFacade.setLikeClean(id);
	}

	@RequestMapping(value="/factorytank/getstatusclean/{id}",method= RequestMethod.GET)
	public @ResponseBody String getCleanStatus(@PathVariable Long id){
		return factoryTankFacade.getCleanStatus(id);
	}

	@RequestMapping(value="/factorytank/incrementnumberfilled/{id}",method= RequestMethod.GET)
	public @ResponseBody void incrementNumberFilled(@PathVariable Long id){
		factoryTankFacade.incrementNumberFilled(id);
	}
	
	@RequestMapping(value="/factorytank/decrementnumberfilled/{id}",method= RequestMethod.GET)
	public @ResponseBody void decrementNumberFilled(@PathVariable Long id){
		factoryTankFacade.decrementNumberFilled(id);
	}
	
	@RequestMapping(value="/factorytank/getactualnumberfilled/{id}",method= RequestMethod.GET)
	public @ResponseBody Long getActualNumberFilled(@PathVariable Long id){
		return factoryTankFacade.getActualNumberFilled(id);
	}
	
	
	@RequestMapping(value="/factorytank/setlikeselectedactive/{id}",method= RequestMethod.GET)
	public @ResponseBody void setLikeSelectedActive(@PathVariable Long id){
		factoryTankFacade.setLikeSelectedActive(id);
	}
	
	@RequestMapping(value="/factorytank/setlikenotselectedactive/{id}",method= RequestMethod.GET)
	public @ResponseBody void setLikeNotSelectedActive(@PathVariable Long id){
		factoryTankFacade.setLikeNotSelectedActive(id);
	}
	
	@RequestMapping(value="/factorytank/setlikebloqued/{id}",method= RequestMethod.GET)
	public @ResponseBody void setLikeBloqued(@PathVariable Long id){
		factoryTankFacade.setLikeBloqued(id);
	}
	
	@RequestMapping(value="/factorytank/setlikenotbloqued/{id}",method= RequestMethod.GET)
	public @ResponseBody void setLikeNotBloqued(@PathVariable Long id){
		factoryTankFacade.setLikeNotBloqued(id);
	}
	
	@RequestMapping(value="/factorytank/getstateactiveselectedfactorytank/{id}",method= RequestMethod.GET)
	public @ResponseBody String getStateActiveSelectedFactoryTank(@PathVariable Long id){
		return factoryTankFacade.getStateActiveSelectedFactoryTank(id);
	}
	
	@RequestMapping(value="/factorytank/addliters/{id}/{liters}",method= RequestMethod.GET)
	public void addLitersFactoryTank(@PathVariable Long id , @PathVariable Long liters){
		factoryTankFacade.addLiters(id, liters);
	}
	
	@RequestMapping(value="/factorytank/substractliters/{id}/{liters}",method= RequestMethod.GET)
	public void substractLitersFactoryTank(@PathVariable Long id,@PathVariable Long liters){
		factoryTankFacade.subtractLiters(id, liters);
	}
}