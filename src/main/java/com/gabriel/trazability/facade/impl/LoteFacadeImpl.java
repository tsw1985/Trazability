package com.gabriel.trazability.facade.impl;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.CubaDAO;
import com.gabriel.trazability.DAO.FactoryTankDAO;
import com.gabriel.trazability.DAO.LoteCounterServiceDAO;
import com.gabriel.trazability.DAO.LoteDAO;
import com.gabriel.trazability.DAO.OperatorDAO;
import com.gabriel.trazability.DAO.PasteurizatorDAO;
import com.gabriel.trazability.command.LoteCommand;
import com.gabriel.trazability.exceptions.NotDataChlorinePhException;
import com.gabriel.trazability.facade.ChlorinePhSalmueraFacade;
import com.gabriel.trazability.facade.ChlorinePhStreetWaterFacade;
import com.gabriel.trazability.facade.LoteFacade;
import com.gabriel.trazability.model.Lote;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
@Transactional
public class LoteFacadeImpl implements LoteFacade {

	@Autowired
	private LoteDAO loteDAO;

	@Autowired
	private OperatorDAO operatorDAO;
	
	@Autowired
	private CubaDAO cubaDAO;
	
	@Autowired
	private LoteCounterServiceDAO loteCounterServiceDAO;
	
	@Autowired
	private FactoryTankDAO factoryTankDAO;
	
	@Autowired
	private PasteurizatorDAO pasteurizatorDAO;
	
	@Autowired
	private ChlorinePhSalmueraFacade chlorinePhSalmueraFacade; 
	
	@Autowired
	private ChlorinePhStreetWaterFacade chlorinePhStreetWaterFacade;
	
	@Override
	public void saveOrderLoteByJson(String loteOrder) {
		Gson gson = new Gson();
	    Type typeOrderLoteCommand = new TypeToken<List<LoteCommand>>(){}.getType();
	    List<LoteCommand> listOrderFromJson = gson.fromJson(loteOrder, typeOrderLoteCommand);
	    
	    for(LoteCommand elementListOrder : listOrderFromJson ){
		    loteDAO.create(commandToLote(elementListOrder));
	    }
	}
	
	private Lote commandToLote(LoteCommand loteCommand)
	{
		Lote lote = new Lote();
		lote.setIdFilledFactoryTank(loteCommand.getIdFilledFactoryTank());
		lote.setFactoryTank(factoryTankDAO.get(loteCommand.getIdFactoryTank()));
		lote.setLiters(loteCommand.getLiters());
		lote.setLoteTime(new Date());
		lote.setOperator(operatorDAO.get(loteCommand.getIdOperator()));
		lote.setCuba(cubaDAO.get(loteCommand.getCuba()));
		lote.setLoteCounter(loteCounterServiceDAO.get(loteCommand.getIdLoteCounter()));
		lote.setPasteurizator(pasteurizatorDAO.get(loteCommand.getPasteurizator()));
		lote.setDeletedLote("NODELETED");
		
		return lote;
	}

	@Override
	public void saveLoteByCommand(LoteCommand loteCommand) throws NotDataChlorinePhException {
		factoryTankDAO.subtractLiters(loteCommand.getIdFactoryTank(), loteCommand.getLiters());
		
		if(chlorinePhSalmueraFacade.getLastChlorinePhSalmuera() == null || 
		   chlorinePhStreetWaterFacade.getLastChlorinePh() == null) throw new NotDataChlorinePhException();
		
		loteDAO.create(commandToLote(loteCommand));
	}

	@Override
	public List<Lote> getDataFromLoteNotFinishedWhereLoteCounterIs(Long loteCounter) {
		return loteDAO.getDataFromLoteNotFinishedWhereLoteCounterIs(loteCounter);
	}

	@Override
	public List<Lote> getLotesNotFinished() {
		return loteDAO.getLotesNotFinished();
	}

	@Override
	public BigDecimal getLitersConsumedByLoteCounter(Long id) {
		return loteDAO.getLitersConsumedByLoteCounter(id);
	}

	@Override
	public void delete(Long id) {
		Lote lote = loteDAO.get(id);
		factoryTankDAO.addLiters(lote.getFactoryTank().getId(), lote.getLiters());
		loteDAO.delete(id);
	}
}