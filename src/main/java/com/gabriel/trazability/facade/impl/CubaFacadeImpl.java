package com.gabriel.trazability.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.CubaDAO;
import com.gabriel.trazability.command.CubaCommand;
import com.gabriel.trazability.exceptions.CubaIsWithoutIdLoteCounterException;
import com.gabriel.trazability.facade.CubaFacade;
import com.gabriel.trazability.model.Cuba;

@Service
@Transactional
public class CubaFacadeImpl implements CubaFacade {

	@Autowired
	private CubaDAO cubaDAO;
	
	@Override
	public void create(CubaCommand cubaCommand) {
			cubaDAO.create(commandToCuba(cubaCommand));
	}
	
	private Cuba commandToCuba(CubaCommand cubaCommand)
	{
		Cuba cuba = new Cuba();
		cuba.setActualCapacity(0L);
		cuba.setCapacity(Long.valueOf(cubaCommand.getCapacity()));
		cuba.setClean("0");
		cuba.setDeletedCuba("0");
		cuba.setDescription(cubaCommand.getDescription());
		cuba.setBloqued("NOBLOQUED");
		
		return cuba;
	}

	@Override
	public Cuba get(Long id) {
		return cubaDAO.get(id);
	}

	@Override
	public void update(Long id, CubaCommand cubaCommand) {
		Cuba cuba = new Cuba();
		cuba.setCapacity(Long.valueOf(cubaCommand.getCapacity()));
		cuba.setDescription(cubaCommand.getDescription());

		cubaDAO.update(id, cuba);	
	}

	@Override
	public void delete(Long id) {
		cubaDAO.delete(id);
	}

	@Override
	public List<Cuba> getAllfactoryCuba() {
		return cubaDAO.getAllFactoryCuba();
	}

	@Override
	public void setLikeDirty(Long id) {
		cubaDAO.setLikeDirty(id);
	}

	@Override
	public void setLikeClean(Long id) {
		cubaDAO.setLikeClean(id);
	}

	@Override
	public void setActualIdLoteCounter(Long idLoteCounter, Long idCuba) {
		cubaDAO.setActualIdLoteCounter(idLoteCounter, idCuba);
	}

	@Override
	public void detachmentActualIdLoteCounterForWork(Long idCuba) {
		cubaDAO.detachmentActualIdLoteCounterForWork(idCuba);
	}

	@Override
	public void setLikeBloqued(Long idCuba) {
		cubaDAO.setLikeBloqued(idCuba);
	}

	@Override
	public void setLikeNotBloqued(Long idCuba) {
		cubaDAO.setLikeNotBloqued(idCuba);		
	}

	@Override
	public Cuba getCubaByLoteCounter(Long id) throws CubaIsWithoutIdLoteCounterException {
		
		try
		{
			Cuba cuba = cubaDAO.getCubaByLoteCounter(id);
			return cuba;
		}
		catch( CubaIsWithoutIdLoteCounterException e){
			throw new CubaIsWithoutIdLoteCounterException();
		}
		
		/*if(cuba == null){
		   
		}else{
			return cuba;
		}*/
	}
	
	
}