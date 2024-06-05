package com.gabriel.trazability.DAO;

import java.util.List;

import com.gabriel.trazability.exceptions.CubaIsWithoutIdLoteCounterException;
import com.gabriel.trazability.model.Cuba;

public interface CubaDAO {
	
	public void create(Cuba cuba);
	
	public Cuba get(Long id);
	
	public Cuba getCubaByLoteCounter(Long id) throws CubaIsWithoutIdLoteCounterException;
	
	public void update(Long id, Cuba cuba);
	
	public void delete(Long id);
	
	public List<Cuba> getAllFactoryCuba();
	
	public String getStatusAboutClean(Long id);
	
	public void setLikeDirty(Long id);
	
	public void setLikeClean(Long id);
	
	public Long getActualCapacity(Long id);
	
	public void setActualCapacity(Long id, Long liters);

	public void incrementNumberFilled(Long id);
	
	public void decrementNumberFilled(Long id);
	
	public Long getActualNumberFilled(Long id);
	
	public void setActualIdLoteCounter(Long idLoteCounter, Long idCuba);
	
	public void detachmentActualIdLoteCounterForWork(Long idCuba);
	
	public void setLikeBloqued(Long idCuba);
	
	public void setLikeNotBloqued(Long idCuba);
}
 
