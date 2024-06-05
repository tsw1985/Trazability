package com.gabriel.trazability.DAO;

import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import com.gabriel.trazability.model.ChlorinePhSalmuera;
import com.gabriel.trazability.model.FactoryTank;
import com.gabriel.trazability.model.IngredientDetail;
import com.gabriel.trazability.model.Operator;
import com.gabriel.trazability.model.Pasteurization;
import com.gabriel.trazability.model.Pasteurizator;
import com.gabriel.trazability.model.sql.query.CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription;
import com.gabriel.trazability.model.sql.query.PasteurizatorData;

public interface DossierDAO {
	
	public ChlorinePhSalmuera getChlorinePhSalmueraFromIdLoteCounter(Long idLoteCounter);

	public List<CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription> setMilkProviderfromIdLoteCounter(Long idLoteCounter);
	
	public List<Operator> getOperatorInThisLote(Long idOperator);
	
	public List<PasteurizatorData> getPasteurizatorUsedInThisLote(Long idLoteCounter);
	
	//public List<ChlorinePhSalmuera> getChlorinePhSalmueraInThisLote(Long idLoteCounter);
	
	public Long getLitersUsedInThisLote(Long idLoteCounter);
	
	public List<FactoryTank> getFactoryTankUsedInThisLote(Long idLoteCounter);
	
	public List<Pasteurization> getPasteurizationDataInThisLote(Long idLoteCounter);
	
	public Time getTimeUsedForDoThisLote(Long idLoteCounter);
	
	public BigInteger getTotalKgInThisLote(Long idLoteCounter);
	
	public List<IngredientDetail>getIngredientsUsedInThisLote(Long idLoteCounter);
	
	
	
	
	
	
	
	
	
	
	

}
