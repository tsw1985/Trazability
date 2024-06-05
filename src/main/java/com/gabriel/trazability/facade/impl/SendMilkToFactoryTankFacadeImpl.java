package com.gabriel.trazability.facade.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.FactoryTankDAO;
import com.gabriel.trazability.DAO.SendMilkToFactoryTankDAO;
import com.gabriel.trazability.DAO.TruckTankDAO;
import com.gabriel.trazability.command.SendMilkToFactoryTankCommand;
import com.gabriel.trazability.facade.SendMilkToFactoryTankFacade;
import com.gabriel.trazability.model.MilkSentToFactoryTank;
import com.gabriel.trazability.service.DatesService;

@Service
@Transactional
public class SendMilkToFactoryTankFacadeImpl implements SendMilkToFactoryTankFacade{
	
	@Autowired
	private SendMilkToFactoryTankDAO sendMilkToFactoryTankDAO;
	
	@Autowired
	private TruckTankDAO truckTankDAO;
	
	@Autowired
	private FactoryTankDAO factoryTankDAO;
	
	@Autowired
	private DatesService datesService;

	@Override
	public MilkSentToFactoryTank get(Long id) {
		return sendMilkToFactoryTankDAO.get(id);
	}

	@Override
	public void update(SendMilkToFactoryTankCommand milkCommand) {
		 
		MilkSentToFactoryTank milkSent = new MilkSentToFactoryTank();
		milkSent.setDateTimeMilkSentToFactoryTank(new Date());
		milkSent.setTruckTank(truckTankDAO.get(milkCommand.getIdTruckTank()));
		milkSent.setFactoryTank(factoryTankDAO.get(milkCommand.getFactoryTank()));
		milkSent.setLiters(milkCommand.getLitersFilled());
		sendMilkToFactoryTankDAO.update(milkCommand.getIdMilkFilled(), milkSent);
	}

	@Override
	public void delete(Long id) {
		sendMilkToFactoryTankDAO.delete(id);
	}

	@Override
	public List<MilkSentToFactoryTank> listAllDataMilkSentToFactoryTankByWorkingDay(Long id) {
		return sendMilkToFactoryTankDAO.listAllDataMilkSentToFactoryTankByWorkingDay(id);
	}

	@Override
	public void setLikeConfirmatedMilkSentToFactoryTankByWorkingDay(Long id) {
		sendMilkToFactoryTankDAO.setLikeConfirmatedMilkSentToFactoryTankByWorkingDay(id);		
	}
}