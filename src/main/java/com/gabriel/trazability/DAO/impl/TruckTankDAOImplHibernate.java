package com.gabriel.trazability.DAO.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.TruckTankDAO;
import com.gabriel.trazability.model.TruckTank;

@Repository
public class TruckTankDAOImplHibernate implements TruckTankDAO {

	@Autowired SessionFactory session;
	
	@Override
	public void create(TruckTank truckTank) {
		session.getCurrentSession().save(truckTank);
	}

	@Override
	public void update(Long id, TruckTank truckTankUpdated) {
		TruckTank truckTank = (TruckTank)session.getCurrentSession().get(TruckTank.class, id);
		truckTank.setDescription(truckTankUpdated.getDescription());
		truckTank.setCapacity(truckTankUpdated.getCapacity());
	}

	@Override
	public void delete(Long id) {
		TruckTank truckTank = (TruckTank)session.getCurrentSession().get(TruckTank.class, id);
		truckTank.setDeletedTruckTank("1");
	}

	@Override
	public List<TruckTank> getAllTruckTank() {
		Query query = session.getCurrentSession().createQuery("from TruckTank where deletedTruckTank =:state order by description")
		.setString("state", "0");

		List<TruckTank> list = query.list();
		
		return list;
	}
	
	@Override
	public Long getTotalLiters(Long id) {
		
			return null;
	}


	@Override
	public TruckTank get(Long id) {
		return (TruckTank)session.getCurrentSession().get(TruckTank.class, id);
	}

	@Override
	public String getStatusAboutClean(Long id) {
		return ((TruckTank)session.getCurrentSession().get(TruckTank.class, id)).getClean();
	}

	@Override
	public void setLikeDirty(Long id) {
		TruckTank truckTank = (TruckTank)session.getCurrentSession().get(TruckTank.class, id);
		truckTank.setClean("dirty");
	}

	@Override
	public void setLikeClean(Long id) {
		TruckTank truckTank = (TruckTank)session.getCurrentSession().get(TruckTank.class, id);
		truckTank.setClean("clean");
	}

	@Override
	public void incrementNumberFilled(Long id) {
		TruckTank truckTank = (TruckTank)session.getCurrentSession().get(TruckTank.class, id);
		Long actualNumber = truckTank.getIdFilled();
		truckTank.setIdFilled(actualNumber+1);
	}

	@Override
	public void decrementNumberFilled(Long id) {
		TruckTank truckTank = (TruckTank)session.getCurrentSession().get(TruckTank.class, id);
		Long actualNumber = truckTank.getIdFilled();
		truckTank.setIdFilled(actualNumber-1);
		
	}

	@Override
	public Long getActualNumberFilled(Long id) {
		return ((TruckTank)session.getCurrentSession().get(TruckTank.class, id)).getIdFilled();
	}

	@Override
	public void addLiters(Long id, Long liters) {
		TruckTank truckTank = (TruckTank)session.getCurrentSession().get(TruckTank.class, id);
		Long actualCapacity = truckTank.getActualCapacity();
		truckTank.setActualCapacity(actualCapacity + liters);
	}

	@Override
	public void subtractLiters(Long id, Long liters) {
		TruckTank truckTank = (TruckTank)session.getCurrentSession().get(TruckTank.class, id);
		Long actualCapacity = truckTank.getActualCapacity();
		truckTank.setActualCapacity(actualCapacity - liters);
	}

	@Override
	public Boolean isOverFlow(Long id, Long liters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isEmpty(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}