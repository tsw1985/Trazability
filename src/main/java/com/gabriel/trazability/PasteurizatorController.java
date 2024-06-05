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

import com.gabriel.trazability.command.PasteurizatorCommand;
import com.gabriel.trazability.facade.CubaFacade;
import com.gabriel.trazability.facade.FactoryTankFacade;
import com.gabriel.trazability.facade.PasteurizatorFacade;
import com.gabriel.trazability.model.Pasteurizator;
import com.gabriel.trazability.service.LoteCounterService;

@Controller
public class PasteurizatorController {

	@Autowired
	private PasteurizatorFacade pasteurizatorFacade;

	@Autowired
	private FactoryTankFacade factoryTankFacade;
	
	@Autowired
	private LoteCounterService loteCounterService;
	
	@Autowired
	private CubaFacade cubaFacade;

	@RequestMapping(value = "/pasteurizator/loadpage", method = RequestMethod.GET)
	public String loadPagePasteurizador(HttpServletRequest request,
			HttpServletResponse response) {
		return "pasteurizator/pasteurizator";
	}

	@RequestMapping(value = "/pasteurizator/setactualidlotecounterforwork/{idpasteurizator}/{valueLoteCounter}/{idFactoryTank}", method = RequestMethod.GET)
	public void setActualIdLoteCounterForWorkAndFactoryTank(
			@PathVariable Long idpasteurizator,
			@PathVariable Long valueLoteCounter,
			@PathVariable Long idFactoryTank, HttpServletRequest request,
			HttpServletResponse response) {

		pasteurizatorFacade.setActualIdLoteCounterForWorkAndFactoryTank(idpasteurizator, valueLoteCounter, idFactoryTank);
		factoryTankFacade.setLikeBloqued(idFactoryTank);
		loteCounterService.setPasteurizatorToLoteCounter(idpasteurizator,valueLoteCounter);
	}

	@RequestMapping(value = "/pasteurizator/detachmentactualidlotecounterforworkinpasteurizator/{idpasteurizator}", method = RequestMethod.GET)
	public void detachmentActualIdLoteCounterForWork(
			@PathVariable Long idpasteurizator, Long valueLoteCounter,
			HttpServletRequest request, HttpServletResponse response) {

		pasteurizatorFacade.detachmentActualIdLoteCounterForWork(idpasteurizator);
	}

	@RequestMapping(value = "/pasteurizator/create", method = RequestMethod.POST)
	public void createPasteurizator(PasteurizatorCommand pasteurizatorCommand,
			HttpServletRequest request, HttpServletResponse response) {
		pasteurizatorFacade.create(pasteurizatorCommand);
	}

	@RequestMapping(value = "/pasteurizator/update", method = RequestMethod.POST)
	public void updatePasteurizator(PasteurizatorCommand pasteurizatorCommand,
			HttpServletRequest request, HttpServletResponse response) {
		pasteurizatorFacade.update(pasteurizatorCommand);
	}

	@RequestMapping(value = "/pasteurizator/turnon/{action}/{idpasteurizator}/{idLoteCounter}/{idCuba}/{idFactoryTank}", method = RequestMethod.GET)
	public void turnON(@PathVariable String action,
			@PathVariable Long idpasteurizator,
			@PathVariable Long idLoteCounter,
			@PathVariable Long idCuba,
			@PathVariable Long idFactoryTank ,
			HttpServletRequest request, HttpServletResponse response) {

		pasteurizatorFacade.turnON(action, idpasteurizator, idLoteCounter);
		pasteurizatorFacade.setActualIdLoteCounterForWorkAndFactoryTank(idpasteurizator, idLoteCounter, idFactoryTank);
		
		cubaFacade.setActualIdLoteCounter(idLoteCounter, idCuba);
		cubaFacade.setLikeBloqued(idCuba);
		
		factoryTankFacade.setActualIdLoteCounterForWork(idLoteCounter, idFactoryTank);
		factoryTankFacade.setLikeBloqued(idFactoryTank);

		loteCounterService.setPasteurizatorToLoteCounter(idpasteurizator, idLoteCounter);
	}
	
	@RequestMapping(value = "/pasteurizator/turnoff/{action}/{id}/{factoryTank}/{idLoteCounter}", method = RequestMethod.GET)
	public void turnOFF(@PathVariable String action, @PathVariable Long id,
			@PathVariable Long factoryTank, @PathVariable Long idLoteCounter,  HttpServletRequest request,
			HttpServletResponse response) {

		pasteurizatorFacade.turnOFF(action, id);
		factoryTankFacade.setLikeNotBloqued(factoryTank);
		loteCounterService.detachmentPasteurizatorInLoteCounter(idLoteCounter);
		pasteurizatorFacade.detachmentActualIdLoteCounterForWork(id);
	}


	@RequestMapping(value = "/pasteurizator/getpasteurizator/{id}", method = RequestMethod.GET)
	public @ResponseBody Pasteurizator getPasteurizator(@PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response) {
		return pasteurizatorFacade.get(id);
	}

	@RequestMapping(value = "/pasteurizator/getactualidlotecounterforwork/{id}", method = RequestMethod.GET)
	public @ResponseBody Long getCurrentLoteForWork(@PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response) {
		return pasteurizatorFacade.get(id).getActualIdLoteCounterForWork();
	}

	@RequestMapping(value = "/pasteurizator/delete/{id}", method = RequestMethod.GET)
	public void deletePasteurizator(@PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response) {
		pasteurizatorFacade.delete(id);
	}

	@RequestMapping(value = "/pasteurizator/getallpasteurizatornotdeleted", method = RequestMethod.GET)
	public @ResponseBody List<Pasteurizator> getAllPasteurizatorNotDeleted(
			HttpServletRequest request, HttpServletResponse response) {
		return pasteurizatorFacade.getAllPasteurizatorNoDeleted();
	}


	@RequestMapping(value = "/pasteurizator/gettemperature/{id}", method = RequestMethod.GET)
	public @ResponseBody String getTemperature(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) {
		return pasteurizatorFacade.getActualTemperature(id);
	}

	@RequestMapping(value = "/pasteurizator/runningstate/{id}", method = RequestMethod.GET)
	public @ResponseBody String getRunningState(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) {
		return pasteurizatorFacade.getRunningState(id);
	}

	@RequestMapping(value = "/pasteurizator/ispasteurizing/{id}", method = RequestMethod.GET)
	public @ResponseBody String isPasteurizing(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) {
		return pasteurizatorFacade.getPasteurizingState(id);
	}
}