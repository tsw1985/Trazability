package com.gabriel.trazability.DAO.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.FactoryTankDAO;
import com.gabriel.trazability.model.FactoryTank;
import com.gabriel.trazability.model.MilkSentToFactoryTank;

@Repository
public class FactoryTankDAOImplHibernate implements FactoryTankDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void create(FactoryTank factoryTank) {
		session.getCurrentSession().save(factoryTank);
	}

	@Override
	public FactoryTank get(Long id) {
		return (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
	}

	@Override
	public void update(Long id, FactoryTank factoryTankUpdated) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		factoryTank.setCapacity(factoryTankUpdated.getCapacity());
		factoryTank.setDescription(factoryTankUpdated.getDescription());
	}

	@Override
	public void delete(Long id) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		factoryTank.setDeletedFactoryTank("1");
	}

	@Override
	public List<FactoryTank> getAllFactoryTank() {
		Query query = session.getCurrentSession().createQuery("from FactoryTank where deletedFactoryTank =:status").setString("status", "0");
		List<FactoryTank> list = query.list();
		return list;
	}

	@Override
	public String getStatusAboutClean(Long id) {
		return ((FactoryTank)session.getCurrentSession().get(FactoryTank.class, id)).getClean();
	}

	@Override
	public void setLikeDirty(Long id) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		factoryTank.setClean("dirty");
	}

	@Override
	public void setLikeClean(Long id) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		factoryTank.setClean("clean");
	}
	
	@Override
	public void setLikeSelectedActive(Long id) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		factoryTank.setSelectedActive("ACTIVE");
	}

	@Override
	public void setLikeNotSelectedActive(Long id) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		factoryTank.setSelectedActive("NOACTIVE");
	}

	@Override
	public void incrementNumberFilled(Long id) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		Long number = factoryTank.getIdFilled();
		factoryTank.setIdFilled(number + 1);
	}

	@Override
	public void decrementNumberFilled(Long id) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		Long number = factoryTank.getIdFilled();
		factoryTank.setIdFilled(number - 1);
	}

	@Override
	public Long getActualNumberFilled(Long id) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		return factoryTank.getIdFilled();
	}

	@Override
	public void setActualCapacity(Long id, Long litersAdded) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		Long actualCapacity = factoryTank.getActualCapacity();
		factoryTank.setActualCapacity(litersAdded + actualCapacity);
	}
	
	@Override
	public Long getActualCapacity(Long id) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		return factoryTank.getActualCapacity();
	}
	
	@Override
	public String getStateActiveSelectedFactoryTank(Long id) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		return factoryTank.getSelectedActive();
	}

	
	@Override
	public void addLitersFromTruckTankToFactoryTank(MilkSentToFactoryTank milkSentToFactoryTank) {
		session.getCurrentSession().save(milkSentToFactoryTank);
	}

	@Override
	public void subtractLiters(Long id, Long liters) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		session.getCurrentSession().save(factoryTank);
		Long actualCapacity = factoryTank.getActualCapacity();
		actualCapacity = actualCapacity - liters;
		factoryTank.setActualCapacity(actualCapacity);
	}
	
	@Override
	public void addLiters(Long id, Long liters) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		session.getCurrentSession().save(factoryTank);
		Long actualCapacity = factoryTank.getActualCapacity();
		actualCapacity = actualCapacity + liters;
		factoryTank.setActualCapacity(actualCapacity);
		
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

	@Override
	public Boolean isExist(Long id) {
		Long count = (Long)session.getCurrentSession().createQuery("select count(*) from FactoryTank where id=:id").setLong("id", id).uniqueResult();

		if( count > 0 )
			return true;
		else
			return false;
	}

	@Override
	public void setLikeBloqued(Long id) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		factoryTank.setBloqued("BLOQUED");
	}

	@Override
	public void setLikeNotBloqued(Long id) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, id);
		factoryTank.setBloqued("NOBLOQUED");
	}

	@Override
	public void setActualIdLoteCounterForWork(Long idLoteCounter , Long idFactoryTank) {
		FactoryTank factoryTank = (FactoryTank)session.getCurrentSession().get(FactoryTank.class, idFactoryTank);
		factoryTank.setActualLoteCounterForWork(idLoteCounter);
	}

	
}