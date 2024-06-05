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

import com.gabriel.trazability.command.ProviderCommand;
import com.gabriel.trazability.facade.ProviderFacade;
import com.gabriel.trazability.model.Provider;

@Controller
public class ProviderController {
	
	@Autowired 
	private ProviderFacade providerFacade;
	
	@RequestMapping(value = "/provider/create", method = RequestMethod.GET)
	public ModelAndView getProvider(@ModelAttribute ProviderCommand providerCreateCommand,
									   HttpServletRequest request,	HttpServletResponse response) {

		Map<String,Object> model = new HashMap<String,Object>();
		
		return new ModelAndView("provider/createprovider",model);
	}

	@RequestMapping(value = "/provider/create", method = RequestMethod.POST)
	public ModelAndView createProvider(@ModelAttribute ProviderCommand providerCreateCommand,
									   HttpServletRequest request,	HttpServletResponse response) {

		providerFacade.create(providerCreateCommand);
		Map<String,Object> model = new HashMap<String,Object>();
		
		return new ModelAndView("provider/createprovider",model);
	}
	
	
	@RequestMapping(value = "/provider/update", method = RequestMethod.GET)
	public ModelAndView updateProvider(@ModelAttribute ProviderCommand providerCommand,
									   HttpServletRequest request,	HttpServletResponse response) {

		providerFacade.update(providerCommand);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("resultado", "Datos cambiados con éxito");
		
		return new ModelAndView("provider/createprovider",model);
	}
	
	
	@RequestMapping(value = "/provider/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody void deleteProvider(@PathVariable Long id) {
		
		providerFacade.delete(id);
	}
	

	@RequestMapping(value = "/provider/getallprovider", method = RequestMethod.GET)
	public @ResponseBody List<Provider> getAllProvider() {
		return providerFacade.getAllProvider();
	}
	
	
	@RequestMapping(value = "/provider/getprovider/{id}", method = RequestMethod.GET)
	public @ResponseBody Provider getProvider(@PathVariable Long id) {
		System.out.println("valor de id "+ id);
		
		return providerFacade.getProvider(id);
	}
}