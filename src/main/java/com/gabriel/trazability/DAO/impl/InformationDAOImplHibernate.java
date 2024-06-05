package com.gabriel.trazability.DAO.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.InformationDAO;
import com.gabriel.trazability.model.FactoryTank;

@Repository
public class InformationDAOImplHibernate implements InformationDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public String getProvidersFromLote(Long id) {

		//Query query = session.getCurrentSession().createQuery("select p.name,p.address,p.cif,p.phone from Provider inner join");
		//List<FactoryTank> list = query.list();
		//return list;
		
		
		return null;
	}

}
