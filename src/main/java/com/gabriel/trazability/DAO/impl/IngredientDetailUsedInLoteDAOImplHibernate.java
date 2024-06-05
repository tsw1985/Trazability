package com.gabriel.trazability.DAO.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.IngredientDetailUsedInLoteDAO;
import com.gabriel.trazability.model.IngredientDetailUsedInLote;

@Repository
public class IngredientDetailUsedInLoteDAOImplHibernate implements IngredientDetailUsedInLoteDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void create(IngredientDetailUsedInLote ingredientDetailUsedInLote) {
		session.getCurrentSession().save(ingredientDetailUsedInLote);
	}
}
