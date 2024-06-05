package com.gabriel.trazability;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InformationController {
	
	@RequestMapping(value="/information/sendorderlote/{lote}", method = RequestMethod.POST)
	public @ResponseBody void saveLoteOrderByJson(@PathVariable Long lote, String order){

	}

}
