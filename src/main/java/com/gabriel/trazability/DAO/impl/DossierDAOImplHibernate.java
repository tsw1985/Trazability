package com.gabriel.trazability.DAO.impl;

import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.DossierDAO;
import com.gabriel.trazability.DAO.IngredientDetailDAO;
import com.gabriel.trazability.model.ChlorinePhSalmuera;
import com.gabriel.trazability.model.FactoryTank;
import com.gabriel.trazability.model.IngredientDetail;
import com.gabriel.trazability.model.Operator;
import com.gabriel.trazability.model.Pasteurization;
import com.gabriel.trazability.model.sql.query.CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription;
import com.gabriel.trazability.model.sql.query.PasteurizatorData;

@Repository
public class DossierDAOImplHibernate implements DossierDAO {
	
	@Autowired
	private SessionFactory session;
	
	@Autowired
	private IngredientDetailDAO ingredientDetailDAO;

	@Override
	public List<CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription> setMilkProviderfromIdLoteCounter(Long idLoteCounter) {

		String SQLQuery = "select MilkFilled.correction, MilkFilled.display  , MilkFilled.filledDate , MilkFilled.incidence , Provider.name , " +
				" MilkFilled.providerTank, MilkFilled.totalFilled, Operator.nameOperator ,TruckTank.description" + 
				" from MilkFilled inner join Provider on MilkFilled.idProvider = Provider.id " +
				" inner join Operator on MilkFilled.idOperator = Operator.id " +
				" inner join TruckTank on MilkFilled.idTruckTank = TruckTank.id " +
				" where " + 
				" MilkFilled.numberWorkDayMilkFilled in  (select MilkSentToFactoryTank.idcounterworkingdaymilkfilled from MilkSentToFactoryTank where " +
				" MilkSentToFactoryTank.numberFilledFactoryTank in(select Lote.idFilledFactoryTank from Lote where Lote.idLoteCounter ="+idLoteCounter+" ) " +
				" and MilkSentToFactoryTank.idFactoryTank in (select Lote.idFactoryTank from Lote where Lote.idLoteCounter = "+idLoteCounter+")) " +
				" and " + 
				" MilkFilled.idTruckTank in (select MilkSentToFactoryTank.idTruckTank from MilkSentToFactoryTank where " +
				" MilkSentToFactoryTank.idcounterworkingdaymilkfilled in " + 
				" (select MilkSentToFactoryTank.idcounterworkingdaymilkfilled from MilkSentToFactoryTank where " +
				" MilkSentToFactoryTank.numberFilledFactoryTank in (select Lote.idFilledFactoryTank from Lote where Lote.idLoteCounter ="+idLoteCounter+") " +
				" and MilkSentToFactoryTank.idFactoryTank in (select Lote.idFactoryTank from Lote where Lote.idLoteCounter ="+idLoteCounter+")) " + 
				" and MilkSentToFactoryTank.idFactoryTank in (select Lote.idFactoryTank from Lote where Lote.idLoteCounter ="+idLoteCounter+"))"; 
		
				List<CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription> list = new ArrayList();
				Iterator i = session.getCurrentSession().createSQLQuery(SQLQuery).list().iterator();
				
				while(i.hasNext()){
				
					Object[] row = (Object[])i.next();
					
					String correction = (String)row[0];
					String display = (String)row[1];
					Timestamp filledDate = (Timestamp)row[2];
					String incidence = (String)row[3];
					String providerName = (String)row[4];
					String providerTank = (String)row[5];
					BigInteger totalFilled = (BigInteger)row[6];
					String operatorName = (String)row[7];
					String truckTankName = (String)row[8];
					
					CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription data = new CorrectionDisplayFilleddateIncidenceNameProvidertankTotalfilledOperatorNameDescription();
					data.setCorrection(correction);
					data.setDisplay(display);
					data.setFilledDate(filledDate.toString());
					data.setIncidence(incidence);
					data.setProviderName(providerName);
					data.setProviderTank(providerTank);
					data.setTotalFilled(String.valueOf(totalFilled));
					data.setOperatorName(operatorName);
					data.setTruckTankName(truckTankName);

					list.add(data);
				}
				
				return list;
	}
	

	@Override
	public ChlorinePhSalmuera getChlorinePhSalmueraFromIdLoteCounter(Long idLoteCounter) {

		String SQLquery = "select chlorine,ph,dateReaded from LoteCounter inner join ChlorinePhSalmuera " +
				"on ChlorinePhSalmuera.id = LoteCounter.idChlorinePhSalmuera where LoteCounter.id =" + idLoteCounter ;

		Iterator iterator =  session.getCurrentSession().createSQLQuery(SQLquery).list().iterator();
		
		ChlorinePhSalmuera cl = new ChlorinePhSalmuera();
		
		while(iterator.hasNext()){
			Object[] row = (Object[])iterator.next();
			
			Double chlorine = (Double)row[0];
			Double ph       = (Double)row[1];
			Timestamp date     = (Timestamp)row[2];
			
			cl.setChlorine(chlorine);
			cl.setPh(ph);
			cl.setDateReaded(date);
		}
		
		return cl;
	}
	
	
	@Override
	public List<PasteurizatorData> getPasteurizatorUsedInThisLote(Long idLoteCounter) {
		
		String SQLquery = "select Pasteurizator.description from Pasteurizator " +
				          "inner join Lote on Pasteurizator.id = Lote.idPasteurizator where Lote.idLoteCounter = " + idLoteCounter;
	
		List<PasteurizatorData> list = new ArrayList<PasteurizatorData>();
		Iterator i = session.getCurrentSession().createSQLQuery(SQLquery).list().iterator();
		
				
		while(i.hasNext()){
		
			String description = i.next().toString();
			
			PasteurizatorData pasteurizatorData = new PasteurizatorData();
			pasteurizatorData.setDescription(description);
			list.add(pasteurizatorData);
		}
		
		return list;
	}


