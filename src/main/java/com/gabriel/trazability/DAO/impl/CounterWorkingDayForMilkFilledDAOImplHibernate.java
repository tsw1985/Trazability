package com.gabriel.trazability.DAO.impl;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.CounterWorkingDayForMilkFilledDAO;
import com.gabriel.trazability.model.CounterWorkingDayForMilkFilled;

@Repository
public class CounterWorkingDayForMilkFilledDAOImplHibernate implements 	CounterWorkingDayForMilkFilledDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void increment() {

		CounterWorkingDayForMilkFilled counter = new CounterWorkingDayForMilkFilled();
		counter.setNumber("OPEN");
		session.getCurrentSession().save(counter);
	}

	
	@Override
	public String getLastCounter() {
		Query query = (Query)session.getCurrentSession()
		.createQuery("from CounterWorkingDayForMilkFilled where number='OPEN' order by id desc limit 1").setMaxResults(1);
		
		List<CounterWorkingDayForMilkFilled> list = query.list();
		return list.get(0).getId().toString();
	}


	@Override
	public CounterWorkingDayForMilkFilled get(Long id) {
		return (CounterWorkingDayForMilkFilled)session.getCurrentSession().get(CounterWorkingDayForMilkFilled.class, id);
	}


	@Override
	public void setLikeOpen(Long id) {
		CounterWorkingDayForMilkFilled counterWorkingDayForMilkFilled = (CounterWorkingDayForMilkFilled)session.getCurrentSession().get(CounterWorkingDayForMilkFilled.class, id);
		counterWorkingDayForMilkFilled.setNumber("OPEN");
		
	}


	@Override
	public void setLikeClose(Long id) {
		CounterWorkingDayForMilkFilled counterWorkingDayForMilkFilled = (CounterWorkingDayForMilkFilled)session.getCurrentSession().get(CounterWorkingDayForMilkFilled.class, id);
		counterWorkingDayForMilkFilled.setNumber("CLOSED");
		
	}

}
