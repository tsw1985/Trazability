package com.gabriel.trazability.DAO.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.LoteCounterServiceDAO;
import com.gabriel.trazability.model.ChlorinePhSalmuera;
import com.gabriel.trazability.model.ChlorinePhStreetWater;
import com.gabriel.trazability.model.LoteCounter;
import com.google.gson.Gson;

@Repository
public class LoteCounterDAOServiceImplHibernate implements	LoteCounterServiceDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void increment() {
		    LoteCounter loteCounter = new LoteCounter();
			session.getCurrentSession().save(loteCounter);
	}

	@Override
	public String getLastLoteCounter() {
		Query query = (Query)session.getCurrentSession()
		.createQuery("from LoteCounter order by id desc").setMaxResults(1);
		List<LoteCounter> list = query.list();
		return list.get(0).getId().toString();
	}
	
	@Override
	public LoteCounter get(Long id){
		return (LoteCounter)session.getCurrentSession().get(LoteCounter.class, id);
	}

	@Override
	public void create(LoteCounter loteCounter) {
		session.getCurrentSession().save(loteCounter);
	}

	@Override
	public List<LoteCounter> getLoteCounterNotFinished() {

		Query query = (Query)session.getCurrentSession()
		.createQuery("select Lote.idLoteCounter , Cuba.description from Lote inner join LoteCounter on Lote.idLoteCounter = LoteCounter.id " +
				" inner join Cuba on Lote.idCuba = Cuba.id where finished =:status").setParameter("status", "NOFINISHED");
		List<LoteCounter> list = query.list();
		return list;
	}

	@Override
	public String getLoteCounterNotFinishedByString() {
		Gson gson = new Gson();
		
		Query query = (Query)session.getCurrentSession()
				.createSQLQuery("select Cuba.description description, Lote.idLoteCounter  from LoteCounter inner join Lote on Lote.idLoteCounter = LoteCounter.id " +
						" inner join Cuba on Lote.idCuba = Cuba.id where LoteCounter.finished =:status").setParameter("status", "NOFINISHED");
				List list = query.list();
				return gson.toJson(list);
	}
	
	@Override
	public List getLoteCounterNotFinishedByList()
	{
		Query query = (Query)session.getCurrentSession()
				.createSQLQuery("select LoteCounter.id from LoteCounter " +
						" where LoteCounter.finished =:status order by id").setParameter("status", "NOFINISHED");
				List list = query.list();
				return list;
	}

	@Override
	public void update(String listIngredients, Long idLoteCounter) {

		LoteCounter loteCounter = (LoteCounter)session.getCurrentSession().get(LoteCounter.class, idLoteCounter);
		session.getCurrentSession().save(loteCounter);
		loteCounter.setIngredientsSelected("");
		loteCounter.setIngredientsSelected(listIngredients);

		
	}

	@Override
	public void setLoteCounterLikeFinished(Long idLoteCounter) {
		LoteCounter loteCounter = (LoteCounter)session.getCurrentSession().get(LoteCounter.class, idLoteCounter);
		session.getCurrentSession().save(loteCounter);
		loteCounter.setFinished("FINISHED");
		
	}

	@Override
	public String getIngredientUsedInLoteCounter(Long idLoteCounter) {
		LoteCounter loteCounter = (LoteCounter)session.getCurrentSession().get(LoteCounter.class, idLoteCounter);
		return loteCounter.getIngredientsSelected();
	}

	@Override
	public void addExtraInformationLote(Long idLoteCounter, Long kgcheese,String cuajedtime, String cuajedTemperature, 
		ChlorinePhSalmuera chlorinePhSalmuera , ChlorinePhStreetWater chlorinePhStreetWater ) {
		
		LoteCounter loteCounter = (LoteCounter)session.getCurrentSession().get(LoteCounter.class, idLoteCounter);
		session.getCurrentSession().save(loteCounter);
		
		loteCounter.setKgCheese(kgcheese);
		loteCounter.setTemperatureCuajo(cuajedTemperature);
		loteCounter.setTimeCuajed(cuajedtime);
		
		loteCounter.setChlorinePhSalmuera(chlorinePhSalmuera);
		loteCounter.setChlorinePhStreetWater(chlorinePhStreetWater);
		
	}

	@Override
	public void setPasteurizatorToLoteCounter(Long idPasteurizator ,Long valueLoteCounter) {
		LoteCounter loteCounter = (LoteCounter)session.getCurrentSession().get(LoteCounter.class, valueLoteCounter);
		session.getCurrentSession().save(loteCounter);
		loteCounter.setActualPasteurizatorAsigned(idPasteurizator);
	}

	@Override
	public void detachmentPasteurizatorInLoteCounter(Long idLoteCounter) {
		LoteCounter loteCounter = (LoteCounter)session.getCurrentSession().get(LoteCounter.class, idLoteCounter);
		session.getCurrentSession().save(loteCounter);
		loteCounter.setActualPasteurizatorAsigned(null);
		
	}
}