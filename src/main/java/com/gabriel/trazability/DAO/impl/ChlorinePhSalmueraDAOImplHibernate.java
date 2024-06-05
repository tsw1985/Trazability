package com.gabriel.trazability.DAO.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.ChlorinePhSalmueraDAO;
import com.gabriel.trazability.model.ChlorinePhSalmuera;

@Repository
public class ChlorinePhSalmueraDAOImplHibernate implements ChlorinePhSalmueraDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void create(ChlorinePhSalmuera chlorinePhSalmuera) {
			session.getCurrentSession().save(chlorinePhSalmuera);
	}

	@Override
	public ChlorinePhSalmuera getLastSalmueraChlorinePh() {
		Query query = (Query)session.getCurrentSession()
				.createQuery("from ChlorinePhSalmuera order by id desc limit 1").setMaxResults(1);
				List<ChlorinePhSalmuera> list = query.list();
				return list.get(0);
	}
}