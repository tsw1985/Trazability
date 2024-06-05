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

import com.gabriel.trazability.command.TruckTankCommand;
import com.gabriel.trazability.facade.TruckTankFacade;
import com.gabriel.trazability.model.TruckTank;

@Controller
public class TruckTankController {
	
	@Autowired
	private TruckTankFacade truckTankFacade;
	
	
	@RequestMapping(value = "/truck/clean", method = RequestMethod.GET)
	public ModelAndView clean(HttpServletRequest request,	HttpServletResponse response) {

		Map<String,Object> model = new HashMap<String,Object>();
		
		return new ModelAndView("cleantrucktank/cleantrucktank",model);
	}

	@RequestMapping(value = "/truck/create", method = RequestMethod.GET)
	public ModelAndView getTruckTank(@ModelAttribute TruckTankCommand truckTankCommand,
									   HttpServletRequest request,	HttpServletResponse response) {

		
		Map<String,Object> model = new HashMap<String,Object>();
		
		return new ModelAndView("truck/truck",model);
	}
	
	@RequestMapping(value = "/truck/create", method = RequestMethod.POST)
	public ModelAndView createTruckTank(@ModelAttribute TruckTankCommand truckTankCommand,
									   HttpServletRequest request,	HttpServletResponse response) {

		truckTankFacade.create(truckTankCommand);
		Map<String,Object> model = new HashMap<String,Object>();

		return new ModelAndView("truck/truck",model);
	}

	@RequestMapping(value="/truck/update",method = RequestMethod.POST)
	public ModelAndView updateTruckTank(@ModelAttribute TruckTankCommand truckTankCommand ,
			   HttpServletRequest request,	HttpServletResponse response)
	{
		truckTankFacade.update(truckTankCommand.getId(), truckTankCommand);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("resultado", "Datos cambiados con éxito");

		return new ModelAndView("truck/truck",model);
	}
	
	@RequestMapping(value="/truck/listalltrucktank",method = RequestMethod.GET)
	public @ResponseBody List<TruckTank> getAllTruckTank()
	{
		return truckTankFacade.getAllTruckTank();
	}
	
	@RequestMapping(value = "/truck/gettrucktank/{id}", method = RequestMethod.GET)
	public @ResponseBody TruckTank getTruckTank(@PathVariable Long id) {
		
		return truckTankFacade.get(id);
	}
	
	@RequestMapping(value="/truck/delete/{id}",method= RequestMethod.GET)
	public @ResponseBody void deleteTruckTank(@PathVariable Long id)
	{
		truckTankFacade.delete(id);
	}
	
	@RequestMapping(value="/truck/setlikedirty/{id}",method= RequestMethod.GET)
	public @ResponseBody void setLikeDirty(@PathVariable Long id)
	{
		truckTankFacade.setLikeDirty(id);
	}

	@RequestMapping(value="/truck/setlikeclean/{id}",method= RequestMethod.GET)
	public @ResponseBody void setLikeClean(@PathVariable Long id)
	{
		truckTankFacade.setLikeClean(id);
	}

	@RequestMapping(value="/truck/getstatusclean/{id}",method= RequestMethod.GET)
	public @ResponseBody String getCleanStatus(@PathVariable Long id)
	{
		return truckTankFacade.getCleanStatus(id);
	}

	@RequestMapping(value="/truck/incrementnumberfilled/{id}",method= RequestMethod.GET)
	public @ResponseBody void incrementNumberFilled(@PathVariable Long id)
	{
		truckTankFacade.incrementNumberFilled(id);
	}
	
	@RequestMapping(value="/truck/decrementnumberfilled/{id}",method= RequestMethod.GET)
	public @ResponseBody void decrementNumberFilled(@PathVariable Long id)
	{
		truckTankFacade.decrementNumberFilled(id);
	}
	
	@RequestMapping(value="/truck/getactualnumberfilled/{id}",method= RequestMethod.GET)
	public @ResponseBody Long getActualNumberFilled(@PathVariable Long id)
	{
		return truckTankFacade.getActualNumberFilled(id);
	}
}
