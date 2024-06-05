package com.gabriel.trazability.facade;

import java.util.List;

import com.gabriel.trazability.command.MilkFilledCommand;
import com.gabriel.trazability.command.OrderMilkFilledCommand;
import com.gabriel.trazability.model.MilkFilled;

public interface MilkFilledFacade {
	
	public void create(OrderMilkFilledCommand milkFilled);
	
	public MilkFilled get(Long id);
	
	public void delete(Long id);
	
	public void saveOrderMilkFilledByJSON(String json);
	
	public List<MilkFilled> getAllMilkFilledFromANumberWorkDayMilkFilled(Long number);
	
	public Long getTotalLitersMilkFilledByNumberWorkDayMilkFilled(Long number);
	
	public List<MilkFilled> getDataMilkFilledOpen();

	void update(OrderMilkFilledCommand miklFilledUpdated);

}
