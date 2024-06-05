package com.gabriel.trazability.DAO;

import java.util.List;

import com.gabriel.trazability.model.ChlorinePhSalmuera;
import com.gabriel.trazability.model.ChlorinePhStreetWater;
import com.gabriel.trazability.model.LoteCounter;

public interface LoteCounterServiceDAO {
	
	public String getLastLoteCounter();

	public void increment();
	
	public void setLoteCounterLikeFinished(Long idLoteCounter);
	
	public LoteCounter get(Long id);
	
	public void create(LoteCounter loteCounter);
	
	public void update(String listIngredients, Long idLoteCounter);
	
	public List<LoteCounter> getLoteCounterNotFinished();
	
	public String getLoteCounterNotFinishedByString();
	
	public List getLoteCounterNotFinishedByList();
	
	public String getIngredientUsedInLoteCounter(Long idLoteCounter);
	
	public void addExtraInformationLote(Long idLoteCounter, Long kgcheese, String cuajedtime, String cuajedTemperature, 
			ChlorinePhSalmuera chlorinePhSalmuera, ChlorinePhStreetWater listIngredients);
	
	public void setPasteurizatorToLoteCounter(Long idPasteurizator, Long valueLoteCounter);
	
	public void detachmentPasteurizatorInLoteCounter(Long idLoteCounter);

}
