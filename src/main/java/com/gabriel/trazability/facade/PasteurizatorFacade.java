package com.gabriel.trazability.facade;
import java.util.List;

import com.gabriel.trazability.command.PasteurizatorCommand;
import com.gabriel.trazability.model.Pasteurizator;

public interface PasteurizatorFacade {
	
	public void create(PasteurizatorCommand pasteurizatorCommand);
	
	public Pasteurizator get(Long id);
	
	public void update(PasteurizatorCommand pasteurizatorCommand);
	
	public List<Pasteurizator> getAllPasteurizatorNoDeleted();
	
	public void delete(Long id);
	
	public void turnON(String parameter, Long id , Long idLoteCounter);
	
	public void turnOFF(String parameter,  Long id);
	
	public String getActualTemperature( Long id);
	
	public void saveActualTemperature(Long id);
	
	public String getRunningState(Long id);
	
	public void saveTurnOFFState( Long id);
	
	public void saveTurnONState(Long id);
	
	public void saveNotPasteurizingState(Long id);
	
	public String getPasteurizingState(Long id);

	String savePasteurizingstate(String parameter, Long id);

	public void setActualIdLoteCounterForWorkAndFactoryTank(Long idPasteurizator,Long valueLoteCounter ,Long idFactoryTank);
	
	public void detachmentActualIdLoteCounterForWork(Long idPasteurizator);

}