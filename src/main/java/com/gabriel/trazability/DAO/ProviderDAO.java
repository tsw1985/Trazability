package com.gabriel.trazability.DAO;

import java.util.List;

import com.gabriel.trazability.model.Provider;


public interface ProviderDAO {

		public void create(Provider provider);
	
		public void update(Long id, Provider provider);
		
		public void delete(Long id);
		
		public Provider getProvider(Long id);
		
		public List<Provider> getAllProvider();
}
