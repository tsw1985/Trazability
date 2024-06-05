package com.gabriel.trazability.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.ProviderDAO;
import com.gabriel.trazability.command.ProviderCommand;
import com.gabriel.trazability.facade.ProviderFacade;
import com.gabriel.trazability.model.Provider;

@Service
@Transactional
public class ProviderFacadeImpl implements ProviderFacade {

	@Autowired
	private ProviderDAO providerDAO;
	
	@Override
	public void create(ProviderCommand providerCreateCommand) {
		providerDAO.create(commandToProvider(providerCreateCommand));
	}
	
	private Provider commandToProvider(ProviderCommand providerCommand)
	{
		Provider provider = new Provider();
		provider.setName(providerCommand.getName());
		provider.setCif(providerCommand.getCif());
		provider.setPhone(providerCommand.getPhone());
		provider.setAddress(providerCommand.getAddress());
		provider.setDeletedProvider("0");
		return provider;
	}

	@Override
	public List<Provider> getAllProvider() {
		return providerDAO.getAllProvider();
	}

	@Override
	public Provider getProvider(Long id) {
		return providerDAO.getProvider(id);
	}

	@Override
	public void update(ProviderCommand providerCommand) {
	
		Provider provider = new Provider();
		provider.setName(providerCommand.getName());
		provider.setCif(providerCommand.getCif());
		provider.setPhone(providerCommand.getPhone());
		provider.setAddress(providerCommand.getAddress());
	
		providerDAO.update(providerCommand.getId(), provider);
	}

	
	@Override
	public void delete(Long id) {
		providerDAO.delete(id);
		
	}
}
