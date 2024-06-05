package com.gabriel.trazability;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gabriel.trazability.facade.ChlorinePhSalmueraFacade;
import com.gabriel.trazability.model.ChlorinePhSalmuera;

@Controller
public class ChlorineSalmueraController {
	
	@Autowired
	private ChlorinePhSalmueraFacade chlorinePhSalmueraFacade; 
	
	@RequestMapping(value = "/chlorinesalmuera/setchlorine/{valueChlorure}/{valuePh}/", method = RequestMethod.GET)
	public void createSalmueraChlorinePh(HttpServletRequest request, HttpServletResponse response,
			                             @PathVariable Double valueChlorure , @PathVariable Double valuePh){
		
		chlorinePhSalmueraFacade.create(valueChlorure, valuePh);
	}
	
	@RequestMapping(value = "/chlorinesalmuera/getlastchlorineph", method = RequestMethod.GET)
	public @ResponseBody ChlorinePhSalmuera getLastSalmueraChlorinePh(){
			return chlorinePhSalmueraFacade.getLastChlorinePhSalmuera();
	}
}
