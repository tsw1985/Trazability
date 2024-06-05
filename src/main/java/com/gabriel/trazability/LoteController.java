package com.gabriel.trazability;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gabriel.trazability.facade.LoteFacade;
import com.gabriel.trazability.model.Lote;

@Controller
public class LoteController {
	
	@Autowired
	private LoteFacade loteFacade;
	
	@RequestMapping(value="/lote/sendorderlote", method = RequestMethod.POST)
	public @ResponseBody void saveLoteOrderByJson(String orderLote,HttpServletRequest request,	HttpServletResponse response){
		System.out.println("ORDER " + orderLote);
		//loteFacade.saveOrderLoteByJson(orderLote);
	}
	
	@RequestMapping(value="/lote/getdatalotenotfinished/{loteCounter}", method = RequestMethod.GET)
	public @ResponseBody List<Lote> getdatalotenotfinished(@PathVariable Long loteCounter,HttpServletRequest request,	HttpServletResponse response){
		return loteFacade.getDataFromLoteNotFinishedWhereLoteCounterIs(loteCounter);
	}
	
	@RequestMapping(value="/lote/getlotenotfinished", method = RequestMethod.GET)
	public @ResponseBody List<Lote> getLotenotfinished(HttpServletRequest request,	HttpServletResponse response){
		return loteFacade.getLotesNotFinished();
	}
	
	@RequestMapping(value="/lote/getlitersconsumed/{id}", method = RequestMethod.GET)
	public @ResponseBody BigDecimal getLitersConsumed(@PathVariable Long id,HttpServletRequest request,	HttpServletResponse response){
		return loteFacade.getLitersConsumedByLoteCounter(id);
	}
	
	@RequestMapping(value="/lote/delete/{id}", method = RequestMethod.GET)
	public void delete(@PathVariable Long id,HttpServletRequest request,	HttpServletResponse response){
		loteFacade.delete(id);
	}
}