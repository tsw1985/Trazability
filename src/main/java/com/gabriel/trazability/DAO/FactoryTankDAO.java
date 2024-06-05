package com.gabriel.trazability.DAO;

import java.util.List;

import com.gabriel.trazability.model.FactoryTank;
import com.gabriel.trazability.model.MilkSentToFactoryTank;

public interface FactoryTankDAO {
	
	public void create(FactoryTank FactoryTank);
	
	public FactoryTank get(Long id);
	
	public void update(Long id, FactoryTank FactoryTank);
	
	public void delete(Long id);
	
	public List<FactoryTank> getAllFactoryTank();
	
	public String getStatusAboutClean(Long id);
	
	public void setLikeDirty(Long id);
	
	public void setLikeClean(Long id);
	
	public void setLikeBloqued(Long id);
	
	public void setLikeNotBloqued(Long id);
	
	public Long getActualCapacity(Long id);
	
	public void setActualCapacity(Long id, Long liters);

	public void incrementNumberFilled(Long id);
	
	public void decrementNumberFilled(Long id);
	
	public Long getActualNumberFilled(Long id);
	
	public void addLitersFromTruckTankToFactoryTank(MilkSentToFactoryTank milkSentToFactoryTank);
	
	public void subtractLiters(Long id, Long liters);
	
	public void addLiters(Long id, Long liters);
	
	public Boolean isOverFlow(Long id, Long liters);
	
	public Boolean isEmpty(Long id);
	
	public Boolean isExist(Long id);
	
	public void setLikeSelectedActive(Long id);
	
	public void setLikeNotSelectedActive(Long id);
	
	public String getStateActiveSelectedFactoryTank(Long id);
	
	public void setActualIdLoteCounterForWork(Long idLoteCounter, Long idFactoryTank);
	
	
}
