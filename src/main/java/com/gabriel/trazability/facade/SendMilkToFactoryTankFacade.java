package com.gabriel.trazability.facade;

import java.util.List;

import com.gabriel.trazability.command.SendMilkToFactoryTankCommand;
import com.gabriel.trazability.model.MilkSentToFactoryTank;

public interface SendMilkToFactoryTankFacade {
	
	public MilkSentToFactoryTank get(Long id);
	
	public void delete(Long id);
	
	public List<MilkSentToFactoryTank> listAllDataMilkSentToFactoryTankByWorkingDay(Long id);

	public void update(SendMilkToFactoryTankCommand milkCommand);
	
	public void setLikeConfirmatedMilkSentToFactoryTankByWorkingDay(Long id);

}
