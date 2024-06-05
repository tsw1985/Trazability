package com.gabriel.trazability;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gabriel.trazability.facade.ChlorinePhStreetWaterFacade;
import com.gabriel.trazability.model.ChlorinePhStreetWater;

@Controller
public class ChlorinePhStreetWaterController {
	
	@Autowired
	private ChlorinePhStreetWaterFacade chlorinePhStreetWaterFacade;

	@RequestMapping(value = "/chlorinestreetwater/setchlorineph/{valuechlorine}/{valueph}/", method = RequestMethod.GET)
	public void setStreetWaterChlorinePh(HttpServletRequest request,	HttpServletResponse response,
			    @PathVariable Double valuechlorine, @PathVariable Double valueph){
		chlorinePhStreetWaterFacade.create(valuechlorine, valueph);
	}
	
	@RequestMapping(value = "/chlorine/getlastchlorineph", method = RequestMethod.GET)
	public @ResponseBody ChlorinePhStreetWater getLastStreetWaterChlorinePh(){
		return chlorinePhStreetWaterFacade.getLastChlorinePh();
	}
}