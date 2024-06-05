package com.gabriel.trazability.DAO.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.IngredientDetailDAO;
import com.gabriel.trazability.model.IngredientDetail;

@Repository
public class IngredientDetailDAOImplHibernate implements IngredientDetailDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public IngredientDetail get(Long id) {
		return (IngredientDetail)session.getCurrentSession().get(IngredientDetail.class, id);
	}

}
