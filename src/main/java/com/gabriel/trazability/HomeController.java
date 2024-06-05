package com.gabriel.trazability;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gabriel.trazability.command.FactoryCommand;

@Controller
public class HomeController {
		
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getFactoryData(@ModelAttribute FactoryCommand factoryCommand,
									   HttpServletRequest request,	HttpServletResponse response) {

		
		/*if(ProductionController.timer != null){
			ProductionController.timer.cancel();
		}*/
		
		Map<String,Object> model = new HashMap<String,Object>();
		
	
		return new ModelAndView("home",model);
	}
}




