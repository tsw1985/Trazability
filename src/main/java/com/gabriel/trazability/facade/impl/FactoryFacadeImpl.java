package com.gabriel.trazability.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.FactoryDAO;
import com.gabriel.trazability.command.FactoryCommand;
import com.gabriel.trazability.facade.FactoryFacade;
import com.gabriel.trazability.model.Factory;

@Service
@Transactional
public class FactoryFacadeImpl implements FactoryFacade {

	@Autowired
	private FactoryDAO factoryDAO;
	
	public void create(FactoryCommand factoryCommand) {
		factoryDAO.create(commandToObject(factoryCommand));
	}

	private Factory commandToObject(FactoryCommand factoryCommand){

		Factory factory = new Factory();
		factory.setName(factoryCommand.getName());
		factory.setAddress(factoryCommand.getAddress());
		factory.setCif(factoryCommand.getCif());
		factory.setPhone(factoryCommand.getPhone());
		return factory;
	}

	public Factory getFactoryById(Long id) {
		return factoryDAO.getFactoryById(id);
	}
}
