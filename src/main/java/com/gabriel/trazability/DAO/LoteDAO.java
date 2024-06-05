package com.gabriel.trazability.DAO;

import java.math.BigDecimal;
import java.util.List;

import com.gabriel.trazability.command.LoteCommand;
import com.gabriel.trazability.model.Lote;

public interface LoteDAO {

	public void create(Lote lote);
	
	public Lote get(Long id);
	
	public void delete(Long id);
	
	public List<Lote> getDataFromLoteNotFinishedWhereLoteCounterIs(Long loteCounter);
	
	public List<Lote> getLotesNotFinished();
	
	public BigDecimal getLitersConsumedByLoteCounter(Long id);
	
}