	@Override
	public List<Operator> getOperatorInThisLote(Long idLoteCounter) {
		
		String SQLquery = "select Operator.nameOperator from Operator " +  
		" inner join Lote on Operator.id = Lote.idOperator " +
		" where Lote.idLoteCounter = "+ idLoteCounter +" group by Operator.nameOperator;";
		
		List<Operator> list = new ArrayList<Operator>();
		Iterator i = session.getCurrentSession().createSQLQuery(SQLquery).list().iterator();
		
		while(i.hasNext()){
			
			String nameOperator = i.next().toString();
			
			Operator operator  = new Operator();
			operator.setNameOperator(nameOperator);
			
			list.add(operator);
		}
		
		return list;
	}


	@Override
	public Long getLitersUsedInThisLote(Long idLoteCounter) {
		// 
		String SQLquery = "select litersConsumed from LoteCounter where id ="+idLoteCounter;
		
		List list = new ArrayList();
		Iterator i = session.getCurrentSession().createSQLQuery(SQLquery).list().iterator();
		
		Long litersNumber = 0L;
		
		while(i.hasNext()){
			
			String liters = i.next().toString();
			litersNumber = Long.valueOf(liters);
		}
		
		
		return litersNumber;
	}


	@Override
	public List<FactoryTank> getFactoryTankUsedInThisLote(Long idLoteCounter) {
		
		String SQLquery = "select FactoryTank.description from FactoryTank " + 
						  " inner join Lote on Lote.idFactoryTank = FactoryTank.id where Lote.idLoteCounter ="+idLoteCounter  +
						  " group by FactoryTank.description;";
		
		List<FactoryTank> list = new ArrayList<FactoryTank>(); 
		Iterator i = session.getCurrentSession().createSQLQuery(SQLquery).list().iterator();
		
		while(i.hasNext()){
			
			String factoryTankName = i.next().toString();
			FactoryTank factoryTank = new FactoryTank();
			factoryTank.setDescription(factoryTankName);
			
			list.add(factoryTank);
		}
		
		return list;
	}


	@Override
	public List<Pasteurization> getPasteurizationDataInThisLote(Long idLoteCounter) {
		// select temperature,idLoteCounter,date from Pasteurization where idLoteCounter='30';
		String SQLquery = "select temperature,idLoteCounter,date from Pasteurization where idLoteCounter=" + idLoteCounter;
		
		List<Pasteurization> list = new ArrayList<Pasteurization>();
		Iterator i= session.getCurrentSession().createSQLQuery(SQLquery).list().iterator();
		
		while(i.hasNext()){
			Object[] row = (Object[])i.next();
			Double temperature = (Double)row[0];
			
			Pasteurization pasteurization = new Pasteurization();
			pasteurization.setTemperature(temperature);
			list.add(pasteurization);
			
		}
		
		return list;
	}



	@Override
	public Time getTimeUsedForDoThisLote(Long idLoteCounter) {
		
		String SQLquery = "select TIMEDIFF((select date from Pasteurization where idLoteCounter ='30' order by id desc limit 1) , " + 
					      " (select date from Pasteurization where idLoteCounter ="+idLoteCounter +" order by id asc limit 1)) as 'TIEMPO';";
		
		Iterator i = session.getCurrentSession().createSQLQuery(SQLquery).list().iterator();
		
		Time time = null;
		
		while(i.hasNext()){
			
			time = (Time)i.next();
		}
		
		return time;
	}



	@Override
	public BigInteger getTotalKgInThisLote(Long idLoteCounter) {
		
		String SQLquery = "select kgCheese from LoteCounter where id ="+ idLoteCounter;
		
		Iterator i = session.getCurrentSession().createSQLQuery(SQLquery).list().iterator();
		
		BigInteger kg = null;
		
		while(i.hasNext()){
			kg = (BigInteger)i.next();
		}
		
		return kg;
	}



	@Override
	public List<IngredientDetail> getIngredientsUsedInThisLote(Long idLoteCounter) {
		
		String SQLquery = "select ingredientsSelected from LoteCounter where id="+idLoteCounter;
		
		Iterator i = session.getCurrentSession().createSQLQuery(SQLquery).list().iterator();
		
		String listIngredient = "";
		
		while(i.hasNext())
			listIngredient = (String)i.next();
		
		String[] ingredientInArray = listIngredient.split("-");
		
		List<IngredientDetail> ingredientsDetailList = new ArrayList<IngredientDetail>();
		
		for(int index =0 ; index < ingredientInArray.length ; index++)
			ingredientsDetailList.add(ingredientDetailDAO.get(Long.valueOf(ingredientInArray[index])));

		
		return ingredientsDetailList;
	}
	
	/*
	@Override
	public List<ChlorinePhSalmuera> getChlorinePhSalmueraInThisLote(
			Long idLoteCounter) {
		// TODO Auto-generated method stub
		return null;
	}*/
	
}