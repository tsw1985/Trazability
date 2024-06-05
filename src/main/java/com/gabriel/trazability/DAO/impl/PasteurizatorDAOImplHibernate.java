package com.gabriel.trazability.DAO.impl;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.PasteurizatorDAO;
import com.gabriel.trazability.model.Pasteurizator;

@Repository
public class PasteurizatorDAOImplHibernate implements PasteurizatorDAO{

	@Autowired
	private SessionFactory session;
	
	@Override
	public void create(Pasteurizator pasteurizator) {
		session.getCurrentSession().save(pasteurizator);
	}
	
	@Override
	public void update(Pasteurizator pasteurizatorUpdated, Long id) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class ,id);
		pasteurizator.setDescription(pasteurizatorUpdated.getDescription());
		pasteurizator.setCaudalSecond(pasteurizatorUpdated.getCaudalSecond());
		pasteurizator.setIp(pasteurizatorUpdated.getIp());
	}

	@Override
	public void delete(Long id) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class ,id);
		pasteurizator.setDeletedPasteurizator("DELETED");	
	}
	
	@Override
	public void turnON(Long id) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, id);
		pasteurizator.setRun("RUNNING");
	}

	@Override
	public void turnOFF(Long id) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, id);
		pasteurizator.setRun("NORUNNING");
	}

	@Override
	public String getActualTemperature(Long id) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, id);
		return pasteurizator.getActualTemperature();
	}

	@Override
	public void saveActualTemperature(Long id, String temperature) {
		Pasteurizator pasteurizator = new Pasteurizator(); 
		pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, id);
		pasteurizator.setActualTemperature(temperature);
	}

	@Override
	public String getRunningState(Long id) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, id);
		return pasteurizator.getRun();
	}

	@Override
	public void saveTurnOFFState(Long id) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, id);
		pasteurizator.setRun("MotorApagado");
	}

	@Override
	public void saveTurnONState(Long id) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, id);
		pasteurizator.setRun("MotorEncendido");
	}

	@Override
	public void savePasteurizingstate(Long id) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, id);
		pasteurizator.setPasteurizing("PASTEURIZANDO");
	}

	@Override
	public void saveNotPasteurizingState(Long id) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, id);
		pasteurizator.setPasteurizing("NOPASTEURIZANDO");
	}

	@Override
	public String getPasteurizingState(Long id) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, id);
		return pasteurizator.getPasteurizing();
	}

	@Override
	public Pasteurizator get(Long id) {
		return (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, id);
	}

	@Override
	public List<Pasteurizator> getAllPasteurizatorNoDeleted() {
		Query query = session.getCurrentSession().createQuery("from Pasteurizator where deletedPasteurizator =:state order by description")
		.setString("state","NODELETED");
		List<Pasteurizator> list = query.list();
		return list;
	}

	@Override
	public void setActualIdLoteCounterForWorkAndFactoryTank(Long idPasteurizator,	Long valueLoteCounter , Long idFactoryTank) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, idPasteurizator);
		//session.getCurrentSession().save(pasteurizator);
		pasteurizator.setActualIdLoteCounterForWork(valueLoteCounter);
		pasteurizator.setActualIdFactoryTankForWork(idFactoryTank);
		
	}

	@Override
	public void detachmentActualIdLoteCounterForWork(Long idPasteurizator) {
		Pasteurizator pasteurizator = (Pasteurizator)session.getCurrentSession().get(Pasteurizator.class, idPasteurizator);
		pasteurizator.setActualIdLoteCounterForWork(null);
	}
}