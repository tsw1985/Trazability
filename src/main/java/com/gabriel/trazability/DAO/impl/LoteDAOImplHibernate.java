package com.gabriel.trazability.DAO.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.LoteDAO;
import com.gabriel.trazability.model.Lote;

@Repository
public class LoteDAOImplHibernate implements LoteDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void create(Lote lote) {
		session.getCurrentSession().save(lote);
	}
	
	@Override
	public Lote get(Long id)
	{
		return (Lote)session.getCurrentSession().get(Lote.class, id);
	}

	@Override
	public List<Lote> getDataFromLoteNotFinishedWhereLoteCounterIs(Long loteCounter) {
		 Query query = session.getCurrentSession().createQuery("from Lote where loteCounter=:loteCounter and deletedLote='NODELETED'")
		.setLong("loteCounter", loteCounter);
		List<Lote> list = query.list();
		return list ;
	}

	///createQuery("select lote.loteCounter from Lote lote where lote.finishedLote =:state group by lote.loteCounter")
	@Override
	public List<Lote> getLotesNotFinished() {
		
		 Query query = session.getCurrentSession().
		 createQuery("select l.loteCounter , c.description from Lote l inner join l.cuba as c where l.finishedLote=:state group by l.loteCounter")
		 .setString("state","N");
		 List<Lote> list = query.list();
		 return list ;
	}

	@Override
	public BigDecimal getLitersConsumedByLoteCounter(Long id) {
		
		String SQLquery = "select SUM(liters) from Lote where idLoteCounter = " + id + " and deletedLote='NODELETED'";
		Query totalLiters = session.getCurrentSession().createSQLQuery(SQLquery);
		List<BigDecimal> list = totalLiters.list();
		return list.get(0);
	}

	@Override
	public void delete(Long id) {
		Lote lote = (Lote)session.getCurrentSession().get(Lote.class, id);
		session.getCurrentSession().save(lote);
		lote.setDeletedLote("DELETED");
	}
}