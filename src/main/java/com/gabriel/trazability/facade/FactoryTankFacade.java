package com.gabriel.trazability.facade;

import java.util.List;

import com.gabriel.trazability.command.FactoryTankCommand;
import com.gabriel.trazability.exceptions.FactoryTankNotFoundException;
import com.gabriel.trazability.exceptions.LiterOverflowFactoryTankException;
import com.gabriel.trazability.model.FactoryTank;

public interface FactoryTankFacade {
	
	public void create(FactoryTankCommand factoryTankCommand);
	
	public FactoryTank get(Long id);
	
	public void update(Long id,FactoryTankCommand factoryTankCommand);
	
	public void delete(Long id);
	
	public List<FactoryTank> getAllfactoryTank();
	
	public void setLikeDirty(Long id);
	
	public void setLikeClean(Long id);
	
	public void setLikeBloqued(Long id);
	
	public void setLikeNotBloqued(Long id);
	
	public void setCapacity(Long id);
	
	public Long getActualCapacity(Long id);
	
	public void setCapacityByNumberMilkFilled(Long id , Long number);
	
	public String getCleanStatus(Long id);
	
	public void incrementNumberFilled(Long id);
	
	public void decrementNumberFilled(Long id);
	
	public Long getActualNumberFilled(Long id);
	
	public void addLitersFromTruckTankToFactoryTank(Long numberCounterMilkFilled ,Long idtruckTank, Long idFactoryTank, Long liters) 
	throws LiterOverflowFactoryTankException,FactoryTankNotFoundException;
	
	public void subtractLiters(Long id, Long liters);
	
	public void addLiters(Long id, Long liters);
	
	public Boolean isOverFlow(Long id, Long liters);
	
	public Boolean isEmpty(Long id);
	
	public Boolean isExist(Long id);
	
	public List<FactoryTank> getAllFactoryTank();
	
	public void setLikeSelectedActive(Long id);
	
	public void setLikeNotSelectedActive(Long id);
	
	public String getStateActiveSelectedFactoryTank(Long id);
	
	public void setActualIdLoteCounterForWork(Long idLoteCounter, Long idFactoryTank);
	
}
