package com.gabriel.trazability.abstractclassImpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.abstractclass.GenericDAO;


public abstract class GenericDAOImpl implements GenericDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void save(Object object) {
		session.getCurrentSession().save(object);

	}

	
	
	@Override
	public void prueba() {
		// TODO Auto-generated method stub

	}

}
