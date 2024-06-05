package com.gabriel.trazability.DAO.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.MilkFilledDAO;
import com.gabriel.trazability.DAO.OperatorDAO;
import com.gabriel.trazability.DAO.ProviderDAO;
import com.gabriel.trazability.DAO.TruckTankDAO;
import com.gabriel.trazability.model.MilkFilled;

@Repository
public class MilkFilledDAOImplHibernate implements MilkFilledDAO {

	@Autowired
	private SessionFactory session;
	
	@Autowired
	private ProviderDAO providerDAO;
	
	@Autowired
	private TruckTankDAO truckTankDAO;
	
	@Autowired
	private OperatorDAO operatorDAO;
	
	@Override
	public void create(MilkFilled milkFilled) {
		session.getCurrentSession().save(milkFilled);
	}
	
	@Override
	public MilkFilled get(Long id) {
		return (MilkFilled)session.getCurrentSession().get(MilkFilled.class, id);
	}

	@Override
	public void update(Long id, MilkFilled milkFilledUpdated) {
		MilkFilled milkFilled = (MilkFilled)session.getCurrentSession().get(MilkFilled.class, id);
		session.getCurrentSession().save(milkFilled);
		
		milkFilled.setCorrection(milkFilledUpdated.getCorrection());
		milkFilled.setDisplay(milkFilledUpdated.getDisplay());
		//milkFilled.setFilledDate(milkFilledUpdated.getFilledDate());
		milkFilled.setIncidence(milkFilledUpdated.getIncidence());
		milkFilled.setOperator(milkFilledUpdated.getOperator());
		milkFilled.setProvider(milkFilledUpdated.getProvider());
		milkFilled.setProviderTank(milkFilledUpdated.getProviderTank());
		milkFilled.setTotalFilled(milkFilledUpdated.getTotalFilled());
		milkFilled.setTruckTank(milkFilledUpdated.getTruckTank());
		
		
	}

	@Override
	public void delete(Long id) {
		MilkFilled milkFilled = (MilkFilled)session.getCurrentSession().get(MilkFilled.class, id);
		session.getCurrentSession().save(milkFilled);
		milkFilled.setDeleteMilkFilled("DELETED");
		
	}


	@Override
	public List<MilkFilled> getTotalMilkFilledFromANumberWorkDayMilkFilled(Long number) {

		//Query query = session.getCurrentSession().createQuery("from MilkFilled where numberWorkDayMilkFilled =:number").setLong("number", number);
		
	//	String SQLquery = "select * from MilkFilled where numberWorkDayMilkFilled =" + number + " and (select id from CounterWorkingDayForMilkFilled where number='OPEN')";
		
		String SQLquery = "select * from MilkFilled where numberWorkDayMilkFilled =" + number +" and deleteMilkFilled='NODELETED'";
		List<MilkFilled> list = new ArrayList<MilkFilled>();
		
		try{
		
			Iterator i = session.getCurrentSession().createSQLQuery(SQLquery).list().iterator();
			//List<MilkFilled> list = new ArrayList<MilkFilled>();
			
			while(i.hasNext()){
				Object[] row = (Object[])i.next();
				MilkFilled milkFilled = new MilkFilled();
				
				milkFilled.setId(Long.valueOf(row[0].toString()));
				milkFilled.setCorrection(String.valueOf(row[1].toString()));
				milkFilled.setDisplay(String.valueOf(row[2]));
				milkFilled.setFilledDate((Timestamp)row[3]);
				milkFilled.setIncidence((String)row[4]);
				milkFilled.setNumberWorkDayMilkFilled(String.valueOf(row[5]));
				milkFilled.setProviderTank(String.valueOf(row[6]));
				milkFilled.setTotalFilled(Long.valueOf(row[7].toString()));
				
				milkFilled.setOperator(operatorDAO.get(Long.valueOf(row[8].toString())));
				milkFilled.setProvider(providerDAO.getProvider((Long.valueOf(row[9].toString()))));
				milkFilled.setTruckTank(truckTankDAO.get(Long.valueOf(row[10].toString())));
				
				list.add(milkFilled);
				
			}
			
			return list;
		}
		catch(Exception e){
			System.out.println("ERROR -> La hay varios dias de recogida abiertos: " + e.toString());
		}

		return list;

		
	}
	
	

	@Override
	public Long getTotalSumLitersMilkFilledByNumberWorkDayMilkFilled(Long number) {
		Query query = session.getCurrentSession().createQuery("select sum(totalFilled) from MilkFilled where numberWorkDayMilkFilled =:number").setLong("number", number).setMaxResults(1);
		List<Long>list = query.list();
		return list.get(0);
	}

	
	
	@Override
	public List<MilkFilled> getDataMilkFilledOpen() {
		
		/*String SQLquery = "select * from MilkFilled and (select id from CounterWorkingDayForMilkFilled where number='OPEN')";
		List<MilkFilled> list = new ArrayList<MilkFilled>();
		
		try{
		
			Iterator i = session.getCurrentSession().createSQLQuery(SQLquery).list().iterator();
			//List<MilkFilled> list = new ArrayList<MilkFilled>();
			
			while(i.hasNext()){
				Object[] row = (Object[])i.next();
				MilkFilled milkFilled = new MilkFilled();
				
				milkFilled.setId(Long.valueOf(row[0].toString()));
				milkFilled.setCorrection(String.valueOf(row[1]));
				milkFilled.setDisplay(String.valueOf(row[2]));
				milkFilled.setFilledDate((Timestamp)row[3]);
				milkFilled.setIncidence((String)row[4]);
				milkFilled.setNumberWorkDayMilkFilled(String.valueOf(row[5]));
				milkFilled.setProviderTank(String.valueOf(row[6]));
				milkFilled.setTotalFilled(Long.valueOf(row[7].toString()));
				
				milkFilled.setOperator(operatorDAO.get(Long.valueOf(row[8].toString())));
				milkFilled.setProvider(providerDAO.getProvider((Long.valueOf(row[9].toString()))));
				milkFilled.setTruckTank(truckTankDAO.get(Long.valueOf(row[10].toString())));
				
				list.add(milkFilled);
				
				return list;
				
			}
		}
		catch(Exception e){
			System.out.println("ERROR -> La hay varios dias de recogida abiertos: " + e.toString());
		}
*/
		return null;

	}

	
	
	
	
	

}
