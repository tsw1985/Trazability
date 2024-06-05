package com.gabriel.trazability.DAO;
import java.util.List;

import com.gabriel.trazability.model.Pasteurizator;

public interface PasteurizatorDAO {
	
	public void create(Pasteurizator pasteurizator);
	
	public Pasteurizator get(Long id);
	
	public List<Pasteurizator> getAllPasteurizatorNoDeleted();
	
	public void setActualIdLoteCounterForWorkAndFactoryTank(Long idPasteurizator , Long valueLoteCounter, Long idFactoryTank);
	
	void turnON(Long id);
	
	public void turnOFF(Long id);
	
	public String getActualTemperature(Long id);
	
	public void saveActualTemperature(Long id, String temperature);
	
	public String getRunningState(Long id);
	
	public void saveTurnOFFState(Long id);
	
	public void saveTurnONState(Long id);
	
	public void savePasteurizingstate(Long id);
	
	public void saveNotPasteurizingState(Long id);
	
	public String getPasteurizingState(Long id);
	
	public void update(Pasteurizator pasteurizator , Long id);
	
	public void delete(Long id);
	
	public void detachmentActualIdLoteCounterForWork(Long idPasteurizator);
}