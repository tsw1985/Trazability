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

import com.gabriel.trazability.command.OperatorCommand;
import com.gabriel.trazability.facade.OperatorFacade;
import com.gabriel.trazability.model.Operator;

@Controller
public class OperatorController {
	
	@Autowired
	private OperatorFacade operatorFacade;
	
	
	@RequestMapping(value="/operator/create",method= RequestMethod.GET)
	public ModelAndView getOperator(@ModelAttribute OperatorCommand operatorCommand , 
			HttpServletRequest request,	HttpServletResponse response)
	{
		Map<String,Object> model = new HashMap<String,Object>();
		
		return new ModelAndView("personal/personal",model);	
	}
	
	
	 @RequestMapping(value = "/operator/create", method = RequestMethod.POST)
	public ModelAndView createOperator(@ModelAttribute OperatorCommand operatorCommand,
									   HttpServletRequest request,	HttpServletResponse response) {

		operatorFacade.create(operatorCommand);
		Map<String,Object> model = new HashMap<String,Object>();
		
		return new ModelAndView("personal/personal",model);
	}
	 
	
	
	
	@RequestMapping(value = "/operator/getoperator/{id}", method = RequestMethod.GET)
	public @ResponseBody Operator getoperator(@PathVariable Long id) {
		
		return operatorFacade.getOperator(id);
	}
	
	@RequestMapping(value = "/operator/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody void delete(@PathVariable Long id) {
		
		operatorFacade.delete(id);
	}
	
	@RequestMapping(value = "/operator/getalloperator", method = RequestMethod.GET)
	public @ResponseBody List<Operator> getAllOperator() {
		return operatorFacade.getAllOperator();
	}
	
	@RequestMapping(value = "/operator/update", method = RequestMethod.POST)
	public ModelAndView updateOperator(@ModelAttribute OperatorCommand operatorCommand,
									   HttpServletRequest request,	HttpServletResponse response) {

		operatorFacade.update(operatorCommand);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("resultado", "Datos cambiados con éxito");
		
		return new ModelAndView("personal/personal",model);
	}
}
