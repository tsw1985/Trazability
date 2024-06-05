package com.gabriel.trazability;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChlorurePhWaterStreetAndWaterSalmueraController {
	
	@RequestMapping(value="/chlorinephreaders/loadpage", method = RequestMethod.GET)
	public String loadPage(){
		return "chlorinephreaders/chlorinephreaders";
	}
	
	
}
