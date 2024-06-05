package com.gabriel.trazability.facade;

import java.math.BigDecimal;
import java.util.List;

import com.gabriel.trazability.command.LoteCommand;
import com.gabriel.trazability.exceptions.NotDataChlorinePhException;
import com.gabriel.trazability.model.Lote;

public interface LoteFacade {
	
	public void saveOrderLoteByJson(String loteOrder);
	
	public void saveLoteByCommand(LoteCommand loteCommand)throws NotDataChlorinePhException;
	
	public List<Lote> getDataFromLoteNotFinishedWhereLoteCounterIs(Long idLoteCounter);
	
	public List<Lote> getLotesNotFinished();
	
	public BigDecimal getLitersConsumedByLoteCounter(Long id);
	
	public void delete(Long id);

}
