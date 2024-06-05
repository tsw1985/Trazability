package com.gabriel.trazability.DAO;

import java.util.List;

import com.gabriel.trazability.model.TruckTank;

public interface TruckTankDAO {
	
	public void create(TruckTank truckTank);
	
	public TruckTank get(Long id);
	
	public void update(Long id, TruckTank truckTank);
	
	public void delete(Long id);
	
	public List<TruckTank> getAllTruckTank();
	
	public String getStatusAboutClean(Long id);
	
	public void setLikeDirty(Long id);
	
	public void setLikeClean(Long id);
	
	public void incrementNumberFilled(Long id);
	
	public void decrementNumberFilled(Long id);
	
	public Long getActualNumberFilled(Long id);
	
	public void addLiters(Long id, Long liters);
	
	public void subtractLiters(Long id, Long liters);
	
	public Boolean isOverFlow(Long id, Long liters);
	
	public Boolean isEmpty(Long id);
	
	public Long getTotalLiters(Long id);
}
