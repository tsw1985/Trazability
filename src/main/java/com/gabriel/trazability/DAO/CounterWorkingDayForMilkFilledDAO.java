package com.gabriel.trazability.DAO;

import com.gabriel.trazability.model.CounterWorkingDayForMilkFilled;

public interface CounterWorkingDayForMilkFilledDAO {

	public void increment();
	
	public String getLastCounter();
	
	public CounterWorkingDayForMilkFilled get(Long id);
	
	public void setLikeOpen(Long id);
	
	public void setLikeClose(Long id);
}
