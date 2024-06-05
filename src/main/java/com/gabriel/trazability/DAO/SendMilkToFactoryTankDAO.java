package com.gabriel.trazability.DAO;

import java.util.List;

import com.gabriel.trazability.model.MilkSentToFactoryTank;

public interface SendMilkToFactoryTankDAO {
	
	public MilkSentToFactoryTank get(Long id);
	
	public void update(Long id , MilkSentToFactoryTank milkSentToFactoryTankUpdated);
	
	public void delete(Long id);
	
	public List<MilkSentToFactoryTank> listAllDataMilkSentToFactoryTankByWorkingDay(Long id);
	
	public void setLikeConfirmatedMilkSentToFactoryTankByWorkingDay(Long id);

}
