package com.gabriel.trazability.facade;

import java.util.List;

import com.gabriel.trazability.command.ProviderCommand;
import com.gabriel.trazability.model.Provider;

public interface ProviderFacade  {

	public void create(ProviderCommand providerCommand);
	
	public void update(ProviderCommand providerCommand);
	
	public void delete(Long id);
	
	public Provider getProvider(Long id);
	
	public List<Provider> getAllProvider();
}
