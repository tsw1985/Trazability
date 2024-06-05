package com.gabriel.trazability.facade.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.CounterWorkingDayForMilkFilledDAO;
import com.gabriel.trazability.DAO.FactoryTankDAO;
import com.gabriel.trazability.DAO.MilkFilledDAO;
import com.gabriel.trazability.DAO.TruckTankDAO;
import com.gabriel.trazability.command.FactoryTankCommand;
import com.gabriel.trazability.exceptions.FactoryTankNotFoundException;
import com.gabriel.trazability.exceptions.LiterOverflowFactoryTankException;
import com.gabriel.trazability.facade.FactoryTankFacade;
import com.gabriel.trazability.model.FactoryTank;
import com.gabriel.trazability.model.MilkSentToFactoryTank;

@Service
@Transactional
public class FactoryTankFacadeImpl implements FactoryTankFacade {

	@Autowired
	private FactoryTankDAO factoryTankDAO;
	
	@Autowired
	private MilkFilledDAO milkFilledDAO;
	
	@Autowired
	private TruckTankDAO truckTankDAO;
	
	@Autowired
	private CounterWorkingDayForMilkFilledDAO counterWorkingDayForMilkFilledDAO;
	
	@Override
	public void create(FactoryTankCommand factoryTankCommand) {
		factoryTankDAO.create(commandToFactoryTank(factoryTankCommand));
	}

	private FactoryTank commandToFactoryTank(FactoryTankCommand factoryTankCommand)
	{
		FactoryTank factoryTank = new FactoryTank();
		factoryTank.setClean("clean");
		factoryTank.setDescription(factoryTankCommand.getDescription());
		factoryTank.setCapacity(Long.valueOf(factoryTankCommand.getCapacity()));
		factoryTank.setDeletedFactoryTank("0");
		factoryTank.setIdFilled(0L);
		factoryTank.setIdPickUp(0L);
		factoryTank.setActualCapacity(0L);	
		factoryTank.setTemperature(0L);
		factoryTank.setSelectedActive("NOACTIVE");
		return factoryTank;
	}
	
	@Override
	public FactoryTank get(Long id) {
		return factoryTankDAO.get(id);
	}

	@Override
	public void update(Long id, FactoryTankCommand factoryTankCommand) {
		FactoryTank factoryTank = new FactoryTank();
		factoryTank.setCapacity(Long.valueOf(factoryTankCommand.getCapacity()));
		factoryTank.setDescription(factoryTankCommand.getDescription());
		
		factoryTankDAO.update(id, factoryTank);
	}

	@Override
	public void delete(Long id) {
		factoryTankDAO.delete(id);

	}

	@Override
	public List<FactoryTank> getAllfactoryTank() {
		return factoryTankDAO.getAllFactoryTank();
	}

	@Override
	public void setLikeDirty(Long id) {
		factoryTankDAO.setLikeDirty(id);
	}

	@Override
	public void setLikeClean(Long id) {
		factoryTankDAO.setLikeClean(id);
	}
	
	
	@Override
	public void setLikeSelectedActive(Long id) {
		factoryTankDAO.setLikeSelectedActive(id);
	}
	
	
	@Override
	public void setLikeNotSelectedActive(Long id) {
		factoryTankDAO.setLikeNotSelectedActive(id);
	}
	
	@Override
	public String getCleanStatus(Long id) {
		return factoryTankDAO.getStatusAboutClean(id);
	}
	
	
	@Override
	public String getStateActiveSelectedFactoryTank(Long id) {
		return factoryTankDAO.getStateActiveSelectedFactoryTank(id);
	}
	

	@Override
	public void incrementNumberFilled(Long id) {
		factoryTankDAO.incrementNumberFilled(id);
	}

	@Override
	public void decrementNumberFilled(Long id) {
		factoryTankDAO.decrementNumberFilled(id);
	}

	@Override
	public Long getActualNumberFilled(Long id) {
		return factoryTankDAO.getActualNumberFilled(id);
	}

	@Override
	public Long getActualCapacity(Long id) {
		return factoryTankDAO.get(id).getActualCapacity();
	}

	
	@Override
	public void setCapacity(Long id) {
		
	}

	@Override
	public void setCapacityByNumberMilkFilled(Long id, Long number) {
		
	}

	@Override
	public void addLitersFromTruckTankToFactoryTank(Long numberCounterMilkFilled ,Long idtruckTank, Long idFactoryTank, Long litersSent) 
	throws LiterOverflowFactoryTankException,FactoryTankNotFoundException{
				
		
				FactoryTank factoryTank = factoryTankDAO.get(idFactoryTank);
				if ( factoryTank == null )	throw new FactoryTankNotFoundException();
				
				Long actualCapacityFactoryTank = factoryTank.getActualCapacity();
				Long factoryTankCapacity = factoryTank.getCapacity();
				Long posibleLitersMaximum = factoryTankCapacity - actualCapacityFactoryTank;
				
				if ( litersSent <= factoryTankCapacity &&  
					 actualCapacityFactoryTank <= factoryTankCapacity && 
					 litersSent <= posibleLitersMaximum ){ 
			    	
					MilkSentToFactoryTank milkSentToFactoryTank = new MilkSentToFactoryTank();
					milkSentToFactoryTank.setCounterWorkingDayForMilkFilled(counterWorkingDayForMilkFilledDAO.get(numberCounterMilkFilled));
					milkSentToFactoryTank.setDateTimeMilkFilled(new Date());
					milkSentToFactoryTank.setDateTimeMilkSentToFactoryTank(new Date());
					milkSentToFactoryTank.setTruckTank(truckTankDAO.get(idtruckTank));
					milkSentToFactoryTank.setFactoryTank(factoryTankDAO.get(idFactoryTank));
					milkSentToFactoryTank.setLiters(litersSent);
					milkSentToFactoryTank.setNumberFilledFactoryTank(factoryTankDAO.getActualNumberFilled(idFactoryTank));
					milkSentToFactoryTank.setDeletedMilkSentToFactoryTank("NODELETED");
					milkSentToFactoryTank.setConfirmated("NOCONFIRMATED");
	
					factoryTankDAO.addLitersFromTruckTankToFactoryTank(milkSentToFactoryTank);
					factoryTankDAO.setActualCapacity(idFactoryTank, litersSent);
					//
					// probar esta ultima linea
					truckTankDAO.subtractLiters(idtruckTank,litersSent );

					
			    }else{
			    	throw new LiterOverflowFactoryTankException();
			    }
	}

	@Override
	public void subtractLiters(Long id, Long liters) {
		factoryTankDAO.subtractLiters(id, liters);
	}
	
	
	@Override
	public void addLiters(Long id, Long liters) {
		factoryTankDAO.addLiters(id, liters);
	}

	@Override
	public Boolean isOverFlow(Long id, Long liters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isEmpty(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FactoryTank> getAllFactoryTank() {
		return factoryTankDAO.getAllFactoryTank();
	}

	@Override
	public Boolean isExist(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLikeBloqued(Long id) {
		factoryTankDAO.setLikeBloqued(id);
	}

	@Override
	public void setLikeNotBloqued(Long id) {
		factoryTankDAO.setLikeNotBloqued(id);
	}

	@Override
	public void setActualIdLoteCounterForWork(Long idLoteCounter , Long idFactoryTank) {
		factoryTankDAO.setActualIdLoteCounterForWork(idLoteCounter , idFactoryTank )  ;
	}
}