package com.gabriel.trazability.service;

import java.util.List;

import com.gabriel.trazability.model.LoteCounter;

public interface LoteCounterService {
	
	public void create();
	
	public void setLoteCounterLikeFinished(Long idLoteCounter);
	
	public void setPasteurizatorToLoteCounter(Long idPasteurizator, Long valueLoteCounter);
	
	public void detachmentPasteurizatorInLoteCounter(Long idLoteCounter);
	
	public LoteCounter get(Long id);
	
	public void addExtraInformationLote(Long idLoteCounter ,Long kgcheese , String cuajedtime , String cuajedTemperature);
	
	public String getLastLoteCounter();

	public void increment();
	
	public List<LoteCounter>getLoteCounterNotFinished();
	
	public String getLoteCounterNotFinishedByString();
	
	public List getLoteCounterNotFinishedByList();
	
	public LoteCounter getConsumedLitersByIdCounterLote(Long idCounterLote);
	
	public String getIngredientUsedInLoteCounter(Long idLoteCounter);
	
	

}
