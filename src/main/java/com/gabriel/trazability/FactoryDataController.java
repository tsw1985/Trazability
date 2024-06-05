package com.gabriel.trazability;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gabriel.trazability.command.FactoryCommand;
import com.gabriel.trazability.exceptions.FactoryNotFoundException;
import com.gabriel.trazability.facade.FactoryFacade;
import com.gabriel.trazability.model.Factory;

@Controller
public class FactoryDataController {
	
	private static final Logger logger = LoggerFactory.getLogger(FactoryDataController.class);
	
	
	@Autowired
	private FactoryFacade factoryFacade;
	
	@RequestMapping(value = "/factory/create", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getFactoryData(@ModelAttribute FactoryCommand factoryCommand,
									                  HttpServletRequest request,	HttpServletResponse response) {

			return new ModelAndView("createFactory/createfactory",null);
	}
	
	
	@RequestMapping(value = "/factory/getfactorydata", method = RequestMethod.GET)
	public @ResponseBody Factory getFactory() {

		try
		{
			Factory factory = factoryFacade.getFactoryById(1L);
			return factory;
		}
		catch(FactoryNotFoundException e)
		{
			return null;
		}
	}

	
	@RequestMapping(value = "/factory/create", method = RequestMethod.POST)
	public ModelAndView putFactoryData(@ModelAttribute FactoryCommand factoryCommand, 
			                           HttpServletRequest request,	HttpServletResponse response) {

		Map<String,Object> model = new HashMap<String,Object>();
		factoryFacade.create(factoryCommand);
		model.put("resultado", "Datos Guardados");
		return new ModelAndView("createFactory/createfactory",model);
	}

	
}
