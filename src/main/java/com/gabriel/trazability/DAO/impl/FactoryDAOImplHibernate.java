package com.gabriel.trazability.DAO.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.FactoryDAO;
import com.gabriel.trazability.abstractclass.GenericDAO;
import com.gabriel.trazability.model.Factory;

@Repository
public class FactoryDAOImplHibernate implements FactoryDAO  {

	@Autowired
	private SessionFactory session;

	public void create(Factory factory) {
		session.getCurrentSession().save(factory);
	}
	
	public Factory getFactoryById(Long id) {
		return (Factory)session.getCurrentSession().get(Factory.class,id);
	}
}