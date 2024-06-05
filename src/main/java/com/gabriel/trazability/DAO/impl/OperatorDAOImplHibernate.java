package com.gabriel.trazability.DAO.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.OperatorDAO;
import com.gabriel.trazability.model.Operator;

@Repository
public class OperatorDAOImplHibernate implements OperatorDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void create(Operator operator) {
		session.getCurrentSession().save(operator);

	}

	@Override
	public void update(Long id, Operator operatorUpdated) {
		
		Operator operator = (Operator)session.getCurrentSession().get(Operator.class, id);
		operator.setNameOperator(operatorUpdated.getNameOperator());
		operator.setAddress(operatorUpdated.getAddress());
		operator.setCif(operatorUpdated.getCif());
		operator.setPhone(operatorUpdated.getPhone());
	}

	@Override
	public void delete(Long id) {
		Operator operator = (Operator)session.getCurrentSession().get(Operator.class, id);
		operator.setDeletedOperator("1");
	}

	@Override
	public Operator get(Long id) {
		Operator operator = (Operator)session.getCurrentSession().get(Operator.class, id);
		return operator;
	}

	@Override
	public List<Operator> getAllOperator() {
		Query query = session.getCurrentSession().createQuery("from Operator where deletedOperator =:state order by nameOperator")
		.setString("state","0");
		List<Operator> list = query.list();
		return list;
	}
}