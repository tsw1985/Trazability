package com.gabriel.trazability;

import java.math.BigInteger;
import java.sql.Time;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gabriel.trazability.facade.DossierFacade;
import com.gabriel.trazability.model.ChlorinePhSalmuera;
import com.gabriel.trazability.model.FactoryTank;
import com.gabriel.trazability.model.IngredientDetail;
import com.gabriel.trazability.model.Operator;
import com.gabriel.trazability.model.Pasteurization;
import com.gabriel.trazability.model.sql.query.CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription;
import com.gabriel.trazability.model.sql.query.PasteurizatorData;

@Controller
public class DossierController {
	
	@Autowired
	private DossierFacade dossierFacade;
	
	@RequestMapping(value = "/dossier/loadpage", method = RequestMethod.GET)
	public String loadPage(HttpServletRequest request,	HttpServletResponse response) {
		return "dossier/dossier";
	}
	
	@RequestMapping(value = "/dossier/createdossierfromlote/{idLote}", method = RequestMethod.GET)
	public @ResponseBody List<CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription> createDossier (HttpServletRequest request,	HttpServletResponse response , @PathVariable Long idLote) {
		return dossierFacade.createDossierFromLote(idLote);
	}
	
	@RequestMapping(value = "/dossier/getchlorinephsalmuerafromidlotecounter/{idLote}", method = RequestMethod.GET)
	public @ResponseBody ChlorinePhSalmuera getChlorinePhSalmueraFromIdLoteCounter (HttpServletRequest request,	HttpServletResponse response , @PathVariable Long idLote) {
		return dossierFacade.getChlorinePhSalmueraFromIdLoteCounter(idLote);
	}
	
	@RequestMapping(value = "/dossier/getpasteurizatorusedinlote/{idLote}", method = RequestMethod.GET)
	public @ResponseBody List<PasteurizatorData> getPasteurizatorUsedInThisLote (HttpServletRequest request,	HttpServletResponse response , @PathVariable Long idLote) {
		return dossierFacade.getPasteurizatorUsedInThisLote(idLote);
	}
	
	@RequestMapping(value = "/dossier/getoperatorinthislote/{idLote}", method = RequestMethod.GET)
	public @ResponseBody List<Operator> getOperatorInThisLote (HttpServletRequest request,	HttpServletResponse response , @PathVariable Long idLote) {
		return dossierFacade.getOperatorInThisLote(idLote);
	}
	
	
	@RequestMapping(value = "/dossier/getlitersusedinthislote/{idLote}", method = RequestMethod.GET)
	public @ResponseBody Long getLitersUsedInThisLote (HttpServletRequest request,	HttpServletResponse response , @PathVariable Long idLote) {
		return dossierFacade.getLitersUsedInThisLote(idLote);
	}
	
	@RequestMapping(value = "/dossier/getfactorytankusedinthislote/{idLote}", method = RequestMethod.GET)
	public @ResponseBody List<FactoryTank> getFactoryTankUsedInThisLote (HttpServletRequest request,	HttpServletResponse response , @PathVariable Long idLote) {
		return dossierFacade.getFactoryTankUsedInThisLote(idLote);
	}
	
	@RequestMapping(value = "/dossier/getpasteurizationdatainthislote/{idLote}", method = RequestMethod.GET)
	public @ResponseBody List<Pasteurization> getPasteurizationDataInThisLote (HttpServletRequest request,	HttpServletResponse response , @PathVariable Long idLote) {
		return dossierFacade.getPasteurizationDataInThisLote(idLote);
	}
	
	@RequestMapping(value = "/dossier/gettimeusedinthislote/{idLote}", method = RequestMethod.GET)
	public @ResponseBody Time getTimeUsedInThisLote (HttpServletRequest request,	HttpServletResponse response , @PathVariable Long idLote) {
		return dossierFacade.getTimeUsedForDoThisLote(idLote);
	}
	
	@RequestMapping(value = "/dossier/getkginthislote/{idLote}", method = RequestMethod.GET)
	public @ResponseBody BigInteger getKgInThisLote (HttpServletRequest request,	HttpServletResponse response , @PathVariable Long idLote) {
		return dossierFacade.getTotalKgInThisLote(idLote);
	}
	
	@RequestMapping(value = "/dossier/getingredientusedinthislote/{idLote}", method = RequestMethod.GET)
	public @ResponseBody List<IngredientDetail> getIngredientUsedInThisLote (HttpServletRequest request,	HttpServletResponse response , @PathVariable Long idLote) {
		return dossierFacade.getIngredientsUsedInThisLote(idLote);
	}
}