package com.gabriel.trazability.facade.impl;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.MilkFilledDAO;
import com.gabriel.trazability.DAO.OperatorDAO;
import com.gabriel.trazability.DAO.ProviderDAO;
import com.gabriel.trazability.DAO.TruckTankDAO;
import com.gabriel.trazability.command.OrderMilkFilledCommand;
import com.gabriel.trazability.facade.MilkFilledFacade;
import com.gabriel.trazability.model.MilkFilled;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
@Transactional
public class MilkFilledFacadeImpl implements MilkFilledFacade {

	@Autowired
	private MilkFilledDAO milkFilledDAO;
	
	@Autowired
	private OperatorDAO operatorDAO;
	
	@Autowired
	private ProviderDAO providerDAO;
	
	@Autowired
	private TruckTankDAO truckTankDAO;
	
	@Override
	public void create(OrderMilkFilledCommand milkFilled) {
		milkFilledDAO.create(commandToMilkFilled(milkFilled));
	}
	
	
	private MilkFilled commandToMilkFilled(OrderMilkFilledCommand milkFilledCommand){
		
		MilkFilled milkFilled = new MilkFilled();
		milkFilled.setCorrection(milkFilledCommand.getCorrection());
		milkFilled.setDisplay(milkFilledCommand.getDisplay());
		milkFilled.setFilledDate(new Date());
		milkFilled.setIncidence(milkFilledCommand.getIncidence());
		milkFilled.setOperator(operatorDAO.get(Long.valueOf(milkFilledCommand.getIdOperator())));
		milkFilled.setProvider(providerDAO.getProvider(Long.valueOf(milkFilledCommand.getIdProvider())));
		milkFilled.setProviderTank(milkFilledCommand.getTankProvider());
		milkFilled.setTotalFilled(Long.valueOf(milkFilledCommand.getLitersFilled()));
		milkFilled.setTruckTank(truckTankDAO.get(Long.valueOf(milkFilledCommand.getIdTruckTank())));
		milkFilled.setNumberWorkDayMilkFilled(milkFilledCommand.getNumberWorkDayMilkFilled());
		milkFilled.setDeleteMilkFilled("NODELETED");
		
		return milkFilled;
	}
	
	
	@Override
	public MilkFilled get(Long id) {
		return milkFilledDAO.get(id);
	}


	@Override
	public void update(OrderMilkFilledCommand milkFilledCommand) {
		
		MilkFilled milkFilled = new MilkFilled();
		milkFilled.setCorrection(milkFilledCommand.getCorrection());
		milkFilled.setDisplay(milkFilledCommand.getDisplay());
		milkFilled.setFilledDate(new Date());
		milkFilled.setIncidence(milkFilledCommand.getIncidence());
		milkFilled.setOperator(operatorDAO.get(Long.valueOf(milkFilledCommand.getIdOperator())));
		milkFilled.setProvider(providerDAO.getProvider(Long.valueOf(milkFilledCommand.getIdProvider())));
		milkFilled.setProviderTank(milkFilledCommand.getTankProvider());
		milkFilled.setTotalFilled(Long.valueOf(milkFilledCommand.getLitersFilled()));
		milkFilled.setTruckTank(truckTankDAO.get(Long.valueOf(milkFilledCommand.getIdTruckTank())));
		milkFilled.setNumberWorkDayMilkFilled(milkFilledCommand.getNumberWorkDayMilkFilled());
		milkFilled.setDeleteMilkFilled("NODELETED");
		
		milkFilledDAO.update(milkFilledCommand.getId(), milkFilled);
	}

	@Override
	public void delete(Long id) {
		milkFilledDAO.delete(id);
	}
	
	@Override
	public List<MilkFilled> getAllMilkFilledFromANumberWorkDayMilkFilled(Long number) {
		return milkFilledDAO.getTotalMilkFilledFromANumberWorkDayMilkFilled(number);
	}

	@Override
	public void saveOrderMilkFilledByJSON(String json) {
	    Gson gson = new Gson();
	    Type typeOrderMilkFilledCommand = new TypeToken<List<OrderMilkFilledCommand>>(){}.getType();
	    List<OrderMilkFilledCommand> listOrderFromJson = gson.fromJson(json, typeOrderMilkFilledCommand);
	    
	    for(OrderMilkFilledCommand elementListOrder : listOrderFromJson ){
		    milkFilledDAO.create(commandToMilkFilled(elementListOrder));
	    }
	}

	@Override
	public Long getTotalLitersMilkFilledByNumberWorkDayMilkFilled(Long number) {
		return milkFilledDAO.getTotalSumLitersMilkFilledByNumberWorkDayMilkFilled(number);
	}

	@Override
	public List<MilkFilled> getDataMilkFilledOpen() {
		return null;
	}
}