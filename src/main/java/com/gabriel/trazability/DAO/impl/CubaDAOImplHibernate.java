package com.gabriel.trazability.DAO.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.CubaDAO;
import com.gabriel.trazability.exceptions.CubaIsWithoutIdLoteCounterException;
import com.gabriel.trazability.model.Cuba;

@Repository
public class CubaDAOImplHibernate implements CubaDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void create(Cuba cuba) {
			session.getCurrentSession().save(cuba);
	}

	@Override
	public Cuba get(Long id) {
		return (Cuba)session.getCurrentSession().get(Cuba.class, id);
	}
	
	
	@Override
	public Cuba getCubaByLoteCounter(Long idLoteCounter) {
		Query query = session.getCurrentSession().createQuery("from Cuba where idLoteCounter =:status").setLong("status", idLoteCounter).setMaxResults(1);
		List<Cuba> list = query.list();
		
		if(list.size() > 0)
			return list.get(0);
		else
		{
			System.out.println("NO HAY CUBA");
			return null;
			//throw new CubaIsWithoutIdLoteCounterException();
		}
		
		/*if(list.size() == 0)
			
		else
			return list.get(0);
			*/
		
	}
	
	@Override
	public void setActualIdLoteCounter(Long idLoteCounter, Long idCuba) {
		Cuba cuba = (Cuba)session.getCurrentSession().get(Cuba.class, idCuba);
		cuba.setIdLoteCounter(idLoteCounter);
		
	}

	@Override
	public void update(Long id, Cuba cubaupdated) {
		Cuba cuba = (Cuba)session.getCurrentSession().get(Cuba.class, id);
		cuba.setActualCapacity(cubaupdated.getActualCapacity());
		cuba.setCapacity(cubaupdated.getCapacity());
		cuba.setDescription(cubaupdated.getDescription());
	}

	@Override
	public void delete(Long id) {
		Cuba cuba = (Cuba)session.getCurrentSession().get(Cuba.class, id);
		cuba.setDeletedCuba("1");
	}

	@Override
	public List<Cuba> getAllFactoryCuba() {
		Query query = session.getCurrentSession().createQuery("from Cuba where deletedCuba =:status")
	.setString("status", "0");
		List<Cuba> list = query.list();
		return list;
	}
	
	@Override
	public void detachmentActualIdLoteCounterForWork(Long idCuba) {
		Cuba cuba = (Cuba)session.getCurrentSession().get(Cuba.class, idCuba);
		cuba.setIdLoteCounter(null);
	}

	@Override
	public String getStatusAboutClean(Long id) {

		return null;
	}

	@Override
	public void setLikeDirty(Long id) {


	}

	@Override
	public void setLikeClean(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long getActualCapacity(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActualCapacity(Long id, Long liters) {
		// TODO Auto-generated method stub

	}

	@Override
	public void incrementNumberFilled(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void decrementNumberFilled(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long getActualNumberFilled(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLikeBloqued(Long idCuba) {
		Cuba cuba = (Cuba)session.getCurrentSession().get(Cuba.class, idCuba);
		cuba.setBloqued("BLOQUED");
		
	}

	@Override
	public void setLikeNotBloqued(Long idCuba) {
		Cuba cuba = (Cuba)session.getCurrentSession().get(Cuba.class, idCuba);
		cuba.setBloqued("NOBLOQUED");
		
	}






}
