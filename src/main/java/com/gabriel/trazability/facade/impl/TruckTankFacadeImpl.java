package com.gabriel.trazability.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.TruckTankDAO;
import com.gabriel.trazability.command.TruckTankCommand;
import com.gabriel.trazability.facade.TruckTankFacade;
import com.gabriel.trazability.model.TruckTank;

@Transactional
@Service
public class TruckTankFacadeImpl implements TruckTankFacade {

	@Autowired
	private TruckTankDAO truckTankDAO;
	
	@Override
	public void create(TruckTankCommand truckTankCommand) {
		truckTankDAO.create(commandToObject(truckTankCommand));
	}
	
	private TruckTank commandToObject(TruckTankCommand truckTankCommand)
	{
		TruckTank truckTank = new TruckTank();
		truckTank.setCapacity(Long.valueOf(truckTankCommand.getCapacity()));
		truckTank.setDescription(truckTankCommand.getDescription());
		truckTank.setDeletedTruckTank("0");
		truckTank.setIdFilled(0L);
		truckTank.setClean("clean");
		return truckTank;
	}
	

	@Override
	public void update(Long id, TruckTankCommand truckTankCommand) {
		
		TruckTank truckTank = new TruckTank();
		truckTank.setCapacity(Long.valueOf(truckTankCommand.getCapacity()));
		truckTank.setDescription(truckTankCommand.getDescription());
		
		truckTankDAO.update(truckTankCommand.getId(), truckTank);
	}

	@Override
	public void delete(Long id) {
		truckTankDAO.delete(id);
	}

	@Override
	public List<TruckTank> getAllTruckTank() {
			return truckTankDAO.getAllTruckTank();
	}

	@Override
	public TruckTank get(Long id) {
		return truckTankDAO.get(id);
	}

	@Override
	public void setLikeDirty(Long id) {
		truckTankDAO.setLikeDirty(id);
	}

	@Override
	public void setLikeClean(Long id) {
		truckTankDAO.setLikeClean(id);
	}

	@Override
	public String getCleanStatus(Long id) {
		return truckTankDAO.getStatusAboutClean(id);
	}

	@Override
	public void incrementNumberFilled(Long id) {
		truckTankDAO.incrementNumberFilled(id);
	}

	@Override
	public void decrementNumberFilled(Long id) {
		truckTankDAO.decrementNumberFilled(id);
	}

	@Override
	public Long getActualNumberFilled(Long id) {
		return truckTankDAO.getActualNumberFilled(id);
	}
}
