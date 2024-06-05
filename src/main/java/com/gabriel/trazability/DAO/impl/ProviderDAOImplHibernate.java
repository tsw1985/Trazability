package com.gabriel.trazability.DAO.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gabriel.trazability.DAO.ProviderDAO;
import com.gabriel.trazability.abstractclassImpl.GenericDAOImpl;
import com.gabriel.trazability.model.Provider;

@Repository
public class ProviderDAOImplHibernate implements ProviderDAO {

	@Autowired
	private SessionFactory session;
	
	
	@Override
	public Provider getProvider(Long id) {
		
		return (Provider)session.getCurrentSession().get(Provider.class, id);
	}

	@Override
	public void create(Provider provider) {
	    
		session.getCurrentSession().save(provider);
		session.getCurrentSession().clear();
	
	}
 
	@Override
	public void update(Long id, Provider providerUpdated)
	{
		Provider provider = (Provider)session.getCurrentSession().get(Provider.class, id);
		provider.setName(providerUpdated.getName());
		provider.setAddress(providerUpdated.getAddress());
		provider.setCif(providerUpdated.getCif());
		provider.setPhone(providerUpdated.getPhone());
	}
	
	
	
	
	@Override
	public List<Provider> getAllProvider() {

		Query query = session.getCurrentSession().createQuery("from Provider where deletedProvider =:state order by name")
		.setString("state","0");
		List<Provider> list = query.list();
		//session.getCurrentSession().clear();
		return list;
	}

	@Override
	public void delete(Long id) {
		Provider provider = (Provider)session.getCurrentSession().get(Provider.class, id);
		provider.setDeletedProvider("1");
		//session.getCurrentSession().clear();

	}
}
