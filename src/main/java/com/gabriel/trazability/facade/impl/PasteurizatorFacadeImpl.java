package com.gabriel.trazability.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.PasteurizatorDAO;
import com.gabriel.trazability.command.PasteurizatorCommand;
import com.gabriel.trazability.facade.PasteurizatorFacade;
import com.gabriel.trazability.model.Pasteurizator;
import com.gabriel.trazability.service.ArduinoService;

@Service
@Transactional
public class PasteurizatorFacadeImpl implements PasteurizatorFacade{

	@Autowired
	private PasteurizatorDAO pasteurizatorDAO;
	
	@Autowired
	private ArduinoService arduinoService;
	
	@Override
	public void create(PasteurizatorCommand pasteurizatorCommand) {
		pasteurizatorDAO.create(commandToPasteurizator(pasteurizatorCommand));
	}
	
	@Override
	public Pasteurizator get(Long id) {
		return pasteurizatorDAO.get(id);
	}
	
	private Pasteurizator commandToPasteurizator(PasteurizatorCommand pasteurizatorCommand){

		Pasteurizator pasteurizator = new Pasteurizator();
		pasteurizator.setCaudalSecond(pasteurizatorCommand.getCaudalSecond());
		pasteurizator.setDescription(pasteurizatorCommand.getDescription());
		pasteurizator.setDeletedPasteurizator("NODELETED");
		pasteurizator.setIp(pasteurizatorCommand.getIp());
		
		return pasteurizator;
	}
	
	@Override
	public void update(PasteurizatorCommand pasteurizatorUpdated) {
		
		Pasteurizator pasteurizator = new Pasteurizator();
		pasteurizator.setCaudalSecond(pasteurizatorUpdated.getCaudalSecond());
		pasteurizator.setDescription(pasteurizatorUpdated.getDescription());
		pasteurizator.setIp(pasteurizatorUpdated.getIp());
		
		pasteurizatorDAO.update(pasteurizator, pasteurizatorUpdated.getId());
	}

	@Override
	public void delete(Long id) {
		pasteurizatorDAO.delete(id);
	}
	
	@Override
	public List<Pasteurizator> getAllPasteurizatorNoDeleted() {
		return pasteurizatorDAO.getAllPasteurizatorNoDeleted();
	}
	
	@Override
	public void turnON(String action, Long idPasteurizator, Long idLoteCounter) {

		pasteurizatorDAO.turnON(idPasteurizator);
		arduinoService.turnOnPasteurizator(action);
	}
	
	@Override
	public void setActualIdLoteCounterForWorkAndFactoryTank(Long idPasteurizator, Long valueLoteCounter, Long idFactoryTank) {
		pasteurizatorDAO.setActualIdLoteCounterForWorkAndFactoryTank(idPasteurizator, valueLoteCounter, idFactoryTank);
	}


	@Override
	public void turnOFF(String parameter, Long id) {
		pasteurizatorDAO.turnOFF(id);
		arduinoService.turnOffPasteurizator(parameter);
	}

	@Override
	public String getActualTemperature(Long id) {
		return pasteurizatorDAO.getActualTemperature(id);
	}
	
	@Override
	public void saveActualTemperature( Long id) {
		String actualTemperature = actualTemperature("T=1");
		pasteurizatorDAO.saveActualTemperature(id, actualTemperature);
	}
	
	//CAMBIAR , VALOR DE PRUBAS
	private String actualTemperature(String parameter){
		return "100";
		//return arduinoService.getTemperaturePasteurizator(parameter);
	}

	@Override
	public String getRunningState(Long id) {
		return pasteurizatorDAO.getRunningState(id);
	}

	@Override
	public void saveTurnOFFState(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveTurnONState( Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String savePasteurizingstate(String parameter, Long id) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public void saveNotPasteurizingState( Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPasteurizingState( Long id) {
		return pasteurizatorDAO.getPasteurizingState(id);
	}

	@Override
	public void detachmentActualIdLoteCounterForWork(Long idPasteurizator) {
			pasteurizatorDAO.detachmentActualIdLoteCounterForWork(idPasteurizator);
	}
}