package com.gabriel.trazability.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.ChlorinePhSalmueraDAO;
import com.gabriel.trazability.DAO.ChlorinePhStreetWaterDAO;
import com.gabriel.trazability.DAO.LoteCounterServiceDAO;
import com.gabriel.trazability.model.LoteCounter;
import com.gabriel.trazability.service.LoteCounterService;

@Service
@Transactional
public class LoteCounterServiceImpl implements LoteCounterService {

	@Autowired
	private LoteCounterServiceDAO loteCounterServiceDAO;
	
	@Autowired
	private ChlorinePhSalmueraDAO chlorinePhSalmueraDAO;
	
	@Autowired
	private ChlorinePhStreetWaterDAO chlorinePhStreetWaterDAO;
	
	@Override
	public void increment() {
		loteCounterServiceDAO.increment();
	}

	@Override
	public String getLastLoteCounter() {
		return loteCounterServiceDAO.getLastLoteCounter();
	}

	@Override
	public void create() {
		LoteCounter loteCounter = new LoteCounter();
		loteCounter.setFinished("NOFINISHED");
		loteCounter.setLitersConsumed(0L);
		loteCounter.setActualPasteurizatorAsigned(null);
		loteCounter.setIngredientsSelected("");
		loteCounter.setLitersConsumed(0L);
		loteCounter.setNumber("");
		
		
		loteCounterServiceDAO.create(loteCounter);
	}

	@Override
	public List<LoteCounter> getLoteCounterNotFinished() {
		return loteCounterServiceDAO.getLoteCounterNotFinished();
	}

	@Override
	public String getLoteCounterNotFinishedByString() {
		return loteCounterServiceDAO.getLoteCounterNotFinishedByString();
	}

	@Override
	public List getLoteCounterNotFinishedByList() {
		return loteCounterServiceDAO.getLoteCounterNotFinishedByList();
	}

	@Override
	public LoteCounter getConsumedLitersByIdCounterLote(Long idCounterLote) {
		return loteCounterServiceDAO.get(idCounterLote);
	}

	@Override
	public LoteCounter get(Long id) {
		return loteCounterServiceDAO.get(id);
	}

	@Override
	public void setLoteCounterLikeFinished(Long idLoteCounter) {
		loteCounterServiceDAO.setLoteCounterLikeFinished(idLoteCounter);
	}

	@Override
	public String getIngredientUsedInLoteCounter(Long idLoteCounter) {
		return loteCounterServiceDAO.getIngredientUsedInLoteCounter(idLoteCounter);
	}
		
	@Override
	public void addExtraInformationLote(Long idLoteCounter, Long kgcheese, String cuajedtime,String cuajedTemperature) {
			
		loteCounterServiceDAO.addExtraInformationLote(
				idLoteCounter, kgcheese, cuajedtime, cuajedTemperature, 
				chlorinePhSalmueraDAO.getLastSalmueraChlorinePh(),
				chlorinePhStreetWaterDAO.getLastChlorinePh() 
			);
	}

	@Override
	public void setPasteurizatorToLoteCounter(Long idPasteurizator , Long valueLoteCounter) {
		loteCounterServiceDAO.setPasteurizatorToLoteCounter(idPasteurizator , valueLoteCounter);
		
	}

	@Override
	public void detachmentPasteurizatorInLoteCounter(Long idLoteCounter) {
		loteCounterServiceDAO.detachmentPasteurizatorInLoteCounter(idLoteCounter);
		
	}
}