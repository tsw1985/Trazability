package com.gabriel.trazability.facade;

import java.util.List;

import com.gabriel.trazability.command.TruckTankCommand;
import com.gabriel.trazability.model.TruckTank;

public interface TruckTankFacade {

	public void create(TruckTankCommand truckTankCommand);
	
	public TruckTank get(Long id);
	
	public void update(Long id,TruckTankCommand truckTankCommand);
	
	public void delete(Long id);
	
	public List<TruckTank> getAllTruckTank();
	
	public void setLikeDirty(Long id);
	
	public void setLikeClean(Long id);
	
	public String getCleanStatus(Long id);
	
	public void incrementNumberFilled(Long id);
	
	public void decrementNumberFilled(Long id);
	
	public Long getActualNumberFilled(Long id);
}
