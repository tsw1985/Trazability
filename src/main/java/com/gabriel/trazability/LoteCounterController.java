package com.gabriel.trazability;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gabriel.trazability.facade.CubaFacade;
import com.gabriel.trazability.facade.PasteurizatorFacade;
import com.gabriel.trazability.model.LoteCounter;
import com.gabriel.trazability.service.LoteCounterService;

@Controller
public class LoteCounterController {
	
	@Autowired
	private LoteCounterService loteCounterService;
	
	@Autowired
	private PasteurizatorFacade pasteurizatorFacade;
	
	@Autowired
	private CubaFacade cubaFacade;
	
	@RequestMapping(value = "/service/getlastlotecounter", method = RequestMethod.GET)
	public @ResponseBody String getlastcounter(HttpServletRequest request,	HttpServletResponse response) {
		return loteCounterService.getLastLoteCounter();
	}
	
	@RequestMapping(value = "/service/getlotecounter/{id}", method = RequestMethod.GET)
	public @ResponseBody LoteCounter getLoteCounter(@PathVariable Long id ,HttpServletRequest request,	HttpServletResponse response) {
		return loteCounterService.get(id);
	}

	@RequestMapping(value = "/service/incrementlotecounter", method = RequestMethod.GET)
	public @ResponseBody void incrementcounter(HttpServletRequest request,	HttpServletResponse response) {
		loteCounterService.create();
	}
	
	@RequestMapping(value = "/service/getlotecounternotfinished", method = RequestMethod.GET)
	public @ResponseBody List<LoteCounter> getLoteCounterNotFinished(HttpServletRequest request,	HttpServletResponse response) {
		return loteCounterService.getLoteCounterNotFinished();
	}
	
	@RequestMapping(value = "/service/getlotecounternotfinishedbystring", method = RequestMethod.GET)
	public @ResponseBody String getLoteCounterNotFinishedbyString(HttpServletRequest request,	HttpServletResponse response) {
		return loteCounterService.getLoteCounterNotFinishedByString();
	}

	@RequestMapping(value = "/service/getlotecounternotfinishedbylist", method = RequestMethod.GET)
	public @ResponseBody List getLoteCounterNotFinishedbyList(HttpServletRequest request,	HttpServletResponse response) {
		return loteCounterService.getLoteCounterNotFinishedByList();
	}
	
	@RequestMapping(value = "/service/getconsumedlitersbyidcounterlote/{idLoteCounter}", method = RequestMethod.GET)
	public @ResponseBody String getConsumedLitersByIdCounterLote(@PathVariable Long idLoteCounter , 
		HttpServletRequest request,	HttpServletResponse response) {
		return loteCounterService.get(idLoteCounter).getLitersConsumed().toString();
	}
	
	@RequestMapping(value = "/service/setlotecounterlikefinished/{idLoteCounter}/{idPasteurizator}/{idCuba}", method = RequestMethod.GET)
	public void setLoteCounterLikeFinished(@PathVariable Long idLoteCounter , @PathVariable Long idPasteurizator, @PathVariable Long idCuba,
		HttpServletRequest request,	HttpServletResponse response) {
		
		loteCounterService.setLoteCounterLikeFinished(idLoteCounter);
		pasteurizatorFacade.detachmentActualIdLoteCounterForWork(idPasteurizator);
		cubaFacade.detachmentActualIdLoteCounterForWork(idCuba);
		cubaFacade.setLikeNotBloqued(idCuba);
		
	}
	
	@RequestMapping(value = "/service/getingredientusedinlotecounter/{idLoteCounter}", method = RequestMethod.GET)
	public @ResponseBody String getIngredientDetailUsedInLoteCounter(@PathVariable Long idLoteCounter , 
		HttpServletRequest request,	HttpServletResponse response) {
		
		return loteCounterService.getIngredientUsedInLoteCounter(idLoteCounter);
	}
	
	
	@RequestMapping(value="/service/create/{lotecounter}/{kgcheese}/{cuajedtime}/{cuajedTemperature}", method = RequestMethod.GET)
	public void create(HttpServletRequest request,	HttpServletResponse response, 
			    @PathVariable Long lotecounter , 
			    @PathVariable Long kgcheese , 
			    @PathVariable String cuajedtime, 
			    @PathVariable String cuajedTemperature){
		
		loteCounterService.addExtraInformationLote(lotecounter, kgcheese, cuajedtime, cuajedtime);
	}
}