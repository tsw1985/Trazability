package com.gabriel.trazability.facade;

import java.util.List;

import com.gabriel.trazability.command.CubaCommand;
import com.gabriel.trazability.exceptions.CubaIsWithoutIdLoteCounterException;
import com.gabriel.trazability.model.Cuba;

public interface CubaFacade {
	
	public void create(CubaCommand cubaCommand);
	
	public void setActualIdLoteCounter(Long idLoteCounter , Long idCuba);
	
	public Cuba get(Long id);
	
	public Cuba getCubaByLoteCounter(Long id) throws CubaIsWithoutIdLoteCounterException;
	
	public void update(Long id,CubaCommand cubaCommand);
	
	public void delete(Long id);
	
	public List<Cuba> getAllfactoryCuba();
	
	public void setLikeDirty(Long id);
	
	public void setLikeClean(Long id);
	
	public void detachmentActualIdLoteCounterForWork(Long idCuba);
	
	public void setLikeBloqued(Long idCuba);
	
	public void setLikeNotBloqued(Long idCuba);
	
}