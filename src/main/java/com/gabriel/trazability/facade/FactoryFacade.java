package com.gabriel.trazability.facade;

import com.gabriel.trazability.command.FactoryCommand;
import com.gabriel.trazability.exceptions.FactoryNotFoundException;
import com.gabriel.trazability.model.Factory;

public interface FactoryFacade {

	public void create(FactoryCommand factoryCommand);
	
	public Factory getFactoryById(Long id) throws FactoryNotFoundException;
}
