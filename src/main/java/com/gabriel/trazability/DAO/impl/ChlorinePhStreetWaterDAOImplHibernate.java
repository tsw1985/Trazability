package com.gabriel.trazability.DAO.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.ChlorinePhStreetWaterDAO;
import com.gabriel.trazability.model.ChlorinePhStreetWater;

@Repository
public class ChlorinePhStreetWaterDAOImplHibernate implements ChlorinePhStreetWaterDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void create(ChlorinePhStreetWater chlorinePhStreetWater) {
			session.getCurrentSession().save(chlorinePhStreetWater);
	}

	@Override
	public ChlorinePhStreetWater getLastChlorinePh() {
			Query query = (Query)session.getCurrentSession()
			.createQuery("from ChlorinePhStreetWater order by id desc limit 1").setMaxResults(1);
			List<ChlorinePhStreetWater> list = query.list();
			return list.get(0);
	}

	@Override
	public void setPh(ChlorinePhStreetWater chlorinePhStreetWater) {
		session.getCurrentSession().save(chlorinePhStreetWater);
	}
}