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

import com.gabriel.trazability.command.CubaCommand;
import com.gabriel.trazability.exceptions.CubaIsWithoutIdLoteCounterException;
import com.gabriel.trazability.facade.CubaFacade;
import com.gabriel.trazability.model.Cuba;

@Controller
public class CubaController {
	
	@Autowired
	private CubaFacade cubaFacade;
	
	@RequestMapping(value = "/cuba/loadpage", method = RequestMethod.GET)
	public String loadPage(HttpServletRequest request,	HttpServletResponse response) {
		return "cuba/cuba";
	}
	
	@RequestMapping(value = "/cuba/create", method = RequestMethod.POST)
	public ModelAndView createFactoryTank(@ModelAttribute CubaCommand cubaCommand,
									      HttpServletRequest request,	HttpServletResponse response) {

		cubaFacade.create(cubaCommand);
		Map<String,Object> model = new HashMap<String,Object>();
		return new ModelAndView("cuba/cuba",model);
	}
	
	@RequestMapping(value="/cuba/update",method = RequestMethod.POST)
	public ModelAndView updateFactoryTank(@ModelAttribute CubaCommand cubaCommand ,
			   HttpServletRequest request,	HttpServletResponse response)
	{
		cubaFacade.update(cubaCommand.getId(), cubaCommand);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("resultado", "Datos cambiados con éxito");
		return new ModelAndView("cuba/cuba",model);
	}
	
	@RequestMapping(value="/cuba/listallcuba",method = RequestMethod.GET)
	public @ResponseBody List<Cuba> getAllcuba(HttpServletRequest request,	HttpServletResponse response)	{
		return cubaFacade.getAllfactoryCuba();
	}
	
	@RequestMapping(value="/cuba/getactualcapacity/{id}",method = RequestMethod.GET)
	public @ResponseBody Long getActualCapacityCuba(@PathVariable Long id,HttpServletRequest request,	HttpServletResponse response)	{
		//return cubaFacade.get
		return null;
	}
	
	@RequestMapping(value = "/cuba/getcuba/{id}", method = RequestMethod.GET)
	public @ResponseBody Cuba getCuba(@PathVariable Long id,HttpServletRequest request,	HttpServletResponse response) {
		return cubaFacade.get(id);
	}
	
	
	@RequestMapping(value = "/cuba/getcubabylotecounter/{idlotecounter}", method = RequestMethod.GET)
	public @ResponseBody Cuba getCubaByLoteCounter(@PathVariable Long idlotecounter,HttpServletRequest request,	HttpServletResponse response) throws CubaIsWithoutIdLoteCounterException  {
		
		try{
			
			return cubaFacade.getCubaByLoteCounter(idlotecounter);
		}
		catch(CubaIsWithoutIdLoteCounterException e){
			
			throw new CubaIsWithoutIdLoteCounterException();
		}
		
	}
	
	@RequestMapping(value="/cuba/delete/{id}",method= RequestMethod.GET)
	public @ResponseBody void deleteCuba(@PathVariable Long id){
		cubaFacade.delete(id);
	}
	
	@RequestMapping(value="/cuba/setactuallotecounterforwork/{loteCounter}/{idCuba}",method= RequestMethod.GET)
	public void setLikeDirty(@PathVariable Long loteCounter , @PathVariable Long idCuba, HttpServletRequest request,	HttpServletResponse response){
		cubaFacade.setActualIdLoteCounter(loteCounter, idCuba);
	}
}