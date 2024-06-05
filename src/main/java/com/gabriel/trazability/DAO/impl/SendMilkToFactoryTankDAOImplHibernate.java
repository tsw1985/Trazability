package com.gabriel.trazability.DAO.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.FactoryTankDAO;
import com.gabriel.trazability.DAO.SendMilkToFactoryTankDAO;
import com.gabriel.trazability.model.MilkSentToFactoryTank;

@Repository
public class SendMilkToFactoryTankDAOImplHibernate implements SendMilkToFactoryTankDAO {
	
	@Autowired
	private SessionFactory session;
	
	@Autowired
	private FactoryTankDAO factoryTankDAO;

	@Override
	public MilkSentToFactoryTank get(Long id) {
		return (MilkSentToFactoryTank)session.getCurrentSession().get(MilkSentToFactoryTank.class, id);
	}

	@Override
	public void update(Long id,MilkSentToFactoryTank milkSentToFactoryTankUpdated) {
		MilkSentToFactoryTank milkSent = (MilkSentToFactoryTank)session.getCurrentSession().get(MilkSentToFactoryTank.class, id);
		session.getCurrentSession().save(milkSent);
		
		milkSent.setDateTimeMilkSentToFactoryTank(milkSentToFactoryTankUpdated.getDateTimeMilkSentToFactoryTank());
		milkSent.setTruckTank(milkSentToFactoryTankUpdated.getTruckTank());
		milkSent.setFactoryTank(milkSentToFactoryTankUpdated.getFactoryTank());
		milkSent.setLiters(milkSentToFactoryTankUpdated.getLiters());
	}

	@Override
	public void delete(Long id) {
		
		MilkSentToFactoryTank milkSent = (MilkSentToFactoryTank)session.getCurrentSession().get(MilkSentToFactoryTank.class, id);
		session.getCurrentSession().save(milkSent);
		milkSent.setDeletedMilkSentToFactoryTank("DELETED");
		
	}

	@Override
	public List<MilkSentToFactoryTank> listAllDataMilkSentToFactoryTankByWorkingDay(Long id) {

		Query query = session.getCurrentSession().createQuery("from MilkSentToFactoryTank where deletedMilkSentToFactoryTank =:state " +
				" and idcounterworkingdaymilkfilled =:day order by idcounterworkingdaymilkfilled desc")
				.setString("state","NODELETED").setLong("day", id);
		
		List<MilkSentToFactoryTank> list = query.list();
		
		return list;
	}

	
	@Override
	public void setLikeConfirmatedMilkSentToFactoryTankByWorkingDay(Long id) {
		
		String SQLquery = "from MilkSentToFactoryTank where idcounterworkingdaymilkfilled ='"+id+"' and deletedMilkSentToFactoryTank='NODELETED'";
		
		Query query = session.getCurrentSession().createQuery(SQLquery);
		List<MilkSentToFactoryTank> list = query.list();
		
		for( int i = 0; i < list.size() ; i++ ){
			session.getCurrentSession().save(list.get(i));
			list.get(i).setConfirmated("CONFIRMATED");
		}
	}
}



















