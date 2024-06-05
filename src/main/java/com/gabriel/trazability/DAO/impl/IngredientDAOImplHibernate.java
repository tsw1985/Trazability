package com.gabriel.trazability.DAO.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.IngredientDAO;
import com.gabriel.trazability.model.Ingredient;
import com.gabriel.trazability.model.IngredientDetail;

@Repository
public class IngredientDAOImplHibernate implements IngredientDAO{

	@Autowired
	private SessionFactory session;
	
	@Override
	public void create(Ingredient ingredient) {
		session.getCurrentSession().save(ingredient);
	}

	@Override
	public void delete(Long id) {
		IngredientDetail ingredientDetail = (IngredientDetail)session.getCurrentSession().get(IngredientDetail.class, id);
		ingredientDetail.setConsumed("CONSUMED");
	}

	@Override
	public void update(Ingredient ingredientUpdated, Long id) {
		Ingredient ingredient = (Ingredient)session.getCurrentSession().get(Ingredient.class, id);
		ingredient.setDescription(ingredientUpdated.getDescription());
	}

	@Override
	public Ingredient get(Long id) {
		return (Ingredient)session.getCurrentSession().get(Ingredient.class, id);
	}

	@Override
	public List<Ingredient> getAllIngredient() {
  	    Query query = session.getCurrentSession().createQuery("from Ingredient where deletedIngredient =:status").setString("status", "0");
		List<Ingredient> list = query.list();
		return list;
	}

	@Override
	public void addIngredientDetail(IngredientDetail ingredientDetail) {
		session.getCurrentSession().save(ingredientDetail);
	}

	@Override
	public IngredientDetail getLastIngredientDetail(Long idIngredient) {

		Query query = (Query)session.getCurrentSession()
		.createQuery("from IngredientDetail where id=:idIngredient order by id desc limit 1").setParameter("idIngredient", idIngredient)
		.setMaxResults(1);
		List<IngredientDetail> list = query.list();
		return list.get(0);
		
	}
	
	@Override
	public List<IngredientDetail> getAllIngredientDetailOrderByIdDesc(){
		Query query = (Query)session.getCurrentSession().createQuery("from IngredientDetail where consumed =:status order by id desc").setParameter("status", "NOCONSUMED");
		List<IngredientDetail> list = query.list();
		return list;
		
	}

	@Override
	public void update(IngredientDetail ingredientDetailUpdated, Long id) {
		
		IngredientDetail ingredientDetail = (IngredientDetail)session.getCurrentSession().get(IngredientDetail.class, id);
		session.getCurrentSession().save(ingredientDetail);
		
		ingredientDetail.setNote(ingredientDetailUpdated.getNote());
		ingredientDetail.setBoughtDate(ingredientDetailUpdated.getBoughtDate());
		//ingredientDetail.setConsumed(ingredientDetailUpdated.getConsumed());
		ingredientDetail.setLote(ingredientDetailUpdated.getLote());
		
		
	}
	
}