package com.gabriel.trazability;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gabriel.trazability.service.CounterWorkindDayForMilkFilledService;

@Controller
public class CounterWorkingDayForMilkFilledServiceController {

	@Autowired
	private CounterWorkindDayForMilkFilledService counterWorkindDayForMilkFilledService; 
	
	@RequestMapping(value = "/service/getlastcounterworkingdayformilkfilled", method = RequestMethod.GET)
	public @ResponseBody String getlastcounter(HttpServletRequest request,	HttpServletResponse response) {
		return counterWorkindDayForMilkFilledService.getLastCounter();
	}

	
	@RequestMapping(value = "/service/incrementcounterworkingdayformilkfilled", method = RequestMethod.GET)
	public @ResponseBody void incrementcounter(HttpServletRequest request,	HttpServletResponse response) {
		counterWorkindDayForMilkFilledService.increment();
	}
	
	
	@RequestMapping(value = "/service/setlikeopen/{number}", method = RequestMethod.GET)
	public void setLikeOpen(@PathVariable Long number ,HttpServletRequest request,	HttpServletResponse response) {
		counterWorkindDayForMilkFilledService.setLikeOpen(number);
	}
	
	@RequestMapping(value = "/service/setlikeclosed/{number}", method = RequestMethod.GET)
	public void setLikeClosed(@PathVariable Long number ,HttpServletRequest request,	HttpServletResponse response) {
		counterWorkindDayForMilkFilledService.setLikeClose(number);
	}
}